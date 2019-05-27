package org.codepasser.base.service.sample.excel;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import org.codepasser.base.service.sample.vo.SampleExcelVo;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.utils.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleExcelServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/19 : base version.
 */
@Service
@RestController
public class SampleExcelServiceImpl implements SampleExcelService {

  @Nonnull
  @Override
  public List<SampleExcelVo> export() throws ServiceException {
    // mock repository query data
    return mockData();
  }

  private List<SampleExcelVo> mockData() {
    List<SampleExcelVo> data = Lists.newArrayList();
    for (int i = 0; i < 100; i++) {
      SampleExcelVo vo = new SampleExcelVo();
      vo.setId(IdGenerator.next());
      vo.setStart(new Date());
      vo.setEnd(new Date());
      vo.setName("测试名称" + i);
      vo.setPostCode("00000" + i);
      vo.setPassword("Sa" + i);
      data.add(vo);
    }
    return data;
  }
}
