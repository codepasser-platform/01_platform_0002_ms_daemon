package org.codepasser.base.web.sample.etag;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/callback/oss")
public class SampleOssApi {

  @RequestMapping(value = "/sample", method = POST)
  public AssertResponse callbackPost(
      HttpServletRequest request, @RequestBody Map<String, Object> map)
      throws ServiceException, UnsupportedEncodingException {
    return AssertResponse.success();
  }

  @RequestMapping(value = "/sample", method = GET)
  public AssertResponse callbackGet(HttpServletRequest request)
      throws ServiceException, UnsupportedEncodingException {
    return AssertResponse.success();
  }
}
