package org.codepasser.base.dao.repository.mapper.sample;

import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.codepasser.base.model.entity.dto.sample.SampleGroupItems;
import org.codepasser.common.model.entity.inner.State;
import org.springframework.stereotype.Repository;

/**
 * SampleGroupMapper.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/10 : base version.
 */
@Mapper
@Repository
public interface SampleGroupXmlMapper {

  List<SampleGroupItems> findListByStateNotIn(@Nonnull @Param("states") Set<State> states);

  List<SampleGroupItems> findListByNameContainingAndStateNotIn(
      @Nonnull @Param("name") String name, @Nonnull @Param("states") Set<State> states);

  int deleteRecord(@Nonnull Long id);
}
