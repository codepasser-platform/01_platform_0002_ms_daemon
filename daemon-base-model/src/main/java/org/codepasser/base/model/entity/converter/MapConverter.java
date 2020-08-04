package org.codepasser.base.model.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;

import org.codepasser.common.model.entity.converter.JsonTypeReferenceConverter;

import java.util.Map;

public class MapConverter extends JsonTypeReferenceConverter<Map> {

  private final TypeReference<Map> typeReference;

  {
    typeReference = new TypeReference<Map>() {};
  }

  @Override
  protected TypeReference<Map> typeReference() {
    return typeReference;
  }
}
