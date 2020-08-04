package org.codepasser.common.service.configuration.cloud;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * FeignBeanFactoryPostProcessor.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Component
public class FeignBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

  /*
   * @Deprecated with spring-cloud-dependencies Finchley.RELEASE
   *
   * The following exception will be shown on shutdown:
   * org.springframework.beans.factory.BeanCreationNotAllowedException: Error creating bean with
   * name 'eurekaAutoServiceRegistration': Singleton bean creation not allowed while singletons
   * of this factory are in destruction (Do not request a bean from a BeanFactory in a destroy
   * method implementation!)
   *
   * https://github.com/spring-cloud/spring-cloud-netflix/issues/1952
   *
   * With Spring Cloud Edgware, the previous workaround doesn't work anymore. The right workaround.
   * should be:
   */

  /**
   * FIXED BUG.
   *
   * @param beanFactory beanFactory
   * @throws BeansException exception
   */
  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    if (containsBeanDefinition(beanFactory, "feignContext", "eurekaAutoServiceRegistration")) {
      BeanDefinition bd = beanFactory.getBeanDefinition("feignContext");
      bd.setDependsOn("eurekaAutoServiceRegistration");
    }
  }

  private boolean containsBeanDefinition(
      ConfigurableListableBeanFactory beanFactory, String... beans) {
    return Arrays.stream(beans).allMatch(b -> beanFactory.containsBeanDefinition(b));
  }
}
