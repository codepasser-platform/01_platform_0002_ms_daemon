package org.codepasser.common.service.conifguration.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
@ConditionalOnClass({MimeMessage.class, MimeType.class})
@EnableConfigurationProperties(MailProperties.class)
public class MailSenderConfiguration {

  @Autowired private MailProperties properties;

  @Bean
  @Scope(SCOPE_PROTOTYPE)
  public JavaMailSenderImpl mailSender() {
    JavaMailSenderImpl sender = new JavaMailSenderImpl();
    applyProperties(sender);
    return sender;
  }

  private void applyProperties(JavaMailSenderImpl sender) {
    sender.setHost(this.properties.getHost());
    if (this.properties.getPort() != null) {
      sender.setPort(this.properties.getPort());
    }
    sender.setUsername(this.properties.getUsername());
    sender.setPassword(this.properties.getPassword());
    sender.setDefaultEncoding(this.properties.getDefaultEncoding().name());
    if (!this.properties.getProperties().isEmpty()) {
      Properties properties = new Properties();
      properties.putAll(this.properties.getProperties());
      sender.setJavaMailProperties(properties);
    }
  }
}
