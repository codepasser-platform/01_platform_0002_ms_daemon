package org.codepasser.base.web.provider;

import com.google.common.base.Throwables;

import org.codepasser.base.model.entity.inner.AttachmentCategory;
import org.codepasser.base.model.entity.inner.AttachmentStatus;
import org.codepasser.base.service.basement.AttachmentService;
import org.codepasser.common.async.AsyncCaller;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.web.configuration.storage.StorageConfiguration;
import org.codepasser.common.web.configuration.storage.StorageHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Nonnull;

/**
 * FileProvider.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/25 : base version.
 */
@Component
public class FileProvider {

  @InjectLogger private static Logger logger;

  @Autowired private StorageConfiguration.StorageSettings storageSettings;

  @Autowired private AttachmentService attachmentService;

  @Autowired private AsyncCaller asyncCaller;

  public void attachmentExpired(Long metaId) throws ServiceException {
    attachmentService.attachmentExpired(metaId);
  }

  /**
   * 附件存储.
   *
   * @param userId 当前操作用户
   * @param metaId 源数据ID
   * @param category 附件类型
   * @param file 上传文件
   * @return 附件ID (多附件外部循环调用)
   */
  @Nonnull
  public Long saveAttachment(
          Long userId, Long metaId, AttachmentCategory category, MultipartFile file)
          throws ServiceException {

    String metaName = file.getOriginalFilename();
    Long size = file.getSize();
    String attachmentName = StorageHelper.generateName(metaName);
    UUID uuid = UUID.randomUUID();
    String directory =
            StorageHelper.generateHashPath(storageSettings.getVolume(), category.key(), uuid);
    String link = StorageHelper.generateHashLink(storageSettings.getLink(), category.key(), uuid);
    String uri =
            StorageHelper.generateHashLink(storageSettings.getRelative(), category.key(), uuid);

    Long attachmentId =
            attachmentService.saveAttachmentData(
                    userId,
                    metaId,
                    metaName,
                    category,
                    directory,
                    directory + attachmentName,
                    link + attachmentName,
                    uri + attachmentName,
                    size);

    logger.info(
            "File provider main thread process start at : {}, user id : {}, meta id : {}, attachment id : {}.",
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()),
            userId,
            metaId,
            attachmentId);

    asyncCaller.asyncCall(
            () -> {
              try {
                logger.info(
                        "File provider sub thread process start at : {}, user id : {}, meta id : {}, attachment id : {}.",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()),
                        userId,
                        metaId,
                        attachmentId);
                StorageHelper.getInstance().saveFile(file.getInputStream(), directory, attachmentName);
                //  Update attachment status
                attachmentService.updateAttachmentStatus(attachmentId, AttachmentStatus.PERSISTED);
              } catch (ServiceException | IOException e) {
                logger.error(
                        "An error occurred in the save temp file, caused by :{}",
                        Throwables.getStackTraceAsString(e));
                try {
                  attachmentService.updateAttachmentStatus(attachmentId, AttachmentStatus.ERROR);
                } catch (ServiceException e1) {
                  logger.error(
                          "An error occurred in the save temp file, caused by :{}",
                          Throwables.getStackTraceAsString(e1));
                }
              } finally {
                logger.info(
                        "File provider sub thread process exit at : {}, user id : {}, meta id : {}, attachment id : {}.",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()),
                        userId,
                        metaId,
                        attachmentId);
              }
            });

    try {
      // FIXED the small file processing time is too fast to be deleted by the container
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // ignore
    }
    logger.info(
            "File provider main thread process exit at : {}, user id : {}, meta id : {}, attachment id : {}.",
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()),
            userId,
            metaId,
            attachmentId);

    return attachmentId;
  }
}
