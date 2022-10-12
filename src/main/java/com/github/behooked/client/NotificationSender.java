package com.github.behooked.client;


import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.behooked.api.EventJSON;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;

public class NotificationSender {

	private final Client client;
	private static final String  DISPATCHER_URL = "http://localhost:8082/events";


	public NotificationSender()
	{
		
		 client = ClientBuilder.newBuilder()
		        .register(JacksonFeature.class)
		        .build();
	}
	
	public void sendNotification(EventJSON event)
	{
	client.target(DISPATCHER_URL).request(MediaType.APPLICATION_JSON)
		.post(Entity.json(event));

	}
	
	public static String getDispatcherUrl() {
		return DISPATCHER_URL;
	}
	
}
