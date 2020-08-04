package org.codepasser.base.service.sample.jpa;

import org.codepasser.base.dao.repository.sample.SampleGroupRepository;
import org.codepasser.base.model.entity.sample.SampleGroupEntity;
import org.codepasser.base.service.sample.vo.SampleGroupVo;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.PagedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.collect.Sets.newHashSet;
import static org.codepasser.common.model.ConstantInterface.CONDITION_BLANK;
import static org.codepasser.common.model.entity.inner.State.DELETED;
import static org.codepasser.common.model.entity.inner.State.EXPIRED;
import static org.codepasser.common.service.helper.PagedDataUtils.rePage;

/**
 * SampleJpaPaginationServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
@Service
@RestController
public class SampleJpaPaginationServiceImpl implements SampleJpaPaginationService {

  @Autowired private SampleGroupRepository sampleGroupRepository;

  @Nonnull
  @Override
  public PagedData<SampleGroupVo> pagination(
      @Nullable @RequestParam(value = "name", required = false) String name,
      @Nonnull @RequestParam("page") int page,
      @Nonnull @RequestParam("size") int size)
      throws ServiceException {
    // 0 <= page <= pageTotal
    Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
    Pageable pageable = PageRequest.of(page, size, sort);
    String conditionName = CONDITION_BLANK;
    if (!StringUtils.isEmpty(name)) {
      conditionName = name;
    }
    Page<SampleGroupEntity> userPage =
        sampleGroupRepository.findAllByNameContainingAndStateNotIn(
            conditionName, newHashSet(DELETED, EXPIRED), pageable);

    return rePage(
        userPage,
        pageable,
        item -> {
          SampleGroupVo sampleGroupVo = new SampleGroupVo().from(item);
          return sampleGroupVo;
        });
  }

  @Nonnull
  @Override
  public PagedData<SampleGroupVo> pageable(
      @Nullable @RequestParam(value = "name", required = false) String name,
      @RequestBody Pageable pageable)
      throws ServiceException {
    // 0 <= page <= pageTotal
    String conditionName = CONDITION_BLANK;
    if (!StringUtils.isEmpty(name)) {
      conditionName = name;
    }
    Page<SampleGroupEntity> userPage =
        sampleGroupRepository.findAllByNameContainingAndStateNotIn(
            conditionName, newHashSet(DELETED, EXPIRED), pageable);
    return rePage(
        userPage,
        pageable,
        item -> {
          SampleGroupVo sampleGroupVo = new SampleGroupVo().from(item);
          return sampleGroupVo;
        });
  }
}
