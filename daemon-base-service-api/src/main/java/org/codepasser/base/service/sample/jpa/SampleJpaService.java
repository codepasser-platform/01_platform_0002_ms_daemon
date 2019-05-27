package org.codepasser.base.service.sample.jpa;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.codepasser.base.service.sample.bo.SampleGroupCreation;
import org.codepasser.base.service.sample.bo.SampleGroupUpdation;
import org.codepasser.base.service.sample.vo.SampleGroupVo;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SampleJpaService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/jpa")
@Service
public interface SampleJpaService {

  @Nonnull
  @RequestMapping(value = "/select/all", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  List<SampleGroupVo> selectAll() throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/select", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  SampleGroupVo select(@Nonnull @RequestParam("id") Long id) throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/create", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse create(
      @Nonnull @Valid @RequestBody SampleGroupCreation sampleGroupCreation,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/update", method = PUT, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse update(
      @Nonnull @Valid @RequestBody SampleGroupUpdation sampleGroupUpdation,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/delete", method = DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse delete(
      @Nonnull @RequestParam("id") Long id, @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException;
}
