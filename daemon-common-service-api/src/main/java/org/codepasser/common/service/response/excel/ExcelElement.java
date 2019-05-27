package org.codepasser.common.service.response.excel;

import java.io.Serializable;
import java.util.List;

/**
 * ExcelSheet.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/26 : base version.
 */
public interface ExcelElement<T extends ExcelElement> extends Serializable {

  void setValue(Object value);

  Object value();

  void setElements(List<T> elements);

  void addElements(T element);

  List<T> elements();
}
