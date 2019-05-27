package org.codepasser.common.model.security;

/**
 * Authority.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
public interface Authority {

  String comment();

  String authority();

  String description();

  enum Role implements Authority {
    USER("用户", "系统普通用户"),
    ADMIN("系统管理员", "系统管理"),
    MGR("手册管理员", "手册管理");

    private String comment;
    private String description;

    Role(String comment, String description) {
      this.comment = comment;
      this.description = description;
    }

    @Override
    public String comment() {
      return this.comment;
    }

    @Override
    public String authority() {
      return "ROLE_" + this.name();
    }

    @Override
    public String description() {
      return this.description;
    }
  }

  enum Session implements Authority {
    AUTHORIZED("认证授权"),
    ANONYMOUS("未认证授权");

    private String comment;

    Session(String comment) {
      this.comment = comment;
    }

    @Override
    public String comment() {
      return this.comment;
    }

    @Override
    public String authority() {
      return "SESSION_" + this.name();
    }

    @Override
    public String description() {
      return "认证信息";
    }
  }
}
