package org.codepasser.common.model.security;

/**
 * UserTokenSession.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/1 : base version.
 */
public class UserTokenSession extends UserSession {

  private static final long serialVersionUID = -3453859664092070569L;
  private String token;

  public UserTokenSession(Authority.Session session) {
    super(session);
  }

  public UserTokenSession(Authority.Session session, String token) {
    super(session);
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
