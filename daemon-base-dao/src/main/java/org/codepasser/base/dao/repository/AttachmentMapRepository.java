package org.codepasser.base.dao.repository;

import org.codepasser.base.model.entity.AttachmentMap;
import org.codepasser.common.model.entity.inner.State;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

/**
 * AttachmentMapRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface AttachmentMapRepository
    extends CrudRepository<AttachmentMap, Long>, JpaSpecificationExecutor<AttachmentMap> {

  @Nonnull
  List<AttachmentMap> findAllByMetaIdAndStateNotIn(
      @Nonnull Long metaId, @Nonnull Set<State> states);

  Optional<AttachmentMap> findByAttachmentIdAndStateNotIn(
      @Nonnull Long metaId, @Nonnull Set<State> states);

  @Nonnull
  List<AttachmentMap> findAllByMetaIdAndStateIn(@Nonnull Long metaId, @Nonnull Set<State> states);

  Optional<AttachmentMap> findByAttachmentIdAndStateIn(
      @Nonnull Long metaId, @Nonnull Set<State> states);

  Optional<AttachmentMap> findByAttachmentId(@Nonnull Long attachmentId);
}
