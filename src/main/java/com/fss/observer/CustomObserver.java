package com.fss.observer;

public interface CustomObserver {
	
	public String update(String eventData,String topic);
	public String update(String eventData);

}
