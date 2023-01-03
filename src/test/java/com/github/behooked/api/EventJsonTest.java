package com.github.behooked.api;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.record.TimestampType;

public class EventJsonTest {


	private static long TIMESTAMP=86400000; // dummyLong
	private java.util.Date dummyDate=new java.util.Date(TIMESTAMP); // 02 January 1970 
	private final String topic = "topicName";
	
	@Test
	public void testEventJSON() {

		@SuppressWarnings("deprecation") // checksum ist deprecated in constructor for ConsumerRecord - but is not significant for this testing purpose
		final ConsumerRecord<String,String> kafkaRecord = new ConsumerRecord<>(topic, 1, 0, TIMESTAMP, TimestampType.CREATE_TIME,1, 0, 0, "key", "value");
		
		EventJSON eventJSON = new EventJSON( kafkaRecord);
		
		assertThat(eventJSON.getName()).isEqualTo("topicName");
		assertThat(eventJSON.getTimestamp()).isEqualTo(dummyDate);
		assertThat(eventJSON.getData()).isEqualTo("value");

         // ein Parameter des Konstruktors ist checksum, dieser ist depricated, checksum spielt für Test jedoch keien Rolle
		// timestamp wird benötigt, darum dieser Konstruktor notwendig
	}

}
