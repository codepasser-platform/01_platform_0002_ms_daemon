package org.codepasser.base.dao.repository;

import org.codepasser.base.model.entity.Org;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import javax.annotation.Nonnull;

/**
 * OrgRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface OrgRepository extends CrudRepository<Org, Long>, JpaSpecificationExecutor<Org> {

  Optional<Org> findById(@Nonnull Long id);
}
