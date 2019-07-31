package org.codepasser.base.web.configuration;

import javax.servlet.http.HttpSessionEvent;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * CasServerConfiguration.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019-07-30 : base version.
 */
@Configuration
@EnableConfigurationProperties({
  CasServerConfiguration.CasServerSettings.class,
  CasServerConfiguration.CasServiceSettings.class
})
public class CasServerConfiguration {

  @Autowired private CasServerSettings casServerSettings;

  @Autowired private CasServiceSettings casServiceSettings;

  @Autowired
  @Qualifier("cas")
  private UserDetailsService casUserIdentityService;

  @Bean
  public ServiceProperties serviceProperties() {
    ServiceProperties serviceProperties = new ServiceProperties();
    serviceProperties.setService(casServiceSettings.getUrl() + this.casServiceSettings.getLogin());
    serviceProperties.setSendRenew(this.casServiceSettings.isSendRenew());
    return serviceProperties;
  }

  @Bean
  @Qualifier("cas")
  public AuthenticationEntryPoint casAuthenticationEntryPoint(ServiceProperties serviceProperties) {
    CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
    entryPoint.setLoginUrl(casServerSettings.getLoginUrl());
    entryPoint.setServiceProperties(serviceProperties);
    return entryPoint;
  }

  @Bean
  public TicketValidator ticketValidator() {
    // 指定cas校验器
    return new Cas30ServiceTicketValidator(casServerSettings.getUrl());
  }
  // cas认证
  @Bean
  @Qualifier("cas")
  public CasAuthenticationProvider casAuthenticationProvider() {
    CasAuthenticationProvider provider = new CasAuthenticationProvider();
    provider.setServiceProperties(serviceProperties());
    provider.setTicketValidator(ticketValidator());
    provider.setUserDetailsService(casUserIdentityService);
    provider.setKey("CAS_PROVIDER_WEB_CAS");
    return provider;
  }

  @Bean
  public SecurityContextLogoutHandler securityContextLogoutHandler() {
    return new SecurityContextLogoutHandler();
  }

  @Bean
  @Qualifier("cas")
  public LogoutFilter logoutFilter() {
    // 退出后转发路径
    LogoutFilter logoutFilter =
        new LogoutFilter(casServerSettings.getLogoutUrl(), securityContextLogoutHandler());
    // cas退出
    logoutFilter.setFilterProcessesUrl(casServiceSettings.getLogout());
    return logoutFilter;
  }

  @Bean
  @Qualifier("cas")
  public SingleSignOutFilter singleSignOutFilter() {
    // 单点退出
    SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
    singleSignOutFilter.setCasServerUrlPrefix(casServerSettings.getUrl());
    singleSignOutFilter.setIgnoreInitConfiguration(true);
    return singleSignOutFilter;
  }

  // 设置退出监听
  @EventListener
  public SingleSignOutHttpSessionListener singleSignOutHttpSessionListener(HttpSessionEvent event) {
    return new SingleSignOutHttpSessionListener();
  }

  public CasServerSettings getCasServerSettings() {
    return casServerSettings;
  }

  public void setCasServerSettings(CasServerSettings casServerSettings) {
    this.casServerSettings = casServerSettings;
  }

  public CasServiceSettings getCasServiceSettings() {
    return casServiceSettings;
  }

  public void setCasServiceSettings(CasServiceSettings casServiceSettings) {
    this.casServiceSettings = casServiceSettings;
  }

  @ConfigurationProperties(prefix = "cas.server")
  public static class CasServerSettings {
    private String url;
    private String loginUrl;
    private String logoutUrl;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getLoginUrl() {
      return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
      this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
      return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
      this.logoutUrl = logoutUrl;
    }
  }

  @ConfigurationProperties(prefix = "cas.service")
  public static class CasServiceSettings {
    private String url;
    private String login;
    private String logout;
    private boolean sendRenew;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getLogin() {
      return login;
    }

    public void setLogin(String login) {
      this.login = login;
    }

    public String getLogout() {
      return logout;
    }

    public void setLogout(String logout) {
      this.logout = logout;
    }

    public boolean isSendRenew() {
      return sendRenew;
    }

    public void setSendRenew(boolean sendRenew) {
      this.sendRenew = sendRenew;
    }
  }
}
