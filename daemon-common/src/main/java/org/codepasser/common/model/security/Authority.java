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

  /** 角色权限 */
  enum Role implements Authority {
    USER("用户", "系统普通用户"),
    ADMIN("系统管理员", "系统管理"),
    MGR("管理员", "管理"),
    CLIENT("客户端", "OAUTH2 客户端");

    private final String comment;
    private final String description;

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

  /** Session */
  enum Session implements Authority {
    AUTHORIZED("认证授权"),
    ANONYMOUS("未认证授权");

    private final String description;

    Session(String description) {
      this.description = description;
    }

    @Override
    public String comment() {
      return this.name();
    }

    @Override
    public String authority() {
      return "SESSION_" + this.name();
    }

    @Override
    public String description() {
      return this.description;
    }
  }

  /** 服务资源 */
  enum Service implements Authority {
    DAEMON_API("服务资源-API");

    private final String description;

    Service(String description) {
      this.description = description;
    }

    @Override
    public String comment() {
      return this.name();
    }

    @Override
    public String authority() {
      return "SERVICE_" + this.name();
    }

    @Override
    public String description() {
      return this.description;
    }
  }

  /** 范围控制 */
  enum Scope implements Authority {
    READ("read", "读取"),
    WRITE("write", "写入"),
    SIGN("sign", "签名");

    private final String comment;
    private final String description;

    Scope(String comment, String description) {
      this.comment = comment;
      this.description = description;
    }

    @Override
    public String comment() {
      return this.comment;
    }

    @Override
    public String authority() {
      return "SCOPE_" + this.name();
    }

    @Override
    public String description() {
      return this.description;
    }
  }

  /** 认证模式 */
  enum Grant implements Authority {
    // authorization_code：授权码类型。
    // implicit：          隐式授权类型。
    // password：          资源所有者(即用户)密码类型。
    // client_credentials：客户端凭据(客户端ID以及Key)类型。
    // refresh_token：     使用刷新令牌获取新的令牌(authorization_code/password两种模式下支持)
    AUTHORIZATION_CODE("authorization_code", "授权码类型"),
    IMPLICIT("implicit", "隐式授权类型"),
    PASSWORD("password", "资源所有者(即用户)密码类型"),
    CLIENT_CREDENTIALS("client_credentials", "客户端凭据(客户端ID以及Key)类型"),
    REFRESH_TOKEN("refresh_token", "使用刷新令牌获取新的令牌(authorization_code/password两种模式下支持)");

    private final String comment;
    private final String description;

    Grant(String comment, String description) {
      this.comment = comment;
      this.description = description;
    }

    @Override
    public String comment() {
      return this.comment;
    }

    @Override
    public String authority() {
      return "GRANT_" + this.name();
    }

    @Override
    public String description() {
      return this.description;
    }
  }
}
