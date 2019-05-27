package org.codepasser.cloud.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
    http.csrf().disable();
    http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String password = encoder.encode("eureka_pw");
    auth.inMemoryAuthentication()
        .passwordEncoder(new BCryptPasswordEncoder())
        // admin
        .withUser("eureka")
        .password(password)
        .roles("MANAGER")
        .and()
        // eureka-security-client
        .withUser("eureka_client")
        .password(password)
        .roles("EUREKA-CLIENT");
  }
}
