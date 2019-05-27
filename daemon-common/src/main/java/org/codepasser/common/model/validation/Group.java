package org.codepasser.common.model.validation;

import javax.validation.groups.Default;
/**
 * Group.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public interface Group {

  interface Create extends Default {}

  interface CreateByApi extends Default {}

  interface Edit extends Default {}

  interface Delete extends Default {}
}
