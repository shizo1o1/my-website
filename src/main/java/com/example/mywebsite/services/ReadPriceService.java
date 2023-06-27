package com.example.mywebsite.services;

import com.example.mywebsite.models.Category;
import com.example.mywebsite.models.Product;
import com.example.mywebsite.repository.CategoryRepository;
import com.example.mywebsite.repository.ProductRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReadPriceService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public static int countLeadingSpaces(String str){
        int count = 0;
        for (int i =0; i < str.length(); i++){
            if (str.charAt(i) == ' '){
                count++;
            }else {
                break;
            }
        }

        return count;
    }

    public Map<String, List<String>> getProductsByCategory() throws IOException {
        Map<String, List<String>> productsByCategory = new HashMap<>();

        try (Workbook workbook = new HSSFWorkbook(new FileInputStream("src/main/resources/static/price.xls"))) {
            Sheet sheet = workbook.getSheetAt(0); // get first sheet
            String currentCategory = null;
            List<String> currentProducts = new ArrayList<>();
            Long categoryId = null;

            for (Row row : sheet) {
                Cell cell = row.getCell(2);
                if (cell != null) {
                    String cellValue = "";
                    if (cell.getCellType() == CellType.STRING) {
                        cellValue = cell.getStringCellValue();
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    if (countLeadingSpaces(cellValue) == 8 && !cellValue.trim().equals("WARRANTY PROTECTION  HOLOGRAM VOID LABELS STICKERS")) {
                        // Найдена новая категория товаров, сохраняем предыдущую категорию и товары
                        if (currentCategory != null) {
                            productsByCategory.put(currentCategory, currentProducts);
                        }
                        currentCategory = cellValue;
                        currentProducts = new ArrayList<>();

                        // Сохраняем категорию в базу данных
                        Category category = new Category();
                        category.setName(currentCategory);
                        categoryRepository.save(category);
                        categoryId = category.getId();
                    } else if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                        // Текущая строка содержит товар
                        Product product = new Product();
                        double article = row.getCell(1).getNumericCellValue();
                        product.setArticle( (long)article );                               // артикул
                        product.setName(cellValue);                                       // название продукта
                        product.setPrice(row.getCell(3).getNumericCellValue());        // цена
                        product.setDescription(String.valueOf(row.getCell(5)));       // гарантия/описание
                        product.setCategoryId(categoryId);                              // ID категории

                        currentProducts.add(cellValue);
                        productRepository.save(product);
                    }
                }
            }

            // Сохраняем последнюю категорию и товары
            if (currentCategory != null) {
                productsByCategory.put(currentCategory, currentProducts);
            }
        }

        return productsByCategory;
    }


}
