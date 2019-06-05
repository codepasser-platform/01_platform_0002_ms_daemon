package org.codepasser.base.dao.repository.security;

import org.codepasser.base.model.entity.security.OAuthClientDetails;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * OAuthClientDetailsRepository.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Repository
public interface OAuthClientDetailsRepository
    extends CrudRepository<OAuthClientDetails, String>,
        JpaSpecificationExecutor<OAuthClientDetails> {}
