package org.codepasser.common.service.conifguration.mail;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.codepasser.common.service.conifguration.template.FreemarkerTemplate;
import org.springframework.mail.javamail.JavaMailSender;

public interface MailSenderDsl {

  AfterGivenSender sender(JavaMailSender sender);

  interface GivenTemplate {
    AfterGivenTemplate template(FreemarkerTemplate template);
  }

  interface GivenData {
    AfterGivenData data(Map<String, Serializable> data);
  }

  interface GivenFrom {
    AfterGivenFrom from(String from);
  }

  interface GivenTo {
    AfterGivenTo to(List<String> to);

    AfterGivenTo to(String to);
  }

  interface GivenBcc {
    AfterGivenBcc bcc(List<String> bcc);

    AfterGivenBcc bcc(String bcc);
  }

  interface GivenSubject {
    AfterGivenSubject subject(String subject);
  }

  interface Send {
    void send(Locale language);

    void send(Locale language, int retryCount);
  }

  interface AfterGivenSender extends GivenTemplate {}

  interface AfterGivenTemplate extends GivenData, GivenFrom {}

  interface AfterGivenData extends GivenFrom {}

  interface AfterGivenFrom extends GivenTo, GivenBcc {}

  interface AfterGivenTo extends GivenBcc, GivenSubject {}

  interface AfterGivenBcc extends GivenSubject {}

  interface AfterGivenSubject extends Send {}
}
