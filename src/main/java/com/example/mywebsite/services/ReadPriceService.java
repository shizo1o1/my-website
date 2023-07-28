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
import java.util.List;

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

    public void saveInDbCategoryProduct() throws IOException {
        List<Product> products = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        // Очищаем таблицы category и product
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        try (Workbook workbook = new HSSFWorkbook(new FileInputStream("/price.xls"))) {
            Sheet sheet = workbook.getSheetAt(0); // Получаем первый лист

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
                        // Сохраняем категорию в базе данных
                        Category category = new Category();
                        category.setName(cellValue);
                        categories.add(category);
                    } else if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                        // Текущая строка содержит товар
                        Product product = new Product();
                        double article = row.getCell(1).getNumericCellValue();
                        product.setArticle((long) article);                                // артикул
                        product.setName(cellValue);                                       // название продукта
                        product.setPrice(row.getCell(3).getNumericCellValue());        // цена
                        product.setDescription(String.valueOf(row.getCell(5)));       // гарантия/описание

                        // Устанавливаем категорию для продукта
                        Category category = categories.get(categories.size() - 1);
                        product.setCategory(category);

                        products.add(product);
                    }
                }
            }

            // Сохраняем все категории и продукты в базе данных
            categoryRepository.saveAll(categories);
            productRepository.saveAll(products);
        }
    }


}
