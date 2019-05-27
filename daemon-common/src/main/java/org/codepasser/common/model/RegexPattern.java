package org.codepasser.common.model;

/**
 * RegexPattern.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public interface RegexPattern {

  /*
   * user name could be chinese, japanese, or other language. not do not allowed the special
   * characters.
   */
  String REGEX_USER_NAME = "^[a-zA-Z0-9_-]{4,16}$"; // length is 4 ~ 16

  String REGEX_GENERAL_NAME = "^[^!\\s*$%^&*()+|~=`{}\\[\\]:/;<>?,.@#'\"\\\\]{1,30}$";

  String REGEX_USER_PASSWORD = "^[\\s\\S]{6,20}$";

  String REGEX_PHONE = "^(1[3578][0-9]{9}|(\\d{3,4}-)\\d{7,8}(-\\d{1,4})?)$";

  String REGEX_MAIL = "^[^@\\s]+@(?:[^@\\s.]+)(?:\\.[^@\\s.]+)+$";

  String REGEX_IDENTIFYING_CODE = "^\\d{6}$";

  String REGEX_FAX = "^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$";

  String REGEX_POST_CODE = "^[0-9]{6}$";

  String REGEX_ID_CARD =
      "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
}
