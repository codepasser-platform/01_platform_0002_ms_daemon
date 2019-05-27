package org.codepasser.base.dao.repository;

import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import org.codepasser.base.model.entity.User;
import org.codepasser.common.model.entity.inner.State;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

  Optional<User> findById(@Nonnull Long id);

  Optional<User> findByUsernameAndStateNotIn(@Nonnull String username, @Nonnull Set<State> states);

  Optional<User> findByPhoneAndStateNotIn(@Nonnull String phone, @Nonnull Set<State> states);

  Optional<User> findByEmailAndStateNotIn(@Nonnull String email, @Nonnull Set<State> states);

  int countByIdNotAndPhoneAndStateNotIn(
      @Nonnull Long id, @Nonnull String phone, @Nonnull Set<State> states);

  int countByIdNotAndEmailAndStateNotIn(
      @Nonnull Long id, @Nonnull String email, @Nonnull Set<State> states);
}
