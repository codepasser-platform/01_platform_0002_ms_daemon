package org.codepasser.base.model.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.EnumSet;
import org.codepasser.common.model.entity.converter.JsonTypeReferenceConverter;
import org.codepasser.common.model.entity.inner.UserStatus;
/**
 * UserStatusSetConverter.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class UserStatusSetConverter extends JsonTypeReferenceConverter<EnumSet<UserStatus>> {

  private TypeReference<EnumSet<UserStatus>> typeReference;

  {
    typeReference = new TypeReference<EnumSet<UserStatus>>() {};
  }

  @Override
  protected TypeReference<EnumSet<UserStatus>> typeReference() {
    return typeReference;
  }
}
