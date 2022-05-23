package com.fss.smsObserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fss.Broker.KafkaPublish;
import com.fss.karixModel.Content;
import com.fss.karixModel.KarixInput;
import com.fss.observer.CustomObserver;

@RestController
public class KarixObserver implements CustomObserver {

	@Value("${karix.user}")
	String Karixuser;
	@Value("${karix.pass}")
	String Karixpass;
	@Value("${karix.apiUrl}")
	String apiUrl;
	@Value("${karix.channel}")
	String channel;

	@Autowired
	private KafkaPublish kafkaPublish;

	@Async("taskExecutor")
	public String sendSms(String eventData) {

		System.out.println("/////////////////////////////////SMS 11111111111111111111111////////////////////");
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		String output = null;

		try {

			json = (JSONObject) parser.parse(eventData);

			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String basicAuthString = Karixuser + ":" + Karixpass;
			byte[] encodedBytes = Base64.encodeBase64(basicAuthString.getBytes());

			String USER_PASS = new String(encodedBytes);
			String basicAuth = "Basic " + USER_PASS;

			conn.setRequestProperty("Authorization", basicAuth);

			KarixInput karixInput = new KarixInput();

			Content content = new Content();

			String text = (String) json.get("text");
			content.setText(text);
			String destination = (String) json.get("destination");
			karixInput.setChannel(channel);

			String source = (String) json.get("source");
			karixInput.setSource(source);

			String[] endpoint = { destination };
			karixInput.setDestination(endpoint);

			karixInput.setContent(content);

			ObjectMapper Obj = new ObjectMapper();

			String jsonStr = Obj.writeValueAsString(karixInput);

			// Displaying JSON String
			System.out.println(jsonStr.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonStr.getBytes());
			os.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Done";
	}

	@Async("myExecutor2")
	public String sendSms2(String eventData) {
		// Code for sending SMS and return the outcome.

		System.out.println("/////////////////////////////////SMS 222222222222222////////////////////");
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		String output = null;

		try {

			json = (JSONObject) parser.parse(eventData);

			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String basicAuthString = Karixuser + ":" + Karixpass;
			byte[] encodedBytes = Base64.encodeBase64(basicAuthString.getBytes());

			String USER_PASS = new String(encodedBytes);
			String basicAuth = "Basic " + USER_PASS;

			conn.setRequestProperty("Authorization", basicAuth);

			KarixInput karixInput = new KarixInput();

			Content content = new Content();

			String text = (String) json.get("text");
			content.setText(text);
			String destination = (String) json.get("destination");
			karixInput.setChannel(channel);

			String source = (String) json.get("source");
			karixInput.setSource(source);

			String[] endpoint = { destination };
			karixInput.setDestination(endpoint);

			karixInput.setContent(content);

			ObjectMapper Obj = new ObjectMapper();

			String jsonStr = Obj.writeValueAsString(karixInput);

			// Displaying JSON String
			System.out.println(jsonStr.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonStr.getBytes());
			os.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json.get("text") + " : SENT SUCESSFULLY BY KarixObserver to " + json.get("mob");
	}

	@Override
	public String update(String eventData, String topic) {
		// TODO Auto-generated method stub

		System.out.println("Topic name passed is :" + topic);
		if ("smsService".equals(topic)) {

			System.out.println("TOPIC IS :\t" + topic);
			sendSms(eventData);
		} else {
			System.out.println("TOPIC IS :\t" + topic);
			sendSms2(eventData);
		}

		return "updated";
	}

	@Override
	public String update(String eventData) {
		// TODO Auto-generated method stub

		JSONParser parser = new JSONParser();
		JSONObject json = null;
		String output = null;

		try {

			json = (JSONObject) parser.parse(eventData);
			String topic = (String) json.get("topic");
			if ("smsService".equals(topic)) {
				sendSms(eventData);
			} else if ("smsService2".equals(topic)) {
				sendSms2(eventData);
			} else {
				System.out.println("Topic is Not Valid");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return output;
	}

	@PostMapping("/publish")
	public String publish(@RequestBody String eventData) {
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {

			//////// For now you have topic in request body
			json = (JSONObject) parser.parse(eventData);
			String topic = (String) json.get("topic");
			kafkaPublish.publish(topic, eventData);

			System.out.println("Data is being Publish to Kafka Server");

		} catch (

		Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Done";
	}

}
