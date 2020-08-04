package org.codepasser.base.service.basement.vo;

import org.codepasser.base.model.business.category.IdentifyingCodeType;

import java.io.Serializable;

/**
 * IdentifyingCode.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class IdentifyingCode implements Serializable {

  private static final long serialVersionUID = 4422677977905018653L;
  private IdentifyingCodeType identifyingCodeType;
  private String code;

  public IdentifyingCodeType getIdentifyingCodeType() {
    return identifyingCodeType;
  }

  public void setIdentifyingCodeType(IdentifyingCodeType identifyingCodeType) {
    this.identifyingCodeType = identifyingCodeType;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
