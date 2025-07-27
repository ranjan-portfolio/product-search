package com.ranjan.productsearch.message;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ranjan.productsearch.entity.OutboxEntity;
import com.ranjan.productsearch.entity.ProductEntity;
import com.ranjan.productsearch.entity.OutboxEntity.EventStatus;
import com.ranjan.productsearch.repository.OutboxRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Data
@Slf4j
public class KafkaProcessOutboxEvents {
    
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    @Autowired
    private OutboxRepository outboxRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    public void processInventoryEvents(){
        log.info("Processing product events"+Instant.now());
        List<OutboxEntity> outboxEvents= outboxRepository.findByStatusIn(List.of(EventStatus.PENDING,EventStatus.FAILED));
        log.info("Got messages to process::"+outboxEvents.size());
        outboxEvents.stream().forEach(event ->{
            try{
                log.info("Before Kafka send");
                kafkaTemplate.send("product-topic",event.getAggregateId(),objectMapper.readValue(event.getPayload(),ProductEntity.class));
                log.info("After kafka send successful for event::"+event.getAggregateId());
                event.setStatus(EventStatus.SENT);
                outboxRepository.save(event);
            }catch(Exception ex){
                log.info("kafka send failed for event::"+event.getAggregateId());
                log.error(ex.getMessage());
                event.setStatus(EventStatus.FAILED);
                outboxRepository.save(event);
            }
            
        }
           
        );

    }




}
