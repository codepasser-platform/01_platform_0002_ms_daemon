package org.codepasser.common.service.conifguration.mail;

import org.codepasser.common.model.security.UserBasic;

import java.io.Serializable;
import java.util.Map;

public interface Mail {

  void send(UserBasic user, Map<String, Serializable> data);

  default void send(UserBasic user) {
    send(user, null);
  }
}
