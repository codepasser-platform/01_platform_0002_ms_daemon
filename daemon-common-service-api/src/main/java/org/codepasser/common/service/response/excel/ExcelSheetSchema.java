package org.codepasser.common.service.response.excel;

/**
 * ExcelSheetSchema.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/26 : base version.
 */
public class ExcelSheetSchema implements ExcelSchema {

  private String title;
  private boolean displayGridLines;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isDisplayGridLines() {
    return displayGridLines;
  }

  public void setDisplayGridLines(boolean displayGridLines) {
    this.displayGridLines = displayGridLines;
  }
}
