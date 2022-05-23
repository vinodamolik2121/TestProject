package com.fss.Broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaPublish {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void publish(String topic, String eventData) {

		kafkaTemplate.send(topic, eventData);
	}

}
