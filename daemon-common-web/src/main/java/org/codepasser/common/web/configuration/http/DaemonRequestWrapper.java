package org.codepasser.common.web.configuration.http;

import org.apache.commons.codec.Charsets;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * DaemonRequestWrapper.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2020/7/9 : base version.
 */
public class DaemonRequestWrapper extends HttpServletRequestWrapper {

  private String body;

  public DaemonRequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    StringBuilder sb = new StringBuilder();
    String line = null;
    BufferedReader reader = request.getReader();
    while ((line = reader.readLine()) != null) {
      sb.append(line);
    }
    body = sb.toString();
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    ByteArrayInputStream byteArrayInputStream =
        new ByteArrayInputStream(body.getBytes(Charsets.UTF_8));
    return new ServletInputStream() {

      @Override
      public int read() throws IOException {
        return byteArrayInputStream.read();
      }

      @Override
      public boolean isFinished() {
        return byteArrayInputStream.available() == 0;
      }

      @Override
      public boolean isReady() {
        return true;
      }

      @Override
      public void setReadListener(ReadListener listener) {}
    };
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
