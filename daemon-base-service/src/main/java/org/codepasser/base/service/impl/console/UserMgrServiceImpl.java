package org.codepasser.base.service.impl.console;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.codepasser.base.dao.repository.UserRepository;
import org.codepasser.base.dao.repository.mapper.UserMapper;
import org.codepasser.base.model.entity.User;
import org.codepasser.base.model.entity.dto.UserItem;
import org.codepasser.base.service.console.UserMgrService;
import org.codepasser.base.service.console.bo.ConsoleUserCreation;
import org.codepasser.base.service.console.bo.ConsoleUserEdition;
import org.codepasser.base.service.console.bo.ConsoleUserRecover;
import org.codepasser.base.service.console.vo.UserDetail;
import org.codepasser.base.service.impl.cell.UserCell;
import org.codepasser.common.model.entity.inner.LockType;
import org.codepasser.common.model.entity.inner.State;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.validation.Group;
import org.codepasser.common.service.exception.ConflictException;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * UserMgrServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
@Service
@RestController
public class UserMgrServiceImpl implements UserMgrService {

  @Autowired private UserMapper userMapper;

  @Autowired private UserCell userCell;

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Nonnull
  @Override
  public PageInfo<UserItem> pagination(
      @Nullable @RequestParam(value = "username", required = false) String username,
      @Nullable @RequestParam(value = "authorities", required = false)
          EnumSet<Authority.Role> authorities,
      @Nonnull @RequestParam("page") int page,
      @Nonnull @RequestParam("size") int size)
      throws ServiceException {
    PageHelper.startPage(page, size); // 1 <= page <= pageTotal
    List<UserItem> users =
        userMapper.findAllByCondition(
            username, authorities, EnumSet.of(State.DELETED, State.EXPIRED));
    return new PageInfo<>(users);
  }

  @Nonnull
  @Override
  public UserDetail detail(@Nonnull @PathVariable("id") Long id) throws ServiceException {
    User user = userCell.validById(id);
    return new UserDetail().from(user);
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse creation(
      @Nonnull @Validated(Group.Create.class) @RequestBody ConsoleUserCreation userCreation,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {

    if (userCell.existByUsername(userCreation.getUsername())) {
      throw new ConflictException(ConflictException.Error.USER_USERNAME);
    }

    if (!StringUtils.isEmpty(userCreation.getEmail())) {
      if (userCell.existByEmail(userCreation.getEmail())) {
        throw new ConflictException(ConflictException.Error.USER_EMAIL);
      }
    }

    if (!StringUtils.isEmpty(userCreation.getPhone())) {
      if (userCell.existByPhone(userCreation.getPhone())) {
        throw new ConflictException(ConflictException.Error.USER_PHONE);
      }
    }
    User user = userCreation.convert(userId);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return AssertResponse.success();
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse edition(
      @Nonnull @Validated(Group.Create.class) @RequestBody ConsoleUserEdition userEdition,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {

    User foundUser = userCell.validById(userEdition.getId());

    if (!StringUtils.isEmpty(userEdition.getEmail())) {
      if (userCell.duplicateByEmail(userEdition.getId(), userEdition.getEmail())) {
        throw new ConflictException(ConflictException.Error.USER_EMAIL);
      }
    }

    if (!StringUtils.isEmpty(userEdition.getPhone())) {
      if (userCell.duplicateByPhone(userEdition.getId(), userEdition.getPhone())) {
        throw new ConflictException(ConflictException.Error.USER_PHONE);
      }
    }

    User edition = userEdition.convert(userId);
    foundUser.setUpdateUser(edition.getUpdateUser());
    foundUser.setUpdateTime(edition.getUpdateTime());
    foundUser.setAuthorities(edition.getAuthorities());
    foundUser.setPhone(edition.getPhone());
    foundUser.setEmail(edition.getEmail());
    userRepository.save(foundUser);
    return AssertResponse.success();
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse deletion(
      @Nonnull @PathVariable("id") Long id, @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {
    User foundUser = userCell.validById(id);
    foundUser.setState(State.DELETED);
    foundUser.setUpdateTime(new Date());
    foundUser.setUpdateUser(userId);
    userRepository.save(foundUser);
    return AssertResponse.success();
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse lock(
      @Nonnull @PathVariable("id") Long id, @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {
    User foundUser = userCell.validById(id);
    foundUser.setLocked(true);
    foundUser.setLockType(LockType.ADMIN);
    foundUser.setUpdateTime(new Date());
    foundUser.setUpdateUser(userId);
    userRepository.save(foundUser);
    return AssertResponse.success();
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse unlock(
      @Nonnull @PathVariable("id") Long id, @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {
    User foundUser = userCell.validById(id);
    foundUser.setLocked(false);
    foundUser.setLockType(null);
    foundUser.setUpdateTime(new Date());
    foundUser.setUpdateUser(userId);
    userRepository.save(foundUser);
    return AssertResponse.success();
  }

  @Transactional
  @Nonnull
  @Override
  public AssertResponse recover(
      @Nonnull @RequestBody ConsoleUserRecover recover,
      @Nonnull @RequestParam("userId") Long userId) {
    User foundUser = userCell.validById(recover.getId());
    foundUser.setPassword(passwordEncoder.encode(recover.getPassword()));
    foundUser.setUpdateUser(userId);
    foundUser.setUpdateTime(new Date());
    userRepository.save(foundUser);
    return AssertResponse.success();
  }
}
