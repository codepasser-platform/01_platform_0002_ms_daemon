package org.codepasser.common.web.configuration.security.auth;

import java.util.Collection;
import org.codepasser.common.model.security.UserBasic;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class UserIdentity implements org.springframework.security.core.userdetails.UserDetails {

  private static final long serialVersionUID = 689793518503603124L;

  // TODO OAUTH-2.0
  // private BasicExternalUser externalUser;

  private UserBasic user;

  public UserIdentity(UserBasic user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.createAuthorityList(user.authorities());
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !user.getLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    } else if (!(obj instanceof UserIdentity)) {
      return false;
    }
    return this.getUsername().equals(((UserIdentity) obj).getUsername());
  }

  public UserBasic getUser() {
    return user;
  }
}
