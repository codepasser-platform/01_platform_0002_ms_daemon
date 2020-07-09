package org.codepasser.base.web.configuration;

import org.codepasser.common.model.security.Authority;
import org.codepasser.common.web.configuration.security.handler.ApiAuthenticationResponseHandler;
import org.codepasser.common.web.configuration.security.handler.AuthenticationResponseEntryPoint;
import org.codepasser.common.web.configuration.security.handler.RedirectAccessDeniedHandler;
import org.codepasser.common.web.configuration.security.handler.WebAuthenticationFailureHandler;
import org.codepasser.common.web.configuration.security.handler.WebAuthenticationSuccessHandler;
import org.codepasser.common.web.configuration.security.handler.WebLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.GET;

/**
 * WebSecurityConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  //  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserDetailsService userIdentityService;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired
  @Qualifier("formLoginRememberMe")
  private PersistentTokenBasedRememberMeServices formLoginRememberMeServices;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.parentAuthenticationManager(authenticationManager)
    auth.userDetailsService(userIdentityService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    /* Security permit all matcher. */
    http.authorizeRequests()
        .antMatchers(
            "/login",
            "/login/**",
            "/actuator/**",
            "/sample/**", // sample api
            "/master/**", // master data api
            "/callback/**", // callback api
            "/oauth/**", // oauth api
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
        .authenticationEntryPoint(
            new AuthenticationResponseEntryPoint("/login")
                // Login must be certified.
                .apiMatcher(new AntPathRequestMatcher("/rest/**"))
                .authenticationResponseHandler(authenticationResponseHandler()))

        /* Security sign in. */
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/index")
        .successHandler(authenticationSuccessHandler().targetUrlParam("target-url"))
        .failureHandler(
            authenticationFailureHandler().errorPage("/login?authentication_error=true"))

        /* Security sign out. */
        .and()
        .logout()
        .deleteCookies("SESSION")
        .invalidateHttpSession(true)
        .logoutUrl("/logout")
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", GET.name()))
        .logoutSuccessHandler(webLogoutSuccessHandler())
        .permitAll()

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
        .key(formLoginRememberMeServices.getKey());
  }

  @Bean
  public RedirectAccessDeniedHandler redirectAccessDeniedHandler() {
    return new RedirectAccessDeniedHandler();
  }

  @Bean
  public ApiAuthenticationResponseHandler authenticationResponseHandler() {
    return new ApiAuthenticationResponseHandler();
  }

  @Bean
  public WebAuthenticationSuccessHandler authenticationSuccessHandler() {
    return new WebAuthenticationSuccessHandler();
  }

  @Bean
  public WebAuthenticationFailureHandler authenticationFailureHandler() {
    return new WebAuthenticationFailureHandler();
  }

  @Bean
  public LogoutSuccessHandler webLogoutSuccessHandler() {
    return new WebLogoutSuccessHandler();
  }
}
