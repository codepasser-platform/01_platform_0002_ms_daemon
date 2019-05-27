package org.codepasser.base.service.bootstrap.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.codepasser.base.model.data.Area;
import org.codepasser.common.model.In;

/**
 * AreaDistrict.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
public class AreaDistrict extends AreaCity implements In<Area> {

  private static final long serialVersionUID = 5812334087131972596L;

  @JsonProperty("city_code")
  private String cityCode;

  private String city;

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public Area convert() {
    Area area = new Area();
    area.setLevel(3);
    area.setLeaf(true);
    area.setCode(this.getCode());
    area.setName(this.getName());
    area.setParent(this.getCityCode());
    //    area.setDisplay();
    return area;
  }
}
