package org.codepasser.common.model.security;

import java.io.Serializable;
import org.codepasser.common.model.entity.inner.UserProvider;

public class BindAccount implements Serializable {

  private static final long serialVersionUID = -5741645727003276661L;
  private String externalUserId;
  private String nickname;
  private String avatar;
  private UserProvider provider;

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

  public UserProvider getProvider() {
    return provider;
  }

  public void setProvider(UserProvider provider) {
    this.provider = provider;
  }
}
