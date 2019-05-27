package org.codepasser.common.service.response;

/**
 * AssertResponse.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class AssertResponse implements BaseResponse {

  private boolean success;

  public AssertResponse() {}

  public AssertResponse(boolean success) {
    this.success = success;
  }

  public static AssertResponse of(boolean result) {
    return new AssertResponse(result);
  }

  public static AssertResponse failure() {
    return AssertResponse.of(false);
  }

  public static AssertResponse success() {
    return AssertResponse.of(true);
  }

  @Override
  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
