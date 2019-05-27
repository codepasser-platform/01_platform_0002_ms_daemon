package org.codepasser.base.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * ServiceBaseApplication.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"org.codepasser.common", "org.codepasser.base"})
@EntityScan(basePackages = {"org.codepasser.base.model.entity"})
@EnableJpaRepositories(basePackages = {"org.codepasser.base.dao.repository"})
@MapperScan(basePackages = "org.codepasser.base.dao.repository")
@EnableMongoRepositories(basePackages = {"org.codepasser.base.dao.repository"})
@EnableElasticsearchRepositories(basePackages = {"org.codepasser.base.dao.repository"})
public class ServiceBaseApplication {

  public static void main(String[] args) {

    /*
     * 程序的其他地方使用了Netty，这里指redis。这影响在实例化传输客户端之前初始化处理器的数量。 实例化传输客户端时，我们尝试初始化处理器的数量。
     * 由于在其他地方使用Netty，因此已经初始化并且Netty会对此进行防范，因此首次实例化会因看到的非法状态异常而失败。
     */
    System.setProperty("es.set.netty.runtime.available.processors", "false");

    SpringApplication.run(ServiceBaseApplication.class, args);
  }
}
