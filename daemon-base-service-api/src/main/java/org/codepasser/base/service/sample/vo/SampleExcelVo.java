package org.codepasser.base.service.sample.vo;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static org.codepasser.common.service.response.excel.ExportSourceType.DATE;
import static org.codepasser.common.service.response.excel.ExportSourceType.DATETIME;
import static org.codepasser.common.service.response.excel.ExportSourceType.NUMBER;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import org.codepasser.common.service.response.excel.ExportSource;

/**
 * SampleExcelVo.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class SampleExcelVo implements Serializable {

  private static final long serialVersionUID = 2471383549571560171L;

  @JsonFormat(shape = STRING)
  @ExportSource(header = "数据ID", type = NUMBER)
  private Long id;

  @ExportSource(header = "名称", width = 5000)
  private String name;

  @ExportSource(header = "邮政编码", width = 5000)
  private String postCode;

  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @ExportSource(header = "起始时间", width = 8000, type = DATETIME)
  private Date start;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @ExportSource(header = "截止时间", width = 8000, type = DATE)
  private Date end;

  private String password;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public Date getStart() {
    return start;
  }

  public void setStart(Date start) {
    this.start = start;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
