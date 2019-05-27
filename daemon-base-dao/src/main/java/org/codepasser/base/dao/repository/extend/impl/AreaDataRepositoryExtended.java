package org.codepasser.base.dao.repository.extend.impl;

import static org.springframework.data.domain.Sort.Direction.ASC;

import javax.annotation.PostConstruct;
import org.codepasser.base.dao.repository.extend.AreaDataRepository;
import org.codepasser.base.model.data.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Repository;

/**
 * AreaDataRepositoryExtended.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
@Repository
public class AreaDataRepositoryExtended implements AreaDataRepository {

  @Autowired private MongoTemplate mongoTemplate;

  @PostConstruct
  private void init() {
    mongoTemplate.indexOps(Area.class).ensureIndex(new Index("display", ASC));
  }
}
