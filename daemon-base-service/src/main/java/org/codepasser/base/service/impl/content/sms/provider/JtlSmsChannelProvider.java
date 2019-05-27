package org.codepasser.base.service.impl.content.sms.provider;

import static org.codepasser.common.service.conifguration.sms.SmsConfiguration.SmsChannel.JTL;

import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.conifguration.sms.SmsChannelProvider;
import org.codepasser.common.service.conifguration.sms.SmsConfiguration;
import org.codepasser.common.utils.CodingUtils;
import org.codepasser.common.utils.IdGenerator;
import org.codepasser.common.utils.Json;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

/**
 * JtlSmsChannelProvider.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/20 : base version.
 */
@Component("jtlSmsChannelProvider")
public class JtlSmsChannelProvider implements SmsChannelProvider {
  private static final String RESULT_SUCCESS = "\"0\"";
  @InjectLogger private Logger logger;
  @Autowired private SmsConfiguration smsConfiguration;

  @Override
  public Long send(String mobile, String content) {

    if (!smsConfiguration.getProperties().isEmpty()) {
      String PREFIX = smsConfiguration.getChannel().toString().toLowerCase();
      // 接口地址
      //    jtl.api: http://58.68.247.137:9053/communication/sendSms.ashx
      // 客户端ID（必选）
      //    jtl.cid: 8199
      // 客户端密码（必选）
      //    jtl.pwd: Jzzx8199
      // 通道组id（必选）
      //    jtl.productid: 20171212
      // 短信类型（可选）:15普通短信,32长短信
      //    jtl.format: 32
      // 自定义签名（可选）
      //    jtl.sign: 【xxxx】

      // 短信唯一标识（可选）用于匹配状态报告
      Long ssid = IdGenerator.next();
      String api = smsConfiguration.getProperties().get(PREFIX + ".api");
      String cid = smsConfiguration.getProperties().get(PREFIX + ".cid");
      String pwd = smsConfiguration.getProperties().get(PREFIX + ".pwd");
      String productid = smsConfiguration.getProperties().get(PREFIX + ".productid");
      String format = smsConfiguration.getProperties().get(PREFIX + ".format");
      String sign = smsConfiguration.getProperties().get(PREFIX + ".sign");

      StringBuilder params = new StringBuilder();
      try {
        params
            .append("cid=")
            .append(CodingUtils.encodeBase64URL(cid))
            .append("&")
            .append("pwd=")
            .append(CodingUtils.encodeBase64URL(pwd))
            .append("&")
            .append("productid=")
            .append(CodingUtils.encodeURL(productid))
            .append("&")
            .append("mobile=")
            .append(CodingUtils.encodeBase64URL(mobile + ""))
            .append("&")
            .append("content=")
            .append(CodingUtils.encodeBase64URL(content))
            .append("&")
            .append("sign=")
            .append(CodingUtils.encodeBase64URL(sign))
            .append("&")
            .append("format=")
            .append(format)
            .append("&")
            .append("ssid=")
            .append(ssid)
            .append("&")
            .append("lcode=")
            .append("&")
            .append("custom=");
      } catch (Exception e) {
        logger.error(
            "Send sms provider send sms fail, current channel: [{}], phone NO. is [{}], please confirm that there is a mistake in the configuration.",
            JTL,
            mobile);
        return null;
      }

      String result = null;
      try {
        result = HttpUtil.sendPostRequestByParam(api, params.toString());
      } catch (Exception e) {
        throw new RestClientException("Send sms provider send sms fail", e);
      }
      if (RESULT_SUCCESS.equals(Json.readNode(result, "status"))) {
        logger.info(
            "Send sms provider is enable，current channel: [{}], The phone NO. is [{}] will be receive the SMS. status:{}",
            JTL,
            mobile,
            Json.readNode(result, "status"));
        return ssid;
      }
      logger.error(
          "Send sms provider send sms fail, current channel: [{}], phone NO. is [{}], cause by: {}, status: {}",
          JTL,
          mobile,
          result,
          Json.readNode(result, "status"));
    }
    logger.error(
        "Send sms provider send sms fail, current channel: [{}], phone NO. is [{}], please confirm that there is a mistake in the configuration.",
        JTL,
        mobile);
    return null;
  }
}
