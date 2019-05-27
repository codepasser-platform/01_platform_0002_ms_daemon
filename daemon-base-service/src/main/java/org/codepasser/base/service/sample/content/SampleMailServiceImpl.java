package org.codepasser.base.service.sample.content;

import javax.annotation.Nonnull;
import org.codepasser.base.model.business.category.IdentifyingCodeType;
import org.codepasser.base.service.basement.vo.IdentifyingCode;
import org.codepasser.base.service.impl.cell.MailCell;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleMailServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/15 : base version.
 */
@Service
@RestController
public class SampleMailServiceImpl implements SampleMailService {

  @Autowired private MailCell mailCell;

  @Nonnull
  @Override
  public AssertResponse mail() throws ServiceException {
    IdentifyingCode identifyingCode = new IdentifyingCode();
    identifyingCode.setCode("000000");
    identifyingCode.setIdentifyingCodeType(IdentifyingCodeType.REGISTRATION_EMAIL);
    mailCell.sendRegistrationTokenMail("yangyang.cheng@valueonline.cn", identifyingCode);
    return AssertResponse.success();
  }

  @Nonnull
  @Override
  public AssertResponse mailRecover() throws ServiceException {
    IdentifyingCode identifyingCode = new IdentifyingCode();
    identifyingCode.setCode("000000");
    identifyingCode.setIdentifyingCodeType(IdentifyingCodeType.RECOVER_EMAIL);
    mailCell.sendRecoverTokenMail("yangyang.cheng@valueonline.cn", identifyingCode);
    return AssertResponse.success();
  }
}
