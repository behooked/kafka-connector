package com.github.behooked.client;


import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.behooked.EventJSON;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class NotificationSender {

	private final Client client;
	private static final String  DISPATCHER_URL = "http://localhost:8082/events";
	private static final Logger logger = LoggerFactory.getLogger(NotificationSender.class);

	public NotificationSender()
	{
		
		 client = ClientBuilder.newBuilder()
		        .register(JacksonFeature.class)
		        .build();
	}
	
	public void sendNotification(EventJSON event)
	{
		Response response =  client.target(DISPATCHER_URL).request(MediaType.APPLICATION_JSON)
		.post(Entity.json(event));
		
		logger.info("HTTP Request has been completed. Status Code: {}", response.getStatus());

	}
	
	public static String getDispatcherUrl() {
		return DISPATCHER_URL;
	}
	
}
