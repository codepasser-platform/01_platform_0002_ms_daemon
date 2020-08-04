package org.codepasser.base.service.basement;

import org.codepasser.base.model.entity.inner.AttachmentCategory;
import org.codepasser.base.model.entity.inner.AttachmentStatus;
import org.codepasser.base.service.basement.vo.AttachmentDetail;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nonnull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * AttachmentService.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/9/25 : base version.
 */
@FeignClient("service-base")
@RequestMapping("/service-base/attachment")
@Service
public interface AttachmentService {

  /**
   * 附件数据保存.
   *
   * @param userId 操作用户
   * @param metaId 源数据ID
   * @param metaName 源文件名
   * @param category 附件类型
   * @param directory 附件存储路径
   * @param path 附件存储路径
   * @param url 附件访问路径
   * @param uri 附件访问路径
   * @param size 文件大小
   * @return 附件ID
   * @throws ServiceException 异常.
   */
  @Nonnull
  @RequestMapping(value = "/save", method = POST, produces = APPLICATION_JSON_VALUE)
  Long saveAttachmentData(
      @RequestParam(value = "userId") Long userId,
      @RequestParam(value = "metaId") Long metaId,
      @RequestParam(value = "metaName") String metaName,
      @RequestParam(value = "category") AttachmentCategory category,
      @RequestParam(value = "directory") String directory,
      @RequestParam(value = "path") String path,
      @RequestParam(value = "url") String url,
      @RequestParam(value = "uri") String uri,
      @RequestParam(value = "size") Long size)
      throws ServiceException;

  /**
   * 更新存储状态.
   *
   * @param id 附件ID
   * @param status 附件状态
   * @return 结果
   * @throws ServiceException 异常.
   */
  @Nonnull
  @RequestMapping(value = "/update", method = PUT, produces = APPLICATION_JSON_VALUE)
  AssertResponse updateAttachmentStatus(
      @RequestParam(value = "id") Long id, @RequestParam(value = "status") AttachmentStatus status)
      throws ServiceException;

  /**
   * 更新存储persisted.
   *
   * @param id 附件ID
   * @return 结果
   * @throws ServiceException 异常.
   */
  @Nonnull
  @RequestMapping(value = "/persisted", method = PUT, produces = APPLICATION_JSON_VALUE)
  AssertResponse updateAttachmentPersisted(
      @RequestParam(value = "id") Long id, @RequestParam(value = "size") Long size)
      throws ServiceException;

  /**
   * 修改时存储文件过期.
   *
   * @param metaId 关联源数据ID
   * @return 操作结果
   * @throws ServiceException 异常.
   */
  @Nonnull
  @RequestMapping(value = "/expired", method = PUT, produces = APPLICATION_JSON_VALUE)
  AssertResponse attachmentExpired(@RequestParam(value = "metaId") Long metaId)
      throws ServiceException;

  /**
   * 删除存储文件.
   *
   * @param metaId 关联源数据ID
   * @return 操作结果
   * @throws ServiceException 异常.
   */
  @Nonnull
  @RequestMapping(value = "/deleted", method = DELETE, produces = APPLICATION_JSON_VALUE)
  AssertResponse attachmentDeleted(@RequestParam(value = "metaId") Long metaId)
      throws ServiceException;

  /**
   * 存储文件获取.
   *
   * @param id 附件ID
   * @return 附件信息
   * @throws ServiceException 异常
   */
  @Nonnull
  @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
  AttachmentDetail findAttachmentById(@PathVariable(value = "id") Long id) throws ServiceException;

  /**
   * 删除存储文件.
   *
   * @param attachmentId 附件数据ID
   * @return 操作结果
   * @throws ServiceException 异常.
   */
  @Nonnull
  @RequestMapping(value = "/delete", method = PUT, produces = APPLICATION_JSON_VALUE)
  AssertResponse attachmentDeleteById(@RequestParam(value = "attachmentId") Long attachmentId)
      throws ServiceException;
}
