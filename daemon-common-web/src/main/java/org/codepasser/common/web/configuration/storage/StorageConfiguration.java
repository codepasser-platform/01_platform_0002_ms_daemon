package org.codepasser.common.web.configuration.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * StorageConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/25 : base version.
 */
@Configuration
public class StorageConfiguration {

  @Autowired private StorageSettings storageSettings;

  public StorageSettings getStorageSettings() {
    return storageSettings;
  }

  public void setStorageSettings(StorageSettings storageSettings) {
    this.storageSettings = storageSettings;
  }

  @Bean
  @ConditionalOnClass(StorageSettings.class)
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    // @Deprecated Set single file size limit
    // factory.setMaxFileSize(storageSettings.getMaxFileSize());
    factory.setMaxFileSize(DataSize.of(storageSettings.getMaxFileSize(), DataUnit.MEGABYTES));
    // @Deprecated Set overall request size limit
    // factory.setMaxRequestSize(storageSettings.getMaxRequestSize());
    factory.setMaxRequestSize(DataSize.of(storageSettings.getMaxRequestSize(), DataUnit.MEGABYTES));
    // factory.setLocation(storageSettings.getTempLocation());
    return factory.createMultipartConfig();
  }

  @Configuration
  @ConfigurationProperties("fiberhome.storage")
  public static class StorageSettings {

    private String relative;
    private String link;
    private String volume;
    private long maxFileSize;
    private long maxRequestSize;
    private String tempLocation;

    public String getRelative() {
      return relative;
    }

    public void setRelative(String relative) {
      this.relative = relative;
    }

    public String getLink() {
      return link;
    }

    public void setLink(String link) {
      this.link = link;
    }

    public String getVolume() {
      return volume;
    }

    public void setVolume(String volume) {
      this.volume = volume;
    }

    public long getMaxFileSize() {
      return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
      this.maxFileSize = maxFileSize;
    }

    public long getMaxRequestSize() {
      return maxRequestSize;
    }

    public void setMaxRequestSize(long maxRequestSize) {
      this.maxRequestSize = maxRequestSize;
    }

    public String getTempLocation() {
      return tempLocation;
    }

    public void setTempLocation(String tempLocation) {
      this.tempLocation = tempLocation;
    }
  }
}
