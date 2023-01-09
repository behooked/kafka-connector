package com.github.behooked;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;

import com.github.behooked.client.NotificationSender;
import static org.mockito.ArgumentMatchers.any;
public class BehookedKafkaConsumerTest {

	private static final NotificationSender NOTIFICATION_SENDER = mock(NotificationSender.class);
	private static long TIMESTAMP=86400000; 
	private java.util.Date dummyDate=new java.util.Date(TIMESTAMP); 

	@Test
	void testConsume() {
	
		final MockConsumer<String,String> mockConsumer = new MockConsumer<String,String>(OffsetResetStrategy.EARLIEST);
		final String topic = "testTopic";

		final TopicPartition topicPartition = new TopicPartition(topic, 0);
		final EventJSON event = new EventJSON();
		final SendingRecordProcessor processor = new SendingRecordProcessor(NOTIFICATION_SENDER, event);
;
		
		BehookedKafkaConsumer behookedConsumer = new BehookedKafkaConsumer(mockConsumer, processor); 
		

	    mockConsumer.schedulePollTask(() -> addTopicPartitionsAssignmentAndAddConsumerRecord(topic, mockConsumer, topicPartition));
	    mockConsumer.schedulePollTask(behookedConsumer::shutdown);
	    behookedConsumer.consume(topic);

	    verify(NOTIFICATION_SENDER).sendNotification(any(EventJSON.class));
	    assertThat(event.getName()).isEqualTo(topic);
	    assertThat(event.getData()).isEqualTo("foo");
	    assertThat(event.getTimestamp()).isEqualTo(dummyDate);
		
	}


	private void addTopicPartitionsAssignmentAndAddConsumerRecord(final String topic,
			final MockConsumer<String, String> mockConsumer,
			final TopicPartition topicPartition) {

		final Map<TopicPartition, Long> beginningOffsets = new HashMap<>();
		beginningOffsets.put(topicPartition, 0L);
		mockConsumer.rebalance(Collections.singletonList(topicPartition));
		mockConsumer.updateBeginningOffsets(beginningOffsets);
		addConsumerRecord(mockConsumer,topic);
	}

	@SuppressWarnings("deprecation") // checksum in constructor of ConsumerRecord is deprecated but this does not affect realibility of this test 
	private void addConsumerRecord(final MockConsumer<String, String> mockConsumer, final String topic) {
		
		mockConsumer.addRecord(new ConsumerRecord<>(topic, 0, 0, TIMESTAMP, TimestampType.CREATE_TIME,1, 0, 0, "key", "foo"));
		
	}

}
