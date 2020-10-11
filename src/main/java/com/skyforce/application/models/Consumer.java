package com.skyforce.application.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Consumer")
public class Consumer {
	
	@Id
	private String consumerScopeName;
	private String providerScopeName;
	private String apiKey;
	
	public Consumer() {
		super();
	}

	public Consumer(String consumerScopeName, String providerScopeName) {
		super();
		this.consumerScopeName = consumerScopeName;
		this.providerScopeName = providerScopeName;
	}

	public String getConsumerScopeName() {
		return consumerScopeName;
	}

	public void setConsumerScopeName(String consumerScopeName) {
		this.consumerScopeName = consumerScopeName;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getProviderScopeName() {
		return providerScopeName;
	}

	public void setProviderScopeName(String providerScopeName) {
		this.providerScopeName = providerScopeName;
	}
}
