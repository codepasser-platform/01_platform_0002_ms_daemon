package org.codepasser.base.service.sample.content;

import org.codepasser.base.model.business.category.IdentifyingCodeType;
import org.codepasser.base.service.basement.vo.IdentifyingCode;
import org.codepasser.base.service.impl.cell.SmsCell;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

/**
 * SampleSmsServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/15 : base version.
 */
@Service
@RestController
public class SampleSmsServiceImpl implements SampleSmsService {

  @Autowired private SmsCell smsCell;

  @Nonnull
  @Override
  public AssertResponse sms() throws ServiceException {
    IdentifyingCode identifyingCode = new IdentifyingCode();
    identifyingCode.setCode("000000");
    identifyingCode.setIdentifyingCodeType(IdentifyingCodeType.REGISTRATION_PHONE);
    smsCell.sendRegistrationTokenSms("15668681342", identifyingCode);
    return AssertResponse.success();
  }

  @Nonnull
  @Override
  public AssertResponse smsRecover() throws ServiceException {
    IdentifyingCode identifyingCode = new IdentifyingCode();
    identifyingCode.setCode("000000");
    identifyingCode.setIdentifyingCodeType(IdentifyingCodeType.RECOVER_PHONE);
    smsCell.sendRecoverTokenSms("15668681342", identifyingCode);
    return AssertResponse.success();
  }
}
