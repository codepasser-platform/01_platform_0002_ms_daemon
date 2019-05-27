package org.codepasser.base.service.bootstrap.impl;

import org.codepasser.base.dao.repository.OrgRepository;
import org.codepasser.base.dao.repository.RoleRepository;
import org.codepasser.base.dao.repository.UserRepository;
import org.codepasser.base.dao.repository.mongo.AreaRepository;
import org.codepasser.base.model.data.Area;
import org.codepasser.base.model.entity.Org;
import org.codepasser.base.model.entity.Role;
import org.codepasser.base.model.entity.User;
import org.codepasser.base.service.bootstrap.DataCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DefaultDataCreator.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
@Component
public class DefaultDataCreator implements DataCreator {

  @Autowired private AreaRepository areaRepository;

  @Autowired private OrgRepository orgRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private UserRepository userRepository;

  @Override
  public DataCreator area(Area area) {
    areaRepository.save(area);
    return this;
  }

  @Override
  public DataCreator organization(Org org) {
    orgRepository.save(org);
    return this;
  }

  @Override
  public DataCreator role(Role role) {
    roleRepository.save(role);
    return this;
  }

  @Override
  public DataCreator userAdmin(User admin) {
    userRepository.save(admin);
    return this;
  }
}
