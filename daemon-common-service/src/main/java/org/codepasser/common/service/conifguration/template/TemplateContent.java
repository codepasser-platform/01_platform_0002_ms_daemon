package org.codepasser.common.service.conifguration.template;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

/**
 * TemplateContent.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/14 : base version.
 */
public class TemplateContent implements Serializable {

  private static final long serialVersionUID = 7237060278258811085L;

  private String name;

  private String content;

  private Locale language;

  private Date creationTime;

  private Date lastModifyTime;

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

  public Locale getLanguage() {
    return language;
  }

  public void setLanguage(Locale language) {
    this.language = language;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Date getLastModifyTime() {
    return lastModifyTime;
  }

  public void setLastModifyTime(Date lastModifyTime) {
    this.lastModifyTime = lastModifyTime;
  }
}
