package org.codepasser.base.model.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * UserIdentifier.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Constraint(validatedBy = {UserIdentifierValidator.class})
@Target({TYPE, FIELD, PARAMETER})
@Retention(value = RUNTIME)
@Documented
public @interface UserIdentifier {

  /**
   * 验证消息.
   *
   * @return 消息
   */
  String message() default "{UserIdentifier.message}";

  /**
   * 校验组.
   *
   * @return 校验组
   */
  Class<?>[] groups() default {};

  /**
   * 报文.
   *
   * @return 报文
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * email 方法名.
   *
   * @return 方法名
   */
  String emailMethod() default "getEmail";

  /**
   * phone number 方法名.
   *
   * @return 方法名
   */
  String phoneNumberMethod() default "getPhone";
}
