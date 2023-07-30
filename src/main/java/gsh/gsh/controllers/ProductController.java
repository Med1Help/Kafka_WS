package gsh.gsh.controllers;

import gsh.gsh.services.XlsxService;
import gsh.gsh.services.productService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1")
public class ProductController {
    private productService service;
    private XlsxService xlsxService;

    public ProductController(productService service, XlsxService xlsxService) {

        this.service     = service;
        this.xlsxService = xlsxService;
    }

    @PostMapping
    public Object getproducts() throws NoSuchFieldException, IOException {
        return this.service.getProducts(this.xlsxService.upload("google.xlsx"));
    }
    @PostMapping("/getupdated")
    public Object getupdate() throws NoSuchFieldException, InterruptedException, IOException {
        List<String> urls = this.xlsxService.upload("google.xlsx");
        for(int i = 0 ; i < 40 ; i++){
            this.service.getUpdate(urls);
            System.out.println("Stop ...");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("Start ...");
        }
        return "fin!!";
    }
    @GetMapping("/file")
    public List<String> getXlsx() throws IOException{
        return this.xlsxService.upload("google.xlsx");
    }
    @GetMapping("/titles")
    public boolean getTitles() throws IOException {
        this.xlsxService.getXlsxTitles(this.service.getTitles());
        return true;
    }
}
