package org.codepasser.base.web.sample.file;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import org.codepasser.base.service.basement.AttachmentService;
import org.codepasser.base.service.basement.vo.AttachmentItem;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.exception.ServiceException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleDownloadApi.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/13 : base version.
 */
@RestController
@RequestMapping("/sample/file")
public class SampleDownloadApi {

  @Autowired private AttachmentService attachmentService;

  @InjectLogger private Logger logger;

  //  @PreAuthorize("isAuthenticated()")
  @Nonnull
  @RequestMapping(
      value = "/download/{resourceId}",
      method = GET,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public AttachmentItem download(@PathVariable(value = "resourceId") Long resourceId)
      throws ServiceException {
    return attachmentService.findAttachmentById(resourceId);
  }

  // @PreAuthorize("isAuthenticated()")
  @Nonnull
  @RequestMapping(
      value = "/download/stream/{resourceId}",
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
    headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
    InputStream is = null;
    try {
      if (userAgent != null) {
        // IE & Edge浏览器下载
        if ((userAgent.contains("msie") || userAgent.contains("like gecko"))
            && !userAgent.contains("chrome")) {
          headers.add(
              "Content-Disposition",
              String.format(
                  "attachment;filename=\"%s\"",
                  URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())));
        } else {
          // 非IE浏览器下载
          headers.add(
              "Content-Disposition",
              String.format(
                  "attachment;filename=\"%s\"",
                  new String(
                      fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
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

  @RequestMapping("/download/stream/size/{resourceId}")
  public Long downloadAsOctetStreamSize(@PathVariable(value = "resourceId") Long resourceId)
      throws ServiceException {
    // Get your file stream from wherever.
    AttachmentItem attachmentItem = attachmentService.findAttachmentById(resourceId);
    File downloadFile = new File(attachmentItem.getPath());
    return downloadFile.length();
  }

  @RequestMapping("/download/stream/range/{resourceId}")
  public ResponseEntity<?> downloadAsOctetStream(
      @PathVariable(value = "resourceId") Long resourceId, HttpServletRequest request)
      throws ServiceException {

    // Get your file stream from wherever.
    AttachmentItem attachmentItem = attachmentService.findAttachmentById(resourceId);
    String fileName = attachmentItem.getName();
    File downloadFile = new File(attachmentItem.getPath());
    byte[] contentParts = null;
    HttpStatus httpStatus = HttpStatus.OK;
    // Settings response headers
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
    // 解析断点续传相关信息
    headers.add("Accept-Ranges", "bytes");
    // set headers for the response
    String userAgent = request.getHeader("USER-AGENT").toLowerCase();

    RandomAccessFile randomFile = null;
    ByteArrayOutputStream swapStream = null;
    try {
      if (userAgent != null) {
        // IE & Edge浏览器下载
        if ((userAgent.contains("msie") || userAgent.contains("like gecko"))
            && !userAgent.contains("chrome")) {
          headers.add(
              "Content-Disposition",
              String.format(
                  "attachment;filename=\"%s\"",
                  URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())));
        } else {
          // 非IE浏览器下载
          headers.add(
              "Content-Disposition",
              String.format(
                  "attachment;filename=\"%s\"",
                  new String(
                      fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
        }
      }

      long downloadSize = downloadFile.length();
      long fullSize = downloadFile.length();
      headers.add("Content-Full-Length", fullSize + "");

      long fromPos = 0, toPos = 0;
      if (request.getHeader("Range") == null) {
        httpStatus = HttpStatus.OK;
        toPos = fullSize;
      } else {
        // 若客户端传来Range，说明之前下载了一部分，设置206状态(SC_PARTIAL_CONTENT)
        httpStatus = HttpStatus.PARTIAL_CONTENT;
        String range = request.getHeader("Range");
        String bytes = range.replaceAll("bytes=", "");
        String[] ary = bytes.split("-");
        fromPos = Long.parseLong(ary[0]);
        if (ary.length == 2) {
          toPos = Long.parseLong(ary[1]);
        }
        int size;
        if (toPos > fromPos) {
          size = (int) (toPos - fromPos);
          if ((toPos - fromPos) > (downloadSize - fromPos)) {
            toPos = downloadSize;
            size = (int) (downloadSize - fromPos);
          }
        } else {
          toPos = downloadSize;
          size = (int) (downloadSize - fromPos);
        }
        downloadSize = size;
      }

      NumberFormat nt = NumberFormat.getPercentInstance();
      nt.setMinimumFractionDigits(2);

      headers.add("Content-Length", downloadSize + "");
      headers.add("Content-Range", nt.format((double) toPos / (double) fullSize) + "");
      headers.add("Content-Range-Type", (toPos == fullSize ? "END" : "RANGE"));
      if (toPos == fullSize) {
        httpStatus = HttpStatus.OK;
      }

      randomFile = new RandomAccessFile(downloadFile, "rw");
      // 设置下载起始位置
      if (fromPos > 0) {
        randomFile.seek(fromPos);
      }
      // 缓冲区大小
      int bufLen = (int) (downloadSize < 2048 ? downloadSize : 2048);
      byte[] buffer = new byte[bufLen];
      int num;
      int count = 0; // 当前写到客户端的大小
      swapStream = new ByteArrayOutputStream();
      while ((num = randomFile.read(buffer)) != -1) {
        count += num;
        swapStream.write(buffer, 0, num);
        // 处理最后一段，计算不满缓冲区的大小
        if (downloadSize - count < bufLen) {
          bufLen = (int) (downloadSize - count);
          if (bufLen == 0) {
            break;
          }
          buffer = new byte[bufLen];
        }
      }
      contentParts = swapStream.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (randomFile != null) {
        try {
          randomFile.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (swapStream != null) {
        try {
          swapStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return new ResponseEntity<>(contentParts, headers, httpStatus);
  }
}
