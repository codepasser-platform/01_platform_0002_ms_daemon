package org.codepasser.base.web.configuration.oauth2;

public class OAuth2ResourceServerProperties {

  private String userIdUri;
  private String userInfoUri;

  String getUserIdUri() {
    return userIdUri;
  }

  public void setUserIdUri(String userIdUri) {
    this.userIdUri = userIdUri;
  }

  public String getUserInfoUri() {
    return userInfoUri;
  }

  public void setUserInfoUri(String userInfoUri) {
    this.userInfoUri = userInfoUri;
  }
}
