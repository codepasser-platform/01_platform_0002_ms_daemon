package org.codepasser.base.model.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Map;
import org.codepasser.common.model.entity.converter.JsonTypeReferenceConverter;

public class MapConverter extends JsonTypeReferenceConverter<Map> {

  private TypeReference<Map> typeReference;

  {
    typeReference = new TypeReference<Map>() {};
  }

  @Override
  protected TypeReference<Map> typeReference() {
    return typeReference;
  }
}
