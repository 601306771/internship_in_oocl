package com.cargosmart.java.entry;

public class StatusEntry {

	String statusCode;
	String interchangeDate;
	String eventDate;
	public StatusEntry(String scode,String idate,String edate){
		this.statusCode = scode;
		this.interchangeDate = idate;
		this.eventDate = edate;
	}
	
	
	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	public String getInterchangeDate() {
		return interchangeDate;
	}


	public void setInterchangeDate(String interchangeDate) {
		this.interchangeDate = interchangeDate;
	}


	public String getEventDate() {
		return eventDate;
	}


	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}


	@Override
	public String toString() {
		return "Status [statusCode=" + statusCode + ", interchangeDate="
				+ interchangeDate + ", eventDate=" + eventDate + "]";
	}
	
	
}
