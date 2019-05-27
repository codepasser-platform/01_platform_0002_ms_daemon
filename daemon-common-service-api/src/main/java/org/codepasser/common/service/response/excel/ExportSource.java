package org.codepasser.common.service.response.excel;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface ExportSource {

  String header();

  int width() default 4000;

  ExportSourceType type() default ExportSourceType.TEXT;
}
