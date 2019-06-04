package org.codepasser.base.model.entity.converter;

import static org.codepasser.common.utils.Json.readValue;
import static org.codepasser.common.utils.Json.writeValueAsString;

import javax.persistence.AttributeConverter;

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
