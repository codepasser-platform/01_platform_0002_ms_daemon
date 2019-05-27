package org.codepasser.base.model.entity.builder;

import java.util.Date;
import org.codepasser.base.model.entity.User;
import org.codepasser.common.model.entity.inner.UserStatus;
import org.codepasser.common.model.entity.inner.UserType;

/**
 * UserBuilder.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public final class UserBuilder {

  private String username;
  private String password;
  private String phoneNumber;
  private String email;
  private UserType type;
  private Date registrationTime;

  public static UserBuilder userBuilder() {
    return new UserBuilder();
  }

  public UserBuilder username(String username) {
    this.username = username;
    return this;
  }

  public UserBuilder email(String email) {
    this.email = email;
    return this;
  }

  public UserBuilder phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public UserBuilder password(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder type(UserType type) {
    this.type = type;
    return this;
  }

  public UserBuilder registrationTime(Date registrationTime) {
    this.registrationTime = registrationTime;
    return this;
  }

  public User build(UserStatus userStatus) {
    User user = new User();
    user.setUsername(this.username);
    user.setEmail(this.email);
    user.setPhone(this.phoneNumber);
    user.setPassword(this.password);
    user.setType(this.type);
    user.setCreateTime(this.registrationTime);
    user.addStatus(userStatus);
    return user;
  }
}
