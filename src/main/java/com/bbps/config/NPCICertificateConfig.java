package com.bbps.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.bbps.utils.ValidateXmlSign;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class NPCICertificateConfig {

	@Value("${bbps.pub.crt.path}")
	public void setPublicKey(String upiCrt) {
		log.debug("Loading BBPS Pub");
		ValidateXmlSign.publicKey = getCertificate(upiCrt);
	}

	private PublicKey getCertificate(String upiCrt) {
		log.info("Loading BBPS Pub Certificate from path [{}]", upiCrt);
		InputStream caInput = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			InputStream in = new FileInputStream(new File(upiCrt));
			caInput = new BufferedInputStream(in);
			Certificate	ca = cf.generateCertificate(caInput);
			log.info("Loaded BBPS Pub Certificate.");
			return ca.getPublicKey();
		} catch (Exception e) {
			log.error("Error in getCertificate {}", e.getMessage());
		} finally {
			if (caInput != null) {
				try {
					caInput.close();
				} catch (IOException e) {
					log.error("Error in getCertificate closing resources{}", e.getMessage());
				}
			}
		}
		return null;
	}

}
