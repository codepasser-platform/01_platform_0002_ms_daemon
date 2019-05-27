package org.codepasser.base.web.console;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import javax.annotation.Nonnull;
import org.codepasser.base.service.console.RoleMgrService;
import org.codepasser.base.service.console.vo.RoleItem;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RoleMgrApi.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/03/28 : base version.
 */
@RestController
@RequestMapping("/rest/console/admin/role")
public class RoleMgrApi {

  @Autowired private RoleMgrService roleMgrService;

  @Nonnull
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public List<RoleItem> list(@AuthenticationPrincipal UserIdentity user) throws ServiceException {
    return roleMgrService.list();
  }
}
