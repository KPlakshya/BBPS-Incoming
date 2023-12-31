package com.bbps.utils;

import java.io.StringReader;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.bbps.schema.BillFetchResponse;
import org.bbps.schema.BillPaymentResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class UnMarshUtils {

	private static final ConcurrentHashMap<String, JAXBContext> contextMap = new ConcurrentHashMap<String, JAXBContext>();

	
	private static <T> T unmarshal(String xmlStr, Class<T> t) throws Exception {
		try (StringReader sr = new StringReader(xmlStr);) {
			log.info("Classs Name {}",t.getSimpleName());
			JAXBContext jAXBContext = contextMap.get(t.getSimpleName());
			if (jAXBContext == null) {
				jAXBContext = JAXBContext.newInstance(t);
				contextMap.putIfAbsent(t.getSimpleName(), jAXBContext);
				log.debug("INFO ***** contextMap {}", jAXBContext);
			}
			Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
			Object obj = unmarshaller.unmarshal(sr);
			return (T) obj;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error while Unmarsheling {}", e);
			log.error("error while Unmarshalling apiClass={} ,errorMsg={} ,xmlStr={} ", t.getName(), e.getMessage(),
					xmlStr);
		}

		return null;
	}
	
	public static String getMsgIdBillFetchResponse(String xmlStr) {

		try {
			log.info("XML Sgttring before marchsall :{}",xmlStr);

			BillFetchResponse ob = unmarshal(xmlStr, BillFetchResponse.class);
			log.info("Ob Respos {}",ob);
			if (ob != null) {
				
				return ob.getTxn().getMsgId();
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while unmarshal BillFetchResponseType {}", e);
		}
		return null;

	}
	
	public static String getMsgIdBillPaymentResponse(String xmlStr) {

		try {
			BillPaymentResponse ob = unmarshal(xmlStr, BillPaymentResponse.class);
			if (ob != null) {
				
				return ob.getTxn().getMsgId();
			}

		} catch (Exception e) {
			log.error("Error while unmarshal BillPaymentResponseType {}", e);
		}
		return null;

	}
	
}
