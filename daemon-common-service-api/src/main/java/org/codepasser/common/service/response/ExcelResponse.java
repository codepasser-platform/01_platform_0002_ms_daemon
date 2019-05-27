package org.codepasser.common.service.response;

import org.codepasser.common.service.response.excel.ExcelCellSchema;
import org.codepasser.common.service.response.excel.ExcelRowSchema;
import org.codepasser.common.service.response.excel.ExcelSheet;
import org.codepasser.common.service.response.excel.ExcelSheetSchema;
import org.codepasser.common.service.response.excel.ExcelTableSchema;

/**
 * ExcelResponse.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/26 : base version.
 */
public class ExcelResponse implements BaseResponse {

  private boolean success;

  private ExcelSheetSchema sheetSchema;
  private ExcelTableSchema tableSchema;
  private ExcelRowSchema rowSchema;
  private ExcelCellSchema cellSchema;
  private ExcelSheet sheet;

  public ExcelResponse() {
    this.success = true;
  }

  public ExcelResponse(boolean success) {
    this.success = success;
  }

  @Override
  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public ExcelSheetSchema getSheetSchema() {
    return sheetSchema;
  }

  public void setSheetSchema(ExcelSheetSchema sheetSchema) {
    this.sheetSchema = sheetSchema;
  }

  public ExcelTableSchema getTableSchema() {
    return tableSchema;
  }

  public void setTableSchema(ExcelTableSchema tableSchema) {
    this.tableSchema = tableSchema;
  }

  public ExcelRowSchema getRowSchema() {
    return rowSchema;
  }

  public void setRowSchema(ExcelRowSchema rowSchema) {
    this.rowSchema = rowSchema;
  }

  public ExcelCellSchema getCellSchema() {
    return cellSchema;
  }

  public void setCellSchema(ExcelCellSchema cellSchema) {
    this.cellSchema = cellSchema;
  }

  public ExcelSheet getSheet() {
    return sheet;
  }

  public void setSheet(ExcelSheet sheet) {
    this.sheet = sheet;
  }
}
