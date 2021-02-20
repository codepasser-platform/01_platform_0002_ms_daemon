package org.codepasser.common.service.conifguration.data;

import com.alibaba.druid.stat.DruidStatManagerFacade;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * DruidController.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2021/2/20 : base version.
 */
@RestController
@RequestMapping("/druid-monitor")
public class DruidController {

  /**
   * Druid stat monitor restfull api.
   *
   * @return stat data.
   */
  @Nonnull
  @RequestMapping(value = "/stat", method = GET, produces = APPLICATION_JSON_VALUE)
  public Object druidStat() {
    // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，
    // 除此之外DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
    return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
  }
}
