package org.codepasser.base.service.impl.console;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.codepasser.base.dao.repository.RoleRepository;
import org.codepasser.base.model.entity.Role;
import org.codepasser.base.service.console.RoleMgrService;
import org.codepasser.base.service.console.vo.RoleItem;
import org.codepasser.base.service.impl.cell.OrgCell;
import org.codepasser.base.service.impl.cell.UserCell;
import org.codepasser.common.model.entity.inner.State;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * RoleMgrServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
@Service
@RestController
public class RoleMgrServiceImpl implements RoleMgrService {

  @Autowired private RoleRepository roleRepository;

  @Autowired private UserCell userCell;

  @Autowired private OrgCell orgCell;

  @Nonnull
  @Override
  public List<RoleItem> list() throws ServiceException {

    List<Role> roles =
        roleRepository.findAllByAuthorityNotInAndStateNotIn(
            EnumSet.of(Authority.Role.USER), EnumSet.of(State.DELETED, State.EXPIRED));
    List<RoleItem> foundRoles =
        roles.stream()
            .map(
                (item) -> {
                  RoleItem vo = new RoleItem();
                  vo.from(item);
                  vo.setCreateUserName(userCell.validById(vo.getCreateUser()).getUsername());
                  vo.setOrgName(orgCell.validById(vo.getOrgId()).getName());
                  return vo;
                })
            .collect(Collectors.toList());

    return foundRoles;
  }
}
