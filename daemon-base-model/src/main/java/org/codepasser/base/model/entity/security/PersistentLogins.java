package org.codepasser.base.model.entity.security;

import org.codepasser.common.utils.IdGenerator;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PersistentLogins.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "persistent_logins")
public class PersistentLogins implements Serializable {

  private static final long serialVersionUID = 1765846193971973080L;

  @Id
  @Column(
      name = "series",
      unique = true,
      nullable = false,
      insertable = false,
      updatable = false,
      length = 64)
  private String series = IdGenerator.nextVarchar();

  @Column(name = "username", nullable = false, length = 64)
  private String username;

  @Column(name = "token", nullable = false, length = 64)
  private String token;

  @Column(name = "last_used", nullable = false, length = 64)
  private Date lastUsed;

  public String getSeries() {
    return series;
  }

  public void setSeries(String series) {
    this.series = series;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getLastUsed() {
    return lastUsed;
  }

  public void setLastUsed(Date lastUsed) {
    this.lastUsed = lastUsed;
  }
}
