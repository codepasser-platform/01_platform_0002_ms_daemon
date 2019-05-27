package org.codepasser.base.model.es.sample.dto;

import org.codepasser.base.model.es.sample.SampleManual;
import org.codepasser.common.model.Out;
import org.springframework.beans.BeanUtils;

/**
 * SampleManualDto.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019-03-25 : base version.
 */
public class SampleManualDto implements Out<SampleManualDto, SampleManual> {

  /*ES ID*/
  private String id;

  /*名称*/
  private String name;

  /*关键词*/
  private String content;

  /*文件路径*/
  private String manualPath;

  /*文件访问路径*/
  private String manualUrl;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getManualPath() {
    return manualPath;
  }

  public void setManualPath(String manualPath) {
    this.manualPath = manualPath;
  }

  public String getManualUrl() {
    return manualUrl;
  }

  public void setManualUrl(String manualUrl) {
    this.manualUrl = manualUrl;
  }

  @Override
  public SampleManualDto from(SampleManual entity) {
    BeanUtils.copyProperties(entity, this);
    return this;
  }
}
