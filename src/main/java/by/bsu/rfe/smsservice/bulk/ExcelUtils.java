package by.bsu.rfe.smsservice.bulk;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import by.bsu.rfe.smsservice.common.enums.RecipientType;

/**
 * Created by pluhin on 7/13/16.
 */
public class ExcelUtils {

  public static Map<String, String> getMessagesFromSheet(Sheet sheet) {
    Iterator<Row> rowIterator = sheet.rowIterator();
    if (!rowIterator.hasNext()) {
      throw new NullPointerException("Sheet does not contain rows.");
    }

    Row mainRow = rowIterator.next();

    Integer recipientsPosition = getRecipientsPosition(mainRow);
    Integer messagePosition = getMessagePosition(mainRow);
    Map<String, String> messages = new HashMap<>();
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Cell recipientCell = row.getCell(recipientsPosition);
      Cell messageCell = row.getCell(messagePosition);

      if (recipientCell == null || messageCell == null) {
        continue;
      }

      recipientCell.setCellType(Cell.CELL_TYPE_STRING);
      messageCell.setCellType(Cell.CELL_TYPE_STRING);
      messages.put(recipientCell.getStringCellValue(), messageCell.getStringCellValue());
    }
    return messages;
  }

  public static Sheet getSheetFromFile(MultipartFile file) {
    try {
      InputStream inputStream = file.getInputStream();
      HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
      return workbook.getSheetAt(0);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static Integer getRecipientsPosition(Row row) {
    Iterator<Cell> cellIterator = row.iterator();
    Integer count = 0;
    while (cellIterator.hasNext()) {
      Cell cell = cellIterator.next();
      if (cell.getStringCellValue().equals(ExcelColumnName.MOBILE_NUMBER.getColumnKey())) {
        return count;
      }
      count++;
    }
    throw new NullPointerException("No recipient column in list");
  }

  private static Integer getMessagePosition(Row row) {
    Iterator<Cell> cellIterator = row.iterator();
    Integer count = 0;
    while (cellIterator.hasNext()) {
      Cell cell = cellIterator.next();
      if (cell.getStringCellValue().equals(ExcelColumnName.MESSAGE.getColumnKey())) {
        return count;
      }
      count++;
    }
    throw new NullPointerException("No message column in list");
  }
}
