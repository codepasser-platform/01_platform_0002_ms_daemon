package org.codepasser.base.service.basement;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.annotation.Nonnull;
import org.codepasser.base.model.business.category.IdentifyingCodeType;
import org.codepasser.base.service.basement.vo.IdentifyingCode;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * IdentifyingCodeService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/master/identifying-code")
@Service
public interface IdentifyingCodeService {

  @Nonnull
  @RequestMapping(value = "/phone", method = GET, produces = APPLICATION_JSON_VALUE)
  AssertResponse sendIdentifyingCode(@Nonnull @RequestParam("target") String target)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/phone", method = POST, produces = APPLICATION_JSON_VALUE)
  AssertResponse checkIdentifyingCode(
      @Nonnull @RequestParam("target") String target, @Nonnull @RequestParam("code") String code)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/email", method = GET, produces = APPLICATION_JSON_VALUE)
  AssertResponse sendIdentifyingCodeByEmail(@Nonnull @RequestParam("target") String target)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/email", method = POST, produces = APPLICATION_JSON_VALUE)
  AssertResponse checkIdentifyingCodeByEmail(
      @Nonnull @RequestParam("target") String target, @Nonnull @RequestParam("code") String code)
      throws ServiceException;

  @Nonnull
  @RequestMapping(value = "/target", method = GET, produces = APPLICATION_JSON_VALUE)
  IdentifyingCode findIdentifyingCode(
      @Nonnull @RequestParam("target") String target,
      @Nonnull @RequestParam("type") IdentifyingCodeType type)
      throws ServiceException;
}
