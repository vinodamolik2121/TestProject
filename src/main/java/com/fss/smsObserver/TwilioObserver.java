package com.fss.smsObserver;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fss.observer.CustomObserver;

@Service
public class TwilioObserver implements CustomObserver {

	@Async("taskExecutor")
	public String sendSms(String eventData) {
		// Code for sending SMS and return the outcome.
		String smsContent = eventData;
		try {
			Thread.sleep(5000);
			System.out.println("SMS Send by TwilioObserver");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return smsContent + " : SENT SUCESSFULLY BY TwilioObserver";
	}

	@Override
	public String update(String eventData, String topic) {
		// TODO Auto-generated method stub

		sendSms(eventData);

		return "updated";
	}

	@Override
	public String update(String eventData) {
		// TODO Auto-generated method stub
		return "updated";
	}

}
