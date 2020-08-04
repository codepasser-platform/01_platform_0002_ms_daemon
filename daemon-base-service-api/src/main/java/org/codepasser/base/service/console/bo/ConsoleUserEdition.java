package org.codepasser.base.service.console.bo;

import org.codepasser.base.model.entity.User;
import org.codepasser.base.model.validation.UserIdentifier;
import org.codepasser.common.model.In;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.validation.Group;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.EnumSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static org.codepasser.common.model.RegexPattern.REGEX_MAIL;
import static org.codepasser.common.model.RegexPattern.REGEX_PHONE;

/**
 * ConsoleUserEdition.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@UserIdentifier(groups = Group.Create.class)
public class ConsoleUserEdition implements In<User> {

  private static final long serialVersionUID = 7668400498737901505L;
  @NotNull private Long id;

  /** 手机. */
  @Pattern(regexp = REGEX_PHONE)
  private String phone;

  /** 邮箱. */
  @Pattern(regexp = REGEX_MAIL)
  private String email;

  private EnumSet<Authority.Role> authorities;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    user.addAuthority(Authority.Role.USER);
    user.setUpdateTime(new Date());
    return user;
  }

  public User convert(Long userId) {
    User user = this.convert();
    user.setUpdateUser(userId);
    return user;
  }
}
