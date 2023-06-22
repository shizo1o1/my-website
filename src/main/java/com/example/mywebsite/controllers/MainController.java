package com.example.mywebsite.controllers;

import com.example.mywebsite.services.DownloadPriceService;
import com.example.mywebsite.services.ReadPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private DownloadPriceService priceService;

    @GetMapping("/")
        public  String showMainPage(Model model) throws IOException {
            System.out.println(ReadPriceService.getCellValueWithLeadingSpaces());
            List<List<String>> data = ReadPriceService.readExelFile();
            model.addAttribute("data", data);
            return "index";
        }
}