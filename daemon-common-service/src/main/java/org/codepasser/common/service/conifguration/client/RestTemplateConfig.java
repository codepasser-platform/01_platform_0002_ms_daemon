package org.codepasser.common.service.conifguration.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateConfig.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/20 : base version.
 */
@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate() {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setReadTimeout(10000); // ms
    factory.setConnectTimeout(30000); // ms
    return new RestTemplate(factory);
  }
}
