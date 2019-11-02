package org.codepasser.base.service.bootstrap;

import org.codepasser.base.model.data.Area;
import org.codepasser.base.model.entity.Org;
import org.codepasser.base.model.entity.Role;
import org.codepasser.base.model.entity.User;
import org.codepasser.base.model.entity.security.OAuthClientDetails;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.exception.ServiceException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.PostConstruct;

import static org.codepasser.common.processor.RunningProfile.INITIALIZE;

/**
 * DatabaseInitializer.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
@Service
@Profile(INITIALIZE)
public class DatabaseInitializer {
  @InjectLogger private Logger logger;

  @Autowired private DataCreator dataCreator;

  @Autowired private DataFinder dataFinder;

  @PostConstruct
  private void initialize() throws ServiceException {

    // Initialize area
    initializeArea();
    // Initialize organization
    initializeOrg();
    // Initialize role
    initializeRole();
    // Initialize admin
    initializeAdmin();
    // Initialize oauth client
    initializeOAuth();
  }

  private void initializeArea() {
    logger.info("Initializer area process start");
    List<Area> areas = dataFinder.findAreas();
    areas.forEach(
        item -> {
          dataCreator.area(item);
        });
    logger.info("Initializer area process finish, process count [{}]", areas.size());
  }

  private void initializeOrg() throws ServiceException {
    logger.info("Initializer organization process start");
    Org org = dataFinder.findOrganization();
    dataCreator.organization(org);
    logger.info("Initializer organization process finish, process org-id [{}]", org.getId());
  }

  private void initializeRole() {
    logger.info("Initializer role process start");
    List<Role> roles = dataFinder.findRole();
    roles.forEach(
        item -> {
          dataCreator.role(item);
        });
    logger.info("Initializer role process finish, process count [{}]", roles.size());
  }

  private void initializeAdmin() {
    logger.info("Initializer admin process start");
    User admin = dataFinder.findAdmin();
    dataCreator.userAdmin(admin);
    logger.info(
        "Initializer admin process finish, process admin-id [{}],org-id [{}]",
        admin.getId(),
        admin.getOrgId());
  }

  private void initializeOAuth() {
    logger.info("Initializer oauth client process start");
    OAuthClientDetails client = dataFinder.findOAuthClient();
    dataCreator.oauthClient(client);
    logger.info(
        "Initializer oauth client process finish, process client-id [{}],client-secret [{}]",
        client.getClientId(),
        client.getClientSecret());
  }
}
