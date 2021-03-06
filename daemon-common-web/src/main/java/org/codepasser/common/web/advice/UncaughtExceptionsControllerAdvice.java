package org.codepasser.common.web.advice;

import org.codepasser.common.exception.AbstractException;
import org.codepasser.common.exception.AbstractRuntimeException;
import org.codepasser.common.exception.Message;
import org.codepasser.common.exception.WrappedRuntimeException;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.exception.BadRequestException;
import org.codepasser.common.service.exception.ConflictException;
import org.codepasser.common.service.exception.ForbiddenException;
import org.codepasser.common.service.exception.IllegalArgumentException;
import org.codepasser.common.service.exception.IllegalTermsException;
import org.codepasser.common.service.exception.NotFoundException;
import org.codepasser.common.service.exception.ReferenceException;
import org.codepasser.common.service.exception.RequestValidateException;
import org.codepasser.common.web.exception.AccessException;
import org.codepasser.common.web.exception.RepresentationMessage;
import org.codepasser.common.web.exception.WebException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.codepasser.common.utils.RequestUtils.getArguments;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
/**
 * UncaughtExceptionsControllerAdvice.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@ControllerAdvice(annotations = {RestController.class})
public class UncaughtExceptionsControllerAdvice {

  @InjectLogger private static Logger logger;

  /**
   * AbstractRuntimeException handling.
   *
   * @param exception AbstractRuntimeException
   * @return message
   */
  @ExceptionHandler(AbstractRuntimeException.class)
  @ResponseBody
  public ResponseEntity<Message> serviceException(
      HttpServletRequest request, AbstractRuntimeException exception) {

    HttpStatus status = BAD_REQUEST;
    if (exception instanceof ConflictException) {
      status = CONFLICT;
    } else if (exception instanceof ForbiddenException) {
      status = FORBIDDEN;
    } else if (exception instanceof IllegalArgumentException) {
      status = BAD_REQUEST;
    } else if (exception instanceof IllegalTermsException) {
      status = BAD_REQUEST;
    } else if (exception instanceof NotFoundException) {
      status = NOT_FOUND;
    } else if (exception instanceof ReferenceException) {
      status = UNPROCESSABLE_ENTITY;
    }
    Message message = new Message(exception);
    warnLog(request, message);
    return ResponseEntity.status(status).contentType(APPLICATION_JSON).body(message);
  }

  /**
   * WrappedRuntimeException handling.
   *
   * @param exception WrappedRuntimeException
   * @return message
   */
  @ExceptionHandler(WrappedRuntimeException.class)
  @ResponseBody
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public Message runtimeException(HttpServletRequest request, WrappedRuntimeException exception) {
    Message message = new Message(exception);
    warnLog(request, message);
    return message;
  }

  /**
   * AbstractException handling.
   *
   * @param exception AbstractException
   * @return message
   */
  @ExceptionHandler(AbstractException.class)
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public Message serviceException(HttpServletRequest request, AbstractException exception) {
    Message message = new Message(exception);
    warnLog(request, message);
    return message;
  }

  /**
   * RuntimeException handling.
   *
   * @param exception RuntimeException
   * @return message
   */
  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public Message runtimeException(HttpServletRequest request, RuntimeException exception) {
    Message message = new Message(new WebException(exception));
    errorLog(request, message, exception);
    return message;
  }

  /**
   * IllegalStateException handling.
   *
   * @param exception IllegalStateException
   * @return message
   */
  @ExceptionHandler(IllegalStateException.class)
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public Message handleArgumentErrors(HttpServletRequest request, IllegalStateException exception) {
    Message message = new Message(new BadRequestException(exception));
    warnLog(request, message);
    return message;
  }

  /**
   * HttpMessageNotReadableException handling.
   *
   * @param exception HttpMessageNotReadableException
   * @return message
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public Message handleArgumentErrors(
      HttpServletRequest request, HttpMessageNotReadableException exception) {
    Message message = new Message(new BadRequestException(exception));
    warnLog(request, message);
    return message;
  }

  /**
   * MethodArgumentNotValidException handling.
   *
   * @param exception MethodArgumentNotValidException
   * @return message
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public Message handleArgumentErrors(
      HttpServletRequest request, MethodArgumentNotValidException exception) {
    Message message = new Message(new RequestValidateException(exception));
    warnLog(request, message);
    return message;
  }

  /**
   * IllegalArgumentException handling.
   *
   * @param exception IllegalArgumentException
   * @return message
   */
  @ExceptionHandler(java.lang.IllegalArgumentException.class)
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  public Message handleArgumentErrors(
      HttpServletRequest request, java.lang.IllegalArgumentException exception) {
    Message message = new Message(new RequestValidateException(exception));
    warnLog(request, message);
    return message;
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseBody
  @ResponseStatus(FORBIDDEN)
  public RepresentationMessage accessException(AccessDeniedException exception) {
    return new RepresentationMessage(new AccessException(exception));
  }

  private void warnLog(HttpServletRequest request, Message message) {
    logger.warn(
        "Process request url '{}', arguments=[{}], errorCode=[{}], errorMsg=[{}], errorDetail=[{}]",
        request.getRequestURL(),
        getArguments(request),
        message.getCode(),
        message.getMessage(),
        message.getDetail());
  }

  private void errorLog(HttpServletRequest request, Message message, Throwable exception) {
    logger.error(
        "Process request url '{}', arguments=[{}], errorCode=[{}], errorMsg=[{}], errorDetail=[{}]",
        request.getRequestURL(),
        getArguments(request),
        message.getCode(),
        message.getMessage(),
        message.getDetail());
    logger.error("An error occurred, Please check stack_trace for details.", exception);
  }
}
