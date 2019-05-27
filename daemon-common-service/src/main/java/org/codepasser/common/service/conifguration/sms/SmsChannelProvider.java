package org.codepasser.common.service.conifguration.sms;

/**
 * SmsChannelProvider.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/20 : base version.
 */
public interface SmsChannelProvider {
  Long send(String mobile, String content);
}
