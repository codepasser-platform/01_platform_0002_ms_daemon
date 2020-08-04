package org.codepasser.common.web.exception;

import org.codepasser.common.exception.AbstractException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.AccountExpiredException;

import static org.codepasser.common.web.exception.AccessException.Error.ACCOUNT_EXPIRED;
import static org.codepasser.common.web.exception.AccessException.Error.ACCOUNT_LOCKED;
import static org.codepasser.common.web.exception.AccessException.Error.AUTH_FAILED;
import static org.codepasser.common.web.exception.AccessException.Error.AUTH_REQUIRED;
import static org.codepasser.common.web.exception.AccessException.Error.BAD_CREDENTIALS;
import static org.codepasser.common.web.exception.AccessException.Error.CREDENTIALS_EXPIRED;
import static org.codepasser.common.web.exception.AccessException.Error.DENIED;
import static org.codepasser.common.web.exception.AccessException.Error.USER_NOT_FOUND;

public class AccessException extends AbstractException {

  public static final Map<String, Error> codeMap = new HashMap<>();
  private static final long serialVersionUID = 5147201406297373117L;

  static {
    codeMap.put(AccessDeniedException.class.getTypeName(), DENIED);
    codeMap.put(InsufficientAuthenticationException.class.getTypeName(), AUTH_REQUIRED);
    codeMap.put(BadCredentialsException.class.getTypeName(), BAD_CREDENTIALS);
    codeMap.put(CredentialsExpiredException.class.getTypeName(), CREDENTIALS_EXPIRED);
    codeMap.put(LockedException.class.getTypeName(), ACCOUNT_LOCKED);
    codeMap.put(AccountExpiredException.class.getTypeName(), ACCOUNT_EXPIRED);
    codeMap.put(UsernameNotFoundException.class.getTypeName(), USER_NOT_FOUND);
  }

  public AccessException() {
    super(DENIED);
  }

  public AccessException(Error error) {
    super(error);
  }

  public AccessException(AccessDeniedException exception) {
    //    super(DENIED, exception);
    //    setCodeValue(exception.getClass().getTypeName());
    super(codeMap.getOrDefault(exception.getClass().getTypeName(), DENIED), exception);
  }

  public AccessException(AuthenticationException exception) {
    super(codeMap.getOrDefault(exception.getClass().getTypeName(), AUTH_FAILED), exception);
  }

  //  public AccessException(Message message) {
  //    super(message);
  //    setCode(Error.valueOf(message.getPartial()));
  //  }

  @Override
  protected String messageResourceBaseName() {
    return "exception.web";
  }

  @Override
  protected String moduleName() {
    return "WEB_ACCESS";
  }

  public enum Error {
    DENIED,
    AUTH_REQUIRED,
    BAD_CREDENTIALS,
    CREDENTIALS_EXPIRED,
    ACCOUNT_LOCKED,
    ACCOUNT_EXPIRED,
    USER_NOT_FOUND,
    AUTH_FAILED
  }
}
