package org.codepasser.base.model.entity;

import org.codepasser.base.model.entity.inner.AttachmentCategory;
import org.codepasser.common.model.entity.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

/**
 * AttachmentMap.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "sys_attachment_map")
public class AttachmentMap extends Base {
  private static final long serialVersionUID = -7489645362026732211L;

  /** 附件 - 元数据ID. */
  @Column(name = "meta_id", nullable = false)
  private Long metaId;

  /** 附件ID. */
  @Column(name = "attachment_id", nullable = false)
  private Long attachmentId;

  /** 附件分类. */
  @Enumerated(STRING)
  @Column(name = "category", nullable = false, length = 20)
  private AttachmentCategory category;

  public Long getMetaId() {
    return metaId;
  }

  public void setMetaId(Long metaId) {
    this.metaId = metaId;
  }

  public Long getAttachmentId() {
    return attachmentId;
  }

  public void setAttachmentId(Long attachmentId) {
    this.attachmentId = attachmentId;
  }

  public AttachmentCategory getCategory() {
    return category;
  }

  public void setCategory(AttachmentCategory category) {
    this.category = category;
  }
}
