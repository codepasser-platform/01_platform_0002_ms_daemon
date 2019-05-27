package org.codepasser.base.dao.repository.es.sample;

import javax.annotation.Nonnull;
import org.codepasser.base.model.es.sample.SampleManual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * SampleManualsRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019-03-25 : base version.
 */
@Repository
public interface SampleManualsRepository extends ElasticsearchRepository<SampleManual, String> {

  Page<SampleManual> findAllByNameContains(@Nonnull String name, @Nonnull Pageable pageable);

  Page<SampleManual> findAllByNameContaining(@Nonnull String name, @Nonnull Pageable pageable);

  Page<SampleManual> findAllByContentContains(@Nonnull String key, @Nonnull Pageable pageable);

  Page<SampleManual> findAllByNameLike(@Nonnull String name, @Nonnull Pageable pageable);

  Page<SampleManual> findAllByContentLike(@Nonnull String content, @Nonnull Pageable pageable);
}
