package org.codepasser.base.model.entity;

import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.codepasser.base.model.entity.converter.MapConverter;
import org.codepasser.common.model.entity.Base;
import org.codepasser.common.model.entity.inner.UserProvider;

/**
 * UserExternal.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "sys_user_external")
public class UserExternal extends Base {

  private static final long serialVersionUID = -8190461950330588469L;

  @Enumerated(EnumType.STRING)
  @Column(name = "provider", nullable = false, updatable = false)
  private UserProvider provider;

  @Column(name = "external_user_id", nullable = false, updatable = false)
  private String externalUserId;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "avatar_url")
  private String avatar;

  @Convert(converter = MapConverter.class)
  @Column(name = "details", columnDefinition = "JSON")
  private Map<String, String> details;

  @Column(name = "registration_time")
  private Date registrationTime;

  /** 绑定用户ID. */
  @Column(name = "user_id", nullable = false)
  private Long userId;

  public UserProvider getProvider() {
    return provider;
  }

  public void setProvider(UserProvider provider) {
    this.provider = provider;
  }

  public String getExternalUserId() {
    return externalUserId;
  }

  public void setExternalUserId(String externalUserId) {
    this.externalUserId = externalUserId;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Map<String, String> getDetails() {
    return details;
  }

  public void setDetails(Map<String, String> details) {
    this.details = details;
  }

  public Date getRegistrationTime() {
    return registrationTime;
  }

  public void setRegistrationTime(Date registrationTime) {
    this.registrationTime = registrationTime;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
