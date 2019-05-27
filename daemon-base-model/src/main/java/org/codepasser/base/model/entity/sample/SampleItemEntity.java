package org.codepasser.base.model.entity.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.codepasser.common.model.entity.Base;

/**
 * SampleItemEntity.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "sample_item")
public class SampleItemEntity extends Base {

  private static final long serialVersionUID = -6027314828223185433L;

  @Column(name = "name", nullable = false, length = 120)
  private String name;

  @Column(name = "group_id", nullable = false)
  private Long groupId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }
}
