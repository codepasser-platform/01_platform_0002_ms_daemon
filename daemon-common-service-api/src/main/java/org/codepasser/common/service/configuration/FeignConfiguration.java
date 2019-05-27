package org.codepasser.common.service.configuration;

import com.google.common.io.CharStreams;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.codepasser.common.exception.AdException;
import org.codepasser.common.exception.Message;
import org.codepasser.common.exception.WrappedRuntimeException;
import org.codepasser.common.utils.Json;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FeignConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Configuration
public class FeignConfiguration {

  @Bean
  public ErrorDecoder errorDecoder() {

    return (methodKey, response) -> {
      try {
        String content = CharStreams.toString(response.body().asReader());
        Message message = Json.readValue(content, Message.class);
        if (message != null && message.getType() != null) {
          String type = message.getType();
          Class<?> causeType = Class.forName(type);
          if (AdException.class.isAssignableFrom(causeType)) {
            Constructor<?> constructor = causeType.getConstructor(Message.class);
            Exception exception = (Exception) constructor.newInstance(message);
            return message.isWrappedInRuntimeException()
                ? new WrappedRuntimeException((AdException) exception)
                : exception;
          }
          if (RuntimeException.class.isAssignableFrom(causeType)) {
            return new WrappedRuntimeException(message);
          }
        }

        return new RuntimeException("unknown error: " + content);
      } catch (IOException
          | ClassNotFoundException
          | NoSuchMethodException
          | InstantiationException
          | InvocationTargetException
          | IllegalAccessException e) {
        throw new RuntimeException("can not create exception from response.", e);
      }
    };
  }

  @Bean
  public Retryer retryer() {
    return new Retryer() {

      @Override
      public void continueOrPropagate(RetryableException e) {
        throw e;
      }

      @Override
      public Retryer clone() {
        return this;
      }
    };
  }
}
