package org.codepasser.base.web.configuration;

import org.codepasser.common.model.security.Authority;
import org.codepasser.common.web.configuration.security.handler.RedirectAccessDeniedHandler;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;

import java.util.Arrays;

/**
 * WebSecurityConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("cas")
  private AuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  @Qualifier("cas")
  private AuthenticationProvider authenticationProvider;

  @Autowired
  @Qualifier("cas")
  private LogoutFilter logoutFilter;

  @Autowired
  @Qualifier("cas")
  private SingleSignOutFilter singleSignOutFilter;

  @Autowired
  @Qualifier("formLoginRememberMe")
  private PersistentTokenBasedRememberMeServices formLoginRememberMeServices;

  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    // 设置cas认证提供
    return new ProviderManager(Arrays.asList(authenticationProvider));
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.parentAuthenticationManager(authenticationManager);
    auth.authenticationProvider(authenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    /* Security permit all matcher. */
    http.authorizeRequests()
        .antMatchers(
            "/login",
            "/login/**",
            "/logout",
            "/logout/**",
            "/actuator/**",
            "/sample/**", // sample api
            "/master/**", // master data api
            "/callback/**", // callback api
            "/error/**", // error path
            "/**/*.html", // static path
            "/**/*.js",
            "/**/*.css",
            "/**/*.ico",
            "/**/*.map")
        .permitAll()
        /* Security permit user matcher. */
        .anyRequest()
        .hasRole(Authority.Role.USER.name())

        /* Security access denied handler */
        .and()
        .exceptionHandling()
        .accessDeniedHandler(redirectAccessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint)

        // * Security sso & oauth2 remember me. */
        .and()
        // .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
        // .addFilterBefore(rememberMeFilter(), BasicAuthenticationFilter.class)
        .csrf()
        .disable()
        .headers()
        .frameOptions()
        .disable()

        /* Web remember me*/
        .and()
        .rememberMe()
        .rememberMeServices(formLoginRememberMeServices)
        .key(formLoginRememberMeServices.getKey())

        /* Security cas*/
        .and()
        .addFilterBefore(logoutFilter, LogoutFilter.class)
        .addFilterBefore(singleSignOutFilter, CasAuthenticationFilter.class);
  }

  @Bean
  public RedirectAccessDeniedHandler redirectAccessDeniedHandler() {
    return new RedirectAccessDeniedHandler();
  }

  @Bean
  public CasAuthenticationFilter casAuthenticationFilter(ServiceProperties serviceProperties)
      throws Exception {
    // cas认证过滤器，当触发本filter时，对ticket进行认证
    CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
    casAuthenticationFilter.setServiceProperties(serviceProperties);
    casAuthenticationFilter.setAuthenticationManager(authenticationManager());
    // casAuthenticationFilter.setFilterProcessesUrl(this.casServiceConfig.getLogin());
    // casAuthenticationFilter.setContinueChainBeforeSuccessfulAuthentication(false);
    casAuthenticationFilter.setAuthenticationSuccessHandler(
        new SimpleUrlAuthenticationSuccessHandler("/"));
    return casAuthenticationFilter;
  }
}
