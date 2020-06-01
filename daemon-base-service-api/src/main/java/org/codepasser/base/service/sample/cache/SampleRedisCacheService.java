package org.codepasser.base.service.sample.cache;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import javax.annotation.Nonnull;
import org.codepasser.base.model.data.Area;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SampleRedisCacheService.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/13 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/redis")
@Service
public interface SampleRedisCacheService {

  @Nonnull
  @RequestMapping(value = "/caching", method = GET, produces = APPLICATION_JSON_VALUE)
  List<Area> caching(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/caching/evict", method = GET, produces = APPLICATION_JSON_VALUE)
  AssertResponse cachingEvict(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/caching/auto", method = GET, produces = APPLICATION_JSON_VALUE)
  List<Area> cachingAuto(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException;

  @Nonnull
  @RequestMapping(
      value = "/caching/auto/evict",
      method = GET,
      produces = APPLICATION_JSON_VALUE)
  AssertResponse cachingAutoEvict(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException;
}
