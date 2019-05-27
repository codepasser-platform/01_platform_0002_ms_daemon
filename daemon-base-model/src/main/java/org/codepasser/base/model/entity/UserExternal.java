package org.codepasser.base.model.entity;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.codepasser.common.model.entity.Base;
import org.codepasser.common.model.entity.inner.UserExternalType;

/**
 * UserExternal.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Entity
@Table(name = "sys_user_external")
public class UserExternal extends Base {

  private static final long serialVersionUID = -8190461950330588469L;
  /** 第三方账户. */
  @Column(name = "account", nullable = false)
  private String account;

  /** 第三方类型. */
  @Enumerated(STRING)
  @Column(name = "type", nullable = false, length = 20)
  private UserExternalType type;

  /** 绑定用户ID. */
  @Column(name = "user_id", nullable = false)
  private Long userId;

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public UserExternalType getType() {
    return type;
  }

  public void setType(UserExternalType type) {
    this.type = type;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
