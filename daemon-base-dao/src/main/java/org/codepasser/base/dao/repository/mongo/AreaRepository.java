package org.codepasser.base.dao.repository.mongo;

import org.codepasser.base.dao.repository.extend.AreaDataRepository;
import org.codepasser.base.model.data.Area;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * AreaRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface AreaRepository extends MongoRepository<Area, String>, AreaDataRepository {

  Optional<Area> findByCode(String code);

  List<Area> findAllByLevel(Integer level);

  List<Area> findAllByParentAndLevel(String parent, Integer level);
}
