package org.codepasser.base.model.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.EnumSet;
import org.codepasser.common.model.entity.converter.JsonTypeReferenceConverter;
import org.codepasser.common.model.security.Authority;

/**
 * AuthoritySetConverter.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class AuthoritySetConverter extends JsonTypeReferenceConverter<EnumSet<Authority.Role>> {

  private TypeReference<EnumSet<Authority.Role>> typeReference;

  {
    typeReference = new TypeReference<EnumSet<Authority.Role>>() {};
  }

  @Override
  protected TypeReference<EnumSet<Authority.Role>> typeReference() {
    return typeReference;
  }
}
