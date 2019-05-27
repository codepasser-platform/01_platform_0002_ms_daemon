package org.codepasser.common.model;

import java.io.Serializable;

/**
 * In.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public interface In<E> extends Serializable {
  E convert();
}
