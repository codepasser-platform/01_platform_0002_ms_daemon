package org.codepasser.common.processor;

import static org.springframework.util.ReflectionUtils.doWithFields;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import static org.springframework.util.ReflectionUtils.setField;

import org.codepasser.common.processor.annotation.InjectLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * LoggerPostProcessor.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Component
public class LoggerPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {

    Logger logger = LoggerFactory.getLogger(bean.getClass());
    doWithFields(
        bean.getClass(),
        field -> {
          makeAccessible(field);
          setField(field, bean, logger);
        },
        field ->
            field.isAnnotationPresent(InjectLogger.class) && Logger.class.equals(field.getType()));

    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object entity, String beanName)
      throws BeansException {
    return entity;
  }
}
