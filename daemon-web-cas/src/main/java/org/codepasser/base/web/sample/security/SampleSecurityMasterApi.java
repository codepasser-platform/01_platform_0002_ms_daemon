package org.codepasser.base.web.sample.security;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.codepasser.common.model.security.UserSelf;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleSessionApi.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
@RestController
@RequestMapping("/master/sample")
public class SampleSecurityMasterApi {

  @RequestMapping(value = "/no/authenticated", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public Map<String, Object> get(HttpSession session) throws ServiceException {
    UUID uid = (UUID) session.getAttribute("uid");
    Map<String, Object> results = Maps.newHashMap();
    results.put("sessionId : ", session.getId());
    if (uid != null) {
      results.put("attr : ", uid.toString());
    }
    return results;
  }

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = "/is/authenticated", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public UserSelf authenticated(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }

  @PreAuthorize("isAnonymous()")
  @RequestMapping(value = "/anonymous", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public UserSelf anonymous(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }

  @PreAuthorize("permitAll")
  @RequestMapping(value = "/permit", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public UserSelf permit(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(value = "/admin", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public UserSelf roleAdmin(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @RequestMapping(value = "/user", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public UserSelf roleUser(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }
}
