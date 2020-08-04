package org.codepasser.base.web.sample.exception;

import org.codepasser.base.service.sample.bo.SampleGroupCreation;
import org.codepasser.base.service.sample.bo.SampleUserCreation;
import org.codepasser.base.service.sample.exception.SampleUncaughtService;
import org.codepasser.common.exception.AbstractException;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * SampleFeignExceptionApi.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@RestController
@RequestMapping("/sample/uncaught/feign")
public class SampleUncaughtFeignApi {

  @Autowired private SampleUncaughtService sampleUncaughtService;

  @RequestMapping(value = "/conflict", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean conflict() throws ServiceException {
    sampleUncaughtService.conflict();
    return false;
  }

  @RequestMapping(value = "/forbidden", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean forbidden() throws ServiceException {
    sampleUncaughtService.forbidden();
    return false;
  }

  @RequestMapping(value = "/illegal", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean illegal() throws ServiceException {
    sampleUncaughtService.illegal();
    return false;
  }

  @RequestMapping(value = "/terms", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean terms() throws ServiceException {
    sampleUncaughtService.terms();
    return false;
  }

  @RequestMapping(value = "/not-found", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean notFound() throws ServiceException {
    sampleUncaughtService.notFound();
    return false;
  }

  @RequestMapping(value = "/reference", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean reference() throws ServiceException {
    sampleUncaughtService.reference();
    return false;
  }

  @RequestMapping(value = "/reference-jpa", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean referenceJpa() throws ServiceException {
    sampleUncaughtService.referenceJpa();
    return false;
  }

  @RequestMapping(value = "/runtime", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean runtime() throws ServiceException {
    sampleUncaughtService.runtime();
    return false;
  }

  @RequestMapping(value = "/abstract", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean abstracts() throws AbstractException {
    sampleUncaughtService.abstracts();
    return false;
  }

  @RequestMapping(value = "/illegal-state", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean illegalState() throws ServiceException {
    sampleUncaughtService.illegalState();
    return false;
  }

  @RequestMapping(value = "/illegal-valid", method = POST, produces = APPLICATION_JSON_VALUE)
  public boolean illegalValid(@RequestBody SampleGroupCreation sampleGroupCreation)
      throws ServiceException {
    sampleUncaughtService.illegalValid(sampleGroupCreation);
    return false;
  }

  @RequestMapping(value = "/illegal-argument", method = GET, produces = APPLICATION_JSON_VALUE)
  public boolean illegalArgument(@RequestParam(value = "phone", required = false) String phone)
      throws ServiceException {
    sampleUncaughtService.illegalArgument(phone);
    return false;
  }

  @RequestMapping(value = "/illegal-group", method = POST, produces = APPLICATION_JSON_VALUE)
  public boolean illegalGroup(@RequestBody SampleUserCreation sampleUserCreation)
      throws ServiceException {
    sampleUncaughtService.illegalGroup(sampleUserCreation);
    return false;
  }
}
