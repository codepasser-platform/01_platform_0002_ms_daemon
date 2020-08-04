package org.codepasser.base.service.sample.cache;

import org.codepasser.base.dao.repository.mongo.AreaRepository;
import org.codepasser.base.model.data.Area;
import org.codepasser.common.service.exception.NotFoundException;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Nonnull;

import static org.codepasser.base.service.impl.cache.CacheableKeysSet.CACHE_AREAS;
import static org.codepasser.base.service.impl.cache.CacheableKeysSet.CACHE_AREAS_AUTO;
import static org.codepasser.base.service.impl.cache.CacheableKeysSet.KEY_GENERATOR;
import static org.codepasser.base.service.impl.cache.CacheableKeysSet.MANAGER_AUTO;
import static org.codepasser.common.service.conifguration.cache.CacheableSupport.MANAGER_DEFAULT;
import static org.codepasser.common.service.exception.NotFoundException.Error.AREA;

/**
 * SampleRedisCacheServiceImpl.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Service
@RestController
public class SampleRedisCacheServiceImpl implements SampleRedisCacheService {

  @Autowired private AreaRepository areaRepository;

  @Nonnull
  @Override
  @Cacheable(keyGenerator = KEY_GENERATOR, value = CACHE_AREAS, cacheManager = MANAGER_DEFAULT)
  public List<Area> caching(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException {
    if (!StringUtils.isEmpty(parent)) {
      areaRepository.findByCode(parent).orElseThrow(() -> new NotFoundException(AREA));
      return areaRepository.findAllByParentAndLevel(parent, level);
    } else {
      return areaRepository.findAllByLevel(level);
    }
  }

  @Nonnull
  @Override
  @CacheEvict(keyGenerator = KEY_GENERATOR, value = CACHE_AREAS, cacheManager = MANAGER_DEFAULT)
  public AssertResponse cachingEvict(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException {
    return AssertResponse.success();
  }

  @Nonnull
  @Override
  @Cacheable(keyGenerator = KEY_GENERATOR, value = CACHE_AREAS_AUTO, cacheManager = MANAGER_AUTO)
  public List<Area> cachingAuto(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException {
    if (!StringUtils.isEmpty(parent)) {
      areaRepository.findByCode(parent).orElseThrow(() -> new NotFoundException(AREA));
      return areaRepository.findAllByParentAndLevel(parent, level);
    } else {
      return areaRepository.findAllByLevel(level);
    }
  }

  @Nonnull
  @Override
  @CacheEvict(keyGenerator = KEY_GENERATOR, value = CACHE_AREAS_AUTO, cacheManager = MANAGER_AUTO)
  public AssertResponse cachingAutoEvict(
      @RequestParam(value = "parent", required = false) String parent,
      @Nonnull @RequestParam(value = "level") Integer level)
      throws ServiceException {
    return AssertResponse.success();
  }
}
