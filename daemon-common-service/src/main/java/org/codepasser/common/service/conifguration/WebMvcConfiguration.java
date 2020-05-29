package org.codepasser.common.service.conifguration;

import org.codepasser.common.service.conifguration.interceptor.UserBehaviorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  /**
   * Interceptors.
   *
   * @param registry registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(userBehaviorInterceptor())
        .excludePathPatterns("/static/**")
        .excludePathPatterns("/library/**");
  }

  @Bean
  public HandlerInterceptor userBehaviorInterceptor() {
    return new UserBehaviorInterceptor();
  }
}
