package org.codepasser.base.service.impl.cell;

import static org.codepasser.common.service.exception.NotFoundException.Error.ORG;

import org.codepasser.base.dao.repository.OrgRepository;
import org.codepasser.base.model.entity.Org;
import org.codepasser.common.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OrgCell.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Component
public class OrgCell {

  @Autowired private OrgRepository orgRepository;

  public Org validById(long orgId) {
    return orgRepository.findById(orgId).orElseThrow(() -> new NotFoundException(ORG));
  }
}
