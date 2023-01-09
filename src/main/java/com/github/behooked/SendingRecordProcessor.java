package com.github.behooked;



import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.behooked.client.NotificationSender;

public class SendingRecordProcessor implements KafkaRecordProcessor {

	public SendingRecordProcessor(NotificationSender notificationSender, EventJSON event) {
		this.notificationSender = notificationSender;
		this.event = event;
	}


	private static final Logger logger = LoggerFactory.getLogger(SendingRecordProcessor.class);
	private NotificationSender notificationSender;
	
	private EventJSON event;
	
	@Override

		public void processRecord(ConsumerRecord<String,String> record) throws Exception {
		
		this.event.setName(record.topic());
		this.event.setTimestamp(record.timestamp());
		this.event.setData(record.value().toString());
		
		logger.info("Consumed record {} from topic {}", record,record.topic());
		this.notificationSender.sendNotification(event);

		logger.info("POST Request send to Behooked-Webhook-Service: url={} event-name ={} payload={} timestamp={}",NotificationSender.getDispatcherUrl(),event.getName(), event.getData(), event.getTimestamp());

	}

}
