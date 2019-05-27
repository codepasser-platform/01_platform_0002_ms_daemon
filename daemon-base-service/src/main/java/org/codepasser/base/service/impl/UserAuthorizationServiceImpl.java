package org.codepasser.base.service.impl;

import static com.google.common.collect.Sets.newHashSet;
import static org.codepasser.common.model.entity.inner.State.DELETED;
import static org.codepasser.common.model.entity.inner.State.EXPIRED;

import java.util.Optional;
import javax.annotation.Nonnull;
import org.codepasser.base.dao.repository.OrgRepository;
import org.codepasser.base.dao.repository.UserRepository;
import org.codepasser.base.model.entity.Org;
import org.codepasser.base.model.entity.User;
import org.codepasser.common.model.security.OrgBasic;
import org.codepasser.common.model.security.UserBasic;
import org.codepasser.common.service.AuthorizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserAuthorizationServiceImpl.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Service
@RestController
public class UserAuthorizationServiceImpl implements AuthorizationService {

  @Autowired private UserRepository userRepository;

  @Autowired private OrgRepository orgRepository;

  @Nonnull
  @Override
  public Optional<UserBasic> loadUserByUsername(
      @Nonnull @RequestParam("username") String username) {
    Optional<User> findUser =
        userRepository.findByUsernameAndStateNotIn(username, newHashSet(DELETED, EXPIRED));
    return remixUserBasic(findUser);
  }

  @Nonnull
  @Override
  public Optional<UserBasic> loadUserByPhone(@Nonnull @RequestParam("username") String username) {
    Optional<User> findUser =
        userRepository.findByPhoneAndStateNotIn(username, newHashSet(DELETED, EXPIRED));
    return remixUserBasic(findUser);
  }

  @Nonnull
  @Override
  public Optional<UserBasic> loadUserByEmail(@Nonnull @RequestParam("username") String username) {
    Optional<User> findUser =
        userRepository.findByEmailAndStateNotIn(username, newHashSet(DELETED, EXPIRED));
    return remixUserBasic(findUser);
  }

  private Optional<UserBasic> remixUserBasic(Optional<User> findUser) {
    Optional<UserBasic> optionalUser = Optional.empty();
    if (findUser.isPresent()) {
      UserBasic userBasic = new UserBasic();
      OrgBasic orgBasic = new OrgBasic();
      Optional<Org> findOrg = orgRepository.findById(findUser.get().getOrgId());
      BeanUtils.copyProperties(findUser.get(), userBasic);
      BeanUtils.copyProperties(findOrg.get(), orgBasic);
      userBasic.setOrg(orgBasic);
      optionalUser = Optional.of(userBasic);
    }
    return optionalUser;
  }
}
