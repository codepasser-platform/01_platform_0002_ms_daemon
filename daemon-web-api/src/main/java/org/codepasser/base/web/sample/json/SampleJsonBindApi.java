package org.codepasser.base.web.sample.json;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Date;
import javax.annotation.Nonnull;
import javax.validation.Valid;
import org.codepasser.base.service.sample.bo.SampleDateBo;
import org.codepasser.base.service.sample.json.SampleJsonBindService;
import org.codepasser.base.service.sample.vo.SampleVo;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleObjectMapperApi.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@RestController
@RequestMapping("/sample/json/bind")
public class SampleJsonBindApi {

  @Autowired private SampleJsonBindService sampleJsonBindService;

  @RequestMapping(value = "/empty", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public SampleVo emptyProperties() throws ServiceException {
    SampleVo sampleVo = new SampleVo();
    sampleVo.setId(111L);
    sampleVo.setPostCode("023452");
    sampleVo.setStart(new Date());
    sampleVo.setEnd(new Date());
    return sampleVo;
  }

  @Nonnull
  @RequestMapping(value = "/date", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  public SampleDateBo dateProperties(@Nonnull @Valid @RequestBody SampleDateBo sampleDateBo)
      throws ServiceException {
    return sampleDateBo;
  }

  @RequestMapping(value = "/empty/fegin", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public SampleVo emptyPropertiesFegin() throws ServiceException {
    return sampleJsonBindService.emptyProperties();
  }

  @Nonnull
  @RequestMapping(value = "/date/fegin", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
  public SampleDateBo datePropertiesFegin(@Nonnull @Valid @RequestBody SampleDateBo sampleDateBo)
      throws ServiceException {
    return sampleJsonBindService.dateProperties(sampleDateBo);
  }
}
