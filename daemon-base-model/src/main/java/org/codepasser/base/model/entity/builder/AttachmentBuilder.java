package org.codepasser.base.model.entity.builder;

import org.codepasser.base.model.entity.Attachment;
import org.codepasser.base.model.entity.inner.AttachmentCategory;
import org.codepasser.base.model.entity.inner.DocumentType;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * AttachmentBuilder.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public final class AttachmentBuilder {

  /** 附件文件名. */
  private String name;
  /** 附件分类. */
  private AttachmentCategory category;
  /** 附件文件存储目录. */
  private String directory;
  /** 附件文件存储路径. */
  private String path;
  /** 附件连接地址-URL. */
  private String url;
  /** 附件连接地址-URI. */
  private String uri;
  /** 附件大小. */
  private Long size;
  /** 附件文档类型. */
  private DocumentType documentType;
  /** 创建人. */
  private Long createUser;
  /** 创建时间. */
  private Date createTime;

  public static AttachmentBuilder attachmentBuilder() {
    return new AttachmentBuilder();
  }

  public String getName() {
    return name;
  }

  public AttachmentCategory getCategory() {
    return category;
  }

  public String getDirectory() {
    return directory;
  }

  public String getPath() {
    return path;
  }

  public String getUrl() {
    return url;
  }

  public String getUri() {
    return uri;
  }

  public Long getSize() {
    return size;
  }

  public DocumentType getDocumentType() {
    return documentType;
  }

  public Long getCreateUser() {
    return createUser;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public AttachmentBuilder name(String name) {
    this.name = name;
    return this;
  }

  public AttachmentBuilder category(AttachmentCategory category) {
    this.category = category;
    return this;
  }

  public AttachmentBuilder directory(String directory) {
    this.directory = directory;
    return this;
  }

  public AttachmentBuilder path(String path) {
    this.path = path;
    return this;
  }

  public AttachmentBuilder url(String url) {
    this.url = url;
    return this;
  }

  public AttachmentBuilder uri(String uri) {
    this.uri = uri;
    return this;
  }

  public AttachmentBuilder size(Long size) {
    this.size = size;
    return this;
  }

  public AttachmentBuilder documentType(DocumentType documentType) {
    this.documentType = documentType;
    return this;
  }

  public AttachmentBuilder createUser(Long createUser) {
    this.createUser = createUser;
    return this;
  }

  public AttachmentBuilder createTime(Date createTime) {
    this.createTime = createTime;
    return this;
  }

  public Attachment build() {
    Attachment attachment = new Attachment();
    BeanUtils.copyProperties(this, attachment);
    return attachment;
  }
}
