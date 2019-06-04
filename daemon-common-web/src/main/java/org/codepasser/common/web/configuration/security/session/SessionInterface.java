package org.codepasser.common.web.configuration.security.session;

/**
 * SessionInterface.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
public interface SessionInterface {

  int SESSION_MAX_INACTIVE_INTERVAL = 1800;
  String SESSION_KEY_PREFIX = "DAEMON:WEB:SESSION";
  String SESSION_AUTH_COOKIE_NAME = "DAEMON-X-AUTH-SESSION"; // Default SESSION
  String SESSION_AUTH_TOKEN_NAME = "DAEMON-X-AUTH-TOKEN"; // Default X-Auth-Token
  String SESSION_REMEMBER_ME_COOKIE = "DAEMON-REMEMBER-SESSION"; // Default remember-me
  String OAUTH2_REMEMBER_ME_COOKIE = "DAEMON-REMEMBER-OAUTH2"; // Default oauth-remember-me
}
