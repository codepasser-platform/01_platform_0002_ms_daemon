package org.codepasser.base.web.sample.file;

import static org.codepasser.common.model.ConstantInterface.ROOT_ADMIN_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.codepasser.base.service.basement.vo.ExcelTitle;
import org.codepasser.base.service.basement.vo.ResourceId;
import org.codepasser.base.service.sample.excel.SampleExcelService;
import org.codepasser.base.web.provider.ExportProvider;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.helper.ExcelResponseBuilder;
import org.codepasser.common.service.response.ExcelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleSessionApi.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
@RestController
@RequestMapping("/sample/file")
public class SampleExportApi {

  @Autowired private ExportProvider exportProvider;

  @Autowired private SampleExcelService sampleExcelService;

  //  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = "/export", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public ResourceId export() throws ServiceException {
    // DEMO @AuthenticationPrincipal UserDetails user
    Long userId = ROOT_ADMIN_ID;
    ExcelResponse response =
        ExcelResponseBuilder.build(sampleExcelService.export(), ExcelTitle.EXPORT_LIST.title());
    return exportProvider.export(userId, response);
  }
}
