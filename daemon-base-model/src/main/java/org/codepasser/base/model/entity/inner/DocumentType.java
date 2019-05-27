package org.codepasser.base.model.entity.inner;

/**
 * DocumentType.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public enum DocumentType {
  VND_MS_EXCEL("application/vnd.ms-excel", ".xls", "Excel document type "),
  VND_MS_WORD("application/msword", ".doc", "Word document type "),
  JSON("application/json", ".json", "JSON format"),
  JS("application/javascript", ".js", "JavaScript (ECMAScript)"),
  HTML("text/html", ".html", "HyperText Markup Language (HTML)"),
  XML("application/xml", ".html", "XML"),
  JPG("application/x-jpg", ".jpg", "JPG Image"),
  JPEG("image/jpeg", ".jpeg", "JPEG Image"),
  GIF("image/gif", ".gif", "GIF Image"),
  PNG("image/png", ".png", "PNG Image"),
  BMP("application/x-bmp", ".bmp", "BMP Image"),
  UNKNOWN("application/unknown", ".unknown", "unknown");

  private String type;
  private String extension;
  private String description;

  DocumentType(String type, String extension, String description) {
    this.type = type;
    this.extension = extension;
    this.description = description;
  }

  public static DocumentType getDocumentType(String _extension) {
    for (DocumentType documentType : DocumentType.values()) {
      if (documentType.extension.equals(_extension)) {
        return documentType;
      }
    }
    return DocumentType.UNKNOWN;
  }

  public String type() {
    return type;
  }

  public String extension() {
    return extension;
  }

  public String description() {
    return description;
  }
}
