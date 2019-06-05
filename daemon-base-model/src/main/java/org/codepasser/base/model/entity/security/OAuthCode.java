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
 * OAuthCode.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "oauth_code")
public class OAuthCode implements Serializable {

  private static final long serialVersionUID = 710014599820546918L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "code", length = 256)
  private String code;

  @Lob
  @Column(name = "authentication", columnDefinition = "BLOB")
  private byte[] authentication;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public byte[] getAuthentication() {
    return authentication;
  }

  public void setAuthentication(byte[] authentication) {
    this.authentication = authentication;
  }
}
