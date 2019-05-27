package org.codepasser.common.service.response.excel;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * ExcelTableSchema.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/26 : base version.
 */
public class ExcelTableSchema implements ExcelSchema {

  private String title;
  private int[] titleCoordinate;
  private boolean showSequence;

  private String titleFontFamily;
  private short titleFontSize;
  private boolean titleFontBold;

  private String headerFontFamily;
  private short headerFontSize;
  private boolean headerFontBold;

  private String fontFamily;
  private short fontSize;
  private boolean fontBold;

  private List<Header> header = Lists.newArrayList();

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int[] getTitleCoordinate() {
    return titleCoordinate;
  }

  public void setTitleCoordinate(int[] titleCoordinate) {
    this.titleCoordinate = titleCoordinate;
  }

  public boolean isShowSequence() {
    return showSequence;
  }

  public void setShowSequence(boolean showSequence) {
    this.showSequence = showSequence;
  }

  public String getTitleFontFamily() {
    return titleFontFamily;
  }

  public void setTitleFontFamily(String titleFontFamily) {
    this.titleFontFamily = titleFontFamily;
  }

  public short getTitleFontSize() {
    return titleFontSize;
  }

  public void setTitleFontSize(short titleFontSize) {
    this.titleFontSize = titleFontSize;
  }

  public boolean isTitleFontBold() {
    return titleFontBold;
  }

  public void setTitleFontBold(boolean titleFontBold) {
    this.titleFontBold = titleFontBold;
  }

  public String getHeaderFontFamily() {
    return headerFontFamily;
  }

  public void setHeaderFontFamily(String headerFontFamily) {
    this.headerFontFamily = headerFontFamily;
  }

  public short getHeaderFontSize() {
    return headerFontSize;
  }

  public void setHeaderFontSize(short headerFontSize) {
    this.headerFontSize = headerFontSize;
  }

  public boolean isHeaderFontBold() {
    return headerFontBold;
  }

  public void setHeaderFontBold(boolean headerFontBold) {
    this.headerFontBold = headerFontBold;
  }

  public String getFontFamily() {
    return fontFamily;
  }

  public void setFontFamily(String fontFamily) {
    this.fontFamily = fontFamily;
  }

  public short getFontSize() {
    return fontSize;
  }

  public void setFontSize(short fontSize) {
    this.fontSize = fontSize;
  }

  public boolean isFontBold() {
    return fontBold;
  }

  public void setFontBold(boolean fontBold) {
    this.fontBold = fontBold;
  }

  public List<Header> getHeader() {
    return header;
  }

  public void setHeader(List<Header> header) {
    this.header = header;
  }

  public void addHeader(String title, int width) {
    if (this.header == null) {
      this.header = Lists.newArrayList();
    }
    this.header.add(new Header(title, width));
  }

  public static class Header {
    private String title;
    private int width;

    public Header(String title, int width) {
      this.title = title;
      this.width = width;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public int getWidth() {
      return width;
    }

    public void setWidth(int width) {
      this.width = width;
    }
  }
}
