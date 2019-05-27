package org.codepasser.base.service.sample.bo;

import static org.codepasser.common.model.RegexPattern.REGEX_MAIL;
import static org.codepasser.common.model.RegexPattern.REGEX_PHONE;
import static org.codepasser.common.model.RegexPattern.REGEX_USER_NAME;

import java.util.Date;
import java.util.EnumSet;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.codepasser.base.model.entity.User;
import org.codepasser.base.model.validation.UserIdentifier;
import org.codepasser.common.model.In;
import org.codepasser.common.model.entity.inner.UserStatus;
import org.codepasser.common.model.entity.inner.UserType;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.validation.Group;
import org.springframework.beans.BeanUtils;

/**
 * SampleUserCreation.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@UserIdentifier(groups = Group.Create.class)
public class SampleUserCreation implements In<User> {

  private static final long serialVersionUID = -8859156928959439004L;
  /** 用户名. */
  @NotNull(groups = Group.Create.class)
  @Pattern(regexp = REGEX_USER_NAME)
  private String username;

  /** 密码. */
  @NotNull(groups = Group.Create.class)
  private String password;

  /** 手机. */
  @Pattern(regexp = REGEX_PHONE)
  private String phone;

  /** 邮箱. */
  @Pattern(regexp = REGEX_MAIL)
  private String email;

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

  @Override
  public User convert() {
    User user = new User();
    BeanUtils.copyProperties(this, user);
    user.setOrgId(0L);
    user.setType(UserType.GENERATED);
    user.setLocked(false);
    user.setAuthorities(EnumSet.of(Authority.Role.USER));
    user.setUserStatuses(EnumSet.of(UserStatus.MANAGED));
    user.setCreateTime(new Date());
    user.setCreateUser(1L);
    return user;
  }
}
