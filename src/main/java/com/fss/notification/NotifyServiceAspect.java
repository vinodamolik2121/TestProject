package com.fss.notification;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotifyServiceAspect {

	@Before(value = "execution(public String sendSms(..))")
	public void beforeSendSms(JoinPoint joinPoint) {
		System.out.println("Before method:" + joinPoint.getSignature());

	}

	@After(value = "execution(public String sendSms(..)) ")
	public void afterSendSms(JoinPoint joinPoint) {
		System.out.println("After method:" + joinPoint.getSignature());

	}

}
