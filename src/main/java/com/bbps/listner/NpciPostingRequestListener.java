//package com.bbps.npci.listner;
//
//import com.bbps.npci.kafka.model.Message;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Component;
//
//
//@Slf4j
//@Component
//public class NpciPostingRequestListener {
//
//
//
//    @KafkaListener(topics ="customer_posting_request",groupId = "NPCI_POSTING_REQUEST_GROUP")
//    public void getPostingRequest(String kafkaReqData, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
//        try {
//            log.info("Tenant [{}] ,message received to process posting reports Queue [{}] Message", topic, kafkaReqData);
//            ObjectMapper mapper = new ObjectMapper()
//                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            Message message = mapper.readValue(kafkaReqData, Message.class);
//        } catch (JsonProcessingException jpe) {
//            log.error("Tenant [{}],Topic [{}] ,JsonProcessing Exception While Receiving Request [{}] ,Exception [{}]", topic, kafkaReqData, jpe);
//        } catch (Exception e) {
//            log.error("Tenant [{}] ,Topic [{}] ,Exception While Receiving Request [{}] ,Exception [{}]", topic, kafkaReqData, e);
//        }
//    }
//}



