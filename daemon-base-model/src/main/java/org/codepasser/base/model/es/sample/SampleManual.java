package org.codepasser.base.model.es.sample;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * SampleManual.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019-03-25 : base version.
 */
@Document(indexName = "sample_manuals", type = "manual")
public class SampleManual {

  /*ES ID*/
  @Id private String id;

  /*名称*/
  @Field(searchAnalyzer = "pinyin", analyzer = "pinyin", type = Text)
  private String name;

  /*关键词*/
  @Field(searchAnalyzer = "ik_smart", analyzer = "ik_smart", type = Text)
  private String content;

  /*文件路径*/
  @Field(type = Text)
  private String manualPath;

  /*文件访问路径*/
  @Field(type = Text)
  private String manualUrl;

  @Field(type = Keyword)
  private String createTime;

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

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
}
