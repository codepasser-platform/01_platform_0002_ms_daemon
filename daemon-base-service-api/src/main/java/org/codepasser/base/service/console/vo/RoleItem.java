package org.codepasser.base.service.console.vo;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.codepasser.base.model.entity.Role;
import org.codepasser.common.model.Out;
import org.codepasser.common.model.security.Authority;
import org.springframework.beans.BeanUtils;

/**
 * RoleItemVo.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
public class RoleItem implements Out<RoleItem, Role> {

  private static final long serialVersionUID = 4626267482709357827L;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  @JsonFormat(shape = STRING)
  private Long createUser;

  private String createUserName;

  private Authority.Role authority;

  /** 角色名. */
  private String name;

  /** 角色说明.. */
  private String description;

  /** 角色显示顺序.. */
  @JsonFormat(shape = STRING)
  private Integer display;

  // obligate
  /** 角色所属ORG - 预留. */
  @JsonFormat(shape = STRING)
  private Long orgId;

  private String orgName;

  @Override
  public RoleItem from(Role entity) {
    BeanUtils.copyProperties(entity, this);
    return this;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getCreateUser() {
    return createUser;
  }

  public void setCreateUser(Long createUser) {
    this.createUser = createUser;
  }

  public String getCreateUserName() {
    return createUserName;
  }

  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }

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

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
}
