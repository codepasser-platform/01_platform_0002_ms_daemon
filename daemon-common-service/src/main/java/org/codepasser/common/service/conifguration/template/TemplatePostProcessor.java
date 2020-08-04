package org.codepasser.common.service.conifguration.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.springframework.util.ReflectionUtils.doWithFields;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import static org.springframework.util.ReflectionUtils.setField;

@Component
@ConditionalOnBean(TemplateConfiguration.class)
public class TemplatePostProcessor implements BeanPostProcessor {

  private static final Logger logger = LoggerFactory.getLogger(TemplatePostProcessor.class);

  @Autowired private TemplateConfiguration configuration;

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    doWithFields(
        bean.getClass(),
        field -> {
          makeAccessible(field);
          setField(field, bean, getTemplate(field));
        },
        field ->
            field.isAnnotationPresent(TemplateSource.class)
                && FreemarkerTemplate.class.equals(field.getType()));

    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }

  private FreemarkerTemplate getTemplate(Field field) {
    TemplateSource template = field.getAnnotation(TemplateSource.class);
    try {
      return configuration.findTemplate(template.name());
    } catch (IOException e) {
      logger.error(
          "can not inject the template resource \"[]\", because error occurs: []",
          template.name(),
          e.getMessage());
    }

    return null;
  }
}
