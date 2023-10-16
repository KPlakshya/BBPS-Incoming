package com.bbps.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.bbps.schema.Ack;
import org.bbps.schema.ErrorMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bbps.constants.Constants;
import com.bbps.utils.ValidateXmlSign;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationService {

	@Value("${is.signature.verify.on}")
	public boolean isSignVerifyOn;

	public Ack getAck(String xmlStr, String api, String refId, String msgId) {
		Ack ack = null;

		if (validateSignature(xmlStr)) {
			ack = createAck(api, refId, msgId);
		} else {

		}

		return ack;

	}

	private Ack createAck(String api, String refId, String msgId) {
		Ack ack = new Ack();
		ack.setApi(api);
		ack.setRefId(refId);
		if (msgId != null) {
			ack.setMsgId(msgId);
		}
		ack.setRspCd("Successful");
		ack.setTs(generateTs());
		return ack;
	}

	public Ack getSignMismatchAck(String api, String refId, String msgId) {
		Ack ack = new Ack();
		ack.setApi(api);
		ack.setRefId(refId);
		if (msgId != null) {
			ack.setMsgId(msgId);
		}
		List<ErrorMessage> emList = ack.getErrorMessages();
		ErrorMessage em = new ErrorMessage();
		em.setErrorCd(Constants.G27);
		em.setErrorDtl(Constants.SIGNATURE_MISMATCH);
		emList.add(em);
		return ack;
	}

	private boolean validateSignature(String xmlStr) {

		if (isSignVerifyOn) {

			boolean isvalid = ValidateXmlSign.isValidXmlSign(xmlStr);
			log.info("Is Valid Signature [{}]", isvalid);
			return isvalid;
		}

		return true;
	}

	public static String generateTs() {
		DateTimeFormatter dtime = DateTimeFormatter.ofPattern(Constants.ISO_TIMEZONE);
		return ZonedDateTime.now().format(dtime);
	}

}
