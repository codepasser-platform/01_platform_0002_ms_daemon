package org.codepasser.common.service;

import org.codepasser.common.model.security.UserBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import javax.annotation.Nonnull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * AuthorizationService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/authorization")
@Service
public interface AuthorizationService {

  @Nonnull
  @RequestMapping(value = "/load/username", method = GET, produces = APPLICATION_JSON_VALUE)
  Optional<UserBasic> loadUserByUsername(@Nonnull @RequestParam("username") String username);

  @Nonnull
  @RequestMapping(value = "/load/phone", method = GET, produces = APPLICATION_JSON_VALUE)
  Optional<UserBasic> loadUserByPhone(@Nonnull @RequestParam("username") String username);

  @Nonnull
  @RequestMapping(value = "/load/email", method = GET, produces = APPLICATION_JSON_VALUE)
  Optional<UserBasic> loadUserByEmail(@Nonnull @RequestParam("username") String username);
}
