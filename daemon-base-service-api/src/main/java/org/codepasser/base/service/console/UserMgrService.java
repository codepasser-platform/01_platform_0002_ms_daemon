package org.codepasser.base.service.console;

import com.github.pagehelper.PageInfo;

import org.codepasser.base.model.entity.dto.UserItem;
import org.codepasser.base.service.console.bo.ConsoleUserCreation;
import org.codepasser.base.service.console.bo.ConsoleUserEdition;
import org.codepasser.base.service.console.bo.ConsoleUserRecover;
import org.codepasser.base.service.console.vo.UserDetail;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.validation.Group;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.EnumSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * UserMgrService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/console/admin/user")
@Service
public interface UserMgrService {

  @Nonnull
  @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
  PageInfo<UserItem> pagination(
      @Nullable @RequestParam(value = "username", required = false) String username,
      @Nullable @RequestParam(value = "authorities", required = false)
          EnumSet<Authority.Role> authorities,
      @Nonnull @RequestParam("page") int page,
      @Nonnull @RequestParam("size") int size)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
  UserDetail detail(@Nonnull @PathVariable("id") Long id) throws ServiceException;

  @Nonnull
  @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
  AssertResponse creation(
      @Nonnull @Validated(Group.Create.class) @RequestBody ConsoleUserCreation userCreation,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException;

  @Nonnull
  @RequestMapping(method = PUT, produces = APPLICATION_JSON_VALUE)
  AssertResponse edition(
      @Nonnull @Validated(Group.Create.class) @RequestBody ConsoleUserEdition userEdition,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
  AssertResponse deletion(
      @Nonnull @PathVariable("id") Long id, @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/lock/{id}", method = PUT, produces = APPLICATION_JSON_VALUE)
  AssertResponse lock(
      @Nonnull @PathVariable("id") Long id, @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/unlock/{id}", method = PUT, produces = APPLICATION_JSON_VALUE)
  AssertResponse unlock(
      @Nonnull @PathVariable("id") Long id, @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/recover", method = PUT, produces = APPLICATION_JSON_VALUE)
  AssertResponse recover(
      @Nonnull @RequestBody ConsoleUserRecover recover,
      @Nonnull @RequestParam("userId") Long userId);
}
