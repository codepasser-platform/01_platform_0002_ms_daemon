package org.codepasser.common.model.entity.converter;

import static org.codepasser.common.utils.Json.readValue;
import static org.codepasser.common.utils.Json.writeValueAsString;

import com.fasterxml.jackson.core.type.TypeReference;
import javax.persistence.AttributeConverter;
/**
 * JsonTypeReferenceConverter.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public abstract class JsonTypeReferenceConverter<T> implements AttributeConverter<T, String> {

  @Override
  public String convertToDatabaseColumn(T value) {
    return writeValueAsString(value, typeReference());
  }

  @Override
  public T convertToEntityAttribute(String content) {
    return content == null ? null : readValue(content, typeReference());
  }

  protected abstract TypeReference<T> typeReference();
}
