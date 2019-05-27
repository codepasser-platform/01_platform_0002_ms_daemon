package org.codepasser.common.async;

import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncCallerConfiguration implements AsyncConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(AsyncCallerConfiguration.class);

  @Bean
  public AsyncCaller asyncCaller() {
    return new AsyncCaller() {};
  }

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setQueueCapacity(100);
    executor.setMaxPoolSize(1000);
    executor.setThreadNamePrefix("async-");
    executor.initialize();
    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (throwable, method, objects) -> {
      logger.error(
          "Async caller uncaught exception, error occurs on method [{}], and the exception message is [{}]",
          method.getName(),
          throwable.getMessage());
      throwable.printStackTrace();
    };
  }
}
