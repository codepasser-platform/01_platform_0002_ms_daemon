package org.codepasser.base.service.sample.mybatis;

import org.codepasser.base.model.entity.dto.sample.SampleGroupItems;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.annotation.Nonnull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * SampleMapperService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/mybatis")
@Service
public interface SampleMapperService {

  @Nonnull
  @RequestMapping(value = "/mapper", method = GET, produces = APPLICATION_JSON_VALUE)
  List<SampleGroupItems> mapper() throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/mapper/xml", method = GET, produces = APPLICATION_JSON_VALUE)
  List<SampleGroupItems> mapperXml() throws ServiceException;
}
