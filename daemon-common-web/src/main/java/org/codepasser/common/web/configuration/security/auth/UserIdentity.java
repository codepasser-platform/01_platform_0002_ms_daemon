package org.codepasser.common.web.configuration.security.auth;

import org.codepasser.common.model.security.UserBasic;
import org.codepasser.common.model.security.UserExternalBasic;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserIdentity implements org.springframework.security.core.userdetails.UserDetails {

  private static final long serialVersionUID = 689793518503603124L;

  private final UserBasic user;

  private UserExternalBasic externalUser;

  public UserIdentity(UserBasic user) {
    this.user = user;
  }

  public UserIdentity(UserExternalBasic user) {
    this.externalUser = user;
    this.user = user.getInnerUser();
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

  public Long getId() {
    return this.user.getId();
  }

  public UserBasic getUser() {
    return user;
  }

  public UserExternalBasic getExternalUser() {
    return this.externalUser;
  }

  public Map<String, String> getDetails() {
    if (this.externalUser != null) {
      Map<String, String> details = this.externalUser.getDetails();
      if (details != null) {
        return details;
      }
    }
    return new HashMap<>();
  }
}
