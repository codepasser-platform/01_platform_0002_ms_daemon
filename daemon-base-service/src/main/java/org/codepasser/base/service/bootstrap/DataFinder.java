package org.codepasser.base.service.bootstrap;

import java.util.List;
import org.codepasser.base.model.data.Area;
import org.codepasser.base.model.entity.Org;
import org.codepasser.base.model.entity.Role;
import org.codepasser.base.model.entity.User;
import org.codepasser.common.service.exception.ServiceException;

/**
 * DataCreator.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
public interface DataFinder {

  List<Area> findAreas();

  Org findOrganization() throws ServiceException;

  List<Role> findRole();

  User findAdmin();
}
