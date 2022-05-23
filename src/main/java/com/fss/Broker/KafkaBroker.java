package com.fss.Broker;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fss.observer.CustomObserver;

@Service
public class KafkaBroker {

	private CustomObserver customObserver;

	@Autowired
	public void setObserver(CustomObserver customObserver) {
		this.customObserver = customObserver;
	}

	private static CountDownLatch latch = new CountDownLatch(1);

	public CountDownLatch getLatch() {
		return latch;
	}

	@KafkaListener(groupId = "#{'${spring.kafka.group}'}", topics = "#{'${spring.kafka.topics}'.split(',')}")
	public void readKafka(String payload) {
		try {

			// customObserver.update(payloadString,topic);
			customObserver.update(payload);

			latch.countDown();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
