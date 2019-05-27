package org.codepasser.common.service.conifguration.mail;

import static com.google.common.collect.ImmutableMap.of;

import com.google.common.util.concurrent.UncheckedExecutionException;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.conifguration.template.FreemarkerTemplate;
import org.slf4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public final class MailSender
    implements MailSenderDsl,
        MailSenderDsl.AfterGivenSender,
        MailSenderDsl.AfterGivenTemplate,
        MailSenderDsl.AfterGivenData,
        MailSenderDsl.AfterGivenFrom,
        MailSenderDsl.AfterGivenTo,
        MailSenderDsl.AfterGivenBcc,
        MailSenderDsl.AfterGivenSubject {

  @InjectLogger private Logger logger;

  private JavaMailSender sender;

  private String from;
  private List<String> to = new ArrayList<>();
  private List<String> bcc = new ArrayList<>();

  private String subject;
  private FreemarkerTemplate template;
  private Map<String, Serializable> data;

  public static MailSenderDsl newMailSender() {
    return new MailSender();
  }

  public AfterGivenSender sender(JavaMailSender sender) {
    this.sender = sender;
    return this;
  }

  public AfterGivenTemplate template(FreemarkerTemplate template) {
    this.template = template;
    return this;
  }

  public AfterGivenData data(Map<String, Serializable> data) {
    this.data = data;
    return this;
  }

  public AfterGivenFrom from(String from) {
    this.from = from;
    return this;
  }

  public AfterGivenTo to(List<String> to) {
    this.to.addAll(to);
    return this;
  }

  @Override
  public AfterGivenTo to(String to) {
    this.to.add(to);
    return this;
  }

  @Override
  public AfterGivenBcc bcc(List<String> bcc) {
    this.bcc.addAll(bcc);
    return this;
  }

  @Override
  public AfterGivenBcc bcc(String bcc) {
    this.bcc.add(bcc);
    return this;
  }

  public AfterGivenSubject subject(String subject) {
    this.subject = subject;
    return this;
  }

  public void send(Locale language) {
    send(language, 3);
  }

  public void send(Locale language, int retryCount) {
    MimeMessage message = sender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    try {
      template.accept(language);
      helper.setFrom(from);
      helper.setTo(to.stream().toArray(String[]::new));
      helper.setBcc(bcc.stream().toArray(String[]::new));
      helper.setSubject(subject);
      helper.setText(template.process(data), true);
      recurrentSendMail(() -> sender.send(message), retryCount);
    } catch (MessagingException | TemplateException | IOException e) {
      throw new UncheckedExecutionException(e);
    }
  }

  private void recurrentSendMail(Runnable runnable, int retryCount) {
    RetryTemplate template = new RetryTemplate();
    template.setRetryPolicy(new SimpleRetryPolicy(retryCount, of(MailException.class, true), true));
    template.execute(
        context -> {
          if (context.getRetryCount() > 0) {
            logger.warn("Send mail failed. Retrying [{}].", context.getRetryCount());
          }
          runnable.run();
          return null;
        },
        (context -> {
          logger.warn("Can not send mail.", context.getLastThrowable());
          return null;
        }));
  }
}
