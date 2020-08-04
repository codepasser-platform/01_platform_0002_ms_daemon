package org.codepasser.base.web.configuration.oauth2;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;

import java.util.Collections;
import java.util.List;

public class OAuth2RestTemplate
    extends org.springframework.security.oauth2.client.OAuth2RestTemplate {

  public OAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
    super(resource, context);
  }

  public void addMessageConverters(List<HttpMessageConverter<?>> additionalMessageConverters) {
    setAccessTokenProvider(
        new AccessTokenProviderChain(
            Collections.<AccessTokenProvider>singletonList(
                new AuthorizationCodeAccessTokenProvider() {
                  @Override
                  public void setMessageConverters(
                      List<HttpMessageConverter<?>> messageConverters) {
                    messageConverters.addAll(additionalMessageConverters);
                    super.setMessageConverters(messageConverters);
                  }
                })));
  }
}
