package com.cargosmart.java.entity;

public class StatusEntity {

	String statusCode;
	String interchangeDate;
	String eventDate;
	
	String filename;
	
	String b2b_receive_status;
	String b2b_process_status;
	String b2b_process_desc;
	
	public StatusEntity(String scode,String idate,String edate){
		this.statusCode = scode;
		this.interchangeDate = idate;
		this.eventDate = edate;
		this.filename = "";
		this.b2b_receive_status = "";
		this.b2b_process_status = "";
		this.b2b_process_desc = "";
	}
	public StatusEntity(String scode,String idate,String edate, String filename, String b2brs, String b2bps, String b2bpd){
		this.statusCode = scode;
		this.interchangeDate = idate;
		this.eventDate = edate;
		
		this.filename = filename;
		
		this.b2b_receive_status = b2brs;
		this.b2b_process_status = b2bps;
		this.b2b_process_desc = b2bpd;
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

	public String getFilename(){
		return filename;
	}
	public void setFilename(String filename){
		this.filename = filename;
	}

	public String getB2bReceiveStatus(){
		return b2b_receive_status;
	}
	public void setB2bReceiveStatus(String b2brs){
		this.b2b_receive_status = b2brs;
	}
	
	public String getB2bProcessStatus(){
		return b2b_process_status;
	}
	public void setB2bProcessStatus(String b2bps){
		this.b2b_process_status = b2bps;
	}
	
	public String getB2bProcessDesc(){
		return b2b_process_desc;
	}
	public void setB2bProcessDesc(String b2bpd){
		this.b2b_process_desc = b2bpd;
	}
	
	
	@Override
	public String toString() {
		return "Status [statusCode=" + statusCode + ", interchangeDate="
				+ interchangeDate + ", eventDate=" + eventDate 
				+ ", filename=" + filename 
				+ ", b2b_receive_status" + b2b_receive_status + ", b2b_process_status" + b2b_process_status +", b2b_process_desc" + b2b_process_desc 
				+"]";
	}
	
	
}
