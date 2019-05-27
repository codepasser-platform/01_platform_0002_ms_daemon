package org.codepasser.common.web.configuration.rememberme;

import javax.sql.DataSource;
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

@Configuration
public class RememberMeServicesConfiguration {

  @Autowired private UserDetailsService userIdentityService;

  @Autowired private DataSource dataSource;

  @Autowired private WebMvcConfiguration.WebSettings webSettings;

  // TODO SSO
  //  @Autowired private ExternalUserService externalUserService;

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

  // TODO SSO
  //    @Bean
  //    @Qualifier("oauth2")
  //    public PersistentTokenBasedRememberMeServices oauth2RememberMeServices() {
  //        PersistentTokenBasedRememberMeServices rememberMeServices = new TokenRememberMeServices(
  //                rememberMeTokenKey,
  //                new OAuthRememberMeUserDetailsService(externalUserService),
  //                persistentTokenRepository(),
  //                webSettings.getRememberMeCookieDomain()
  //        );
  //
  //        rememberMeServices.setCookieName("oauth-remember-me");
  //        rememberMeServices.setAlwaysRemember(true);
  //        return rememberMeServices;
  //    }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
    db.setDataSource(dataSource);
    return db;
  }
}
