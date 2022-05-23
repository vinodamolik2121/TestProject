package com.fss.karixModel;

public class KarixInput {

	String channel;
	String source;
	String destination[];
	Content content;
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String[] getDestination() {
		return destination;
	}
	public void setDestination(String[] destination) {
		this.destination = destination;
	}
	
	
	
}
