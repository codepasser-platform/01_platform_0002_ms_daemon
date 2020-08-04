package org.codepasser.base.model.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.codepasser.common.model.entity.inner.LockType;
import org.codepasser.common.model.entity.inner.UserType;
import org.codepasser.common.model.security.Authority;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

/**
 * UserItem.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
public class UserItem implements Serializable {

  private static final long serialVersionUID = -2936998992984755361L;

  @JsonFormat(shape = STRING)
  private Long id;

  @JsonFormat(shape = STRING)
  private Long createUser;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  @JsonFormat(shape = STRING)
  private Long updateUser;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date updateTime;

  @JsonFormat(shape = STRING)
  private Long orgId;

  /** 用户名. */
  private String username;

  /** 手机. */
  private String phone;

  /** 邮箱. */
  private String email;

  /** 用户类型. */
  private UserType type;

  /** 是否锁定. */
  private Boolean locked;

  /** 锁定类型. */
  private LockType lockType;

  /** 最后访问时间. */
  private Date lastAccessTime;

  private EnumSet<Authority.Role> authorities;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCreateUser() {
    return createUser;
  }

  public void setCreateUser(Long createUser) {
    this.createUser = createUser;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(Long updateUser) {
    this.updateUser = updateUser;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Long getOrgId() {
    return orgId;
  }

  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserType getType() {
    return type;
  }

  public void setType(UserType type) {
    this.type = type;
  }

  public Boolean getLocked() {
    return locked;
  }

  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  public LockType getLockType() {
    return lockType;
  }

  public void setLockType(LockType lockType) {
    this.lockType = lockType;
  }

  public Date getLastAccessTime() {
    return lastAccessTime;
  }

  public void setLastAccessTime(Date lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }

  public EnumSet<Authority.Role> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(EnumSet<Authority.Role> authorities) {
    this.authorities = authorities;
  }
}
