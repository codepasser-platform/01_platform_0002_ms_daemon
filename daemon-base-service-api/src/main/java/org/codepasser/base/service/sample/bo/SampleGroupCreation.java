package org.codepasser.base.service.sample.bo;

import static org.codepasser.common.model.RegexPattern.REGEX_GENERAL_NAME;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.codepasser.base.model.entity.sample.SampleGroupEntity;
import org.codepasser.common.model.In;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * SampleGroupBo.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
public class SampleGroupCreation implements In<SampleGroupEntity> {
  private static final long serialVersionUID = -2585479220139977056L;

  @NotNull
  @Pattern(regexp = REGEX_GENERAL_NAME)
  private String name;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date expiryDate;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date expiryTime;

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

  public Date getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(Date expiryTime) {
    this.expiryTime = expiryTime;
  }

  @Override
  public SampleGroupEntity convert() {
    SampleGroupEntity sampleGroupEntity = new SampleGroupEntity();
    BeanUtils.copyProperties(this, sampleGroupEntity);
    return sampleGroupEntity;
  }
}
