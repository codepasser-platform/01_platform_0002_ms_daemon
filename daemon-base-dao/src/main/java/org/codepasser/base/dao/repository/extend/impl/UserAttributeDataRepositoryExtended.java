package org.codepasser.base.dao.repository.extend.impl;

import org.codepasser.base.dao.repository.extend.UserAttributeDataRepository;
import org.codepasser.base.model.data.UserAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

import static org.springframework.data.domain.Sort.Direction.ASC;

/**
 * UserAttributeDataRepositoryExtended.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
@Repository
public class UserAttributeDataRepositoryExtended implements UserAttributeDataRepository {

  @Autowired private MongoTemplate mongoTemplate;

  @PostConstruct
  private void init() {
    mongoTemplate.indexOps(UserAttribute.class).ensureIndex(new Index("userId", ASC));
  }
}
