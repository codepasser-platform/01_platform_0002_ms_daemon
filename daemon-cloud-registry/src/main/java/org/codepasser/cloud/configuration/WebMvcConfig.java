package org.codepasser.cloud.configuration;

import org.codepasser.cloud.configuration.logger.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  /**
   * Interceptors.
   *
   * @param registry registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(logInterceptor())
        .excludePathPatterns("/static/**")
        .excludePathPatterns("/library/**")
        .excludePathPatterns("/eureka/js/**")
        .excludePathPatterns("/eureka/css/**")
        .excludePathPatterns("/eureka/images/**")
        .excludePathPatterns("/eureka/fonts/**");
  }

  @Bean
  public HandlerInterceptor logInterceptor() {
    return new LogInterceptor();
  }
}
