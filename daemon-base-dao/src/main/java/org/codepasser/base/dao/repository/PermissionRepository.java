package org.codepasser.base.dao.repository;

import org.codepasser.base.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * PermissionRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface PermissionRepository
    extends CrudRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {}
