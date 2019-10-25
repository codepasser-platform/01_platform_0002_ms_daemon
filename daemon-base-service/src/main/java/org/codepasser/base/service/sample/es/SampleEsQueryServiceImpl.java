package org.codepasser.base.service.sample.es;

import org.codepasser.base.dao.repository.es.sample.SampleManualsRepository;
import org.codepasser.base.model.es.sample.SampleManual;
import org.codepasser.base.model.es.sample.dto.SampleManualDto;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.PagedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.codepasser.common.model.ConstantInterface.CONDITION_BLANK;
import static org.codepasser.common.service.helper.PagedDataUtils.rePage;

/**
 * SampleEsQueryServiceImpl.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019-03-25 : base version.
 */
@Service
@RestController
public class SampleEsQueryServiceImpl implements SampleEsQueryService {

  @Autowired private SampleManualsRepository sampleManualsRepository;

  @Nonnull
  @Override
  public PagedData<SampleManualDto> queryPageable(
      @Nullable @RequestParam(value = "name", required = false) String name,
      @RequestBody Pageable pageable)
      throws ServiceException {
    String condition = name;
    if (StringUtils.isEmpty(name)) {
      condition = CONDITION_BLANK;
    }
    Page<SampleManual> manualPage = sampleManualsRepository.findAllByNameLike(condition, pageable);
    return rePage(manualPage, pageable, item -> new SampleManualDto().from(item));
  }

  @Nonnull
  @Override
  public PagedData<SampleManualDto> keyPageable(
      @Nullable @RequestParam(value = "key", required = false) String key,
      @RequestBody Pageable pageable)
      throws ServiceException {
    String condition = key;
    if (StringUtils.isEmpty(key)) {
      condition = CONDITION_BLANK;
    }
    Page<SampleManual> manualPage =
        sampleManualsRepository.findAllByContentLike(condition, pageable);
    return rePage(manualPage, pageable, item -> new SampleManualDto().from(item));
  }
}
