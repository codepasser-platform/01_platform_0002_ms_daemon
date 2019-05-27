package org.codepasser.base.service.sample.mybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.codepasser.base.dao.repository.mapper.sample.SampleGroupXmlMapper;
import org.codepasser.base.model.entity.dto.sample.SampleGroupItems;
import org.codepasser.common.model.entity.inner.State;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleMapperPaginationServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
@Service
@RestController
public class SampleMapperPaginationServiceImpl implements SampleMapperPaginationService {

  @Autowired private SampleGroupXmlMapper sampleGroupXmlMapper;

  @Nonnull
  @Override
  public PageInfo<SampleGroupItems> pagination(
      @Nullable @RequestParam(value = "name", required = false) String name,
      @Nonnull @RequestParam("page") int page,
      @Nonnull @RequestParam("size") int size)
      throws ServiceException {
    PageHelper.startPage(page, size); // 1 <= page <= pageTotal
    List<SampleGroupItems> groupItems =
        sampleGroupXmlMapper.findListByNameContainingAndStateNotIn(
            name, EnumSet.of(State.DELETED, State.EXPIRED));
    PageInfo<SampleGroupItems> pageInfo = new PageInfo(groupItems);
    return pageInfo;
  }
}
