package org.codepasser.common.web.configuration.security.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * MethodPermissionEvaluator.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2020/6/3 : base version.
 */
@Configuration
public class MethodPermissionEvaluator implements PermissionEvaluator {
  /**
   * 普通的targetDomainObject判断.
   *
   * @param authentication 身份鉴权.
   * @param targetDomainObject 访问目标.
   * @param permission 权限点.
   * @return 鉴权结果.
   */
  @Override
  public boolean hasPermission(
      Authentication authentication, Object targetDomainObject, Object permission) {
    // TODO user permission implements
    return true;
  }

  /**
   * ACL 鉴权.
   *
   * @param authentication 身份鉴权.
   * @param targetId 目标ID.
   * @param targetType 目标类型.
   * @param permission 权限点.
   * @return 鉴权结果.
   */
  @Override
  public boolean hasPermission(
      Authentication authentication, Serializable targetId, String targetType, Object permission) {
    return true;
  }
}
