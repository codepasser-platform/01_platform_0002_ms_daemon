package org.codepasser.base.service.impl.cell;

import static com.google.common.collect.Maps.newHashMap;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nonnull;
import org.codepasser.base.service.basement.vo.IdentifyingCode;
import org.codepasser.base.service.impl.content.sms.RegistrationSms;
import org.codepasser.common.model.security.UserBasic;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.conifguration.sms.AbstractSms;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SmsCell.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/19 : base version.
 */
@Component
public class SmsCell {

  @InjectLogger private static Logger logger;

  @Autowired private RegistrationSms registrationSms;

  /**
   * 注册验证码短信.
   *
   * @param phone
   * @param identifyingCode
   */
  public void sendRegistrationTokenSms(
      @Nonnull String phone, @Nonnull IdentifyingCode identifyingCode) {
    Map<String, Serializable> templateData = newHashMap();
    templateData.put("token", identifyingCode.getCode());
    sendSms(registrationSms, phone, templateData);
  }

  /**
   * 抽取发送短信共通方法.
   *
   * @param smsImpl
   * @param phone
   * @param templateData
   */
  private void sendSms(
      @Nonnull AbstractSms smsImpl,
      @Nonnull String phone,
      @Nonnull Map<String, Serializable> templateData) {
    UserBasic userBasic = new UserBasic();
    userBasic.setPhone(phone);
    String sign = smsImpl.getSmsConfiguration().getSign();
    templateData.put("sign", sign);
    smsImpl.send(userBasic, templateData);
  }
}
