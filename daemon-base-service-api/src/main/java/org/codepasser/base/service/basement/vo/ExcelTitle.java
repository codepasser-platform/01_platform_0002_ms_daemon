package org.codepasser.base.service.basement.vo;

/**
 * ExcelTitle.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/26 : base version.
 */
public enum ExcelTitle {
  EXPORT_LIST("名单"),
  EXPORT_REPAIRS("维修案例反馈"),
  EXPORT_REVIEW("维修信息反馈"),
  EXPORT_CART("购物车清单");

  private final String title;

  ExcelTitle(String title) {
    this.title = title;
  }

  public String title() {
    return title;
  }
}
