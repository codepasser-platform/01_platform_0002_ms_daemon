package org.codepasser.base.dao.repository;

import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import org.codepasser.base.model.entity.Role;
import org.codepasser.common.model.entity.inner.State;
import org.codepasser.common.model.security.Authority;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long>, JpaSpecificationExecutor<Role> {

  List<Role> findAllByStateNotIn(@Nonnull Set<State> states);

  List<Role> findAllByAuthorityNotInAndStateNotIn(
      @Nonnull Set<Authority.Role> authority, @Nonnull Set<State> states);

  List<Role> findAllByStateEquals(@Nonnull State state);
}
