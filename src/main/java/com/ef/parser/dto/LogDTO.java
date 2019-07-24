package com.ef.parser.dto;


public class LogDTO {
	private String startDate;
	private String ip;
	private String requestMethod;
	private String requestStatus;
	private String requestBody;
	
	public LogDTO(){
		
	}
	
	public LogDTO(String startDate, String ip, String requestContent) {
		super();
		this.startDate = startDate;
		this.ip = ip;
		this.requestMethod = requestContent;
	}
	
	public LogDTO(String startDate, String ip, String requestMethod,
			String requestStatus, String requestContent) {
		super();
		this.startDate = startDate;
		this.ip = ip;
		this.requestMethod = requestMethod;
		this.requestStatus = requestStatus;
		this.requestBody = requestContent;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	@Override
	public String toString() {
		return " date: " + startDate + ", ip: " + ip
				+ ", Request: " + requestMethod + ", Status: "
				+ requestStatus + ", User Agent: " + requestBody + "]";
	}

	
}
