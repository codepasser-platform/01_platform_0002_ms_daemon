package org.codepasser.base.model.entity;

import org.codepasser.common.model.entity.Base;
import org.codepasser.common.model.entity.inner.OrgType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

/**
 * Org.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "sys_org")
public class Org extends Base {

  private static final long serialVersionUID = -8240397627190751263L;
  /** 组织机构名. */
  @Column(name = "name", nullable = false, length = 120)
  private String name;

  /** 组织机构类型. */
  @Enumerated(STRING)
  @Column(name = "type", nullable = false, length = 20)
  private OrgType type;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OrgType getType() {
    return type;
  }

  public void setType(OrgType type) {
    this.type = type;
  }
}
