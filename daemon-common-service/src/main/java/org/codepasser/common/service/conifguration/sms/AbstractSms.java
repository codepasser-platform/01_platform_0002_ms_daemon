package org.codepasser.common.service.conifguration.sms;

import org.codepasser.common.async.AsyncCaller;
import org.codepasser.common.model.security.UserBasic;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.conifguration.template.FreemarkerTemplate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Locale.CHINESE;
import static org.codepasser.common.service.conifguration.sms.SmsSender.newSmsSender;

public abstract class AbstractSms implements Sms {

  @InjectLogger private static Logger logger;

  @Autowired private AsyncCaller asyncCaller;

  @Autowired
  @Qualifier("jtlSmsChannelProvider")
  private SmsChannelProvider jtlSmsChannelProvider;

  @Autowired
  @Qualifier("dyxxSmsChannelProvider")
  private SmsChannelProvider dyxxSmsChannelProvider;

  @Override
  public void send(UserBasic user, Map<String, Serializable> data) {
    if (!getSmsConfiguration().isEnable()) {
      // sms gate
      logger.warn(
          "Send sms provider is disabled，current channel: [{}], The number is [{}] unable to receive the SMS.",
          getSmsConfiguration().getChannel(),
          user.getPhone());
      return;
    }
    asyncCaller.asyncCall(
        () -> {
          // sms provider settings
          SmsChannelProvider provider = jtlSmsChannelProvider;
          if (SmsConfiguration.SmsChannel.JTL == getSmsConfiguration().getChannel()) {
            provider = jtlSmsChannelProvider;
          }
          if (SmsConfiguration.SmsChannel.DYXX == getSmsConfiguration().getChannel()) {
            provider = dyxxSmsChannelProvider;
          }
          // sms content settings
          HashMap<String, Serializable> templateData =
              data != null ? newHashMap(data) : new HashMap<>();
          updateTemplateData(user, templateData);
          newSmsSender()
              .provider(provider)
              .template(getTemplate())
              .data(templateData)
              .to(user.getPhone())
              .send(CHINESE, getSmsConfiguration().getRetryCount());
        });
  }

  void updateTemplateData(UserBasic user, Map<String, Serializable> data) {
    data.putIfAbsent("user", user);
  }

  public abstract SmsConfiguration getSmsConfiguration();

  public abstract FreemarkerTemplate getTemplate();
}
