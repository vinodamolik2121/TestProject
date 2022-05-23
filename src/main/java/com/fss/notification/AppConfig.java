package com.fss.notification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.fss.Broker.KafkaBroker;
import com.fss.Broker.KafkaPublish;
import com.fss.observer.CustomObserver;
import com.fss.smsObserver.KarixObserver;

@EnableAsync
@Configuration
public class AppConfig {

	@Bean
	public KafkaBroker kafkaBroker() {
		return new KafkaBroker();
	}

	@Bean
	public KafkaPublish kafkaPublish() {
		return new KafkaPublish();
	}

	@Bean
	public CustomObserver customObserver() {
		return new KarixObserver();
		// return new TwilioObserver();
	}

	@Bean
	@Qualifier("taskExecutor")
	public TaskExecutor taskExecutor() {
		return new ThreadPoolTaskExecutor();
	}
}
