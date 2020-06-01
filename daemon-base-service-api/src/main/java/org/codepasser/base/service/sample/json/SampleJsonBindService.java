package org.codepasser.base.service.sample.json;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.codepasser.base.service.sample.bo.SampleDateBo;
import org.codepasser.base.service.sample.vo.SampleVo;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SampleJsonBindService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/json/bind")
@Service
public interface SampleJsonBindService {

  @Nonnull
  @RequestMapping(value = "/empty", method = GET, produces = APPLICATION_JSON_VALUE)
  SampleVo emptyProperties() throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/date", method = POST, produces = APPLICATION_JSON_VALUE)
  SampleDateBo dateProperties(@Nonnull @Valid @RequestBody SampleDateBo sampleDateBo)
      throws ServiceException;
}
