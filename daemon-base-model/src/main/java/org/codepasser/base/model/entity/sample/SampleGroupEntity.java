package org.codepasser.base.model.entity.sample;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codepasser.common.model.entity.Base;

/**
 * SampleGroupEntity.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "sample_group")
public class SampleGroupEntity extends Base {

  private static final long serialVersionUID = -7469301796472965802L;

  @Column(name = "name", nullable = false, length = 120)
  private String name;

  @Column(name = "expiry_date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date expiryDate;

  @Column(name = "expiry_time", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
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
}
