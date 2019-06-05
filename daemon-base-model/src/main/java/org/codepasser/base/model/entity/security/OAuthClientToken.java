package org.codepasser.base.model.entity.security;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.codepasser.common.utils.IdGenerator;

/**
 * OAuthClientToken.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "oauth_client_token")
public class OAuthClientToken implements Serializable {

  private static final long serialVersionUID = 6636895341182308809L;

  @Id
  @Column(
      name = "authentication_id",
      unique = true,
      nullable = false,
      insertable = false,
      updatable = false,
      length = 256)
  private String authenticationId = IdGenerator.nextVarchar();

  @Column(name = "client_id", length = 256)
  private String clientId;

  @Column(name = "token_id", length = 256)
  private String tokenId;

  @Column(name = "user_name", length = 256)
  private String userName;

  @Lob
  @Column(name = "token", columnDefinition = "BLOB")
  private byte[] token;

  public String getAuthenticationId() {
    return authenticationId;
  }

  public void setAuthenticationId(String authenticationId) {
    this.authenticationId = authenticationId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public byte[] getToken() {
    return token;
  }

  public void setToken(byte[] token) {
    this.token = token;
  }
}
