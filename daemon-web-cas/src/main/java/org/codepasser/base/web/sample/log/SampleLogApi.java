package org.codepasser.base.web.sample.log;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.annotation.Nonnull;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleObjectMapperApi.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@RestController
@RequestMapping("/sample/log")
public class SampleLogApi {

  @InjectLogger private Logger logger;

  @Nonnull
  @RequestMapping(value = "/info", method = GET, produces = APPLICATION_JSON_VALUE)
  public AssertResponse info() throws ServiceException {
    logger.info("inject logger : '{}'", "testing info message");
    return AssertResponse.success();
  }
}
