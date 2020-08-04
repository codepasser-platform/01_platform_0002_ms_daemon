package org.codepasser.base.service.sample.exception;

import org.codepasser.base.service.sample.bo.SampleGroupCreation;
import org.codepasser.base.service.sample.bo.SampleUserCreation;
import org.codepasser.common.exception.AbstractException;
import org.codepasser.common.exception.CommonException;
import org.codepasser.common.model.validation.Group;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.exception.ConflictException;
import org.codepasser.common.service.exception.ForbiddenException;
import org.codepasser.common.service.exception.IllegalArgumentException;
import org.codepasser.common.service.exception.IllegalTermsException;
import org.codepasser.common.service.exception.NotFoundException;
import org.codepasser.common.service.exception.ReferenceException;
import org.codepasser.common.service.exception.ServiceException;
import org.slf4j.Logger;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import static com.google.common.base.Preconditions.checkArgument;
import static org.codepasser.common.exception.CommonException.Error.QR_CODE_GENERATE_FAILED;
import static org.codepasser.common.model.RegexPattern.REGEX_PHONE;
import static org.codepasser.common.service.exception.ConflictException.Error.USER_PHONE;
import static org.codepasser.common.service.exception.ForbiddenException.Error.PERMISSIONS;
import static org.codepasser.common.service.exception.IllegalArgumentException.Error.INVALID_IDENTIFYING_CODE;
import static org.codepasser.common.service.exception.IllegalTermsException.Error.IDENTIFYING_CODE;
import static org.codepasser.common.service.exception.NotFoundException.Error.USER;
import static org.codepasser.common.service.exception.ReferenceException.Error.FAILED;

/**
 * SampleUncaughtExceptionServiceImpl.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Service
@RestController
public class SampleUncaughtServiceImpl implements SampleUncaughtService {

  @InjectLogger private Logger logger;

  @Override
  public boolean conflict() throws ServiceException {
    logger.info("SampleUncaughtExceptionServiceImpl > conflict");
    if (true) {
      throw new ConflictException(USER_PHONE);
    }
    return false;
  }

  @Override
  public boolean forbidden() throws ServiceException {
    if (true) {
      throw new ForbiddenException(PERMISSIONS);
    }
    return false;
  }

  @Override
  public boolean illegal() throws ServiceException {
    if (true) {
      throw new IllegalArgumentException(INVALID_IDENTIFYING_CODE);
    }
    return false;
  }

  @Override
  public boolean terms() throws ServiceException {
    if (true) {
      throw new IllegalTermsException(IDENTIFYING_CODE);
    }
    return false;
  }

  @Override
  public boolean notFound() throws ServiceException {
    if (true) {
      throw new NotFoundException(USER);
    }
    return false;
  }

  @Override
  public boolean reference() throws ServiceException {
    if (true) {
      throw new ReferenceException(FAILED);
    }
    return false;
  }

  @Override
  public boolean referenceJpa() throws ServiceException {
    if (true) {
      throw new JpaObjectRetrievalFailureException(new EntityNotFoundException("user not found"));
    }
    return false;
  }

  @Override
  public boolean runtime() throws ServiceException {
    if (true) {
      throw new RuntimeException("mock runtime exception");
    }
    return false;
  }

  @Override
  public boolean abstracts() throws AbstractException {
    if (true) {
      throw new CommonException(QR_CODE_GENERATE_FAILED);
    }
    return false;
  }

  @Override
  public boolean illegalState() throws ServiceException {
    if (true) {
      throw new IllegalStateException("invalid state");
    }
    return false;
  }

  @Override
  public boolean illegalValid(@Nonnull @Valid @RequestBody SampleGroupCreation sampleGroupCreation)
      throws ServiceException {
    return false;
  }

  @Override
  public boolean illegalArgument(@Nonnull @RequestParam("phone") String phone)
      throws ServiceException {
    checkArgument(phone.matches(REGEX_PHONE), "invalid phone number");
    return false;
  }

  @Override
  public boolean illegalGroup(
      @Nonnull @Validated(Group.Create.class) @RequestBody SampleUserCreation sampleUserCreation)
      throws ServiceException {
    return false;
  }
}
