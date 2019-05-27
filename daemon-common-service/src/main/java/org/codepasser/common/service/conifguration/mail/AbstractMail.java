package org.codepasser.common.service.conifguration.mail;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Locale.CHINESE;
import static org.codepasser.common.service.conifguration.mail.MailSender.newMailSender;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.codepasser.common.async.AsyncCaller;
import org.codepasser.common.model.security.UserBasic;
import org.codepasser.common.service.conifguration.template.FreemarkerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public abstract class AbstractMail implements Mail {

  @Autowired private AsyncCaller asyncCaller;

  @Autowired private JavaMailSenderImpl javaMailSender;

  @Autowired private MailConfiguration mailConfiguration;

  @PostConstruct
  private void init() {
    javaMailSender.setUsername(mailConfiguration.getUserName());
    javaMailSender.setPassword(mailConfiguration.getPassword());
  }

  @Override
  public void send(UserBasic user, Map<String, Serializable> data) {
    String nick = mailConfiguration.getNickName();

    asyncCaller.asyncCall(
        () -> {
          String senderAddress = "ORG.CODEPASSER";
          try {
            senderAddress =
                new InternetAddress(nick + " <" + mailConfiguration.getUserName() + ">").toString();
          } catch (AddressException e) {
            e.printStackTrace();
          }
          HashMap<String, Serializable> templateData =
              data != null ? newHashMap(data) : new HashMap<>();
          updateTemplateData(user, templateData);
          newMailSender()
              .sender(javaMailSender)
              .template(getTemplate())
              .data(templateData)
              .from(senderAddress)
              .to(user.getEmail())
              .subject(getMailSubjectConfiguration().getSubject())
              .send(CHINESE, mailConfiguration.getRetryCount());
        });
  }

  void updateTemplateData(UserBasic user, Map<String, Serializable> data) {
    data.putIfAbsent("user", user);
  }

  public abstract MailSubjectConfiguration getMailSubjectConfiguration();

  public abstract FreemarkerTemplate getTemplate();
}
