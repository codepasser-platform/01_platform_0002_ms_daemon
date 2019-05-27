package org.codepasser.base.model.entity;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.codepasser.common.model.entity.Base;
import org.codepasser.common.model.security.Authority;

/**
 * Role.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "sys_role")
public class Role extends Base {

  private static final long serialVersionUID = 6800392100425795632L;

  @Column(name = "authority", nullable = false, length = 20)
  @Enumerated(STRING)
  private Authority.Role authority;

  /** 角色名. */
  @Column(name = "name", nullable = false, length = 120)
  private String name;

  /** 角色说明.. */
  @Column(name = "description")
  private String description;

  /** 角色显示顺序.. */
  @Column(name = "display", nullable = false)
  private Integer display;

  // obligate
  /** 角色所属ORG - 预留. */
  @Column(name = "org_id")
  private Long orgId;

  public Authority.Role getAuthority() {
    return authority;
  }

  public void setAuthority(Authority.Role authority) {
    this.authority = authority;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getDisplay() {
    return display;
  }

  public void setDisplay(Integer display) {
    this.display = display;
  }

  public Long getOrgId() {
    return orgId;
  }

  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
}
