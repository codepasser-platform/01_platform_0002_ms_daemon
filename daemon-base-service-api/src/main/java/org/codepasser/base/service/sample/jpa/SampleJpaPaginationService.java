package org.codepasser.base.service.sample.jpa;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.codepasser.base.service.sample.vo.SampleGroupVo;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.PagedData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SampleJpaPaginationService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/jpa")
@Service
public interface SampleJpaPaginationService {

  @Nonnull
  @RequestMapping(value = "/pagination", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  PagedData<SampleGroupVo> pagination(
      @Nullable @RequestParam(value = "name", required = false) String name,
      @Nonnull @RequestParam("page") int page,
      @Nonnull @RequestParam("size") int size)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/pageable", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  PagedData<SampleGroupVo> pageable(
      @Nullable @RequestParam(value = "name", required = false) String name,
      @RequestBody Pageable pageable)
      throws ServiceException;
}
