package org.codepasser.base.model.data;

import org.codepasser.common.model.data.BaseData;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Area.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Document(collection = "sys_area")
public class Area extends BaseData {

  private static final long serialVersionUID = -1008783748820825546L;
  /** 区域 - 名. */
  private String name;

  /** 区域 - 代码. */
  private String code;

  /** 区域 - 父节点代码. */
  private String parent;

  /** 区域 - 层级. */
  private Integer level;

  /** 区域 - 是否为叶子. */
  private Boolean isLeaf;

  private Integer display;

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

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
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

  public Integer getDisplay() {
    return display;
  }

  public void setDisplay(Integer display) {
    this.display = display;
  }
}
