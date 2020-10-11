package com.skyforce.application.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.skyforce.application.utility.GatewayUtility;

@Component
@Order(Integer.MAX_VALUE)
public class JwtFilter implements Filter {
	
	GatewayUtility gatewayUtility;
	
	public JwtFilter(GatewayUtility gatewayUtility) {
		this.gatewayUtility = gatewayUtility;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String httpAuthToken = httpRequest.getHeader("HTTP_AUTH_TOKEN");
		String hsToken = httpRequest.getHeader("HS_TOKEN");
		
		if(httpAuthToken != null) {
			if(gatewayUtility.validateRsaToken(httpAuthToken))
				chain.doFilter(request, response);
			httpResponse.sendError(401, "Unauthorized !");
		}
		
		else if(hsToken != null) {
			if(gatewayUtility.validateHSToken(hsToken)) {
				String token = gatewayUtility.createToken();
				httpResponse.setHeader("Auth-Token-Value", token);
			} else {
				httpResponse.sendError(401, "Unauthorized !");
			}
		}
		
		else
			httpResponse.sendError(401, "Unauthorized !");
	}
}
