package org.codepasser.common.model.entity;

import static javax.persistence.EnumType.STRING;
import static org.codepasser.common.model.entity.inner.State.OK;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import org.codepasser.common.model.entity.inner.State;
import org.codepasser.common.utils.IdGenerator;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Base.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@MappedSuperclass
@DynamicUpdate
public abstract class Base implements Serializable {

  private static final long serialVersionUID = -3342549882205845275L;

  @Id
  @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
  private Long id = IdGenerator.next();

  @Column(name = "create_user", nullable = false, updatable = false)
  private Long createUser;

  @Column(name = "create_time", nullable = false, updatable = false)
  private Date createTime;

  @Column(name = "update_user")
  private Long updateUser;

  @Column(name = "update_time")
  private Date updateTime;

  @NotNull
  @Enumerated(STRING)
  @Column(name = "state", nullable = false, length = 20)
  private State state = OK;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCreateUser() {
    return createUser;
  }

  public void setCreateUser(Long createUser) {
    this.createUser = createUser;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(Long updateUser) {
    this.updateUser = updateUser;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Base)) {
      return false;
    }
    Base base = (Base) o;
    return Objects.equals(getId(), base.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
