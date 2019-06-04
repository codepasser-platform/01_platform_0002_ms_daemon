package org.codepasser.base.web.configuration.oauth2;

import java.util.Map;
import org.springframework.util.StringUtils;

public class OAuth2AttributeDefinitions {

  private String id;
  private String email;
  private String nickname;
  private String gender;
  private String phoneNumber;
  private String avatar;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getProperty(Map<String, Object> map, String key) {
    return StringUtils.isEmpty(key) ? null : String.valueOf(map.get(key));
  }
}
