package org.codepasser.base.service.sample.mybatis;

import org.codepasser.base.dao.repository.mapper.sample.SampleGroupMapper;
import org.codepasser.base.dao.repository.mapper.sample.SampleGroupXmlMapper;
import org.codepasser.base.model.entity.dto.sample.SampleGroupItems;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nonnull;

import static org.codepasser.common.model.entity.inner.State.DELETED;
import static org.codepasser.common.model.entity.inner.State.EXPIRED;

/**
 * SampleMapperServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
@Service
@RestController
public class SampleMapperServiceImpl implements SampleMapperService {

  @Autowired private SampleGroupMapper sampleGroupMapper;

  @Autowired private SampleGroupXmlMapper sampleGroupXmlMapper;

  @Nonnull
  @Override
  public List<SampleGroupItems> mapper() throws ServiceException {
    return sampleGroupMapper.findListStateNotIn(EnumSet.of(DELETED, EXPIRED));
  }

  @Nonnull
  @Override
  public List<SampleGroupItems> mapperXml() throws ServiceException {
    return sampleGroupXmlMapper.findListByStateNotIn(EnumSet.of(DELETED, EXPIRED));
  }
}
