package org.codepasser.base.service.bootstrap;

import org.codepasser.base.model.data.Area;
import org.codepasser.base.model.entity.Org;
import org.codepasser.base.model.entity.Role;
import org.codepasser.base.model.entity.User;
import org.codepasser.base.model.entity.security.OAuthClientDetails;

/**
 * DataCreator.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/11 : base version.
 */
public interface DataCreator {

  DataCreator area(Area area);

  DataCreator organization(Org org);

  DataCreator role(Role role);

  DataCreator userAdmin(User admin);

  DataCreator oauthClient(OAuthClientDetails client);
}
