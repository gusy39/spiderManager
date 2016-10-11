package com.ecmoho.message;


public interface MessageTransport {
	
	void retry(Msg message) ;
	
	void retryAsync(Msg message);
	
	String send(Msg message, MessagingEngine.QueuePolicy queuePolicy) throws Exception;
	
	void sendAsync(Msg message, MessagingEngine.QueuePolicy queuePolicy);
}
