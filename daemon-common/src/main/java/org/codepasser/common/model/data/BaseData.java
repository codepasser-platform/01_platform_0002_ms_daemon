package org.codepasser.common.model.data;

import org.codepasser.common.model.data.inner.State;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

import static org.codepasser.common.model.data.inner.State.OK;
/**
 * BaseData.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public abstract class BaseData implements Serializable {
  private static final long serialVersionUID = 5050667946461088947L;

  @Id private String id;

  private State state = OK;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }
}
