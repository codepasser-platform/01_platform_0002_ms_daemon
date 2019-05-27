package org.codepasser.base.model.entity;

import static javax.persistence.EnumType.STRING;
import static org.codepasser.common.model.RegexPattern.REGEX_MAIL;
import static org.codepasser.common.model.RegexPattern.REGEX_PHONE;
import static org.codepasser.common.model.RegexPattern.REGEX_USER_NAME;

import java.util.Date;
import java.util.EnumSet;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.codepasser.base.model.entity.converter.AuthoritySetConverter;
import org.codepasser.base.model.entity.converter.UserStatusSetConverter;
import org.codepasser.base.model.validation.UserIdentifier;
import org.codepasser.common.model.entity.Base;
import org.codepasser.common.model.entity.inner.LockType;
import org.codepasser.common.model.entity.inner.UserStatus;
import org.codepasser.common.model.entity.inner.UserType;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.validation.Group;

/**
 * User.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
// http://stackoverflow.com/questions/13012584/jpa-how-to-convert-a-native-query-result-set-to
// -pojo-class-collection
@Entity
@Table(name = "sys_user")
@UserIdentifier(groups = Group.Create.class)
public class User extends Base {

  private static final long serialVersionUID = 6285205193810581202L;

  /** 用户名. */
  @NotNull(groups = Group.Create.class)
  @Pattern(regexp = REGEX_USER_NAME)
  @Column(name = "username", nullable = false, length = 20)
  private String username;

  /** 密码. */
  @NotNull(groups = Group.Create.class)
  @Column(name = "password", nullable = false)
  private String password;

  /** 手机. */
  @Pattern(regexp = REGEX_PHONE)
  @Column(name = "phone", length = 20)
  private String phone;

  /** 邮箱. */
  @Pattern(regexp = REGEX_MAIL)
  @Column(name = "email", length = 120)
  private String email;

  /** 用户类型. */
  @NotNull(groups = Group.Create.class)
  @Enumerated(STRING)
  @Column(name = "type", nullable = false, length = 20)
  private UserType type;

  /** 用户状态. */
  @Convert(converter = UserStatusSetConverter.class)
  @Column(name = "user_statuses", columnDefinition = "JSON")
  private EnumSet<UserStatus> userStatuses;

  /** 是否锁定. */
  @NotNull(groups = Group.Create.class)
  @Column(name = "locked", nullable = false)
  private Boolean locked;

  /** 锁定类型. */
  @Column(name = "lock_type", length = 20)
  @Enumerated(STRING)
  private LockType lockType;

  /** 最后访问时间. */
  @Column(name = "last_access_time")
  private Date lastAccessTime;

  @Convert(converter = AuthoritySetConverter.class)
  @Column(name = "authorities", columnDefinition = "JSON")
  private EnumSet<Authority.Role> authorities;

  // obligate
  /** 组织ID. */
  @Column(name = "org_id")
  private Long orgId;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public void setLockType(@Nullable LockType lockType) {
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

  public void addStatus(UserStatus userStatus) {
    if (userStatuses == null) {
      userStatuses = EnumSet.noneOf(UserStatus.class);
    }
    userStatuses.add(userStatus);
  }

  public void addAuthority(Authority.Role authority) {
    if (authorities == null) {
      authorities = EnumSet.noneOf(Authority.Role.class);
    }
    authorities.add(authority);
  }
}
