package org.codepasser.base.service.sample.vo;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.codepasser.base.model.entity.sample.SampleGroupEntity;
import org.codepasser.common.model.Out;
import org.springframework.beans.BeanUtils;

/**
 * SampleGroupVo.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
public class SampleGroupVo implements Out<SampleGroupVo, SampleGroupEntity> {

  private static final long serialVersionUID = 4613932997417957585L;

  @JsonFormat(shape = STRING)
  private long id;

  private String name;

  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date expiryDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date expiryDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date expiryTime;

  private Date createTime;

  @Override
  public SampleGroupVo from(SampleGroupEntity entity) {
    BeanUtils.copyProperties(entity, this);
    this.setExpiryDateTime(this.getExpiryDate());
    return this;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

  public Date getExpiryDateTime() {
    return expiryDateTime;
  }

  public void setExpiryDateTime(Date expiryDateTime) {
    this.expiryDateTime = expiryDateTime;
  }

  public Date getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(Date expiryTime) {
    this.expiryTime = expiryTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
