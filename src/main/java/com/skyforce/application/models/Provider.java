package com.skyforce.application.models;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Provider")
public class Provider {
	
	@Id
	private String scopeName;
	@Indexed(unique = true)
	private String apiKey;
	private String apiSecret;
	
	public String getScopeName() {
		return scopeName;
	}
	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey2) {
		this.apiKey = apiKey2;
	}
	public String getApiSecret() {
		return apiSecret;
	}
	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}
}
