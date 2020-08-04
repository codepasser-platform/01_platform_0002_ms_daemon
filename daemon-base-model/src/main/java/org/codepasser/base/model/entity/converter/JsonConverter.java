package org.codepasser.base.model.entity.converter;

import javax.persistence.AttributeConverter;

import static org.codepasser.common.utils.Json.readValue;
import static org.codepasser.common.utils.Json.writeValueAsString;

abstract class JsonConverter<T> implements AttributeConverter<T, String> {

  @Override
  public String convertToDatabaseColumn(T attribute) {
    return writeValueAsString(attribute);
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    return readValue(dbData, type());
  }

  protected abstract Class<T> type();
}
