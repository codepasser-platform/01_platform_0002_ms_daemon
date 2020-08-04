package org.codepasser.base.service.bootstrap.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.codepasser.base.model.data.Area;
import org.codepasser.common.model.In;

/**
 * AreaCity.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
public class AreaCity extends AreaProvince implements In<Area> {

  private static final long serialVersionUID = -5626296051932103533L;

  @JsonProperty("province_code")
  private String provinceCode;

  private String province;

  public String getProvinceCode() {
    return provinceCode;
  }

  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  @Override
  public Area convert() {
    Area area = new Area();
    area.setLevel(2);
    area.setLeaf(false);
    area.setCode(this.getCode());
    area.setName(this.getName());
    area.setParent(this.getProvinceCode());
    //    area.setDisplay();
    return area;
  }
}
