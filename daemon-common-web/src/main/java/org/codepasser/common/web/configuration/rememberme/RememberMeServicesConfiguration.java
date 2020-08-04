package org.codepasser.common.web.configuration.rememberme;

import org.codepasser.common.web.configuration.WebMvcConfiguration;
import org.codepasser.common.web.configuration.security.session.SessionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class RememberMeServicesConfiguration {

  @Autowired private UserDetailsService userIdentityService;

  @Autowired private DataSource dataSource;

  @Autowired private WebMvcConfiguration.WebSettings webSettings;

  @Bean
  @Qualifier("formLoginRememberMe")
  public PersistentTokenBasedRememberMeServices formLoginRememberMeServices() {
    PersistentTokenBasedRememberMeServices rememberMeServices =
        new WebRememberMeServices(
            SessionInterface.SESSION_REMEMBER_ME_COOKIE,
            userIdentityService,
            persistentTokenRepository(),
            webSettings.getRememberMeCookieDomain(),
            webSettings.getCookiePath());
    return rememberMeServices;
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
    db.setDataSource(dataSource);
    return db;
  }
}
