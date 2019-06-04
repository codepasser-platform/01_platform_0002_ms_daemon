package org.codepasser.base.web.configuration;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.http.HttpMethod.GET;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import org.codepasser.base.web.configuration.oauth2.clients.DefaultClientResources;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.web.configuration.security.handler.ApiAuthenticationResponseHandler;
import org.codepasser.common.web.configuration.security.handler.AuthenticationResponseEntryPoint;
import org.codepasser.common.web.configuration.security.handler.RedirectAccessDeniedHandler;
import org.codepasser.common.web.configuration.security.handler.WebAuthenticationFailureHandler;
import org.codepasser.common.web.configuration.security.handler.WebAuthenticationSuccessHandler;
import org.codepasser.common.web.configuration.security.handler.WebLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CompositeFilter;

/**
 * WebSecurityConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
@Configuration
@EnableOAuth2Client
// @EnableAuthorizationServer
// @Order(2)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  //  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserDetailsService userIdentityService;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired
  @Qualifier("formLoginRememberMe")
  private PersistentTokenBasedRememberMeServices formLoginRememberMeServices;

  @Autowired
  @Qualifier("oauth2")
  private PersistentTokenBasedRememberMeServices oauth2RememberMeServices;

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
            "/sample/**", // sample api
            "/master/**", // master data api
            "/callback/**", // callback api
            "/oauth/**", // oauth api
            "/error/**", // error path
            "/**/*.html", // static path
            "/**/*.js",
            "/**/*.css")
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
        .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
        .addFilterBefore(rememberMeFilter(), BasicAuthenticationFilter.class)
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

  @Bean(name = "github")
  @ConfigurationProperties("github")
  protected DefaultClientResources github() {
    return new DefaultClientResources();
  }

  @Bean
  protected FilterRegistrationBean oAuth2ClientFilterRegistration() {
    FilterRegistrationBean<OAuth2ClientContextFilter> registrationBean =
        new FilterRegistrationBean<>();
    registrationBean.setFilter(new OAuth2ClientContextFilter());
    registrationBean.setOrder(-100);
    return registrationBean;
  }

  private Filter ssoFilter() {
    CompositeFilter filter = new CompositeFilter();
    List<Filter> filters = new ArrayList<>();
    filters.add(github().filter("/login/github"));
    //    filters.add(weibo().filter("/login/weibo"));
    //    filters.add(qq().filter("/login/qq"));
    //    filters.add(wechatWeb().filter("/login/wechat/connect"));
    //    filters.add(wechat().filter("/login/wechat"));
    filter.setFilters(filters);
    return filter;
  }

  private Filter rememberMeFilter() {
    ProviderManager providerManager =
        new ProviderManager(
            newArrayList(new RememberMeAuthenticationProvider(oauth2RememberMeServices.getKey())));
    return new RememberMeAuthenticationFilter(providerManager, oauth2RememberMeServices);
  }

  // TODO CSRF filter
  //  @Bean
  //  public CsrfTokenRepository csrfTokenRepository() {
  //    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
  //    repository.setHeaderName(Header.HEADER_X_XSRF_TOKEN);
  //    return repository;
  //  }
}
