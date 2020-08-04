package org.codepasser.common.web.configuration.storage;

import com.google.common.base.Throwables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * StorageHelper.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public final class StorageHelper {

  private static final Logger logger = LoggerFactory.getLogger(StorageHelper.class);

  private static final String FILE_TIMESTAMP = "yyyyMMddHHmmssSSS";
  // 散列划分大小
  private static final int DEFAULT_STEP = 2;
  private static final String DIR_SEPARATOR = "/";
  private static final String FILE_SEPARATOR = ".";
  private static final String FILE_SEPARATOR_PATTERN = "\\.";
  private static final String UNKNOWN_FILE = ".unknown";

  public static StorageHelper getInstance() {
    return new StorageHelper();
  }

  /**
   * 时间戳生成文件名.
   *
   * @param metaName 源文件名
   * @return 生成文件名
   */
  public static String generateName(String metaName) {
    String[] nameParts = metaName.split(FILE_SEPARATOR_PATTERN);
    String ext = UNKNOWN_FILE;
    if (nameParts.length > 1) {
      ext = FILE_SEPARATOR + nameParts[nameParts.length - 1];
    }
    return new SimpleDateFormat(FILE_TIMESTAMP).format(new Date()) + ext;
  }

  /**
   * 生成文件存储路径.
   *
   * @param dirPrefix 文件存储基路径
   * @param fileCategory 文件分类
   * @param uuid 散列ID
   * @return 文件存储路径
   */
  public static String generateHashPath(String dirPrefix, String fileCategory, UUID uuid) {
    String hashPath = hashPath(uuid.toString());
    if (StringUtils.isEmpty(hashPath)) {
      return null;
    }
    return dirPrefix + fileCategory + DIR_SEPARATOR + hashPath + DIR_SEPARATOR;
  }

  /**
   * 生成文件访问URL.
   *
   * @param linkPrefix 文件访问基URL
   * @param fileCategory 文件分类
   * @param uuid 散列ID
   * @return 文件访问URL
   */
  public static String generateHashLink(String linkPrefix, String fileCategory, UUID uuid) {
    String hashPath = hashPath(uuid.toString());
    if (StringUtils.isEmpty(hashPath)) {
      return null;
    }
    return linkPrefix + fileCategory + DIR_SEPARATOR + hashPath + DIR_SEPARATOR;
  }

  private static String hashPath(String uuid) {
    StringBuilder path = new StringBuilder();
    if (StringUtils.isEmpty(uuid)) {
      return null;
    }
    uuid = uuid.replaceAll("-", "");
    int length = uuid.length();
    for (int i = 0; i < length; i += DEFAULT_STEP) {
      String temp = "";
      if (length - i < DEFAULT_STEP) {
        temp = uuid.substring(i);
      } else {
        temp = uuid.substring(i, i + DEFAULT_STEP);
      }
      path.append(temp).append(DIR_SEPARATOR);
      if (temp.length() < DEFAULT_STEP) {
        break;
      }
    }
    return path.substring(0, path.length() - 1);
  }

  private static boolean cascadeDir(String path) {
    String[] folders = path.split(DIR_SEPARATOR);
    folders[1] = DIR_SEPARATOR + folders[1];
    String dir = "";
    boolean isExist = true;
    for (int index = 1; isExist && index < folders.length; index++) {
      if (index == 1) {
        dir = folders[index];
      } else {
        dir = dir + DIR_SEPARATOR + folders[index];
      }
      File file = new File(dir);
      if (!file.exists()) {
        isExist = file.mkdir();
      }
    }
    return isExist;
  }

  /**
   * 文件存储.
   *
   * @param is 文件流
   * @param path 文件路径
   * @param filename 文件名称
   */
  public long saveFile(InputStream is, String path, String filename) {
    if (!cascadeDir(path)) {
      logger.error(
          "An error occurred in the export file, caused by File Directory Creation failed");
      return 0L;
    }
    FileOutputStream fs = null;
    try {
      fs = new FileOutputStream(path + filename);
      byte[] buffer = new byte[1024 * 1024];
      int byteRead = 0;
      while ((byteRead = is.read(buffer)) != -1) {
        fs.write(buffer, 0, byteRead);
        fs.flush();
      }
    } catch (Exception fileError) {
      logger.error(
          "An error occurred in the export file, caused by :{}",
          Throwables.getStackTraceAsString(fileError));
    } finally {
      try {
        if (fs != null) {
          fs.close();
        }
        if (is != null) {
          is.close();
        }
      } catch (Exception e) {
        logger.warn(
            "An warn occurred in the export file, caused by :{}",
            Throwables.getStackTraceAsString(e));
      }
    }
    long size = 0L;
    File file = new File(path + filename);
    if (file.exists()) {
      size = file.length();
    }
    return size;
  }
}
