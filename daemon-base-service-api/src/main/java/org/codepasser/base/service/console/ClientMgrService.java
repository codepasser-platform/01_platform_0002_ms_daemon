package org.codepasser.base.service.console;

import org.codepasser.base.service.console.bo.OAuthClientCreation;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nonnull;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * ClientMgrService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2020/4/22 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/console/admin/client")
@Service
public interface ClientMgrService {

  @Nonnull
  @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
  AssertResponse creation(
      @Nonnull @Valid @RequestBody OAuthClientCreation clientCreation,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException;
}
