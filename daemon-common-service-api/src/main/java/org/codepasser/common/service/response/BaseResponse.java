package org.codepasser.common.service.response;

import java.io.Serializable;
/**
 * BaseResponse.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public interface BaseResponse extends Serializable {

  boolean isSuccess();
}
