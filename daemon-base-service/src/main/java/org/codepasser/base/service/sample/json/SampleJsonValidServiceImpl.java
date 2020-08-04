package org.codepasser.base.service.sample.json;

import org.codepasser.base.dao.repository.UserRepository;
import org.codepasser.base.dao.repository.sample.SampleItemRepository;
import org.codepasser.base.model.entity.User;
import org.codepasser.base.model.entity.sample.SampleItemEntity;
import org.codepasser.base.service.sample.bo.SampleItemCreation;
import org.codepasser.base.service.sample.bo.SampleUserCreation;
import org.codepasser.common.model.validation.Group;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.AssertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.validation.Valid;

/**
 * SampleJsonValidServiceImpl.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Service
@RestController
public class SampleJsonValidServiceImpl implements SampleJsonValidService {

  @Autowired private SampleItemRepository sampleItemRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private UserRepository userRepository;

  @Nonnull
  @Override
  public AssertResponse pattern(@Nonnull @Valid @RequestBody SampleItemCreation sampleItemCreation)
      throws ServiceException {
    SampleItemEntity entity = sampleItemCreation.convert();
    entity.setCreateTime(new Date());
    entity.setCreateUser(1l);
    sampleItemRepository.save(entity);
    return AssertResponse.success();
  }

  @Nonnull
  @Override
  @Transactional
  public AssertResponse group(
      @Nonnull @Validated(Group.Create.class) @RequestBody SampleUserCreation sampleUserCreation)
      throws ServiceException {
    User entity = sampleUserCreation.convert();
    entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    userRepository.save(entity);
    //    int[] a = {1, 2, 3};
    //    System.out.println(a[3]);
    return AssertResponse.success();
  }
}
