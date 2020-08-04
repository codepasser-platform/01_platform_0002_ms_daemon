package org.codepasser.common.service.conifguration.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.codepasser.common.service.conifguration.sms.SmsConfiguration.SmsChannel.JTL;

/**
 * SmsConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/20 : base version.
 */
@Configuration
@ConfigurationProperties(prefix = "daemon.sms")
public class SmsConfiguration {

  private boolean enable;
  private String sign;
  private SmsChannel channel = JTL;
  private int retryCount = 3;
  private Map<String, String> properties;

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public SmsChannel getChannel() {
    return channel;
  }

  public void setChannel(SmsChannel channel) {
    this.channel = channel;
  }

  public int getRetryCount() {
    return retryCount;
  }

  public void setRetryCount(int retryCount) {
    this.retryCount = retryCount;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public enum SmsChannel {
    JTL,
    DYXX
  }
}
