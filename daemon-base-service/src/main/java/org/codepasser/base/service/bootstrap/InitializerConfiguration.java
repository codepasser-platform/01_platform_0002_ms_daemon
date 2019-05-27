package org.codepasser.base.service.bootstrap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * InitializerConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
@Configuration
@ConfigurationProperties("daemon.service.initializer")
public class InitializerConfiguration {

  private AreaSettings areaSettings;

  private OrgSettings orgSettings;

  private AdminSettings adminSettings;

  public AreaSettings getAreaSettings() {
    return areaSettings;
  }

  public void setAreaSettings(AreaSettings areaSettings) {
    this.areaSettings = areaSettings;
  }

  public OrgSettings getOrgSettings() {
    return orgSettings;
  }

  public void setOrgSettings(OrgSettings orgSettings) {
    this.orgSettings = orgSettings;
  }

  public AdminSettings getAdminSettings() {
    return adminSettings;
  }

  public void setAdminSettings(AdminSettings adminSettings) {
    this.adminSettings = adminSettings;
  }

  public static class AreaSettings {

    private String provincesJson;
    private String citiesJson;
    private String districtsJson;

    public String getProvincesJson() {
      return provincesJson;
    }

    public void setProvincesJson(String provincesJson) {
      this.provincesJson = provincesJson;
    }

    public String getCitiesJson() {
      return citiesJson;
    }

    public void setCitiesJson(String citiesJson) {
      this.citiesJson = citiesJson;
    }

    public String getDistrictsJson() {
      return districtsJson;
    }

    public void setDistrictsJson(String districtsJson) {
      this.districtsJson = districtsJson;
    }
  }

  public static class OrgSettings {

    private Long rootOrgId;
    private String rootOrgName;

    public Long getRootOrgId() {
      return rootOrgId;
    }

    public void setRootOrgId(Long rootOrgId) {
      this.rootOrgId = rootOrgId;
    }

    public String getRootOrgName() {
      return rootOrgName;
    }

    public void setRootOrgName(String rootOrgName) {
      this.rootOrgName = rootOrgName;
    }
  }

  public static class AdminSettings {

    private Long adminId;
    private String adminName;
    private String adminPassword;
    private String adminPhone;
    private String adminEmail;

    public Long getAdminId() {
      return adminId;
    }

    public void setAdminId(Long adminId) {
      this.adminId = adminId;
    }

    public String getAdminName() {
      return adminName;
    }

    public void setAdminName(String adminName) {
      this.adminName = adminName;
    }

    public String getAdminPassword() {
      return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
      this.adminPassword = adminPassword;
    }

    public String getAdminPhone() {
      return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
      this.adminPhone = adminPhone;
    }

    public String getAdminEmail() {
      return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
      this.adminEmail = adminEmail;
    }
  }
}
