package org.codepasser.base.service.impl.cache;

import org.codepasser.common.service.conifguration.cache.CacheableSupport;

/**
 * CacheInterface.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/17 : base version.
 */
public interface CacheableKeysSet extends CacheableSupport {

  String CACHE_AREAS = "DAEMON:BASE:CACHE:AREAS";

  String CACHE_AREAS_AUTO = "DAEMON:BASE:CACHE:AREAS:AUTO";
}
