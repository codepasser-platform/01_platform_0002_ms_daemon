package org.codepasser.base.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@EnableTransactionManagement
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private DataSource dataSource;

  @Autowired private UserDetailsService userIdentityService;

  @Bean
  public ApprovalStore approvalStore() {
    return new JdbcApprovalStore(dataSource);
  }

  @Bean
  protected AuthorizationCodeServices authorizationCodeServices() {
    return new JdbcAuthorizationCodeServices(dataSource);
  }

  @Bean
  public TokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }

  /**
   * @param clients a configurer that defines the client details service.
   * @throws Exception
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.jdbc(dataSource); // oauth_client_details
  }

  /**
   * @param oauthServer a configurer that defines defines the security constraints on the token
   *     endpoint.
   * @throws Exception
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
        .allowFormAuthenticationForClients();
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .authenticationManager(authenticationManager)
        .approvalStore(approvalStore()) // oauth_approvals
        .authorizationCodeServices(authorizationCodeServices()) // oauth_code
        .tokenStore(tokenStore()) // oauth_access_token & oauth_refresh_token
        .userDetailsService(userIdentityService); // refresh token required user detail service
    // pathMapping 方法提供修改默认路径。
    // defaultPath：默认的端点URL
    // customPath：自定义的URL
    // .pathMapping("/oauth/authorize", "/oauth2/authorize")
    // .pathMapping("/oauth/token","/oauth2/token");
  }
}
