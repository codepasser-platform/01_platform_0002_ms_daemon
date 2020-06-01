package org.codepasser.common.service;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.codepasser.common.model.entity.inner.UserProvider;
import org.codepasser.common.model.security.UserExternalBasic;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AuthorizationService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/oauth2")
public interface OAuth2Service {

  @RequestMapping(value = "/", method = POST, produces = APPLICATION_JSON_VALUE)
  @Nonnull
  UserExternalBasic save(@Valid @RequestBody UserExternalBasic externalUser)
      throws ServiceException;

  @RequestMapping(value = "/", method = PUT, produces = APPLICATION_JSON_VALUE)
  void update(@Valid @RequestBody UserExternalBasic externalUser) throws ServiceException;

  @RequestMapping(value = "/{externalUserId}", method = GET, produces = APPLICATION_JSON_VALUE)
  @Nonnull
  Optional<UserExternalBasic> findByExternalUserIdAndProvider(
      @Nonnull @PathVariable("externalUserId") String externalUserId,
      @Nonnull @RequestParam("provider") UserProvider provider)
      throws ServiceException;

  @RequestMapping(
      value = "/provider/{provider}/bind_user/{userId}",
      produces = APPLICATION_JSON_VALUE)
  @Nonnull
  Optional<UserExternalBasic> findByUserIdAndProvider(
      @Nonnull @PathVariable("userId") Long userId,
      @Nonnull @PathVariable("provider") UserProvider provider)
      throws ServiceException;
}
