package org.codepasser.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestUtils.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class RequestUtils {

  public static String getArguments(HttpServletRequest request) {
    String arguments = null;
    try {
      arguments = Json.writeValueAsString(request.getParameterMap());
    } catch (Exception ex) {
      // watch ignore
    }
    return arguments;
  }
}
