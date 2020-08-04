package org.codepasser.base.service.impl.content.sms.provider;

import com.google.common.base.Splitter;

import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.conifguration.sms.SmsChannelProvider;
import org.codepasser.common.service.conifguration.sms.SmsConfiguration;
import org.codepasser.common.utils.IdGenerator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import javax.annotation.Nullable;

import static org.codepasser.common.service.conifguration.sms.SmsConfiguration.SmsChannel.DYXX;

/**
 * DyxxSmsChannelProvider.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/20 : base version.
 */
@Component("dyxxSmsChannelProvider")
public class DyxxSmsChannelProvider implements SmsChannelProvider {

  private static final String PARAM_USER_NAME = "name";
  private static final String PARAM_USER_PWD = "pwd";
  private static final String PARAM_MOBILE = "mobile";
  private static final String PARAM_CONTENT = "content";
  private static final String PARAM_SIGN = "sign";
  private static final String PARAM_TYPE = "type";
  private static final String RESULT_SUCCESS = "0";
  @InjectLogger private Logger logger;
  @Autowired private SmsConfiguration smsConfiguration;
  @Autowired private RestTemplate restTemplate;

  @Nullable
  @Override
  public Long send(String mobile, String content) {
    if (!smsConfiguration.getProperties().isEmpty()) {
      String PREFIX = smsConfiguration.getChannel().toString().toLowerCase();

      // 接口地址
      //      dyxx.api: http://sms.1xinxi.cn/asmx/smsservice.aspx
      // 类型
      //      dyxx.type: pt
      // 签名
      //      dyxx.sign: 【xxxx】
      String api = smsConfiguration.getProperties().get(PREFIX + ".api");
      String username = smsConfiguration.getProperties().get(PREFIX + ".username");
      String pwd = smsConfiguration.getProperties().get(PREFIX + ".pwd");
      String type = smsConfiguration.getProperties().get(PREFIX + ".type");
      String sign = smsConfiguration.getProperties().get(PREFIX + ".sign");

      MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
      parameters.add(PARAM_USER_NAME, username);
      parameters.add(PARAM_USER_PWD, pwd);
      parameters.add(PARAM_TYPE, type);
      parameters.add(PARAM_SIGN, sign);
      parameters.add(PARAM_MOBILE, mobile);
      parameters.add(PARAM_CONTENT, content);

      String result = restTemplate.postForObject(api, parameters, String.class);
      List<String> resultList = Splitter.on(",").splitToList(result);
      if (RESULT_SUCCESS.equals(resultList.get(0))) {
        logger.info(
            "Send sms provider is enable，current channel: [{}], The phone NO. is [{}] will be receive the SMS.",
            DYXX,
            mobile);
        return IdGenerator.next();
      }
      logger.error(
          "Send sms provider send sms fail, current channel: [{}], phone NO. is [{}], cause by: {}",
          DYXX,
          mobile,
          resultList);
    }
    logger.error(
        "Send sms provider send sms fail, current channel: [{}], phone NO. is [{}], please confirm that there is a mistake in the configuration.",
        DYXX,
        mobile);
    return null;
  }
}
