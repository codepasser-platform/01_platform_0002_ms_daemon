package org.codepasser.base.service.sample.mybatis;

import com.github.pagehelper.PageInfo;

import org.codepasser.base.model.entity.dto.sample.SampleGroupItems;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * SampleMapperPaginationService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/mybatis")
@Service
public interface SampleMapperPaginationService {

  @Nonnull
  @RequestMapping(value = "/pagination", method = GET, produces = APPLICATION_JSON_VALUE)
  PageInfo<SampleGroupItems> pagination(
      @Nullable @RequestParam(value = "name", required = false) String name,
      @Nonnull @RequestParam("page") int page,
      @Nonnull @RequestParam("size") int size)
      throws ServiceException;
}
