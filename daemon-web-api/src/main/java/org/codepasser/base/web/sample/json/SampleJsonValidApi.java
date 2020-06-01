package org.codepasser.base.web.sample.json;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.codepasser.base.service.sample.bo.SampleItemCreation;
import org.codepasser.base.service.sample.bo.SampleUserCreation;
import org.codepasser.base.service.sample.json.SampleJsonValidService;
import org.codepasser.common.model.validation.Group;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/sample/json/valid")
public class SampleJsonValidApi {

  @Autowired private SampleJsonValidService sampleJsonValidService;

  @Nonnull
  @RequestMapping(value = "/pattern", method = POST, produces = APPLICATION_JSON_VALUE)
  public AssertResponse pattern(@Nonnull @Valid @RequestBody SampleItemCreation sampleItemCreation)
      throws ServiceException {
    return sampleJsonValidService.pattern(sampleItemCreation);
  }

  @Nonnull
  @RequestMapping(value = "/group", method = POST, produces = APPLICATION_JSON_VALUE)
  public AssertResponse group(
      @Nonnull @Validated(Group.Create.class) @RequestBody SampleUserCreation sampleUserCreation)
      throws ServiceException {
    return sampleJsonValidService.group(sampleUserCreation);
  }
}
