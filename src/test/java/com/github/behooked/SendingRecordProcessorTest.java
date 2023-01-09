package com.github.behooked;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.record.TimestampType;
import org.junit.jupiter.api.Test;

import com.github.behooked.client.NotificationSender;

public class SendingRecordProcessorTest {

	private static final NotificationSender NOTIFICATION_SENDER = mock(NotificationSender.class);

	private static long TIMESTAMP=86400000; // dummy Long
	private final java.util.Date dummyDate=new java.util.Date(TIMESTAMP); // dummyDate:  02 January 1970 
	private final String topic = "testTopic";


	@Test
	void testProcessRecord() throws Exception{
		final EventJSON event = new EventJSON();
		final SendingRecordProcessor processor = new SendingRecordProcessor(NOTIFICATION_SENDER,event);
		final ConsumerRecord<String, String> record = createConsumerRecord();

		processor.processRecord(record);

		verify(NOTIFICATION_SENDER).sendNotification(any(EventJSON.class));
		assertThat(event.getName()).isEqualTo(topic);
		assertThat(event.getData()).isEqualTo("foo");
		assertThat(event.getTimestamp()).isEqualTo(dummyDate);

	}


	@SuppressWarnings("deprecation") // checksum in constructor of ConsumerRecord is deprecated but this does not affect realibility of  this test 
	private ConsumerRecord<String, String> createConsumerRecord() {
		final int  partition = 0;
		return new ConsumerRecord<>(topic, partition, 0, TIMESTAMP, TimestampType.CREATE_TIME,1, 0, 0, "key", "foo");

	}
}
