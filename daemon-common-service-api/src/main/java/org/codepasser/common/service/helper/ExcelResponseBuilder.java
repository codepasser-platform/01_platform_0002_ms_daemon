package org.codepasser.common.service.helper;

import static org.codepasser.common.model.ConstantInterface.CONDITION_BLANK;
import static org.springframework.util.ReflectionUtils.doWithFields;
import static org.springframework.util.ReflectionUtils.getField;
import static org.springframework.util.ReflectionUtils.makeAccessible;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.codepasser.common.service.response.ExcelResponse;
import org.codepasser.common.service.response.excel.ExcelCell;
import org.codepasser.common.service.response.excel.ExcelCellSchema;
import org.codepasser.common.service.response.excel.ExcelRow;
import org.codepasser.common.service.response.excel.ExcelRowSchema;
import org.codepasser.common.service.response.excel.ExcelSheet;
import org.codepasser.common.service.response.excel.ExcelSheetSchema;
import org.codepasser.common.service.response.excel.ExcelTable;
import org.codepasser.common.service.response.excel.ExcelTableSchema;
import org.codepasser.common.service.response.excel.ExportSource;
import org.codepasser.common.service.response.excel.ExportSourceType;

/**
 * ExcelRow.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/26 : base version.
 */
public final class ExcelResponseBuilder {

  private List<? extends Serializable> data;
  private ExcelSheetSchema sheetSchema;
  private ExcelTableSchema tableSchema;
  private ExcelRowSchema rowSchema;
  private ExcelCellSchema cellSchema;

  private ExcelResponseBuilder(List<? extends Serializable> data) {
    if (data == null) {
      this.data = Lists.newArrayList();
    } else {
      this.data = data;
    }
  }

  public static ExcelResponse build(List<? extends Serializable> data, String title) {
    return new ExcelResponseBuilder(data).build(title);
  }

  private ExcelResponse build(String title) {

    // Default schema settings
    this.defaultSchemaSettings(title);

    // Sheet
    ExcelSheet sheet = new ExcelSheet();
    sheet.setValue(title);
    // Table
    ExcelTable table = new ExcelTable();
    table.setValue(title);

    sheet.addElements(table);

    // Handle data
    AtomicInteger rowIndex = new AtomicInteger();
    for (Object item : this.data) {
      // Row
      ExcelRow row = new ExcelRow();
      row.setValue(rowIndex.get());
      doWithFields(
          item.getClass(),
          field -> {
            makeAccessible(field);
            Object value = getField(field, item);
            // table schema
            ExportSource exportSource = field.getAnnotation(ExportSource.class);
            if (rowIndex.get() == 0) {
              this.tableSchema.addHeader(exportSource.header(), exportSource.width());
            }
            // Cell
            ExcelCell cell = new ExcelCell();
            if (value == null) {
              cell.setValue(CONDITION_BLANK);
            } else {
              // Date Cell
              if (exportSource.type() == ExportSourceType.DATE
                  || exportSource.type() == ExportSourceType.DATETIME) {
                cell.setValue(new SimpleDateFormat(exportSource.type().format()).format(value));
              } else {
                // TODO Parsing other data  types
                cell.setValue(value);
              }
            }
            row.addElements(cell);
          },
          field -> field.isAnnotationPresent(ExportSource.class));
      table.addElements(row);
      rowIndex.getAndIncrement();
    }

    // Response schema
    ExcelResponse response = new ExcelResponse();
    response.setSheetSchema(this.sheetSchema);
    response.setTableSchema(this.tableSchema);
    response.setRowSchema(this.rowSchema);
    response.setCellSchema(this.cellSchema);
    response.setSheet(sheet);
    return response;
  }

  private void defaultSchemaSettings(String title) {
    this.sheetSchema = new ExcelSheetSchema();
    sheetSchema.setTitle(title);
    sheetSchema.setDisplayGridLines(false);

    this.tableSchema = new ExcelTableSchema();
    tableSchema.setTitle(title);
    // coordinate row min value : 0
    // coordinate col min value : 0
    tableSchema.setTitleCoordinate(new int[] {1, 1}); // 第二行 第二列
    tableSchema.setShowSequence(true);

    // table title
    tableSchema.setTitleFontFamily("SimSun");
    tableSchema.setTitleFontSize((short) 20);
    tableSchema.setTitleFontBold(true);

    // table header
    tableSchema.setHeaderFontFamily("SimSun");
    tableSchema.setHeaderFontSize((short) 14);
    tableSchema.setHeaderFontBold(true);

    // table font
    tableSchema.setFontFamily("Arial");
    tableSchema.setFontSize((short) 12);
    tableSchema.setFontBold(false);

    this.rowSchema = new ExcelRowSchema();
    this.cellSchema = new ExcelCellSchema();
  }
}
