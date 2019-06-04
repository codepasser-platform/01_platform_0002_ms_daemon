package org.codepasser.common.model.security;

import java.io.Serializable;
import java.util.Map;

public class UserExternalBasic extends BindAccount implements Serializable {

  private static final long serialVersionUID = -6477503804881604047L;

  private Map<String, String> details;

  private UserBasic innerUser;

  public Map<String, String> getDetails() {
    return details;
  }

  public void setDetails(Map<String, String> details) {
    this.details = details;
  }

  public UserBasic getInnerUser() {
    return innerUser;
  }

  public void setInnerUser(UserBasic innerUser) {
    this.innerUser = innerUser;
  }
}
