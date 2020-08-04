package org.codepasser.common.service.response.excel;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * ExcelGeneralElement.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/26 : base version.
 */
public abstract class ExcelGeneralElement<T extends ExcelElement> implements ExcelElement<T> {
  private static final long serialVersionUID = -7819437924938307978L;

  private Object value;

  private List<T> elements = Lists.newArrayList();

  public void setValue(Object value) {
    this.value = value;
  }

  public Object value() {
    return value;
  }

  public void setElements(List<T> elements) {
    this.elements = elements;
  }

  public void addElements(T element) {
    if (elements == null) {
      elements = Lists.newArrayList();
    }
    elements.add(element);
  }

  public List<T> elements() {
    return elements;
  }
}
