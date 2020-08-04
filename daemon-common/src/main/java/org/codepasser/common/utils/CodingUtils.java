package org.codepasser.common.utils;

import org.codepasser.common.processor.annotation.InjectLogger;
import org.slf4j.Logger;
import org.springframework.util.Base64Utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * CodingUtils.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/19 : base version.
 */
public final class CodingUtils {

  @InjectLogger private static Logger logger;

  /**
   * 转换 Url encoding -> base64.
   *
   * @param arg 源
   * @return 结果
   * @throws Exception 异常
   */
  public static String encodeBase64URL(String arg) throws Exception {
    String content = new String(Base64Utils.encode(URLEncoder.encode(arg, "UTF-8").getBytes()));
    return content;
  }

  /**
   * 解码 -> base64 -> Url encoding .
   *
   * @param arg 源
   * @return 结果
   * @throws Exception 异常
   */
  public static String decodeBase64URL(String arg) throws Exception {
    String content = URLDecoder.decode(new String(Base64Utils.decode(arg.getBytes())), "UTF-8");
    return content;
  }

  /**
   * 转换 Url encoding.
   *
   * @param arg 源
   * @return 结果
   * @throws Exception 异常
   */
  public static String encodeURL(String arg) throws Exception {
    String content = URLEncoder.encode(arg, "UTF-8");
    return content;
  }

  /**
   * 解码 Url encoding.
   *
   * @param arg 源
   * @return 结果
   * @throws Exception 异常
   */
  public static String decodeURL(String arg) throws Exception {
    String content = URLDecoder.decode(arg, "UTF-8");
    return content;
  }
}
