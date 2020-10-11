package com.skyforce.application.utility;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.skyforce.application.models.Consumer;
import com.skyforce.application.repository.ConsumerRepository;
import com.skyforce.application.repository.ProviderRepository;

@Component
public class GatewayUtility {
	
	@Autowired
	ConsumerRepository consumerRepository;
	@Autowired
	ProviderRepository providerRepository;
	
	private static String rs256Token = "";

	public boolean validateHSToken(String hsToken) {
		/*Will take from vault*/
		String HMACSecret = "hmac-secret-key";
		String RSASecret = "rsa-secret-key";
		/*Will take from vault*/
		
		Consumer consumer = null;
		String consumerName = JWT.require(Algorithm.HMAC256(HMACSecret)).build().verify(hsToken).getIssuer();
		String apiKey = JWT.require(Algorithm.HMAC256(HMACSecret)).build().verify(hsToken).getSubject();
		String apiSecret = JWT.require(Algorithm.HMAC256(HMACSecret)).build().verify(hsToken).getClaim("apiSecret").asString();
		try {
			if(consumerRepository.findById(consumerName) != null)
				consumer = consumerRepository.findById(consumerName).get();
			if(consumer != null & consumer.getApiKey().equals(apiKey)) {
				/*Encode to Base64*/
				String providerName = consumer.getProviderScopeName();
				String providerSecretKey = providerRepository.findById(providerName).get().getApiSecret();
				/*Encode to Base64*/
				if(providerSecretKey.equals(apiSecret)) {
					rs256Token = JWT.create()
						.withIssuer(consumerName)
		                .withSubject(providerSecretKey)
		                .withExpiresAt(new Date(System.currentTimeMillis() + 1000000))
		                .sign(Algorithm.HMAC512(RSASecret));
					
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public String createToken() {
		return rs256Token;
	}

	public boolean validateRsaToken(String httpAuthToken) {
		String RSASecret = "rsa-secret-key";
		DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(RSASecret))
						        .build()
						        .verify(httpAuthToken);
						     
		if(decodedJWT.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) == 1)
			return true;
		else
			return false;
	}
}
