package org.codepasser.base.model.entity;

import static javax.persistence.EnumType.STRING;
import static org.codepasser.base.model.entity.inner.AttachmentStatus.INIT;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.codepasser.base.model.entity.inner.AttachmentCategory;
import org.codepasser.base.model.entity.inner.AttachmentStatus;
import org.codepasser.base.model.entity.inner.DocumentType;
import org.codepasser.common.model.entity.Base;

/**
 * Attachment.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "sys_attachment")
public class Attachment extends Base {

  private static final long serialVersionUID = -1649547890241193616L;
  /** 附件文件名. */
  @Column(name = "name", nullable = false, length = 120)
  private String name;

  /** 附件分类. */
  @Enumerated(STRING)
  @Column(name = "category", nullable = false, length = 20)
  private AttachmentCategory category;

  /** 附件存储状态. */
  @Enumerated(STRING)
  @Column(name = "status", nullable = false, length = 20)
  private AttachmentStatus status;

  /** 附件文件存储目录. */
  @Column(name = "directory", nullable = false)
  private String directory;

  /** 附件文件存储路径. */
  @Column(name = "path", nullable = false)
  private String path;

  /** 附件连接地址-URL. */
  @Column(name = "url", nullable = false)
  private String url;

  /** 附件连接地址-URI. */
  @Column(name = "uri", nullable = false)
  private String uri;

  /** 附件大小. */
  @Column(name = "size", nullable = false)
  private Long size;

  /** 附件文档类型. */
  @Enumerated(STRING)
  @Column(name = "document_type")
  private DocumentType documentType;

  public Attachment() {
    super();
    this.status = INIT;
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
}
