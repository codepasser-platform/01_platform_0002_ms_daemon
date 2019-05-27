package org.codepasser.base.model.data;

import org.codepasser.base.model.business.category.Gender;
import org.codepasser.common.model.data.BaseData;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * UserAttribute.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
@Document(collection = "sys_user_attribute")
public class UserAttribute extends BaseData {

  private static final long serialVersionUID = -1520236753298111983L;

  private Long userId;

  private String realName;

  private Gender gender;

  private String occupation;

  private String major;

  private String fax;

  private String postCode;

  private String address;

  private String signature;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }
}
