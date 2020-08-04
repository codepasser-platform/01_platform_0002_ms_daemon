package org.codepasser.base.web.sample.cache;

import org.codepasser.base.model.data.Area;
import org.codepasser.base.service.sample.cache.SampleRedisCacheService;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Nonnull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * SampleRedisCacheApi.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@RestController
@RequestMapping("/sample/redis")
public class SampleRedisCacheApi {

  @Autowired private SampleRedisCacheService sampleRedisCacheService;

  @Nonnull
  @RequestMapping(value = "/caching", method = GET, produces = APPLICATION_JSON_VALUE)
  public List<Area> caching(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException {
    return sampleRedisCacheService.caching(parent, level);
  }

  @Nonnull
  @RequestMapping(value = "/caching/evict", method = GET, produces = APPLICATION_JSON_VALUE)
  public AssertResponse cachingEvict(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException {
    return sampleRedisCacheService.cachingEvict(parent, level);
  }

  @Nonnull
  @RequestMapping(value = "/caching/auto", method = GET, produces = APPLICATION_JSON_VALUE)
  public List<Area> cachingAuto(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException {
    return sampleRedisCacheService.cachingAuto(parent, level);
  }

  @Nonnull
  @RequestMapping(value = "/caching/auto/evict", method = GET, produces = APPLICATION_JSON_VALUE)
  public AssertResponse cachingAutoEvict(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException {
    return sampleRedisCacheService.cachingAutoEvict(parent, level);
  }
}
