package gsh.gsh.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gsh.gsh.models.VisualObj;
import gsh.gsh.models.product;
import gsh.gsh.repositories.OrderRepo;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import gsh.gsh.repositories.productRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class productService {

    private RestTemplate restTemplate;
    private productRepo productRepo;
    private OrderRepo orderRepo;
    private final SimpMessagingTemplate template;
    private KafkaTemplate<String, VisualObj> kafkaTemplate;

    public productService(RestTemplateBuilder restTemplate, productRepo productRepo, OrderRepo orderRepo, SimpMessagingTemplate template, KafkaTemplate<String, VisualObj> kafkaTemplate) {
        this.restTemplate = restTemplate.build();
        this.productRepo  = productRepo;
        this.orderRepo    = orderRepo;
        this.template     = template;
        this.kafkaTemplate= kafkaTemplate;
    }
    // produce product those are updated on kafka product_solde topic
    public List<String> getUpdate(List<String> urls){
        List<String> orderResps =null;
        int limit = 2000;
        int count = 1;
        for(String url : urls){
            if(count == limit) return orderResps;
            count ++;
            try{
            JsonObject  resp = (JsonObject) new JsonParser().parse(this.restTemplate.getForObject(url.strip()+"/products.json",String.class));
            JsonArray   products = resp.getAsJsonArray("products");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);


            String id = null;
            String title = null;
            String updated = null;
            String created = null;
            String urlProduct = null;
            orderResps = new ArrayList<>();
            // TODO it should be better than that
            List<product> oldProducts = ((List<product>) this.productRepo.findAll());
            product oldProduct = null;
            for( int i = 0 ; i < products.size() ; i++ ){
                id          = products.get(i).getAsJsonObject().get("id").getAsString();
                title       = products.get(i).getAsJsonObject().get("title").getAsString();
                updated     = products.get(i).getAsJsonObject().get("updated_at").getAsString();
                created     = products.get(i).getAsJsonObject().get("created_at").getAsString();
                urlProduct  = url+"/products/"+products.get(i).getAsJsonObject().get("handle").getAsString();
                System.out.println("porduct: ----------- "+i);
                System.out.println("    id: "+id);
                System.out.println("    title: "+title);
                System.out.println("    updated-at: "+updated);
                System.out.println("    created-at: "+created);
                System.out.println("    url: "+urlProduct);
                for(product p : oldProducts){
                    if(p.getId().equals(id)){
                        int score = p.getUpdateScore();
                        int scoreLastDay = p.getUpdateScoreLastDay();
                        if( !(LocalDateTime.parse(updated,formatter).equals(p.getUpdatedAt()))){
                            score += 1;
                            orderResps.add(id);
                            if((LocalDateTime.parse(updated,formatter).isAfter(p.getLastDay().plusDays(1)))){
                                scoreLastDay += 1;
                            }else{
                                p.setLastDay(LocalDateTime.parse(updated,formatter));
                                scoreLastDay = 1;
                            }
                            p.setUpdateScoreLastDay(scoreLastDay);
                        }
                        p.setUpdateScore(score);
                        p.setUpdatedAt(LocalDateTime.parse(updated,formatter));
                        this.productRepo.save(p);
                        System.out.println("updated :: "+title+"  : "+score);
                        //this.template.convertAndSend("/scores",  new VisualObj(p.getId(),p.getUrl(),p.getTitle(),p.getUpdateScore(),p.getUpdateScoreLastDay(),p.getLastDay()));
                        this.kafkaTemplate.send("products_sold",new VisualObj(p.getId(),p.getUrl(),p.getTitle(),p.getUpdateScore(),p.getUpdateScoreLastDay(),p.getLastDay()));
                        TimeUnit.SECONDS.sleep(5);
                    }

                }
                // oldProduct = this.productRepo.findById(id);
                // orderResps.add(new orderResp(id,title,LocalDateTime.parse(updated,formatter) != oldProduct.get().getUpdatedAt(),urlProduct));
            }
        }catch(Exception e){
            System.out.println("from >>>> "+url+"  -------------- exc : "+e.getMessage());
        }
        }

        return orderResps;
    }

    public Object getProducts(List<String> urls) throws NoSuchFieldException {
        JsonArray   products = null ;
        for(String url : urls){
            System.out.println(url.strip().concat("/products.json"));
            try{
                JsonObject  resp = (JsonObject) new JsonParser().parse(this.restTemplate.getForObject(url.strip().concat("/products.json"),String.class));
                products = resp.getAsJsonArray("products");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);
                String id = null;
                String title = null;
                String updated = null;
                String created = null;
                String urlProduct = null;
                for( int i = 0 ; i < products.size() ; i++ ){
                    id          = products.get(i).getAsJsonObject().get("id").getAsString();
                    title       = products.get(i).getAsJsonObject().get("title").getAsString();
                    updated     = products.get(i).getAsJsonObject().get("updated_at").getAsString();
                    created     = products.get(i).getAsJsonObject().get("created_at").getAsString();
                    urlProduct  = url+"/products/"+products.get(i).getAsJsonObject().get("handle").getAsString();
                    System.out.println("porduct: ----------- "+i);
                    System.out.println("    id: "+id);
                    System.out.println("    title: "+title);
                    System.out.println("    updated-at: "+updated);
                    System.out.println("    created-at: "+created);
                    System.out.println("    url: "+urlProduct);

                    this.productRepo.save(new product(
                            id,
                            title,
                            LocalDateTime.parse(updated,formatter),
                            LocalDateTime.parse(created,formatter),
                            urlProduct,
                            0,
                            0,
                            LocalDateTime.parse(updated,formatter)
                    ));
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return products ;
    }

}
