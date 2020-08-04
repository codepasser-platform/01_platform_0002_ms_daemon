package org.codepasser.common.service.response.excel;

/**
 * ExportSourceType.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/28 : base version.
 */
public enum ExportSourceType {
  TEXT("\\s"), // 配任何空白字符，包括空格、制表符、换页符等等
  NUMBER("^(\\-|\\+)?\\d+(\\.\\d+)?$"), // 正数、负数、和小数
  DATETIME("yyyy-MM-dd HH:mm:ss"),
  DATE("yyyy-MM-dd");

  private final String format;

  ExportSourceType(String format) {
    this.format = format;
  }

  public String format() {
    return format;
  }
}
