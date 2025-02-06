package dev.ernandorezende.file_manager.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GenerateExcelTest {
    Logger logger = Logger.getLogger(GenerateExcelTest.class.getName());
    @Test
    public void testGenerateExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("test");
        Row headeerRow = sheet.createRow(0);

        String[] columnNames = {"Name", "Age", "Gender"};
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = headeerRow.createCell(i);
            cell.setCellValue(columnNames[i]);
        }
        int rowNum = 1;
        for(TestData testData : getTestData()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(testData.name());
            row.createCell(1).setCellValue(testData.age());
            row.createCell(2).setCellValue(testData.gender());
        }

        try {
            workbook.write(new FileOutputStream("test.xlsx"));
            workbook.close();
            logger.log(Level.INFO, "Excel generated");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Excel generation failed", e);
        }

    }

    List<TestData> getTestData() {
        var test1 = new TestData("Ernando", 41, "Male");
        var test2 = new TestData("Luciana", 45, "Female");
        var test3 = new TestData("Lavinia", 9, "Female");

        return List.of(test1, test2, test3);
    }
}

record TestData(String name, int age, String gender) {
}