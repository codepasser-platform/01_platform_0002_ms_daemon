package org.codepasser.base.service.console.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.codepasser.base.model.entity.User;
import org.codepasser.common.model.Out;
import org.codepasser.common.model.entity.inner.LockType;
import org.codepasser.common.model.entity.inner.UserStatus;
import org.codepasser.common.model.entity.inner.UserType;
import org.codepasser.common.model.security.Authority;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.EnumSet;

/**
 * UserDetail.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
public class UserDetail implements Out<UserDetail, User> {

  private static final long serialVersionUID = -6561632271725036518L;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;

  /** 用户名. */
  private String username;

  /** 手机. */
  private String phone;

  /** 邮箱. */
  private String email;

  /** 用户类型. */
  private UserType type;

  /** 用户状态. */
  private EnumSet<UserStatus> userStatuses;

  /** 是否锁定. */
  private Boolean locked;

  /** 锁定类型. */
  private LockType lockType;

  /** 最后访问时间. */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date lastAccessTime;

  private EnumSet<Authority.Role> authorities;

  // obligate
  /** 组织ID. */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long orgId;

  @Override
  public UserDetail from(User entity) {
    BeanUtils.copyProperties(entity, this);
    return this;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public EnumSet<UserStatus> getUserStatuses() {
    return userStatuses;
  }

  public void setUserStatuses(EnumSet<UserStatus> userStatuses) {
    this.userStatuses = userStatuses;
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

  public Long getOrgId() {
    return orgId;
  }

  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
}
