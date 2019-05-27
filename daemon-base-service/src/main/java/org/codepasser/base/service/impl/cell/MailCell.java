package org.codepasser.base.service.impl.cell;

import static com.google.common.collect.Maps.newHashMap;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nonnull;
import org.codepasser.base.service.basement.vo.IdentifyingCode;
import org.codepasser.base.service.impl.content.mail.RecoverMail;
import org.codepasser.base.service.impl.content.mail.RegistrationMail;
import org.codepasser.common.model.security.UserBasic;
import org.codepasser.common.service.conifguration.mail.AbstractMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MailCell.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/19 : base version.
 */
@Component
public class MailCell {

  @Autowired private RegistrationMail registrationMail;

  @Autowired private RecoverMail passwordMail;

  public void sendRegistrationTokenMail(
      @Nonnull String email, @Nonnull IdentifyingCode identifyingCode) {
    Map<String, Serializable> templateData = newHashMap();
    templateData.put("token", identifyingCode.getCode());
    sendMail(registrationMail, email, templateData);
  }

  public void sendRecoverTokenMail(
      @Nonnull String email, @Nonnull IdentifyingCode identifyingCode) {
    Map<String, Serializable> templateData = newHashMap();
    templateData.put("token", identifyingCode.getCode());
    sendMail(passwordMail, email, templateData);
  }

  private void sendMail(
      @Nonnull AbstractMail mailImpl,
      @Nonnull String email,
      @Nonnull Map<String, Serializable> templateData) {
    UserBasic userBasic = new UserBasic();
    userBasic.setEmail(email);
    mailImpl.send(userBasic, templateData);
  }
}
