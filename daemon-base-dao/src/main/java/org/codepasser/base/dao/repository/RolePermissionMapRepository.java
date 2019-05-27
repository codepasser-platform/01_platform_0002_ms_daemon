package org.codepasser.base.dao.repository;

import org.codepasser.base.model.entity.RolePermissionMap;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * RolePermissionMapRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface RolePermissionMapRepository
    extends CrudRepository<RolePermissionMap, Long>, JpaSpecificationExecutor<RolePermissionMap> {}
