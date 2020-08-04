package org.codepasser.base.web.sample.file;

import org.codepasser.base.model.entity.inner.AttachmentCategory;
import org.codepasser.base.service.sample.bo.SampleDateBo;
import org.codepasser.base.web.provider.FileProvider;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import javax.validation.Valid;

import static org.codepasser.common.model.ConstantInterface.ROOT_ADMIN_ID;
import static org.codepasser.common.model.ConstantInterface.ROOT_ORG_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * SampleUploadApi.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
@RestController
@RequestMapping("/sample/file")
public class SampleUploadApi {

  @Autowired private FileProvider fileProvider;

  // @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = "/upload", method = POST, produces = APPLICATION_JSON_VALUE)
  public AssertResponse upload(
      @Nonnull @Valid SampleDateBo sampleDateBo,
      @RequestParam(value = "file", required = false) MultipartFile file)
      throws ServiceException {

    // 1 DEMO @AuthenticationPrincipal UserDetails user
    Long orgId = ROOT_ORG_ID;
    Long userId = ROOT_ADMIN_ID;
    // 2 DEMO Store business data back to ID
    Long metaId = 10000L;

    // 3 DEMO edit detail with file override;
    // fileProvider.attachmentExpired(metaId);
    if (file != null) {
      fileProvider.saveAttachment(userId, metaId, AttachmentCategory.AVATAR, file);
    }
    return new AssertResponse(true);
  }

  // @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = "/upload/multipart", method = POST, produces = APPLICATION_JSON_VALUE)
  public AssertResponse multipart(
      @Nonnull @Valid SampleDateBo sampleDateBo, @RequestParam("files") MultipartFile[] files)
      throws ServiceException {

    // 1 DEMO @AuthenticationPrincipal UserDetails user
    Long orgId = ROOT_ORG_ID;
    Long userId = ROOT_ADMIN_ID;

    // 2 DEMO Store business data back to ID
    Long metaId = 10000L;

    for (int fileIndex = 0; fileIndex < files.length; fileIndex++) {
      // 3 DEMO edit detail with file override;
      // fileProvider.attachmentExpired(metaId);
      fileProvider.saveAttachment(userId, metaId, AttachmentCategory.AVATAR, files[fileIndex]);
    }
    return new AssertResponse(true);
  }
}
