package org.codepasser.cloud.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //    super.configure(http);
    http.authorizeRequests()
        .antMatchers("/actuator/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .and()
        .httpBasic();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
    http.csrf().disable();
  }

  @Autowired
  protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    String password = passwordEncoder().encode("eureka_pw");
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
        .roles("EUREKA_CLIENT");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
