package org.codepasser.base.service.basement.vo;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;

/**
 * ResourceId.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/26 : base version.
 */
public class ResourceId implements Serializable {

  private static final long serialVersionUID = -2807054453654804966L;

  @JsonFormat(shape = STRING)
  private Long id;

  public ResourceId() {}

  public ResourceId(Long id) {
    this.id = id;
  }

  public static ResourceId build(Long id) {
    return new ResourceId(id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
