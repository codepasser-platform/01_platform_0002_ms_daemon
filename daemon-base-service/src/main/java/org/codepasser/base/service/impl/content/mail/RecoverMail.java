package org.codepasser.base.service.impl.content.mail;

import org.codepasser.base.service.impl.content.mail.subject.RecoverMailSubjectConfiguration;
import org.codepasser.common.service.conifguration.mail.AbstractMail;
import org.codepasser.common.service.conifguration.mail.MailSubjectConfiguration;
import org.codepasser.common.service.conifguration.template.FreemarkerTemplate;
import org.codepasser.common.service.conifguration.template.TemplateSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RecoverMail extends AbstractMail {

  @TemplateSource(name = "/mail/recover.ftl")
  private FreemarkerTemplate template;

  @Autowired
  @Qualifier("recoverMailSubjectConfiguration")
  private MailSubjectConfiguration mailSubjectConfiguration;

  @Override
  public MailSubjectConfiguration getMailSubjectConfiguration() {
    return this.mailSubjectConfiguration;
  }

  @Override
  public FreemarkerTemplate getTemplate() {
    return this.template;
  }

  @Bean(name = "recoverMailSubjectConfiguration")
  @ConfigurationProperties(prefix = "daemon.mail.recover")
  public RecoverMailSubjectConfiguration recoverMailSubjectConfiguration() {
    return new RecoverMailSubjectConfiguration();
  }
}
