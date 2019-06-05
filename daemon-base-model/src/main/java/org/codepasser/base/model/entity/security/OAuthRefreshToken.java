package org.codepasser.base.model.entity.security;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * OAuthRefreshToken.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "oauth_refresh_token")
public class OAuthRefreshToken implements Serializable {

  private static final long serialVersionUID = -4315398592437539239L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "token_id", length = 256)
  private String tokenId;

  @Lob
  @Column(name = "token", columnDefinition = "BLOB")
  private byte[] token;

  @Lob
  @Column(name = "authentication", columnDefinition = "BLOB")
  private byte[] authentication;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public byte[] getToken() {
    return token;
  }

  public void setToken(byte[] token) {
    this.token = token;
  }

  public byte[] getAuthentication() {
    return authentication;
  }

  public void setAuthentication(byte[] authentication) {
    this.authentication = authentication;
  }
}
