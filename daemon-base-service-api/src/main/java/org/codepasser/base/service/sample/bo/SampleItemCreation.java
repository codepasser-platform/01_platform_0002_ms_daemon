package org.codepasser.base.service.sample.bo;

import org.codepasser.base.model.entity.sample.SampleItemEntity;
import org.codepasser.common.model.In;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static org.codepasser.common.model.RegexPattern.REGEX_GENERAL_NAME;

/**
 * SampleItemCreation.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class SampleItemCreation implements In<SampleItemEntity> {

  private static final long serialVersionUID = -1966486155730948765L;

  @NotNull
  @Pattern(regexp = REGEX_GENERAL_NAME)
  private String name;

  @NotNull private Long groupId;

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

  @Override
  public SampleItemEntity convert() {
    SampleItemEntity entity = new SampleItemEntity();
    BeanUtils.copyProperties(this, entity);
    return entity;
  }
}
