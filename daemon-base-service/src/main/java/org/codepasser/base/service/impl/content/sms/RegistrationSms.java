package org.codepasser.base.service.impl.content.sms;

import org.codepasser.common.service.conifguration.sms.AbstractSms;
import org.codepasser.common.service.conifguration.sms.SmsConfiguration;
import org.codepasser.common.service.conifguration.template.FreemarkerTemplate;
import org.codepasser.common.service.conifguration.template.TemplateSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationSms extends AbstractSms {

  @TemplateSource(name = "/sms/registration.ftl")
  private FreemarkerTemplate template;

  @Autowired private SmsConfiguration smsConfiguration;

  @Override
  public SmsConfiguration getSmsConfiguration() {
    return this.smsConfiguration;
  }

  @Override
  public FreemarkerTemplate getTemplate() {
    return this.template;
  }
}
