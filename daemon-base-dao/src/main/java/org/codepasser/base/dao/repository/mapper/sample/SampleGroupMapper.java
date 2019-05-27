package org.codepasser.base.dao.repository.mapper.sample;

import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
public interface SampleGroupMapper {

  @Select(
      "<script>\n"
          + "  SELECT g.id AS id, g.name AS name, i.id AS itemId, i.name AS itemName\n"
          + "  FROM sample_group g,\n"
          + "  sample_item i\n"
          + "  WHERE g.state NOT IN\n"
          + "  <foreach collection='states' item='_item' open='(' separator=',' close=')'>\n"
          + "    #{_item}\n"
          + "  </foreach>\n"
          + "  AND i.state NOT IN\n"
          + "  <foreach collection='states' item='_item' open='(' separator=',' close=')'>\n"
          + "    #{_item}\n"
          + "  </foreach>\n"
          + "  AND g.id = i.group_id\n"
          + "</script>")
  List<SampleGroupItems> findListStateNotIn(@Param("states") Set<State> states);

  @Delete("DELETE FROM sample_group where id=#{id}")
  int deleteRecord(Long id);
}
