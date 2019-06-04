package org.codepasser.base.dao.repository;

import java.util.Optional;
import javax.annotation.Nonnull;
import org.codepasser.base.model.entity.UserExternal;
import org.codepasser.common.model.entity.inner.UserProvider;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * UserExternalRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface UserExternalRepository
    extends CrudRepository<UserExternal, Long>, JpaSpecificationExecutor<UserExternal> {

  @Nonnull
  Optional<UserExternal> findByExternalUserIdAndProvider(
      @Nonnull String externalUserId, @Nonnull UserProvider provider);

  Optional<UserExternal> findByUserIdAndProvider(
      @Nonnull Long userId, @Nonnull UserProvider provider);
}
