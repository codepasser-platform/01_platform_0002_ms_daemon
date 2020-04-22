package org.codepasser.base.service.console.bo;

import org.apache.tomcat.util.buf.StringUtils;
import org.codepasser.base.model.entity.security.OAuthClientDetails;
import org.codepasser.common.model.In;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.utils.Json;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * OAuthClientCreation.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2020/4/22 : base version.
 */
public class OAuthClientCreation implements In<OAuthClientDetails> {

  private EnumSet<Authority.Service> resourceIds = EnumSet.of(Authority.Service.DAEMON_API);

  private EnumSet<Authority.Scope> scope =
      EnumSet.of(Authority.Scope.READ, Authority.Scope.WRITE, Authority.Scope.SIGN);

  private EnumSet<Authority.Grant> authorizedGrantTypes =
      EnumSet.of(
          Authority.Grant.AUTHORIZATION_CODE,
          Authority.Grant.IMPLICIT,
          Authority.Grant.PASSWORD,
          Authority.Grant.CLIENT_CREDENTIALS,
          Authority.Grant.REFRESH_TOKEN);

  // TODO
  private EnumSet<Authority.Role> authorities =
      EnumSet.of(Authority.Role.USER, Authority.Role.CLIENT);

  private String webServerRedirectUri;

  @Valid private AdditionalInformation additionalInformation = new AdditionalInformation();

  public String getResourceIds() {
    String _values = new String();
    if (!resourceIds.isEmpty()) {
      _values =
          StringUtils.join(
              resourceIds.stream().map(Authority.Service::comment).collect(Collectors.toList()));
    }
    return _values;
  }

  public String getScope() {
    String _values = new String();
    if (!scope.isEmpty()) {
      _values =
          StringUtils.join(
              scope.stream().map(Authority.Scope::comment).collect(Collectors.toList()));
    }
    return _values;
  }

  public String getAuthorizedGrantTypes() {
    String _values = new String();
    if (!authorizedGrantTypes.isEmpty()) {
      _values =
          StringUtils.join(
              authorizedGrantTypes.stream()
                  .map(Authority.Grant::comment)
                  .collect(Collectors.toList()));
    }
    return _values;
  }

  public String getAuthorities() {
    String _values = new String();
    if (!authorities.isEmpty()) {
      _values =
          StringUtils.join(
              authorities.stream().map(Authority.Role::authority).collect(Collectors.toList()));
    }
    return _values;
  }

  public String getWebServerRedirectUri() {
    return webServerRedirectUri;
  }

  public String getAdditionalInformation() {
    return Json.writeValueAsString(additionalInformation);
  }

  public void setResourceIds(EnumSet<Authority.Service> resourceIds) {
    this.resourceIds = resourceIds;
  }

  public void setScope(EnumSet<Authority.Scope> scope) {
    this.scope = scope;
  }

  public void setAuthorizedGrantTypes(EnumSet<Authority.Grant> authorizedGrantTypes) {
    this.authorizedGrantTypes = authorizedGrantTypes;
  }

  public void setAuthorities(EnumSet<Authority.Role> authorities) {
    this.authorities = authorities;
  }

  public void setWebServerRedirectUri(String webServerRedirectUri) {
    this.webServerRedirectUri = webServerRedirectUri;
  }

  public void setAdditionalInformation(AdditionalInformation additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  @Override
  public OAuthClientDetails convert() {
    OAuthClientDetails clientDetails = new OAuthClientDetails();
    clientDetails.setResourceIds(getResourceIds());
    clientDetails.setScope(getScope());
    clientDetails.setAuthorizedGrantTypes(getAuthorizedGrantTypes());
    clientDetails.setAuthorities(getAuthorities());
    clientDetails.setWebServerRedirectUri(getWebServerRedirectUri());
    clientDetails.setAdditionalInformation(getAdditionalInformation());
    return clientDetails;
  }

  public static class AdditionalInformation implements Serializable {

    private static final long serialVersionUID = -5218529299499340633L;

    @NotNull private String title;
    @NotNull private String description;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }
  }

  //  public static void main(String[] args) {
  //    //      OAuthClientCreation clientCreation = new OAuthClientCreation();
  //    //      AdditionalInformation information = new AdditionalInformation();
  //    //      information.setTitle("oauth2 client");
  //    //      information.setDescription("oauth2 client test");
  //    //      clientCreation.setAdditionalInformation(information);
  //    //      System.out.println(clientCreation.getResourceIds());
  //    //      System.out.println(clientCreation.getScope());
  //    //      System.out.println(clientCreation.getAuthorizedGrantTypes());
  //    //      System.out.println(clientCreation.getAuthorities());
  //    //      System.out.println(clientCreation.getWebServerRedirectUri());
  //    //      System.out.println(clientCreation.getAdditionalInformation());
  //    //      String secret = DigestUtils.md5DigestAsHex("72801661".getBytes());
  //    //      System.out.println(secret);
  //  }
}
