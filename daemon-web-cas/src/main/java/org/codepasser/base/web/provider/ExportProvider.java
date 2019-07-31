package org.codepasser.base.web.provider;

import static org.codepasser.base.model.entity.inner.AttachmentCategory.EXPORT;

import com.google.common.base.Throwables;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codepasser.base.model.entity.inner.AttachmentStatus;
import org.codepasser.base.service.basement.AttachmentService;
import org.codepasser.base.service.basement.vo.ResourceId;
import org.codepasser.common.async.AsyncCaller;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.service.response.ExcelResponse;
import org.codepasser.common.service.response.excel.ExcelRow;
import org.codepasser.common.service.response.excel.ExcelSheetSchema;
import org.codepasser.common.service.response.excel.ExcelTable;
import org.codepasser.common.service.response.excel.ExcelTableSchema;
import org.codepasser.common.web.configuration.storage.StorageConfiguration;
import org.codepasser.common.web.configuration.storage.StorageHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FileProvider.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/9/25 : base version.
 */
@Component
public class ExportProvider {

  @InjectLogger private static Logger logger;

  @Autowired private StorageConfiguration.StorageSettings storageSettings;

  @Autowired private AsyncCaller asyncCaller;

  @Autowired private AttachmentService attachmentService;

  public ResourceId export(Long userId, ExcelResponse response) throws ServiceException {

    String metaName = response.getSheet().value() + ".xlsx";
    String attachmentName = StorageHelper.generateName(metaName);
    UUID uuid = UUID.randomUUID();
    String directory =
        StorageHelper.generateHashPath(storageSettings.getVolume(), EXPORT.key(), uuid);
    String link = StorageHelper.generateHashLink(storageSettings.getLink(), EXPORT.key(), uuid);
    String uri = StorageHelper.generateHashLink(storageSettings.getRelative(), EXPORT.key(), uuid);

    Long attachmentId =
        attachmentService.saveAttachmentData(
            userId,
            userId,
            metaName,
            EXPORT,
            directory,
            directory + attachmentName,
            link + attachmentName,
            uri + attachmentName,
            0L);

    asyncCaller.asyncCall(
        () -> {
          try {
            long size = saveExcel(response, directory, attachmentName);
            //  Update attachment status
            attachmentService.updateAttachmentPersisted(attachmentId, size);
          } catch (Exception e) {
            logger.error(
                "An error occurred in the export file, caused by :{}",
                Throwables.getStackTraceAsString(e));
            try {
              attachmentService.updateAttachmentStatus(attachmentId, AttachmentStatus.ERROR);
            } catch (ServiceException e1) {
              logger.error(
                  "An error occurred in the export file, caused by :{}",
                  Throwables.getStackTraceAsString(e1));
            }
          }
        });

    return new ResourceId(attachmentId);
  }

