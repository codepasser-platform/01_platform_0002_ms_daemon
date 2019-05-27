package org.codepasser.base.model.entity.dto.sample;

import java.io.Serializable;

/**
 * SampleGroupItems.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/10 : base version.
 */
public class SampleGroupItems implements Serializable {

  private static final long serialVersionUID = 1010719199823820633L;

  private Long id;

  private String name;

  private Long itemId;

  private String itemName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }
}
