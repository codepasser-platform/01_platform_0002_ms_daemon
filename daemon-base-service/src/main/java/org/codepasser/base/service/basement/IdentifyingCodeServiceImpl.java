package org.codepasser.base.service.basement;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableBiMap.of;
import static org.codepasser.base.model.business.category.IdentifyingCodeType.REGISTRATION_EMAIL;
import static org.codepasser.base.model.business.category.IdentifyingCodeType.REGISTRATION_PHONE;
import static org.codepasser.common.model.RegexPattern.REGEX_IDENTIFYING_CODE;
import static org.codepasser.common.model.RegexPattern.REGEX_MAIL;
import static org.codepasser.common.model.RegexPattern.REGEX_PHONE;
import static org.codepasser.common.service.exception.IllegalTermsException.Error.IDENTIFYING_CODE;
import static org.codepasser.common.service.exception.NotFoundException.Error.DATA;
import static org.codepasser.common.utils.Conditions.checkState;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import org.codepasser.base.dao.repository.redis.IdentifyingCodeRepository;
import org.codepasser.base.model.business.category.IdentifyingCodeType;
import org.codepasser.base.service.basement.vo.IdentifyingCode;
import org.codepasser.base.service.impl.cell.UserCell;
import org.codepasser.common.exception.CommonException;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.conifguration.ServiceConfiguration.ServiceSettings;
import org.codepasser.common.service.exception.IllegalTermsException;
import org.codepasser.common.service.exception.NotFoundException;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * IdentifyingCodeServiceImpl.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Service
@RestController
public class IdentifyingCodeServiceImpl implements IdentifyingCodeService {

  @InjectLogger private Logger logger;

  @Autowired private IdentifyingCodeRepository identifyingCodeRepository;

  @Autowired private ServiceSettings serviceSettings;

  //  @Autowired private MailCell mailCell;

  //  @Autowired private SmsCell smsCell;

  @Autowired private UserCell userCell;

  @Nonnull
  @Override
  public AssertResponse sendIdentifyingCode(@Nonnull @RequestParam("target") String target)
      throws ServiceException {
    checkArgument(target.matches(REGEX_PHONE), "invalid phone number");
    if (userCell.existByPhone(target)) {
      return new AssertResponse(false);
    }
    IdentifyingCode identifyingCode = getIdentifyingCode(target, REGISTRATION_PHONE);
    // TODO sms
    //      smsCell.sendRegistrationTokenSms(phone, identifyingCode);
    logger.info("smsCell.sendRegistrationTokenSms(target, identifyingCode)");
    return new AssertResponse(true);
  }

  @Nonnull
  @Override
  public AssertResponse checkIdentifyingCode(
      @Nonnull @RequestParam("target") String target, @Nonnull @RequestParam("code") String code)
      throws ServiceException {
    checkArgument(target.matches(REGEX_PHONE), "invalid phone number");
    checkArgument(code.matches(REGEX_IDENTIFYING_CODE), "invalid identifying code");
    boolean assets =
        identifyingCodeRepository.isIdentifyingCodeValidate(REGISTRATION_PHONE, target, code);
    if (assets) { // 赎回认证码
      identifyingCodeRepository.redeemIdentifyingCode(REGISTRATION_PHONE, target, code);
    }
    return new AssertResponse(assets);
  }

  @Nonnull
  @Override
  public AssertResponse sendIdentifyingCodeByEmail(@Nonnull @RequestParam("target") String target)
      throws ServiceException {
    checkArgument(target.matches(REGEX_MAIL), "invalid email account");
    if (userCell.existByEmail(target)) {
      return new AssertResponse(false);
    }
    IdentifyingCode identifyingCode = getIdentifyingCode(target, REGISTRATION_EMAIL);
    // TODO mail
    //    mailCell.sendRegistrationTokenMail(email, identifyingCode);
    logger.info("mailCell.sendRegistrationTokenMail.send(target, identifyingCode)");
    return new AssertResponse(true);
  }

  @Nonnull
  @Override
  public AssertResponse checkIdentifyingCodeByEmail(
      @Nonnull @RequestParam("target") String target, @Nonnull @RequestParam("code") String code)
      throws ServiceException {
    checkArgument(target.matches(REGEX_MAIL), "invalid email account");
    checkArgument(code.matches(REGEX_IDENTIFYING_CODE), "invalid identifying code");
    boolean assets =
        identifyingCodeRepository.isIdentifyingCodeValidate(REGISTRATION_EMAIL, target, code);
    if (assets) { // 赎回认证码
      identifyingCodeRepository.redeemIdentifyingCode(REGISTRATION_EMAIL, target, code);
    }
    return new AssertResponse(assets);
  }

  @Nonnull
  @Override
  public IdentifyingCode findIdentifyingCode(
      @Nonnull @RequestParam("target") String target,
      @Nonnull @RequestParam("type") IdentifyingCodeType type)
      throws ServiceException {

    if (type == REGISTRATION_PHONE) {
      checkArgument(target.matches(REGEX_PHONE), "invalid phone number");
    } else if (type == REGISTRATION_EMAIL) {
      checkArgument(target.matches(REGEX_MAIL), "invalid email account");
    }

    String findCode = identifyingCodeRepository.findIdentifyingCode(target, type);
    checkState(!StringUtils.isEmpty(findCode), () -> new NotFoundException(DATA));
    IdentifyingCode identifyingCode = new IdentifyingCode();
    identifyingCode.setIdentifyingCodeType(type);
    identifyingCode.setCode(findCode);
    return identifyingCode;
  }

  private IdentifyingCode getIdentifyingCode(String target, IdentifyingCodeType type) {
    try {
      // 索取验证码
      Map<String, String> values =
          of("target", target, "issue_time", String.valueOf(new Date().getTime()));
      String code =
          identifyingCodeRepository.claimIdentifyingCode(
              target, type, values, serviceSettings.getIdentifyingCodeTimeout(), TimeUnit.MINUTES);
      IdentifyingCode identifyingCode = new IdentifyingCode();
      identifyingCode.setCode(code);
      identifyingCode.setIdentifyingCodeType(type);
      return identifyingCode;
    } catch (CommonException e) {
      throw new IllegalTermsException(IDENTIFYING_CODE);
    }
  }
}
