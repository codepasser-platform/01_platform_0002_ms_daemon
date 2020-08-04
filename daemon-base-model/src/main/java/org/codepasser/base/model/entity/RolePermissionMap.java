package org.codepasser.base.model.entity;

import org.codepasser.common.model.entity.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * RolePermissionMap.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "sys_role_permission_map")
public class RolePermissionMap extends Base {

  private static final long serialVersionUID = 8760284836606546215L;

  /** 权限 - 元数据ID. */
  @Column(name = "meta_id", nullable = false)
  private Long metaId;

  /** 角色ID. */
  @Column(name = "role_id", nullable = false)
  private Long roleId;

  public Long getMetaId() {
    return metaId;
  }

  public void setMetaId(Long metaId) {
    this.metaId = metaId;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
}
