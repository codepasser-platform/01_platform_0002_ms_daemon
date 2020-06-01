package org.codepasser.base.service.sample.json;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.codepasser.base.service.sample.bo.SampleItemCreation;
import org.codepasser.base.service.sample.bo.SampleUserCreation;
import org.codepasser.common.model.validation.Group;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SampleJsonValidService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/json/valid")
@Service
public interface SampleJsonValidService {

  @Nonnull
  @RequestMapping(value = "/pattern", method = POST, produces = APPLICATION_JSON_VALUE)
  AssertResponse pattern(@Nonnull @Valid @RequestBody SampleItemCreation sampleItemCreation)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/group", method = POST, produces = APPLICATION_JSON_VALUE)
  AssertResponse group(
      @Nonnull @Validated(Group.Create.class) @RequestBody SampleUserCreation sampleUserCreation)
      throws ServiceException;
}
