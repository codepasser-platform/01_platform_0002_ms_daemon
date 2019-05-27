package org.codepasser.base.service.bootstrap.data;

import java.io.Serializable;
import org.codepasser.base.model.data.Area;
import org.codepasser.common.model.In;

/**
 * AreaProvince.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
public class AreaProvince implements Serializable, In<Area> {

  private static final long serialVersionUID = 5812334087131972596L;

  private String code;

  private String name;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Area convert() {
    Area area = new Area();
    area.setLevel(1);
    area.setLeaf(false);
    area.setCode(this.getCode());
    area.setName(this.getName());
    //    area.setParent();
    //    area.setDisplay();
    return area;
  }
}
