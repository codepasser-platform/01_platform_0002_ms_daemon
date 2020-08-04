package org.codepasser.base.web.configuration.oauth2;

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import javax.servlet.Filter;

public abstract class ClientResources {

  private final OAuth2ProtectedResourceDetails client = new AuthorizationCodeResourceDetails();
  private final OAuth2ResourceServerProperties resource = new OAuth2ResourceServerProperties();
  private final OAuth2AttributeDefinitions definition = new OAuth2AttributeDefinitions();

  public OAuth2ProtectedResourceDetails getClient() {
    return client;
  }

  public OAuth2ResourceServerProperties getResource() {
    return resource;
  }

  public OAuth2AttributeDefinitions getDefinition() {
    return definition;
  }

  public abstract Filter filter(String path);
}
