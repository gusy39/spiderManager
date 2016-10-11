package com.ecmoho.message;

import java.util.List;

public class Msg {

	private String to;
	private String from;
	private String subject;
	private String content;
	private List<String> attachementsFileNames;
	//db object here, will improve latter.
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getAttachementsFileNames() {
		return attachementsFileNames;
	}
	public void setAttachementsFileNames(List<String> attachementsFileNames) {
		this.attachementsFileNames = attachementsFileNames;
	}
	
}
