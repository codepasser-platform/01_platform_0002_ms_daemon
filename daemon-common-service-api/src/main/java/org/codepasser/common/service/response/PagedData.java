package org.codepasser.common.service.response;

import java.util.List;
/**
 * PagedData.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class PagedData<E> {

  private List<E> result;
  private int size;
  private int page;
  private long total;
  private boolean isLast;

  public PagedData() {}

  public PagedData(List<E> result, int size, int page, long total, boolean isLast) {
    this.result = result;
    this.size = size;
    this.page = page;
    this.total = total;
    this.isLast = isLast;
  }

  public List<E> getResult() {
    return result;
  }

  public void setResult(List<E> result) {
    this.result = result;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public boolean isLast() {
    return isLast;
  }

  public void setLast(boolean last) {
    isLast = last;
  }
}
