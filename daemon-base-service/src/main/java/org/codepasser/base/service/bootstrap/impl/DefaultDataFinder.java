package org.codepasser.base.service.bootstrap.impl;

import static java.lang.Boolean.FALSE;
import static org.codepasser.common.model.entity.inner.OrgType.ROOT;
import static org.codepasser.common.model.entity.inner.UserStatus.MANAGED;
import static org.codepasser.common.model.entity.inner.UserType.GENERATED;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import org.codepasser.base.model.data.Area;
import org.codepasser.base.model.entity.Org;
import org.codepasser.base.model.entity.Role;
import org.codepasser.base.model.entity.User;
import org.codepasser.base.model.entity.security.OAuthClientDetails;
import org.codepasser.base.service.bootstrap.DataFinder;
import org.codepasser.base.service.bootstrap.DataLoader;
import org.codepasser.base.service.bootstrap.InitializerConfiguration;
import org.codepasser.base.service.bootstrap.data.AreaCity;
import org.codepasser.base.service.bootstrap.data.AreaDistrict;
import org.codepasser.base.service.bootstrap.data.AreaProvince;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.utils.Json;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DefaultDataCreator.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
@Component
public class DefaultDataFinder implements DataFinder {

  @InjectLogger private Logger logger;

  @Autowired private DataLoader dataLoader;

  @Autowired private InitializerConfiguration settings;

  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  public List<Area> findAreas() {
    List<Area> areas = Lists.newArrayList();

    // provinces
    String result = readFile(dataLoader.provincesJson());
    List<AreaProvince> provinces =
        Json.readValue(result, new TypeReference<List<AreaProvince>>() {});

    assert provinces != null;
    provinces.forEach(
        item -> {
          areas.add(item.convert());
        });

    // cities
    result = readFile(dataLoader.citiesJson());
    List<AreaCity> cities = Json.readValue(result, new TypeReference<List<AreaCity>>() {});

    assert cities != null;
    cities.forEach(
        item -> {
          areas.add(item.convert());
        });

    // districts
    result = readFile(dataLoader.districtsJson());
    List<AreaDistrict> districts =
        Json.readValue(result, new TypeReference<List<AreaDistrict>>() {});

    assert districts != null;
    districts.forEach(
        item -> {
          areas.add(item.convert());
        });

    logger.info("Initializer area count [{}]", areas.size());

    return areas;
  }

  @Override
  public Org findOrganization() throws ServiceException {
    Org org = new Org();
    org.setId(settings.getOrgSettings().getRootOrgId());
    org.setName(settings.getOrgSettings().getRootOrgName());
    org.setType(ROOT);

    org.setCreateUser(settings.getAdminSettings().getAdminId());
    org.setCreateTime(new Date());
    return org;
  }

  @Override
  public List<Role> findRole() {
    List<Role> roles = Lists.newArrayList();
    Authority.Role[] authorities = Authority.Role.values();
    for (int index = 0; index < authorities.length; index++) {
      Authority.Role authority = authorities[index];
      Role role = new Role();
      role.setAuthority(authority);
      role.setName(authority.comment());
      role.setDescription(authority.description());
      role.setDisplay(index);
      role.setCreateTime(new Date());
      role.setCreateUser(settings.getAdminSettings().getAdminId());
      role.setOrgId(settings.getOrgSettings().getRootOrgId());
      roles.add(role);
    }
    return roles;
  }

  @Override
  public User findAdmin() {
    User admin = new User();

    admin.setId(settings.getAdminSettings().getAdminId());
    admin.setOrgId(settings.getOrgSettings().getRootOrgId());
    admin.setUsername(settings.getAdminSettings().getAdminName());
    admin.setPassword(passwordEncoder.encode(settings.getAdminSettings().getAdminPassword()));
    admin.setPhone(settings.getAdminSettings().getAdminPhone());
    admin.setEmail(settings.getAdminSettings().getAdminEmail());

    admin.setType(GENERATED);
    admin.setLocked(FALSE);
    admin.addStatus(MANAGED);
    admin.addAuthority(Authority.Role.USER);
    admin.addAuthority(Authority.Role.ADMIN);
    admin.addAuthority(Authority.Role.MGR);
    admin.setCreateUser(admin.getId());
    admin.setCreateTime(new Date());
    admin.setOrgId(settings.getOrgSettings().getRootOrgId());
    return admin;
  }

  @Override
  public OAuthClientDetails findOAuthClient() {
    OAuthClientDetails clientDetails = new OAuthClientDetails();
    clientDetails.setClientId(settings.getOauthSettings().getClientId());
    clientDetails.setClientSecret(
        passwordEncoder.encode(settings.getOauthSettings().getClientSecret()));
    clientDetails.setScope(settings.getOauthSettings().getScope());
    clientDetails.setAuthorizedGrantTypes(settings.getOauthSettings().getAuthorizedGrantTypes());
    clientDetails.setWebServerRedirectUri(settings.getOauthSettings().getWebServerRedirectUri());
    clientDetails.setAuthorities(settings.getOauthSettings().getAuthorities());
    clientDetails.setAccessTokenValidity(settings.getOauthSettings().getAccessTokenValidity());
    clientDetails.setRefreshTokenValidity(settings.getOauthSettings().getRefreshTokenValidity());
    clientDetails.setAutoApprove(settings.getOauthSettings().getAutoapprove());
    return clientDetails;
  }

  private String readFile(File file) {
    String encoding = "UTF-8";
    Long length = file.length();
    byte[] content = new byte[length.intValue()];
    StringBuilder result = new StringBuilder();
    try {
      FileInputStream in = new FileInputStream(file);
      in.read(content);
      in.close();
      result.append(new String(content, encoding));
    } catch (Exception e) {
      logger.warn("Initializer area files read error");
      e.printStackTrace();
    }
    return result.toString();
  }
}
