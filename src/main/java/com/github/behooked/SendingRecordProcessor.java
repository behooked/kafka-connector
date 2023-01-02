package com.github.behooked;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.behooked.api.EventJSON;
import com.github.behooked.client.NotificationSender;

public class SendingRecordProcessor implements KafkaRecordProcessor {

	private static final Logger logger = LoggerFactory.getLogger(SendingRecordProcessor.class);
	private NotificationSender notificationSender;
	private EventJSON event;

	@Override
	public void processRecord(ConsumerRecord<String, String> record, String topicName) throws Exception {

		this.notificationSender = new NotificationSender();
		this.event= new EventJSON(record,topicName);

		logger.info("Consumed record {} from topic {}", record, topicName);

		notificationSender.sendNotification(event);

		logger.info("POST Request send to Behooked-Webhook-Service: url={} event-name ={} payload={} timestamp={}",NotificationSender.getDispatcherUrl(),event.getName(), event.getData(), event.getTimestamp());


	}

}
