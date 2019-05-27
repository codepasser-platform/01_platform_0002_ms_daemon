package org.codepasser.base.service.console;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import javax.annotation.Nonnull;
import org.codepasser.base.service.console.vo.RoleItem;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * RoleMgrService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019/3/28 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/console/admin/role")
@Service
public interface RoleMgrService {

  @Nonnull
  @RequestMapping(method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  List<RoleItem> list() throws ServiceException;
}
