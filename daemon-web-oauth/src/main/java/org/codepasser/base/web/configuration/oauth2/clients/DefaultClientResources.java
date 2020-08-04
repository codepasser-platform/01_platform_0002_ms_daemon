package org.codepasser.base.web.configuration.oauth2.clients;

import org.codepasser.base.web.configuration.oauth2.ClientResources;
import org.codepasser.base.web.configuration.oauth2.OAuth2RestTemplate;
import org.codepasser.base.web.configuration.oauth2.OAuth2UserInfoTokenServices;
import org.codepasser.common.model.entity.inner.UserProvider;
import org.codepasser.common.model.security.UserExternalBasic;
import org.codepasser.common.service.OAuth2Service;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.codepasser.common.web.configuration.security.handler.WebAuthenticationFailureHandler;
import org.codepasser.common.web.configuration.security.handler.WebAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

public class DefaultClientResources extends ClientResources {

  @Autowired
  @Qualifier("oauth2ClientContext")
  private OAuth2ClientContext oAuth2ClientContext;

  @Autowired private OAuth2Service oAuth2Service;

  @Autowired private WebAuthenticationSuccessHandler webAuthenticationSuccessHandler;

  @Override
  public OAuth2ClientAuthenticationProcessingFilter filter(String path) {
    OAuth2ClientAuthenticationProcessingFilter filter =
        new AdOAuth2ClientAuthenticationProcessingFilter(path);
    filter.setRestTemplate(new OAuth2RestTemplate(getClient(), oAuth2ClientContext));
    filter.setTokenServices(
        new OAuth2UserInfoTokenServices(
            getResource(), getClient(), getDefinition(), filter.restTemplate));
    filter.setAuthenticationSuccessHandler(webAuthenticationSuccessHandler);
    filter.setAuthenticationFailureHandler(authenticationFailureHandler().errorPage("/error"));
    return filter;
  }

  @Bean(name = "oauth2")
  public WebAuthenticationFailureHandler authenticationFailureHandler() {
    return new WebAuthenticationFailureHandler();
  }

  class AdOAuth2ClientAuthenticationProcessingFilter
      extends OAuth2ClientAuthenticationProcessingFilter {

    AdOAuth2ClientAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
      super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

      Map<String, Object> oAuthUserDetails = getOAuthUserDetails(request, response);
      String openId = getDefinition().getProperty(oAuthUserDetails, getDefinition().getId());

      UserExternalBasic externalUser = findOrCreateExternalUser(oAuthUserDetails, openId);
      // TODO why not response the Authentication of OAuth2Authentication directly ?
      return new UsernamePasswordAuthenticationToken(
          new UserIdentity(externalUser), "N/A", commaSeparatedStringToAuthorityList("ROLE_USER"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getOAuthUserDetails(
        HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
      OAuth2Authentication oAuthAuthentication =
          (OAuth2Authentication) super.attemptAuthentication(request, response);
      Authentication userAuthentication = oAuthAuthentication.getUserAuthentication();
      return (Map<String, Object>) userAuthentication.getDetails();
    }

    private UserExternalBasic findOrCreateExternalUser(
        Map<String, Object> oAuthUserDetails, String openId) {
      try {
        UserProvider provider = UserProvider.valueOf(getClient().getId());
        Optional<UserExternalBasic> externalUserOptional =
            oAuth2Service.findByExternalUserIdAndProvider(openId, provider);

        String nickname =
            getDefinition().getProperty(oAuthUserDetails, getDefinition().getNickname());
        String avatar = getDefinition().getProperty(oAuthUserDetails, getDefinition().getAvatar());

        Map<String, String> details = new HashMap<>();
        oAuthUserDetails.forEach(
            (key, value) -> {
              if (value != null) {
                details.put(key, value.toString());
              }
            });
        if (!externalUserOptional.isPresent()) {
          UserExternalBasic basicExternalUser = new UserExternalBasic();
          basicExternalUser.setExternalUserId(openId);
          basicExternalUser.setNickname(nickname);
          basicExternalUser.setAvatar(avatar);
          basicExternalUser.setProvider(provider);
          basicExternalUser.setDetails(details);
          return oAuth2Service.save(basicExternalUser);
        }

        UserExternalBasic externalUser = externalUserOptional.get();
        externalUser.setNickname(nickname);
        externalUser.setAvatar(avatar);
        externalUser.setDetails(details);
        oAuth2Service.update(externalUser);
        return externalUser;
      } catch (ServiceException e) {
        throw new AuthenticationServiceException("can not retrieve the external user entity", e);
      }
    }
  }
}
