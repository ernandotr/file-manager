package dev.ernandorezende.file_manager.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GenerateExcelTest {
    public static final String FILE_NAME = "test.xlsx";
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
            workbook.write(new FileOutputStream(FILE_NAME));
            workbook.close();
            logger.log(Level.INFO, "Excel generated: {0}", FILE_NAME);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Excel generation failed", e);
        }

    }

    @Test
    void readExcel() throws FileNotFoundException {
        try(FileInputStream file = new FileInputStream(new File(FILE_NAME))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            if(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                System.out.print(row.getCell(0).getStringCellValue());
                System.out.print(", "+ row.getCell(1).getStringCellValue());
                System.out.println(", "+ row.getCell(2).getStringCellValue());

            }

            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String name = row.getCell(0).getStringCellValue();
                double age = row.getCell(1).getNumericCellValue();
                String gender = row.getCell(2).getStringCellValue();
                TestData testData = new TestData(name, (int) age, gender);
                logger.log(Level.INFO, "Row: {0} ", testData);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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