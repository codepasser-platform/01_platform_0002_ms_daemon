package org.codepasser.base.dao.repository.sample;

import java.util.Optional;
import java.util.Set;
import org.codepasser.base.model.entity.sample.SampleItemEntity;
import org.codepasser.common.model.entity.inner.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface SampleItemRepository
    extends CrudRepository<SampleItemEntity, Long>, JpaSpecificationExecutor<SampleItemEntity> {

  Optional<SampleItemEntity> findById(long id);

  Page<SampleItemEntity> findAllByStateNotIn(Set<State> states, Pageable pageable);
}
