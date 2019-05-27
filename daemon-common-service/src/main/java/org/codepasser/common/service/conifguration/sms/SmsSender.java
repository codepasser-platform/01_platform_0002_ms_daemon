package org.codepasser.common.service.conifguration.sms;

import static com.google.common.collect.ImmutableMap.of;

import com.google.common.util.concurrent.UncheckedExecutionException;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import org.codepasser.common.service.conifguration.template.FreemarkerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClientException;

public final class SmsSender {

  private static final Logger logger = LoggerFactory.getLogger(SmsSender.class);

  private SmsChannelProvider provider;
  private FreemarkerTemplate template;
  private Map<String, Serializable> data;
  private String to;

  public static SmsSender newSmsSender() {
    return new SmsSender();
  }

  public SmsSender provider(SmsChannelProvider provider) {
    this.provider = provider;
    return this;
  }

  public SmsSender to(String to) {
    this.to = to;
    return this;
  }

  public SmsSender template(FreemarkerTemplate template) {
    this.template = template;
    return this;
  }

  public SmsSender data(Map<String, Serializable> data) {
    this.data = data;
    return this;
  }

  public void send(Locale language, int retryCount) {
    try {
      template.accept(language);
      String content = template.process(data);
      recurrentSendSms(() -> provider.send(to, content), retryCount);
    } catch (TemplateException | IOException e) {
      throw new UncheckedExecutionException(e);
    }
  }

  private void recurrentSendSms(Runnable runnable, int retryCount) {
    RetryTemplate template = new RetryTemplate();
    template.setRetryPolicy(
        new SimpleRetryPolicy(retryCount, of(RestClientException.class, true), true));
    template.execute(
        context -> {
          if (context.getRetryCount() > 0) {
            logger.warn("Send sms failed. Retrying [{}].", context.getRetryCount());
          }
          runnable.run();
          return null;
        },
        (context -> {
          logger.warn("Can not send sms.", context.getLastThrowable());
          return null;
        }));
  }
}
