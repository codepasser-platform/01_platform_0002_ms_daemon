package org.codepasser.base.service.sample.es;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.codepasser.base.model.es.sample.dto.SampleManualDto;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.PagedData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SampleEsQueryService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/es")
@Service
public interface SampleEsQueryService {

  @Nonnull
  @RequestMapping(
      value = "/indexes/query/pageable",
      method = POST,
      produces = APPLICATION_JSON_UTF8_VALUE)
  PagedData<SampleManualDto> queryPageable(
      @Nullable @RequestParam(value = "name", required = false) String name,
      @RequestBody Pageable pageable)
      throws ServiceException;

  @Nonnull
  @RequestMapping(
      value = "/indexes/query/pageable/key",
      method = POST,
      produces = APPLICATION_JSON_UTF8_VALUE)
  PagedData<SampleManualDto> keyPageable(
      @Nullable @RequestParam(value = "key", required = false) String key,
      @RequestBody Pageable pageable)
      throws ServiceException;
}
