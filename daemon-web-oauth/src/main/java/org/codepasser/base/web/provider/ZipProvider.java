package org.codepasser.base.web.provider;

import com.google.common.base.Throwables;

import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.web.configuration.storage.StorageHelper;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * ZipProvider.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/25 : base version.
 */
@Component
public class ZipProvider {

  private static final String DIR_SEPARATOR = "/";

  @InjectLogger private static Logger logger;

  public boolean decompress(String zipPath, String directory) throws ServiceException {

    File file = new File(zipPath);
    if (!file.exists()) {
      logger.warn("An error occurred in the decompress file, caused by :{}", "file not found");
      return false;
    }
    ZipFile zipFile = null;
    try {
      zipFile = new ZipFile(file);
    } catch (IOException e) {
      logger.error(
          "An error occurred in the decompress file, caused by :{}",
          Throwables.getStackTraceAsString(e));
      return false;
    }

    Enumeration fileElements = zipFile.entries();
    while (fileElements.hasMoreElements()) {
      ZipEntry fileElement = (ZipEntry) fileElements.nextElement();
      logger.info("decompress file >>> {}", fileElement.getName());
      if (!fileElement.isDirectory()) { // 文件
        if (StringUtils.isEmpty(fileElement.getName())) {
          logger.error("An error occurred in the decompress file, caused by :{}", "file not found");
          return false;
        }
        String path = directory;
        String fileName = fileElement.getName();
        if (fileName.lastIndexOf(DIR_SEPARATOR) > 0) {
          path = directory + fileName.substring(0, fileName.lastIndexOf(DIR_SEPARATOR) + 1);
          fileName = fileName.substring(fileName.lastIndexOf(DIR_SEPARATOR) + 1);
        }
        logger.info("decompress path >>> {} file >>> {}", path, fileName);
        try {
          InputStream is = zipFile.getInputStream(fileElement);
          // 将压缩文件内容写入到这个文件中
          saveFile(is, path, fileName);
        } catch (IOException e) {
          logger.error("An error occurred in the decompress file, caused by :{}", "file not found");
          return false;
        }
      }
    }
    return true;
  }

  private void saveFile(InputStream is, String path, String filename) {
    StorageHelper.getInstance().saveFile(is, path, filename);
  }
}
