package com.bbps.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bbps.constants.Constants;
import com.bbps.kafka.KafkaMessagePostingService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BbpsIncomingService {

	@Autowired
	KafkaMessagePostingService kafkaMessagePostingService;

	@Value("${bbps.core.topic}")
	String bbpsCoreTopic;

	public void pushToTopic(String xmlStr, String api) throws JsonProcessingException {
		log.debug("pushing to queue {} ", xmlStr);
		Map<String, Object> headersMap = new HashMap();
		headersMap.put(Constants.REQ_TYPE, api);
		kafkaMessagePostingService.postMessage(xmlStr, headersMap, api);

	}

}
