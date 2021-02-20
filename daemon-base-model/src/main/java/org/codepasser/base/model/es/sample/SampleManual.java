package org.codepasser.base.model.es.sample;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

/**
 * SampleManual.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019-03-25 : base version.
 */
@Document(indexName = "sample_manuals", type = "_doc")
@Setting(settingPath = "/es/index-setting.json")
@Mapping(mappingPath = "/es/sample-manual-mapping.json")
public class SampleManual {

  /*ES ID*/
  @Id private String id;

  /*名称*/
  //  @Field(searchAnalyzer = "pinyin", analyzer = "pinyin", type = Text)
  private String name;

  /*关键词*/
  //  @Field(searchAnalyzer = "ik_smart", analyzer = "ik_smart", type = Text)
  private String content;

  /*文件路径*/
  //  @Field(type = Text)
  private String manualPath;

  /*文件访问路径*/
  //  @Field(type = Text)
  private String manualUrl;

  //  @Field(type = Keyword)
  private Date createTime;

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

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
