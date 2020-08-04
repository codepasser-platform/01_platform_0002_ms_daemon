package org.codepasser.base.model.es.sample.builder;

import org.codepasser.base.model.es.sample.SampleManual;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SampleManualBuilder.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019-03-25 : base version.
 */
public class SampleManualBuilder {

  /*名称*/
  private String name;

  /*关键词*/
  private String content;

  /*文件路径*/
  private String manualPath;

  /*文件访问路径*/
  private String manualUrl;

  public static SampleManualBuilder build() {
    return new SampleManualBuilder();
  }

  public SampleManual get() {
    SampleManual manual = new SampleManual();
    BeanUtils.copyProperties(this, manual);
    manual.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    return manual;
  }

  public String getName() {
    return name;
  }

  public SampleManualBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public String getContent() {
    return content;
  }

  public SampleManualBuilder setContent(String content) {
    this.content = content;
    return this;
  }

  public String getManualPath() {
    return manualPath;
  }

  public SampleManualBuilder setManualPath(String manualPath) {
    this.manualPath = manualPath;
    return this;
  }

  public String getManualUrl() {
    return manualUrl;
  }

  public SampleManualBuilder setManualUrl(String manualUrl) {
    this.manualUrl = manualUrl;
    return this;
  }
}
