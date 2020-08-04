package org.codepasser.base.service.sample.exception;

import org.codepasser.base.service.sample.bo.SampleGroupCreation;
import org.codepasser.base.service.sample.bo.SampleUserCreation;
import org.codepasser.common.exception.AbstractException;
import org.codepasser.common.model.validation.Group;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nonnull;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * SampleUncaughtExceptionService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/uncaught")
@Service
public interface SampleUncaughtService {

  @RequestMapping(value = "/conflict", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean conflict() throws ServiceException;

  @RequestMapping(value = "/forbidden", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean forbidden() throws ServiceException;

  @RequestMapping(value = "/illegal", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean illegal() throws ServiceException;

  @RequestMapping(value = "/terms", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean terms() throws ServiceException;

  @RequestMapping(value = "/not-found", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean notFound() throws ServiceException;

  @RequestMapping(value = "/reference", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean reference() throws ServiceException;

  @RequestMapping(value = "/reference-jpa", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean referenceJpa() throws ServiceException;

  @RequestMapping(value = "/runtime", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean runtime() throws ServiceException;

  @RequestMapping(value = "/abstract", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean abstracts() throws AbstractException;

  @RequestMapping(value = "/illegal-state", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean illegalState() throws ServiceException;

  @RequestMapping(value = "/illegal-valid", method = POST, produces = APPLICATION_JSON_VALUE)
  boolean illegalValid(@Nonnull @Valid @RequestBody SampleGroupCreation sampleGroupCreation)
      throws ServiceException;

  @RequestMapping(value = "/illegal-argument", method = GET, produces = APPLICATION_JSON_VALUE)
  boolean illegalArgument(@Nonnull @RequestParam("phone") String phone) throws ServiceException;

  @RequestMapping(value = "/illegal-group", method = POST, produces = APPLICATION_JSON_VALUE)
  boolean illegalGroup(
      @Nonnull @Validated(Group.Create.class) @RequestBody SampleUserCreation sampleUserCreation)
      throws ServiceException;
}
