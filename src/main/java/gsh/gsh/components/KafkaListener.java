package gsh.gsh.components;

import gsh.gsh.models.VisualObj;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener {
    private final SimpMessagingTemplate template;

    public KafkaListener(SimpMessagingTemplate template) {
        this.template = template;
    }

    @org.springframework.kafka.annotation.KafkaListener(
            topics = "products_solde",
            groupId = "groupeId"
    )
    public void listener(String data){
        System.out.println("Listener reiceve: "+data);
        this.template.convertAndSend("/scores",data);
    }
}
