package org.codepasser.base.web.console;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.github.pagehelper.PageInfo;
import java.util.EnumSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.codepasser.base.model.entity.dto.UserItem;
import org.codepasser.base.service.console.UserMgrService;
import org.codepasser.base.service.console.bo.ConsoleUserCreation;
import org.codepasser.base.service.console.bo.ConsoleUserEdition;
import org.codepasser.base.service.console.bo.ConsoleUserRecover;
import org.codepasser.base.service.console.vo.UserDetail;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.validation.Group;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserMgrApi.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/03/28 : base version.
 */
@RestController
@RequestMapping("/rest/console/admin/user")
public class UserMgrApi {

  @Autowired private UserMgrService userMgrService;

  @Nonnull
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  PageInfo<UserItem> pagination(
      @Nullable @RequestParam(value = "username", required = false) String username,
      @Nullable @RequestParam(value = "authorities", required = false)
          EnumSet<Authority.Role> authorities,
      @Nonnull @RequestParam("page") int page,
      @Nonnull @RequestParam("size") int size)
      throws ServiceException {
    return userMgrService.pagination(username, authorities, page, size);
  }

  @Nonnull
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  UserDetail detail(@Nonnull @PathVariable("id") Long id) throws ServiceException {
    return userMgrService.detail(id);
  }

  @Nonnull
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse creation(
      @Nonnull @Validated(Group.Create.class) @RequestBody ConsoleUserCreation userCreation,
      @AuthenticationPrincipal UserIdentity user)
      throws ServiceException {
    return userMgrService.creation(userCreation, user.getUser().getId());
  }

  @Nonnull
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(method = PUT, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse edition(
      @Nonnull @Validated(Group.Create.class) @RequestBody ConsoleUserEdition userEdition,
      @AuthenticationPrincipal UserIdentity user)
      throws ServiceException {
    return userMgrService.edition(userEdition, user.getUser().getId());
  }

  @Nonnull
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse deletion(
      @Nonnull @PathVariable("id") Long id, @AuthenticationPrincipal UserIdentity user)
      throws ServiceException {
    return userMgrService.deletion(id, user.getUser().getId());
  }

  @Nonnull
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(value = "/lock/{id}", method = PUT, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse lock(
      @Nonnull @PathVariable("id") Long id, @AuthenticationPrincipal UserIdentity user)
      throws ServiceException {
    return userMgrService.lock(id, user.getUser().getId());
  }

  @Nonnull
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(value = "/unlock/{id}", method = PUT, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse unlock(
      @Nonnull @PathVariable("id") Long id, @AuthenticationPrincipal UserIdentity user)
      throws ServiceException {
    return userMgrService.unlock(id, user.getUser().getId());
  }

  @Nonnull
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(value = "/recover", method = PUT, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse recover(
      @Nonnull @RequestBody ConsoleUserRecover recover, @AuthenticationPrincipal UserIdentity user)
      throws ServiceException {
    return userMgrService.recover(recover, user.getUser().getId());
  }
}
