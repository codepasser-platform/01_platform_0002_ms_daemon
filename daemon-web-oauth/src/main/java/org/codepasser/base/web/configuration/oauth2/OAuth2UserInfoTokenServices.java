package org.codepasser.base.web.configuration.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;
import static org.springframework.security.oauth2.common.OAuth2AccessToken.BEARER_TYPE;

public class OAuth2UserInfoTokenServices implements ResourceServerTokenServices {

  private static final Logger logger = LoggerFactory.getLogger(OAuth2UserInfoTokenServices.class);
  protected OAuth2RestOperations restTemplate;
  private final OAuth2ProtectedResourceDetails client;
  private final OAuth2ResourceServerProperties resource;
  private final OAuth2AttributeDefinitions definition;

  public OAuth2UserInfoTokenServices(
      OAuth2ResourceServerProperties resource,
      OAuth2ProtectedResourceDetails client,
      OAuth2AttributeDefinitions definition,
      OAuth2RestOperations oAuth2RestTemplate) {
    this.resource = resource;
    this.client = client;
    this.definition = definition;
    this.restTemplate = oAuth2RestTemplate;
  }

  @Override
  public OAuth2Authentication loadAuthentication(String accessToken)
      throws AuthenticationException, InvalidTokenException {
    try {
      updateOAuth2ClientContext(accessToken, restTemplate);
      Authentication token = getAuthenticationToken(accessToken);
      return new OAuth2Authentication(
          new OAuth2Request(null, client.getId(), null, true, null, null, null, null, null), token);

    } catch (Exception ex) {
      logger.info("Could not fetch user : " + ex.getMessage());
      throw new InvalidTokenException(accessToken);
    }
  }

  @Override
  public OAuth2AccessToken readAccessToken(String accessToken) {
    throw new UnsupportedOperationException("Not supported: read access token");
  }

  private Authentication getAuthenticationToken(String accessToken) throws IOException {
    Map<String, Object> userProfileMap = retrieveUserProfileFromToken(accessToken);

    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(
            userProfileMap.get(definition.getId()),
            "N/A",
            commaSeparatedStringToAuthorityList("ROLE_USER"));
    token.setDetails(userProfileMap);
    return token;
  }

  private Map<String, Object> retrieveUserProfileFromToken(final String accessToken)
      throws IOException {
    String userId = getUserId(resource.getUserIdUri(), accessToken, definition.getId());

    String profileUrl = getProfileUrl(accessToken, userId, client.getClientId());
    Map<String, Object> userProfileMap = extractUserProfile(sendRequestForData(profileUrl));

    if (!StringUtils.isEmpty(userId)) {
      userProfileMap.put(definition.getId(), userId);
    }
    return userProfileMap;
  }

  public String getUserId(String userIdEndpointUrl, String accessToken, String principalKey)
      throws IOException {
    if (StringUtils.isEmpty(userIdEndpointUrl)) {
      return null;
    }

    String userInfoCallback = sendRequestForData(userIdEndpointUrl);
    return String.valueOf(
        new ObjectMapper().readValue(userInfoCallback, Map.class).get(principalKey));
  }

  private String getProfileUrl(String accessToken, String userId, String clientId) {
    return String.format(resource.getUserInfoUri(), userId, clientId, accessToken);
  }

  @SuppressWarnings("unchecked")
  protected Map<String, Object> extractUserProfile(String body) throws IOException {
    return new ObjectMapper().readValue(body, Map.class);
  }

  private void updateOAuth2ClientContext(String accessToken, OAuth2RestOperations restTemplate) {
    OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext().getAccessToken();
    if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
      DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
      token.setTokenType(BEARER_TYPE);
      restTemplate.getOAuth2ClientContext().setAccessToken(token);
    }
  }

  protected String sendRequestForData(String url) {
    return restTemplate.getForEntity(url, String.class).getBody();
  }
}
