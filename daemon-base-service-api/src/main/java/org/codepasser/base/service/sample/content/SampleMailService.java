package org.codepasser.base.service.sample.content;

import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Nonnull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * SampleMailService.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/13 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/content")
@Service
public interface SampleMailService {

  @Nonnull
  @RequestMapping(value = "/mail", method = GET, produces = APPLICATION_JSON_VALUE)
  AssertResponse mail() throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/mail/recover", method = GET, produces = APPLICATION_JSON_VALUE)
  AssertResponse mailRecover() throws ServiceException;
}
