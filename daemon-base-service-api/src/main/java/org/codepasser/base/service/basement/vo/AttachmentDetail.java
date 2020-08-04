package org.codepasser.base.service.basement.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.codepasser.base.model.entity.Attachment;
import org.codepasser.base.model.entity.inner.AttachmentCategory;
import org.codepasser.base.model.entity.inner.AttachmentStatus;
import org.codepasser.base.model.entity.inner.DocumentType;
import org.codepasser.common.model.Out;
import org.springframework.beans.BeanUtils;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

/**
 * AttachmentDetail.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/26 : base version.
 */
public class AttachmentDetail implements Out<AttachmentDetail, Attachment> {

  private static final long serialVersionUID = 5095825423051100067L;

  @JsonFormat(shape = STRING)
  private Long id;

  /** 附件文件名. */
  private String name;

  /** 附件分类. */
  private AttachmentCategory category;

  /** 附件存储状态. */
  private AttachmentStatus status;

  /** 附件文件存储目录. */
  private String directory;

  /** 附件文件存储路径. */
  private String path;

  /** 附件连接地址-URL. */
  private String url;

  /** 附件连接地址-URI. */
  private String uri;

  /** 附件大小. */
  @JsonFormat(shape = STRING)
  private Long size;

  /** 附件文档类型. */
  private DocumentType documentType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AttachmentCategory getCategory() {
    return category;
  }

  public void setCategory(AttachmentCategory category) {
    this.category = category;
  }

  public AttachmentStatus getStatus() {
    return status;
  }

  public void setStatus(AttachmentStatus status) {
    this.status = status;
  }

  public String getDirectory() {
    return directory;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public DocumentType getDocumentType() {
    return documentType;
  }

  public void setDocumentType(DocumentType documentType) {
    this.documentType = documentType;
  }

  @Override
  public AttachmentDetail from(Attachment entity) {
    BeanUtils.copyProperties(entity, this);
    return this;
  }
}
