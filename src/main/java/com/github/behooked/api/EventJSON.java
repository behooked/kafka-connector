package com.github.behooked.api;

import java.util.Date;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class EventJSON {

	private String name; 
	private Date timestamp; 
	private String data;
	
	
	@JsonCreator
	public EventJSON(ConsumerRecord<String,String> kafkaRecord) {   
	
		this.name = kafkaRecord.topic();
		this.timestamp=new Date(kafkaRecord.timestamp());
		this.data = kafkaRecord.value().toString();
	}
	
	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonProperty
	public String getData() {
		return data;
	}

	@JsonProperty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getTimestamp() {
		return timestamp;
	}
	
	@JsonSetter("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonSetter("data")
	public void setData(String data) {
		this.data = data;
	}

	@JsonSetter("timestamp")
	public void setTimestamp(Long timestamp) {
		this.timestamp = new Date(timestamp);    // convert timestamp type Long to Date to match disptacher's api
	}
	
}
