package org.codepasser.base.dao.repository.mapper;

import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.codepasser.base.model.entity.dto.UserItem;
import org.codepasser.common.model.entity.inner.State;
import org.codepasser.common.model.security.Authority;
import org.springframework.stereotype.Repository;

/**
 * UserMapper.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/10 : base version.
 */
@Mapper
@Repository
public interface UserMapper {

  List<UserItem> findAllByCondition(
      @Nonnull @Param("username") String username,
      @Nonnull @Param("authorities") Set<Authority.Role> authorities,
      @Nonnull @Param("states") Set<State> states);
}
