package org.codepasser.base.service.bootstrap.impl;

import org.codepasser.base.service.bootstrap.DataLoader;
import org.codepasser.base.service.bootstrap.InitializerConfiguration;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;

/**
 * DefaultDataCreator.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
@Component
public class DefaultDataLoader implements DataLoader {

  @InjectLogger private Logger logger;

  @Autowired private InitializerConfiguration initializerConfiguration;

  @Override
  public File provincesJson() {
    return classLoaderFile(initializerConfiguration.getAreaSettings().getProvincesJson());
  }

  @Override
  public File citiesJson() {
    return classLoaderFile(initializerConfiguration.getAreaSettings().getCitiesJson());
  }

  @Override
  public File districtsJson() {
    return classLoaderFile(initializerConfiguration.getAreaSettings().getDistrictsJson());
  }

  private File classLoaderFile(String fileName) {
    logger.info("Initializer json data files [{}]", fileName);
    ClassLoader classLoader = getClass().getClassLoader();
    URL url = classLoader.getResource(fileName);
    File file = new File(url.getFile());
    return file;
  }
}
