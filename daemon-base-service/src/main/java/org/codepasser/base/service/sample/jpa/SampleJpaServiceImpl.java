package org.codepasser.base.service.sample.jpa;

import static com.google.common.collect.Sets.newHashSet;
import static org.codepasser.common.model.entity.inner.State.DELETED;
import static org.codepasser.common.model.entity.inner.State.EXPIRED;
import static org.codepasser.common.service.exception.NotFoundException.Error.DATA;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.codepasser.base.dao.repository.sample.SampleGroupRepository;
import org.codepasser.base.model.entity.sample.SampleGroupEntity;
import org.codepasser.base.service.sample.bo.SampleGroupCreation;
import org.codepasser.base.service.sample.bo.SampleGroupUpdation;
import org.codepasser.base.service.sample.vo.SampleGroupVo;
import org.codepasser.common.model.entity.inner.State;
import org.codepasser.common.service.exception.NotFoundException;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleJpaServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
@Service
@RestController
public class SampleJpaServiceImpl implements SampleJpaService {

  @Autowired private SampleGroupRepository sampleGroupRepository;

  @Nonnull
  @Override
  public List<SampleGroupVo> selectAll() throws ServiceException {
    List<SampleGroupEntity> groupEntities =
        sampleGroupRepository.findSampleGroupEntitiesByStateNotIn(newHashSet(DELETED, EXPIRED));
    return groupEntities.stream()
        .map(
            (entity) -> {
              SampleGroupVo vo = new SampleGroupVo();
              return vo.from(entity);
            })
        .collect(Collectors.toList());
  }

  @Nonnull
  @Override
  public SampleGroupVo select(@Nonnull @RequestParam("id") Long id) throws ServiceException {
    return new SampleGroupVo()
        .from(sampleGroupRepository.findById(id).orElseThrow(() -> new NotFoundException(DATA)));
  }

  @Nonnull
  @Override
  @Transactional
  public AssertResponse create(
      @Nonnull @Valid @RequestBody SampleGroupCreation sampleGroupCreation,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {
    SampleGroupEntity entity = sampleGroupCreation.convert();
    entity.setCreateTime(new Date());
    entity.setCreateUser(userId);
    sampleGroupRepository.save(entity);
    return AssertResponse.success();
  }

  @Nonnull
  @Override
  @Transactional
  public AssertResponse update(
      @Nonnull @Valid @RequestBody SampleGroupUpdation sampleGroupUpdation,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {

    sampleGroupRepository
        .findById(sampleGroupUpdation.getId())
        .orElseThrow(() -> new NotFoundException(DATA));

    SampleGroupEntity entity = sampleGroupUpdation.convert();
    entity.setUpdateTime(new Date());
    entity.setUpdateUser(userId);
    sampleGroupRepository.save(entity);
    return AssertResponse.success();
  }

  @Nonnull
  @Override
  @Transactional
  public AssertResponse delete(
      @Nonnull @RequestParam("id") Long id, @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {
    SampleGroupEntity entity =
        sampleGroupRepository.findById(id).orElseThrow(() -> new NotFoundException(DATA));
    entity.setState(State.DELETED);
    entity.setUpdateTime(new Date());
    entity.setUpdateUser(userId);
    sampleGroupRepository.save(entity);
    return AssertResponse.success();
  }
}
