package org.codepasser.base.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * WebApiApplication.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"org.codepasser.common.service", "org.codepasser.base.service"})
@ComponentScan(basePackages = {"org.codepasser.common", "org.codepasser.base.web"})
public class WebCasApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebCasApplication.class, args);
  }
}
