package org.codepasser.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * [ServerRegistryApplication].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class CloudRegistryApplication {

  public static void main(String[] args) {
    SpringApplication.run(CloudRegistryApplication.class, args);
  }
}
