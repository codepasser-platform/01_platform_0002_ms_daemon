package org.codepasser.base.dao.repository.sample;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import org.codepasser.base.model.entity.sample.SampleGroupEntity;
import org.codepasser.common.model.entity.inner.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * SampleGroupEntityRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface SampleGroupRepository
    extends CrudRepository<SampleGroupEntity, Long>, JpaSpecificationExecutor<SampleGroupEntity> {

  Optional<SampleGroupEntity> findById(long id);

  @Query(value = "SELECT id,name FROM SampleGroupEntity WHERE state NOT IN ('DELETE', 'EXPIRED')")
  List<SampleGroupEntity> findList();

  List<SampleGroupEntity> findSampleGroupEntitiesByStateNotIn(@Nonnull Set<State> states);

  Page<SampleGroupEntity> findAllByNameContainingAndStateNotIn(
      @Nonnull String conditionName, @Nonnull Set<State> states, @Nonnull Pageable pageable);
}
