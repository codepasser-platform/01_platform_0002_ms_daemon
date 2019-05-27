package org.codepasser.common.model;

import java.io.Serializable;

/**
 * Out.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public interface Out<V, E> extends Serializable {
  V from(E entity);
}
