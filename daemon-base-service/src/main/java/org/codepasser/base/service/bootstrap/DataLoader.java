package org.codepasser.base.service.bootstrap;

import java.io.File;

/**
 * DataLoader.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
public interface DataLoader {

  File provincesJson();

  File citiesJson();

  File districtsJson();
}
