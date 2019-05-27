package org.codepasser.common.model.entity.inner;

/**
 * OrgType.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public enum OrgType {
  ROOT("ROOT", "顶级组织"),
  FUNCTIONAL("FUNCTIONAL", "职能部门");

  private String key;
  private String comment;

  OrgType(String key, String comment) {
    this.key = key;
    this.comment = comment;
  }

  public String key() {
    return key;
  }

  public String comment() {
    return comment;
  }
}
