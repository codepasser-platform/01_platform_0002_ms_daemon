package org.codepasser.common.model.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import org.codepasser.common.model.entity.inner.OrgType;

/**
 * EnterpriseBasic.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/22 : base version.
 */
public class OrgBasic implements Serializable {

  private static final long serialVersionUID = 2407384580804108831L;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;

  /** 组织机构名. */
  private String name;

  /** 组织机构类型. */
  private OrgType type;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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
