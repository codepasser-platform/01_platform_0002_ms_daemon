package org.codepasser.base.model.entity;

import org.codepasser.common.model.entity.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PermissionGroup.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019-03-24 : base version.
 */
@Entity
@Table(name = "sys_permission_group")
public class PermissionGroup extends Base {

  private static final long serialVersionUID = 7347405941626212180L;
  /** 权限组名. */
  @Column(name = "name", nullable = false, length = 120)
  private String name;

  @Column(name = "parent_id", length = 20)
  private Long parentId;

  @Column(name = "level", nullable = false)
  private Integer level;

  @Column(name = "is_leaf", nullable = false)
  private Boolean isLeaf;

  @Column(name = "path", nullable = false)
  private String path;

  @Column(name = "name_path", nullable = false)
  private String namePath;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Boolean getLeaf() {
    return isLeaf;
  }

  public void setLeaf(Boolean leaf) {
    isLeaf = leaf;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getNamePath() {
    return namePath;
  }

  public void setNamePath(String namePath) {
    this.namePath = namePath;
  }
}
