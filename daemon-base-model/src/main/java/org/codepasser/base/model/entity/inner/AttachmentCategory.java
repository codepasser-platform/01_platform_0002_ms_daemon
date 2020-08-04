package org.codepasser.base.model.entity.inner;

/**
 * AttachmentCategory.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public enum AttachmentCategory {
  AVATAR("avatar", "头像"),
  EXPORT("export", "导出excel文件"),
  MANUAL("manual", "手册zip包"),
  MANUAL_VEHICLE("manual_vehicle", "车载手册zip包"),
  REPAIRS("repairs", "维修案例反馈附件"),
  REPAIRS_PDF("repairs_pdf", "维修案例反馈PDF"),
  REVIEW("review", "手册错误反馈附件");

  private final String key;
  private final String comment;

  AttachmentCategory(String key, String comment) {
    this.key = key;
    this.comment = comment;
  }

  public String key() {
    return key;
  }

  public String comment() {
    return comment;
  }
}
