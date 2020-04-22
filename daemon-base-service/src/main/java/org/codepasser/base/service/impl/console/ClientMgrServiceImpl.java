package org.codepasser.base.service.impl.console;

import org.codepasser.base.dao.repository.security.OAuthClientDetailsRepository;
import org.codepasser.base.model.entity.security.OAuthClientDetails;
import org.codepasser.base.service.console.ClientMgrService;
import org.codepasser.base.service.console.bo.OAuthClientCreation;
import org.codepasser.common.service.conifguration.ServiceConfiguration;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.validation.Valid;

/**
 * ClientMgrServiceImpl.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/13 : base version.
 */
@Service
@RestController
public class ClientMgrServiceImpl implements ClientMgrService {

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private ServiceConfiguration.OauthSettings oauthSettings;

  @Autowired private OAuthClientDetailsRepository oAuthClientDetailsRepository;

  @Transactional
  @Nonnull
  @Override
  public AssertResponse creation(
      @Nonnull @Valid @RequestBody OAuthClientCreation clientCreation,
      @Nonnull @RequestParam("userId") Long userId)
      throws ServiceException {
    OAuthClientDetails clientDetails = clientCreation.convert();
    String secret = DigestUtils.md5DigestAsHex(clientDetails.getClientId().getBytes());
    // clientDetails.setClientId(); generator
    clientDetails.setClientSecret(passwordEncoder.encode(secret));
    clientDetails.setSecret(secret);
    clientDetails.setAccessTokenValidity(oauthSettings.getAccessTokenValidity());
    clientDetails.setRefreshTokenValidity(oauthSettings.getRefreshTokenValidity());
    clientDetails.setAutoApprove(String.valueOf(oauthSettings.isAutoapprove()));
    oAuthClientDetailsRepository.save(clientDetails);
    return AssertResponse.success();
  }
}
