package org.codepasser.common.async;

import org.springframework.scheduling.annotation.Async;

public interface AsyncCaller {

  @Async
  default void asyncCall(Runnable runnable) {
    runnable.run();
  }
}
