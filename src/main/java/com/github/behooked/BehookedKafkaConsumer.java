package com.github.behooked;

import java.time.Duration;
import java.util.Collections;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.behooked.api.EventJSON;
import com.github.behooked.client.NotificationSender;

public class BehookedKafkaConsumer {

	private NotificationSender notificationSender;
	private EventJSON event;
	private static final Logger logger = LoggerFactory.getLogger(BehookedKafkaConsumer.class);

	private Properties properties;

	public BehookedKafkaConsumer(Properties properties) {
		this.properties = properties;
		this.notificationSender = new NotificationSender();

	}

	public void consume(String topicName, KafkaRecordProcessor processor) {

		try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
			consumer.subscribe(Collections.singleton(topicName));
			logger.info("Subscribed to topic {}", topicName);


			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
				if (records.isEmpty()) {
					logger.debug("No records consumed");
				}

				for (ConsumerRecord<String, String> record : records) {
					try {
						processor.processRecord(record, topicName);


						 this.event= new EventJSON(record,topicName);
						

						notificationSender.sendNotification(event);

						logger.info("POST Request send to Behooked-Webhook-Service: url={} event-name ={} payload={} timestamp={}",NotificationSender.getDispatcherUrl(),event.getName(), event.getData(), event.getTimestamp());

						
								
					} catch (Exception e) {
						logger.error("Could not process record", e);
					}
				}
			}
		}
	}

}
