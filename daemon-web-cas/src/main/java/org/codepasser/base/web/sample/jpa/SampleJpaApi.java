package org.codepasser.base.web.sample.jpa;

import org.codepasser.base.service.sample.bo.SampleGroupCreation;
import org.codepasser.base.service.sample.bo.SampleGroupUpdation;
import org.codepasser.base.service.sample.jpa.SampleJpaService;
import org.codepasser.base.service.sample.vo.SampleGroupVo;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Nonnull;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * SampleJpaApi.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@RestController
@RequestMapping("/sample/jpa")
public class SampleJpaApi {

  @Autowired private SampleJpaService sampleJpaService;

  @Nonnull
  @RequestMapping(value = "/select/all", method = GET, produces = APPLICATION_JSON_VALUE)
  public List<SampleGroupVo> selectAll() throws ServiceException {
    return sampleJpaService.selectAll();
  }

  @Nonnull
  @RequestMapping(value = "/select", method = GET, produces = APPLICATION_JSON_VALUE)
  public SampleGroupVo select(@Nonnull @RequestParam("id") Long id) throws ServiceException {
    return sampleJpaService.select(id);
  }

  @Nonnull
  @RequestMapping(value = "/create", method = POST, produces = APPLICATION_JSON_VALUE)
  public AssertResponse create(
      @Nonnull @Valid @RequestBody SampleGroupCreation sampleGroupCreation,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {
    return sampleJpaService.create(sampleGroupCreation, userId);
  }

  @Nonnull
  @RequestMapping(value = "/update", method = PUT, produces = APPLICATION_JSON_VALUE)
  public AssertResponse update(
      @Nonnull @Valid @RequestBody SampleGroupUpdation sampleGroupUpdation,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {
    return sampleJpaService.update(sampleGroupUpdation, userId);
  }

  @Nonnull
  @RequestMapping(value = "/delete", method = DELETE, produces = APPLICATION_JSON_VALUE)
  public AssertResponse delete(
      @Nonnull @RequestParam("id") Long id, @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {
    return sampleJpaService.delete(id, userId);
  }
}
