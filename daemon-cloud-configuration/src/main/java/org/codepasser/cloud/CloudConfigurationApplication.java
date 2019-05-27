package org.codepasser.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * [ConfigurationServerApplication].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@SpringBootApplication
@EnableConfigServer
public class CloudConfigurationApplication {

  public static void main(String[] args) {
    SpringApplication.run(CloudConfigurationApplication.class, args);
  }
}
