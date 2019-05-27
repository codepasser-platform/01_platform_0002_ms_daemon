package org.codepasser.base.service.console.bo;

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
 * ConsoleUserCreation.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@UserIdentifier(groups = Group.Create.class)
public class ConsoleUserCreation implements In<User> {

  private static final long serialVersionUID = 606443699827375083L;
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

  private EnumSet<Authority.Role> authorities;

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

  public EnumSet<Authority.Role> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(EnumSet<Authority.Role> authorities) {
    this.authorities = authorities;
  }

  @Override
  public User convert() {
    User user = new User();
    BeanUtils.copyProperties(this, user);
    user.setOrgId(0L);
    user.setLocked(false);
    user.setType(UserType.GENERATED);
    user.setUserStatuses(EnumSet.of(UserStatus.MANAGED));
    user.addAuthority(Authority.Role.USER);
    user.setCreateTime(new Date());
    return user;
  }

  public User convert(Long userId) {
    User user = this.convert();
    user.setCreateUser(userId);
    return user;
  }
}
