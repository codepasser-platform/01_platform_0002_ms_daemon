package org.codepasser.common.service.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * PageableBuilder.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019-03-28 : base version.
 */
public final class PageableBuilder {

  private static final int MIN_LIMIT = 1;
  private static final int LIMIT = 100;

  private PageableBuilder() {}

  public static Pageable build(int page, int size, Sort.Direction direction, String... properties) {
    int _limit = limit(size);
    Sort sort = Sort.by(direction, properties);
    return PageRequest.of(page, _limit, sort);
  }

  public static Pageable build(int page, int size) {
    int _limit = limit(size);
    return PageRequest.of(page, _limit);
  }

  private static int limit(int size) {
    if (size >= LIMIT) {
      return LIMIT;
    }
    if (size <= MIN_LIMIT) {
      return MIN_LIMIT;
    }
    return size;
  }
}
