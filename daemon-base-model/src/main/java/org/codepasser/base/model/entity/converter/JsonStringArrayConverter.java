package org.codepasser.base.model.entity.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import org.codepasser.common.model.entity.converter.JsonTypeReferenceConverter;

/**
 * JsonStringArrayConverter.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class JsonStringArrayConverter extends JsonTypeReferenceConverter<ArrayList<String>> {

  private TypeReference<ArrayList<String>> typeReference;

  {
    typeReference = new TypeReference<ArrayList<String>>() {};
  }

  @Override
  protected TypeReference<ArrayList<String>> typeReference() {
    return typeReference;
  }
}
