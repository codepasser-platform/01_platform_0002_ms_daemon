package org.codepasser.common.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.valueOf;
import static java.util.regex.Matcher.quoteReplacement;

/**
 * Variables.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public final class Variables {

  public static VariableExpressionReplacer variables(Map<String, Object> variables) {
    return new VariableExpressionReplacer(variables);
  }

  public static final class VariableExpressionReplacer {

    private static final Pattern VAR_HOLDER_PATTERN = Pattern.compile("\\$\\{(\\w+)\\}");
    private final Map<String, Object> variables;
    private Pattern holderPattern = VAR_HOLDER_PATTERN;

    VariableExpressionReplacer(Map<String, Object> variables) {
      this.variables = variables;
    }

    public VariableExpressionReplacer pattern(String pattern) {
      this.holderPattern = Pattern.compile(pattern);
      return this;
    }

    public String replace(CharSequence charSequence) {
      Matcher matcher = holderPattern.matcher(charSequence);
      StringBuffer sb = new StringBuffer(charSequence.length());
      while (matcher.find()) {
        matcher.appendReplacement(
            sb,
            quoteReplacement(valueOf(variables.getOrDefault(matcher.group(1), matcher.group(0)))));
      }

      matcher.appendTail(sb);
      return sb.toString();
    }
  }
}
