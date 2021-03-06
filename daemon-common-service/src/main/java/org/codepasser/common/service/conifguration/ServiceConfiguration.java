package org.codepasser.common.service.conifguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * WebMvcConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Configuration
public class ServiceConfiguration {

  @Autowired private ServiceSettings serviceSettings;

  public ServiceSettings getServiceSettings() {
    return serviceSettings;
  }

  public void setServiceSettings(ServiceSettings serviceSettings) {
    this.serviceSettings = serviceSettings;
  }

  @Configuration
  @ConfigurationProperties("daemon.service")
  public static class ServiceSettings {

    private long identifyingCodeTimeout = 10;

    private String areaScope;

    public long getIdentifyingCodeTimeout() {
      return identifyingCodeTimeout;
    }

    public void setIdentifyingCodeTimeout(long identifyingCodeTimeout) {
      this.identifyingCodeTimeout = identifyingCodeTimeout;
    }

    public String getAreaScope() {
      return areaScope;
    }

    public void setAreaScope(String areaScope) {
      this.areaScope = areaScope;
    }
  }

  @Configuration
  @ConfigurationProperties("daemon.oauth")
  public static class OauthSettings {
    private int accessTokenValidity;
    private int refreshTokenValidity;
    private boolean autoapprove;

    public int getAccessTokenValidity() {
      return accessTokenValidity;
    }

    public void setAccessTokenValidity(int accessTokenValidity) {
      this.accessTokenValidity = accessTokenValidity;
    }

    public int getRefreshTokenValidity() {
      return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(int refreshTokenValidity) {
      this.refreshTokenValidity = refreshTokenValidity;
    }

    public boolean isAutoapprove() {
      return autoapprove;
    }

    public void setAutoapprove(boolean autoapprove) {
      this.autoapprove = autoapprove;
    }
  }
}
