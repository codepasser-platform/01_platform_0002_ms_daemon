package org.codepasser.base.model.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;

import org.codepasser.common.model.entity.converter.JsonTypeReferenceConverter;
import org.codepasser.common.model.entity.inner.UserStatus;

import java.util.EnumSet;
/**
 * UserStatusSetConverter.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class UserStatusSetConverter extends JsonTypeReferenceConverter<EnumSet<UserStatus>> {

  private final TypeReference<EnumSet<UserStatus>> typeReference;

  {
    typeReference = new TypeReference<EnumSet<UserStatus>>() {};
  }

  @Override
  protected TypeReference<EnumSet<UserStatus>> typeReference() {
    return typeReference;
  }
}
