package org.codepasser.base.service.sample.es;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import javax.annotation.Nonnull;
import org.codepasser.base.model.es.sample.dto.SampleManualDto;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.codepasser.common.service.response.PagedData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SampleEsService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/sample/es")
@Service
public interface SampleEsService {

  @Nonnull
  @RequestMapping(value = "/indexes", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse creationDocument(@Nonnull @RequestParam("name") String category)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/indexes", method = DELETE, produces = APPLICATION_JSON_UTF8_VALUE)
  AssertResponse deletionDocument(@Nonnull @RequestParam("name") String category)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/indexes/all", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  List<SampleManualDto> all() throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/indexes/all/sort", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  List<SampleManualDto> allSort() throws ServiceException;

  @Nonnull
  @RequestMapping(
      value = "/indexes/all/pageable",
      method = GET,
      produces = APPLICATION_JSON_UTF8_VALUE)
  PagedData<SampleManualDto> allPageable() throws ServiceException;

  @Nonnull
  @RequestMapping(
      value = "/indexes/find/{id}",
      method = GET,
      produces = APPLICATION_JSON_UTF8_VALUE)
  SampleManualDto find(@Nonnull @PathVariable("id") String id) throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/indexes/find/ids", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  List<SampleManualDto> find(@Nonnull String[] ids) throws ServiceException;
}
