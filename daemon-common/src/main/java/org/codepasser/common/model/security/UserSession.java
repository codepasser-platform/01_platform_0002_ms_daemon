package org.codepasser.common.model.security;

import java.io.Serializable;

public class UserSession implements Serializable {
  private static final long serialVersionUID = 823490372905869085L;

  private Authority.Session session;

  private UserSession() {
    // Prohibition of use
  }

  public UserSession(Authority.Session session) {
    this.session = session;
  }

  public Authority.Session getSession() {
    return session;
  }

  public void setSession(Authority.Session session) {
    this.session = session;
  }
}
