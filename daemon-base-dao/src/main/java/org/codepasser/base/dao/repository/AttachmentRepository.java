package org.codepasser.base.dao.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import org.codepasser.base.model.entity.Attachment;
import org.codepasser.base.model.entity.inner.AttachmentCategory;
import org.codepasser.common.model.entity.inner.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * AttachmentRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface AttachmentRepository
    extends CrudRepository<Attachment, Long>, JpaSpecificationExecutor<Attachment> {

  @Nonnull
  List<Attachment> findAllByIdInAndStateNotIn(@Nonnull List<Long> ids, @Nonnull Set<State> states);

  @Nonnull
  Optional<Attachment> findAllByIdAndStateNotIn(
      @Nonnull Long attachmentId, @Nonnull Set<State> newHashSet);

  @Nonnull
  List<Attachment> findAllByIdInAndStateIn(@Nonnull List<Long> ids, @Nonnull Set<State> states);

  @Nonnull
  Optional<Attachment> findAllByIdAndStateIn(
      @Nonnull Long attachmentId, @Nonnull Set<State> newHashSet);

  Page<Attachment> findAllByNameContainingAndCategoryEqualsAndStateNotIn(
      @Nonnull String name,
      @Nonnull AttachmentCategory category,
      @Nonnull Set<State> states,
      @Nonnull Pageable pageable);
}
