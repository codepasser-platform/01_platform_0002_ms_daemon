package org.codepasser.base.web.console;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import org.codepasser.base.service.basement.AttachmentService;
import org.codepasser.base.service.basement.vo.AttachmentItem;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ResourceApi.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/13 : base version.
 */
@RestController
@RequestMapping("/rest/console/resource")
public class ResourceApi {

  @Autowired private AttachmentService attachmentService;

  @Nonnull
  @PreAuthorize("permitAll")
  @RequestMapping(value = "/{resourceId}", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
  public AttachmentItem download(@PathVariable(value = "resourceId") Long resourceId)
      throws ServiceException {
    return attachmentService.findAttachmentById(resourceId);
  }

  @Nonnull
  @PreAuthorize("permitAll")
  @RequestMapping(
      value = "/stream/{resourceId}",
      method = GET,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> downloadAsStream(
      @PathVariable(value = "resourceId") Long resourceId, HttpServletRequest request)
      throws ServiceException, UnsupportedEncodingException {
    AttachmentItem attachmentItem = attachmentService.findAttachmentById(resourceId);
    String fileName = attachmentItem.getName();
    String userAgent = request.getHeader("USER-AGENT").toLowerCase();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    headers.add("content-type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
    InputStream is = null;
    try {
      if (userAgent != null) {
        // IE & Edge浏览器下载
        if ((userAgent.contains("msie") || userAgent.contains("like gecko"))
            && !userAgent.contains("chrome")) {
          headers.add(
              "Content-Disposition",
              String.format("attachment;filename=\"%s\"", URLEncoder.encode(fileName, "UTF-8")));
        } else {
          // 非IE浏览器下载
          headers.add(
              "Content-Disposition",
              String.format(
                  "attachment;filename=\"%s\"",
                  new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
        }
      }
      is = new FileInputStream(new File(attachmentItem.getPath()));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(new InputStreamResource(is));
  }
}
