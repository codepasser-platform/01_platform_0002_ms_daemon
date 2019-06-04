package org.codepasser.base.web.configuration.rememberme;

import org.codepasser.common.web.configuration.WebMvcConfiguration;
import org.codepasser.common.web.configuration.rememberme.WebRememberMeServices;
import org.codepasser.common.web.configuration.security.session.SessionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class OAuth2RememberMeServicesConfiguration {

  // TODO OAuth2 RememberME
  @Autowired
  @Qualifier("oauth2")
  private UserDetailsService oAuth2UserIdentityService;

  @Autowired private WebMvcConfiguration.WebSettings webSettings;

  @Autowired private PersistentTokenRepository persistentTokenRepository;

  @Bean
  @Qualifier("oauth2")
  public PersistentTokenBasedRememberMeServices oauth2RememberMeServices() {
    PersistentTokenBasedRememberMeServices rememberMeServices =
        new WebRememberMeServices(
            SessionInterface.OAUTH2_REMEMBER_ME_COOKIE,
            oAuth2UserIdentityService,
            persistentTokenRepository,
            webSettings.getRememberMeCookieDomain(),
            webSettings.getCookiePath());
    //    rememberMeServices.setCookieName("oauth-remember-me");
    rememberMeServices.setAlwaysRemember(true);
    return rememberMeServices;
  }
}
