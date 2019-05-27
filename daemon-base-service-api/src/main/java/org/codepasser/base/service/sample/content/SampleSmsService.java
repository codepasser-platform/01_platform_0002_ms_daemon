package org.codepasser.base.service.sample.content;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.annotation.Nonnull;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SampleSmsService.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/13 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/content")
@Service
public interface SampleSmsService {

  @Nonnull
  @RequestMapping(value = "/sms", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse sms() throws ServiceException;
}
