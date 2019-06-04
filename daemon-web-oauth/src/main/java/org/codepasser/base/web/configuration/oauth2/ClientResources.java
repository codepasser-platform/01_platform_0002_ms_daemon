package org.codepasser.base.web.configuration.oauth2;

import javax.servlet.Filter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

public abstract class ClientResources {

  private OAuth2ProtectedResourceDetails client = new AuthorizationCodeResourceDetails();
  private OAuth2ResourceServerProperties resource = new OAuth2ResourceServerProperties();
  private OAuth2AttributeDefinitions definition = new OAuth2AttributeDefinitions();

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
