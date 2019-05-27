package org.codepasser.base.model.business.info;

import java.io.Serializable;

/**
 * Address.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class Address implements Serializable {
  private static final long serialVersionUID = 3515170547162573549L;

  private String province;
  private String city;
  private String area;

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }
}
