package com.bbps.controller;

import org.bbps.schema.Ack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbps.constants.Constants;
import com.bbps.service.BbpsIncomingService;
import com.bbps.service.ValidationService;
import com.bbps.utils.UnMarshUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
public class BbpsIncomingController {

	@Autowired
	private ValidationService validationService;

	@Autowired
	private BbpsIncomingService bbpsIncomingService;

	@PostMapping(value = Constants.BBPS + Constants.FWD_SLASH + Constants.BILLER_FETCH_RESPONSE + Constants.URL_1_0)
	public ResponseEntity<Ack> billerfetch(@PathVariable(value = "referenceId") String referenceId,
			@RequestBody String xmlString) throws JsonProcessingException {
		log.info("Biller Fetch request [{}]", xmlString);
		Ack ack = validationService.getAck(Constants.BILLER_FETCH_RESPONSE, xmlString, referenceId, null);
		bbpsIncomingService.pushToTopic(xmlString, Constants.BILLER_FETCH_RESPONSE);

		log.info("Biller Fetch  ack-[{}]", ack);
		return new ResponseEntity<>(ack, HttpStatus.OK);
	}

	@PostMapping(value = Constants.BBPS + Constants.FWD_SLASH + Constants.BILL_FETCH_RESPONSE + Constants.URL_1_0)
	public ResponseEntity<Ack> billfetch(@PathVariable(value = "referenceId") String referenceId,
			@RequestBody String xmlString) throws JsonProcessingException {
		log.info("Biller Fetch request [{}]", xmlString);
		String msgId = UnMarshUtils.getMsgIdBillFetchResponse(xmlString);
		Ack ack = validationService.getAck(Constants.BILL_FETCH_RESPONSE, xmlString, referenceId, msgId);
		bbpsIncomingService.pushToTopic(xmlString, Constants.BILL_FETCH_RESPONSE);

		log.info("Bill Fetch  ack-[{}]", ack);
		return new ResponseEntity<>(ack, HttpStatus.OK);
	}

	@PostMapping(value = Constants.BBPS + Constants.FWD_SLASH + Constants.BILL_PAYMENT_RESPONSE + Constants.URL_1_0)
	public ResponseEntity<Ack> billPayment(@PathVariable(value = "referenceId") String referenceId,
			@RequestBody String xmlString) throws JsonProcessingException {
		log.info("Biller Fetch request [{}]", xmlString);
		String msgId = UnMarshUtils.getMsgIdBillPaymentResponse(xmlString);
		Ack ack = validationService.getAck(Constants.BILL_PAYMENT_RESPONSE, xmlString, referenceId, msgId);
		bbpsIncomingService.pushToTopic(xmlString, Constants.BILL_PAYMENT_RESPONSE);

		log.info("Bill payment  ack-[{}]", ack);
		return new ResponseEntity<>(ack, HttpStatus.OK);
	}

	@PostMapping(value = Constants.BBPS + Constants.FWD_SLASH + Constants.BILL_VALIDATION_RESPONSE + Constants.URL_1_0)
	public ResponseEntity<Ack> billValidation(@PathVariable(value = "referenceId") String referenceId,
			@RequestBody String xmlString) throws JsonProcessingException {
		log.info("Biller Validation request [{}]", xmlString);
		Ack ack = validationService.getAck(Constants.BILL_VALIDATION_RESPONSE, xmlString, referenceId, null);
		bbpsIncomingService.pushToTopic(xmlString, Constants.BILL_VALIDATION_RESPONSE);

		log.info("Bill validation ack-[{}]", ack);
		return new ResponseEntity<>(ack, HttpStatus.OK);
	}

	@PostMapping(value = Constants.BBPS + Constants.FWD_SLASH + Constants.RES_DIAGNOSTIC + Constants.URL_1_0)
	public ResponseEntity<Ack> respDiagnotic(@PathVariable(value = "referenceId") String referenceId,
			@RequestBody String xmlString) throws JsonProcessingException {
		log.info("RespDiagnotic request [{}]", xmlString);
		Ack ack = validationService.getAck(Constants.RES_DIAGNOSTIC, xmlString, referenceId, null);
		bbpsIncomingService.pushToTopic(xmlString, Constants.RES_DIAGNOSTIC);

		log.info("Bill validation ack-[{}]", ack);
		return new ResponseEntity<>(ack, HttpStatus.OK);
	}

}