  private long saveExcel(ExcelResponse response, String attachmentPath, String attachmentName) {

    XSSFWorkbook workbook = new XSSFWorkbook();

    XSSFSheet sheet = workbook.createSheet();
    // Excel sheet
    ExcelSheetSchema sheetSchema = response.getSheetSchema();
    ExcelTableSchema tableSchema = response.getTableSchema();

    sheet.setDisplayGridlines(sheetSchema.isDisplayGridLines());
    workbook.setSheetName(workbook.getSheetIndex(sheet), sheetSchema.getTitle());

    // Excel sheet table scope
    List<ExcelTable> excelTables = response.getSheet().elements();
    List<ExcelRow> excelRows = excelTables.get(0).elements();
    List<ExcelTableSchema.Header> excelTableHeaders = tableSchema.getHeader();

    // Excel sheet row and column start with 0
    int rowScope =
        tableSchema.getTitleCoordinate()[0]
            + excelRows.size()
            + 1 // excel table title between with header rows count without header,so increment.
            + 1; // excel table cell rows count without header,so increment.
    int rowStart = tableSchema.getTitleCoordinate()[0] + 2;

    // Excel sheet table column
    int columnScope = tableSchema.getTitleCoordinate()[1] + excelTableHeaders.size();
    AtomicInteger headerIndex = new AtomicInteger();
    int columnStart = tableSchema.getTitleCoordinate()[1];
    if (tableSchema.isShowSequence()) {
      columnScope++;
      columnStart++;
    }

    int finalTableHeaderRowStart = rowStart;
    int finalTableDataRowStart = rowStart + 1;
    int finalTableSequenceColumnStart = columnStart - 1;
    int finalTableDataColumnStart = columnStart;

    tableSchema
        .getHeader()
        .forEach(
            (header -> {
              // 设置列宽
              // Excel sheet row and column start with 0
              sheet.setColumnWidth(
                  finalTableDataColumnStart + headerIndex.get(), header.getWidth());
              headerIndex.getAndIncrement();
            }));

    // Excel sheet table title style
    XSSFCellStyle tableTitleStyle =
        cellStyle(
            workbook,
            false,
            false,
            tableSchema.isTitleFontBold(),
            tableSchema.getTitleFontSize(),
            tableSchema.getTitleFontFamily(),
            VerticalAlignment.TOP,
            HorizontalAlignment.LEFT,
            FillPatternType.SOLID_FOREGROUND,
            IndexedColors.WHITE,
            IndexedColors.GREY_80_PERCENT);

    // Excel sheet table header style
    XSSFCellStyle tableHeaderStyle =
        cellStyle(
            workbook,
            true,
            true,
            tableSchema.isHeaderFontBold(),
            tableSchema.getHeaderFontSize(),
            tableSchema.getHeaderFontFamily(),
            VerticalAlignment.CENTER,
            HorizontalAlignment.CENTER,
            FillPatternType.SOLID_FOREGROUND,
            IndexedColors.WHITE1,
            IndexedColors.GREY_80_PERCENT);

    // Excel sheet table cell style
    XSSFCellStyle tableCellStyle =
        cellStyle(
            workbook,
            true,
            true,
            tableSchema.isFontBold(),
            tableSchema.getFontSize(),
            tableSchema.getFontFamily(),
            VerticalAlignment.CENTER,
            HorizontalAlignment.LEFT,
            FillPatternType.SOLID_FOREGROUND,
            IndexedColors.BLACK1,
            IndexedColors.WHITE1);

    // print style
    for (int rowIndex = 0; rowIndex <= rowScope; rowIndex++) {
      XSSFRow newsRow = sheet.createRow(rowIndex);
      for (int columnIndex = 0; columnIndex < columnScope; columnIndex++) {
        XSSFCell newsRowCell = newsRow.createCell(columnIndex);

        // Table title row scope
        if (rowIndex == tableSchema.getTitleCoordinate()[0]) {
          if (columnIndex == tableSchema.getTitleCoordinate()[1]) {
            newsRowCell.setCellStyle(tableTitleStyle);
          }
        }

        // Table header row scope
        if (rowIndex == tableSchema.getTitleCoordinate()[0] + 2) {
          if (columnIndex >= tableSchema.getTitleCoordinate()[1]) {
            newsRowCell.setCellStyle(tableHeaderStyle);
          }
        }

        // Table data row scope
        if (rowIndex > tableSchema.getTitleCoordinate()[0] + 2) {
          if (columnIndex >= tableSchema.getTitleCoordinate()[1]) {
            newsRowCell.setCellStyle(tableCellStyle);
          }
        }
      }
    }

    // print table title
    XSSFRow titleRow = sheet.getRow(tableSchema.getTitleCoordinate()[0]);
    titleRow.getCell(tableSchema.getTitleCoordinate()[1]).setCellValue(tableSchema.getTitle());

    // print table header
    XSSFRow headerRow = sheet.getRow(finalTableHeaderRowStart);
    if (tableSchema.isShowSequence()) {
      // print table header sequence title
      headerRow.getCell(finalTableSequenceColumnStart).setCellValue("序号");
    }
    AtomicInteger headerColumnIndex = new AtomicInteger();
    excelTableHeaders.forEach(
        (header) -> {
          XSSFCell currentRowCell =
              headerRow.getCell(finalTableDataColumnStart + headerColumnIndex.get());
          currentRowCell.setCellValue(header.getTitle());
          headerColumnIndex.getAndIncrement();
        });

    // print table data
    excelRows.forEach(
        (row) -> {
          XSSFRow currentRow = sheet.getRow(finalTableDataRowStart + (int) row.value());
          AtomicInteger cellIndex = new AtomicInteger();
          row.elements()
              .forEach(
                  (cell) -> {
                    XSSFCell currentRowCell =
                        currentRow.getCell(finalTableDataColumnStart + cellIndex.get());
                    currentRowCell.setCellValue(cell.value().toString());
                    cellIndex.getAndIncrement();
                  });

          if (tableSchema.isShowSequence()) {
            // print table header sequence value
            currentRow.getCell(finalTableSequenceColumnStart).setCellValue(((int) row.value()) + 1);
          }
        });

    long size = 0L;
    try {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      workbook.write(os);
      byte[] content = os.toByteArray();
      InputStream is = new ByteArrayInputStream(content);
      size = StorageHelper.getInstance().saveFile(is, attachmentPath, attachmentName);
      os.close();
      workbook.close();
    } catch (IOException e) {
      logger.error(
          "An error occurred in the export file, caused by :{}",
          Throwables.getStackTraceAsString(e));
    }
    return size;
  }

  private XSSFCellStyle cellStyle(
      XSSFWorkbook workbook,
      boolean border,
      boolean wrapText,
      boolean fontBold,
      short fontSize,
      String frontFamily,
      VerticalAlignment verticalAlignment,
      HorizontalAlignment horizontalAlignment,
      FillPatternType fillPatternType,
      IndexedColors fontColor,
      IndexedColors groundColor) {
    XSSFCellStyle style = workbook.createCellStyle();

    // 字体
    XSSFFont font = workbook.createFont();
    font.setFontHeightInPoints(fontSize);
    font.setFontName(frontFamily);
    font.setBold(fontBold);
    font.setColor(fontColor.getIndex());
    style.setFont(font);

    // 格式
    XSSFDataFormat format = workbook.createDataFormat();
    style.setDataFormat(format.getFormat("@"));

    // 自动换行
    style.setWrapText(wrapText);

    // 边框
    if (border) {
      style.setBorderBottom(BorderStyle.THIN);
      style.setBorderLeft(BorderStyle.THIN);
      style.setBorderRight(BorderStyle.THIN);
      style.setBorderTop(BorderStyle.THIN);
    }

    // 对齐方式
    style.setVerticalAlignment(verticalAlignment);
    style.setAlignment(horizontalAlignment);

    // 背景色
    style.setFillPattern(fillPatternType);
    style.setFillForegroundColor(groundColor.getIndex());

    return style;
  }
}
