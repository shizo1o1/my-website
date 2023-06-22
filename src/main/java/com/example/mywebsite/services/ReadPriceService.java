package com.example.mywebsite.services;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadPriceService {

    public static List<List<String>> readExelFile () throws IOException{
        List<List<String>> data = new ArrayList<>();

        try (Workbook workbook = new HSSFWorkbook(new FileInputStream("src/main/resources/static/price.xls"))){
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet){
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row){
                    String cellValue = "";
                    if (cell.getCellType() == CellType.STRING){
                        cellValue = cell.getStringCellValue();
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    rowData.add(cellValue);
                }
                data.add(rowData);
            }
        }
        return data;
    }

    public static int countLeadingSpacesTrimm(String str){
        int originalLength = str.length();
        int trimmedLength = str.trim().length();
        return originalLength - trimmedLength;
    }

    public static List<String> getCellValueWithLeadingSpaces() throws IOException {
        List<String> cellValues = new ArrayList<>();

        try (Workbook workbook = new HSSFWorkbook(new FileInputStream("src/main/resources/static/price.xls"))) {
            Sheet sheet = workbook.getSheetAt(0); // Получить первый лист
            for (Row row : sheet) {
                Cell cell = row.getCell(1); // Второй столбец
                if (cell != null) {
                    String cellValue = "";
                    if (cell.getCellType() == CellType.STRING) {
                        cellValue = cell.getStringCellValue();
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    if (countLeadingSpaces(cellValue) == 8) {
                        cellValues.add(cellValue);
                    }
                }
            }
        }

        return cellValues;
    }

    private static int countLeadingSpaces(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }


}
