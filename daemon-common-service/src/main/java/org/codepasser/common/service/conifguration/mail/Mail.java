package org.codepasser.common.service.conifguration.mail;

import java.io.Serializable;
import java.util.Map;
import org.codepasser.common.model.security.UserBasic;

public interface Mail {

  void send(UserBasic user, Map<String, Serializable> data);

  default void send(UserBasic user) {
    send(user, null);
  }
}
