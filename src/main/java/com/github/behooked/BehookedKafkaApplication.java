package com.github.behooked;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.behooked.client.NotificationSender;

public class BehookedKafkaApplication {

	private static Logger logger = LoggerFactory.getLogger(BehookedKafkaApplication.class);
	public static void main(String[] args) {
		
		if (args.length != 2) {
			logger.error("This program takes two arguments: address of kafka and topic name");
			return;
		}

		
		String kafkaAddress = args[0];
		String topicName = args[1];

		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", kafkaAddress);
		properties.setProperty("group.id", "test-group");
		properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		new BehookedKafkaConsumer( new KafkaConsumer<String,String>(properties),new SendingRecordProcessor(new NotificationSender(), new EventJSON())).consume(topicName);

	}

}
