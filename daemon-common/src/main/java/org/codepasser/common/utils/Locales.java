package org.codepasser.common.utils;

import java.io.IOException;
import java.util.Locale;

public final class Locales {

  public static Locale fromValue(String value) throws IOException {
    int ix = value.indexOf('_');
    if (ix < 0) { // single argument
      return new Locale(value);
    }
    String first = value.substring(0, ix);
    value = value.substring(ix + 1);
    ix = value.indexOf('_');
    if (ix < 0) { // two pieces
      return new Locale(first, value);
    }
    String second = value.substring(0, ix);
    return new Locale(first, second, value.substring(ix + 1));
  }
}
