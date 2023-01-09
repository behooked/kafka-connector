package com.github.behooked;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class EventJsonTest {

	@Test
	void testEventJson() {
		final long timestamp=86400000; // dummy Long
		final java.util.Date expectedDate=new java.util.Date(timestamp); // dummyDate:  02 January 1970 
		final String name = "name";
		final String data = "This is a Test";
		
		
		final EventJSON event = new EventJSON();
		
		event.setName(name);
		event.setTimestamp(timestamp);
		event.setData(data);
		
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getTimestamp()).isEqualTo(expectedDate);
		assertThat(event.getData()).isEqualTo(data);
	}
}
