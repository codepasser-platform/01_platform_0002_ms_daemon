package org.codepasser.base.dao.repository.mongo;

import java.util.Optional;
import org.codepasser.base.dao.repository.extend.UserAttributeDataRepository;
import org.codepasser.base.model.data.UserAttribute;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * UserAttributeRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface UserAttributeRepository
    extends MongoRepository<UserAttribute, String>, UserAttributeDataRepository {

  Optional<UserAttribute> findByUserId(Long userId);
}
