package org.codepasser.base.web.configuration.security.auth;

import static org.codepasser.common.model.entity.inner.UserProvider.GITHUB;

import org.codepasser.common.model.security.UserExternalBasic;
import org.codepasser.common.service.OAuth2Service;
import org.codepasser.common.service.exception.NotFoundException;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("oauth2")
public class OAuth2UserIdentityService implements UserDetailsService {

  @Autowired private OAuth2Service oAuth2Service;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    // OUR principal is user id (long type)
    UserExternalBasic basicExternalUser = null;
    try {
      basicExternalUser =
          oAuth2Service
              .findByUserIdAndProvider(Long.valueOf(userId), GITHUB)
              .orElseThrow(() -> new NotFoundException(NotFoundException.Error.USER));
    } catch (ServiceException e) {
      throw new UsernameNotFoundException(
          "can not find the external user or other error occurs.", e);
    }
    return new UserIdentity(basicExternalUser);
  }
}
