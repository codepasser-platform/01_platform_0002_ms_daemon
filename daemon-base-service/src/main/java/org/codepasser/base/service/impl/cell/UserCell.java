package org.codepasser.base.service.impl.cell;

import static com.google.common.collect.Sets.newHashSet;
import static org.codepasser.common.model.entity.inner.State.DELETED;
import static org.codepasser.common.model.entity.inner.State.EXPIRED;
import static org.codepasser.common.service.exception.NotFoundException.Error.USER;

import javax.annotation.Nonnull;
import org.codepasser.base.dao.repository.UserRepository;
import org.codepasser.base.model.entity.User;
import org.codepasser.common.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserCell.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Component
public class UserCell {

  @Autowired private UserRepository userRepository;

  public User validById(long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(USER));
  }

  public boolean existById(long userId) {
    return userRepository.findById(userId).isPresent();
  }

  public boolean existByUsername(@Nonnull String userName) {
    return userRepository
        .findByUsernameAndStateNotIn(userName, newHashSet(DELETED, EXPIRED))
        .isPresent();
  }

  public boolean existByPhone(@Nonnull String phone) {
    return userRepository.findByPhoneAndStateNotIn(phone, newHashSet(DELETED, EXPIRED)).isPresent();
  }

  public boolean existByEmail(@Nonnull String email) {
    return userRepository.findByEmailAndStateNotIn(email, newHashSet(DELETED, EXPIRED)).isPresent();
  }

  public boolean duplicateByPhone(@Nonnull Long id, @Nonnull String phone) {
    return userRepository.countByIdNotAndPhoneAndStateNotIn(id, phone, newHashSet(DELETED, EXPIRED))
        > 0;
  }

  public boolean duplicateByEmail(@Nonnull Long id, @Nonnull String email) {
    return userRepository.countByIdNotAndEmailAndStateNotIn(id, email, newHashSet(DELETED, EXPIRED))
        > 0;
  }
}
