package gsh.gsh.controllers;

import gsh.gsh.models.VisualObj;
import gsh.gsh.services.XlsxService;
import gsh.gsh.services.productService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.kafka.core.KafkaTemplate;
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
    private KafkaTemplate<String, String> kafkaTemplate;

    public ProductController(productService service, XlsxService xlsxService,KafkaTemplate<String, String> kafkaTemplate) {

        this.service     = service;
        this.xlsxService = xlsxService;
        this.kafkaTemplate= kafkaTemplate;
    }

    @PostMapping
    public Object getproducts() throws NoSuchFieldException, IOException {
        List<String> url = new ArrayList<>();
        url.add("https://afshopy.com");
        url.add("https://kenoya.shop");
        //return this.service.getProducts(this.xlsxService.upload("google.xlsx"));
        return this.service.getProducts(url);
    }
    @PostMapping("/getupdated")
    public Object getupdate() throws NoSuchFieldException, InterruptedException, IOException {
        List<String> urls = new ArrayList<>();
        urls.add("https://chamafrique.com");
        urls.add("https://aichafrique.com");
        urls.add("https://luxafrique.boutique");
        urls.add("https://nubianbeautyshop.com");
        urls.add("https://afshopy.com");
        urls.add("https://kenoya.shop");
        //List<String> urls = this.xlsxService.upload("google.xlsx");
        for(int i = 0 ; i < 100 ; i++){
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
        for(String title : this.service.getTitles()){
            this.kafkaTemplate.send("titles",title);
        }
        //this.xlsxService.getXlsxTitles(this.service.getTitles());
        return true;
    }
}
