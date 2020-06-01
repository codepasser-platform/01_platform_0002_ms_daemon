package org.codepasser.base.service.sample.excel;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import javax.annotation.Nonnull;
import org.codepasser.base.service.sample.vo.SampleExcelVo;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SampleExcelService.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/13 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/excel")
@Service
public interface SampleExcelService {

  @Nonnull
  @RequestMapping(value = "/export", method = GET, produces = APPLICATION_JSON_VALUE)
  List<SampleExcelVo> export() throws ServiceException;
}
