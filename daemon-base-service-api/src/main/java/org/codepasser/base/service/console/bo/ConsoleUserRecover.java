package org.codepasser.base.service.console.bo;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * ConsoleUserRecover.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class ConsoleUserRecover implements Serializable {

  private static final long serialVersionUID = -3290055622728474483L;
  @NotNull() private Long id;

  @NotNull private String password;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
