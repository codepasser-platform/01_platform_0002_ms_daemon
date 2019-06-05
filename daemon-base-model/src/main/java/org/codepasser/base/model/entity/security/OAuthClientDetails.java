package org.codepasser.base.model.entity.security;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.codepasser.common.utils.IdGenerator;

/**
 * OAuthClientDetails.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "oauth_client_details")
public class OAuthClientDetails implements Serializable {

  private static final long serialVersionUID = 527832411876221129L;

  @Id
  @Column(
      name = "client_id",
      unique = true,
      nullable = false,
      insertable = false,
      updatable = false,
      length = 256)
  private String clientId = IdGenerator.nextVarchar();

  @Column(name = "resource_ids", length = 256)
  private String resourceIds;

  @Column(name = "client_secret", length = 256)
  private String clientSecret;

  @Column(name = "scope", length = 256)
  private String scope;

  @Column(name = "authorized_grant_types", length = 256)
  private String authorizedGrantTypes;

  @Column(name = "web_server_redirect_uri", length = 256)
  private String webServerRedirectUri;

  @Column(name = "authorities", length = 256)
  private String authorities;

  @Column(name = "access_token_validity")
  private Integer accessTokenValidity;

  @Column(name = "refresh_token_validity")
  private Integer refreshTokenValidity;

  @Column(name = "additional_information", length = 4096)
  private String additionalInformation;

  @Column(name = "autoapprove", length = 256)
  private String autoApprove;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getResourceIds() {
    return resourceIds;
  }

  public void setResourceIds(String resourceIds) {
    this.resourceIds = resourceIds;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getAuthorizedGrantTypes() {
    return authorizedGrantTypes;
  }

  public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
    this.authorizedGrantTypes = authorizedGrantTypes;
  }

  public String getWebServerRedirectUri() {
    return webServerRedirectUri;
  }

  public void setWebServerRedirectUri(String webServerRedirectUri) {
    this.webServerRedirectUri = webServerRedirectUri;
  }

  public String getAuthorities() {
    return authorities;
  }

  public void setAuthorities(String authorities) {
    this.authorities = authorities;
  }

  public Integer getAccessTokenValidity() {
    return accessTokenValidity;
  }

  public void setAccessTokenValidity(Integer accessTokenValidity) {
    this.accessTokenValidity = accessTokenValidity;
  }

  public Integer getRefreshTokenValidity() {
    return refreshTokenValidity;
  }

  public void setRefreshTokenValidity(Integer refreshTokenValidity) {
    this.refreshTokenValidity = refreshTokenValidity;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public String getAutoApprove() {
    return autoApprove;
  }

  public void setAutoApprove(String autoApprove) {
    this.autoApprove = autoApprove;
  }
}
