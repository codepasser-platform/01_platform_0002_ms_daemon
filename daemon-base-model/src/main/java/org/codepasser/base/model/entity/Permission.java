package org.codepasser.base.model.entity;

import org.codepasser.base.model.entity.inner.PermissionMethod;
import org.codepasser.common.model.entity.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

/**
 * Permission.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "sys_permission")
public class Permission extends Base {

  private static final long serialVersionUID = -1657746107963947060L;

  /** 权限点名. */
  @Column(name = "name", nullable = false, length = 120)
  private String name;

  /** 权限点. */
  @Column(name = "code", nullable = false, length = 20)
  private String code;

  /** 权限点. */
  @Column(name = "uri")
  private String uri;

  /** 权限点. */
  @Enumerated(STRING)
  @Column(name = "method", length = 20)
  private PermissionMethod method;

  /** 权限点 - 权限组. */
  @Column(name = "group_id", nullable = false, length = 20)
  private String gourpId;

  /** 权限点说明. */
  @Column(name = "description")
  private String description;

  /** 权限点显示顺序. */
  @Column(name = "display", nullable = false)
  private Integer display;

  // obligate
  /** 权限点所属ORG - 预留. */
  @Column(name = "org_id")
  private Long orgId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public PermissionMethod getMethod() {
    return method;
  }

  public void setMethod(PermissionMethod method) {
    this.method = method;
  }

  public String getGourpId() {
    return gourpId;
  }

  public void setGourpId(String gourpId) {
    this.gourpId = gourpId;
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
