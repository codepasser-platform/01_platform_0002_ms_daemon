package org.codepasser.base.web.sample.mybatis;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import javax.annotation.Nonnull;
import org.codepasser.base.model.entity.dto.sample.SampleGroupItems;
import org.codepasser.base.service.sample.mybatis.SampleMapperService;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleMapperApi.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@RestController
@RequestMapping("/sample/mybatis")
public class SampleMapperApi {

  @Autowired private SampleMapperService sampleMapperService;

  @Nonnull
  @RequestMapping(value = "/mapper", method = GET, produces = APPLICATION_JSON_VALUE)
  public List<SampleGroupItems> mapper() throws ServiceException {
    return sampleMapperService.mapper();
  }

  @Nonnull
  @RequestMapping(value = "/mapper/xml", method = GET, produces = APPLICATION_JSON_VALUE)
  public List<SampleGroupItems> mapperXml() throws ServiceException {
    return sampleMapperService.mapperXml();
  }
}
