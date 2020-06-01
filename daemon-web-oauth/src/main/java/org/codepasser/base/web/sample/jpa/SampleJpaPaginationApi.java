package org.codepasser.base.web.sample.jpa;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.codepasser.base.service.sample.jpa.SampleJpaPaginationService;
import org.codepasser.base.service.sample.vo.SampleGroupVo;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.PagedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleJpaPaginationApi.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@RestController
@RequestMapping("/sample/jpa")
public class SampleJpaPaginationApi {

  @Autowired private SampleJpaPaginationService sampleJpaPaginationService;

  @Nonnull
  @RequestMapping(value = "/pagination", method = GET, produces = APPLICATION_JSON_VALUE)
  public PagedData<SampleGroupVo> pagination(
      @Nullable @RequestParam(value = "name", required = false) String name,
      @Nonnull @RequestParam("page") int page,
      @Nonnull @RequestParam("size") int size)
      throws ServiceException {
    return sampleJpaPaginationService.pagination(name, page, size);
  }

  @Deprecated
  @Nonnull
  @RequestMapping(value = "/pageable", method = RequestMethod.GET)
  public PagedData<SampleGroupVo> pageable(
      @Nullable @RequestParam(value = "name", required = false) String name,
      @PageableDefault(
              sort = {"createTime"},
              direction = DESC)
          Pageable pageable)
      throws ServiceException {
    PageRequest pageRequest =
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
    return sampleJpaPaginationService.pageable(name, pageRequest);
  }
}
