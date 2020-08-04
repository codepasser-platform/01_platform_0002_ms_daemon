package org.codepasser.common.service.conifguration.sms;

import org.codepasser.common.model.security.UserBasic;

import java.io.Serializable;
import java.util.Map;

/**
 * Sms.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/20 : base version.
 */
public interface Sms {

  void send(UserBasic user, Map<String, Serializable> data);

  default void send(UserBasic user) {
    send(user, null);
  }
}
