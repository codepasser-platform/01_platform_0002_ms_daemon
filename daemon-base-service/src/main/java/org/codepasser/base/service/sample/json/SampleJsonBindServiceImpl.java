package org.codepasser.base.service.sample.json;

import org.codepasser.base.service.sample.bo.SampleDateBo;
import org.codepasser.base.service.sample.vo.SampleVo;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.validation.Valid;

/**
 * SampleJsonBindServiceImpl.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Service
@RestController
public class SampleJsonBindServiceImpl implements SampleJsonBindService {

  @Nonnull
  @Override
  public SampleVo emptyProperties() throws ServiceException {
    SampleVo sampleVo = new SampleVo();
    sampleVo.setId(111L);
    //    sampleVo.setPostCode("123321");
    sampleVo.setStart(new Date());
    sampleVo.setEnd(new Date());
    return sampleVo;
  }

  @Nonnull
  @Override
  public SampleDateBo dateProperties(@Nonnull @Valid @RequestBody SampleDateBo sampleDateBo)
      throws ServiceException {
    return sampleDateBo;
  }
}
