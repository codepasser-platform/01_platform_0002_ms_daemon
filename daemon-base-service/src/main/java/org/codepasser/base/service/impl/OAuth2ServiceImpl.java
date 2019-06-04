package org.codepasser.base.service.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.codepasser.common.model.ConstantInterface.ROOT_ADMIN_ID;
import static org.codepasser.common.model.ConstantInterface.ROOT_ORG_ID;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.Date;
import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.codepasser.base.dao.repository.UserExternalRepository;
import org.codepasser.base.dao.repository.UserRepository;
import org.codepasser.base.model.entity.User;
import org.codepasser.base.model.entity.UserExternal;
import org.codepasser.base.service.impl.cell.UserCell;
import org.codepasser.common.model.entity.inner.UserProvider;
import org.codepasser.common.model.entity.inner.UserStatus;
import org.codepasser.common.model.entity.inner.UserType;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.security.UserExternalBasic;
import org.codepasser.common.service.OAuth2Service;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class OAuth2ServiceImpl implements OAuth2Service {

  @Autowired private UserExternalRepository userExternalRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private UserCell userCell;

  @Transactional
  @Nonnull
  @Override
  public UserExternalBasic save(@Valid @RequestBody UserExternalBasic basicExternalUser)
      throws ServiceException {
    Date now = new Date();
    User user = new User();
    // TODO default external password nonnull
    user.setUsername(basicExternalUser.getExternalUserId());
    user.setPassword(passwordEncoder.encode("123456"));
    user.setType(UserType.EXTERNAL);
    user.setLocked(false);
    user.setUserStatuses(EnumSet.of(UserStatus.MANAGED));
    user.addAuthority(Authority.Role.USER);
    user.setOrgId(ROOT_ORG_ID);
    user.setCreateUser(ROOT_ADMIN_ID);
    user.setCreateTime(now);

    UserExternal externalUser = new UserExternal();
    externalUser.setExternalUserId(basicExternalUser.getExternalUserId());
    externalUser.setNickname(basicExternalUser.getNickname());
    externalUser.setAvatar(basicExternalUser.getAvatar());
    externalUser.setProvider(basicExternalUser.getProvider());
    externalUser.setDetails(basicExternalUser.getDetails());
    externalUser.setRegistrationTime(now);
    externalUser.setUserId(user.getId());
    externalUser.setCreateTime(now);
    externalUser.setCreateUser(user.getId());

    userRepository.save(user);
    UserExternal savedExternalUser = userExternalRepository.save(externalUser);
    return buildExternalUser(savedExternalUser);
  }

  @Transactional
  @Override
  public void update(@Valid @RequestBody UserExternalBasic externalUser) throws ServiceException {
    String externalUserId = externalUser.getExternalUserId();
    Optional<UserExternal> saved =
        userExternalRepository.findByExternalUserIdAndProvider(
            externalUserId, externalUser.getProvider());
    saved.ifPresent(
        u -> {
          u.setAvatar(externalUser.getAvatar());
          u.setDetails(externalUser.getDetails());
          u.setUpdateTime(new Date());
          u.setUpdateUser(ROOT_ADMIN_ID);
          userExternalRepository.save(u);
        });
  }

  @Nonnull
  @Override
  public Optional<UserExternalBasic> findByExternalUserIdAndProvider(
      @Nonnull @PathVariable("externalUserId") String externalUserId,
      @Nonnull @RequestParam("provider") UserProvider provider)
      throws ServiceException {
    checkArgument(!isEmpty(externalUserId));
    checkArgument(!isEmpty(provider));
    Optional<UserExternal> foundExternalUser =
        userExternalRepository.findByExternalUserIdAndProvider(externalUserId, provider);
    if (foundExternalUser.isPresent()) {
      return Optional.of(buildExternalUser(foundExternalUser.get()));
    }

    return Optional.empty();
  }

  @Nonnull
  @Override
  public Optional<UserExternalBasic> findByUserIdAndProvider(
      @Nonnull @PathVariable("userId") Long userId,
      @Nonnull @PathVariable("provider") UserProvider provider)
      throws ServiceException {
    checkArgument(!isEmpty(userId));
    checkArgument(!isEmpty(provider));
    Optional<UserExternal> foundExternalUser =
        userExternalRepository.findByUserIdAndProvider(userId, provider);
    if (foundExternalUser.isPresent()) {
      return Optional.of(buildExternalUser(foundExternalUser.get()));
    }
    return Optional.empty();
  }

  private UserExternalBasic buildExternalUser(UserExternal externalUser) throws ServiceException {
    UserExternalBasic basicExternalUser = new UserExternalBasic();
    basicExternalUser.setExternalUserId(externalUser.getExternalUserId());
    basicExternalUser.setNickname(externalUser.getNickname());
    basicExternalUser.setAvatar(externalUser.getAvatar());
    basicExternalUser.setProvider(externalUser.getProvider());
    basicExternalUser.setDetails(externalUser.getDetails());
    basicExternalUser.setInnerUser(userCell.buildInnerUser(externalUser.getUserId()));
    return basicExternalUser;
  }
}
