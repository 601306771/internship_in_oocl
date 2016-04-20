package com.cargosmart.java.entry;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

public class EventEntry implements Comparable<EventEntry> {
	
	public EventEntry(ArrayList<String> array){
		this.Company=array.get(0);
		this.LOA_on_CT=array.get(1);
		this.Provider=array.get(2);
		this.Carrier=array.get(2);
		this.BK_Number=array.get(3);
		this.CNTR_Number=array.get(4);
		this.CCC=array.get(5);
		this.ETA=array.get(9);
		this.ETD=array.get(10);
		this.ATA=array.get(11);
		this.ATD=array.get(12);
	}
	public EventEntry(){}
	private static final long serialVersionUID = 1L;

	String Provider;
	String Carrier;
	String BK_Number;
	String CNTR_Number;
	String CCC;
	String LOA_on_CT;
	String Company;
	String ETA;
	String ETD;   
	String ATA;
	String ATD;     
	public HashMap<String,StatusEntry> status = new HashMap<String,StatusEntry>();

	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int compareTo(EventEntry o) {
		return this.BK_Number.compareTo(o.BK_Number);  //排序bkNumbers
	}
	
	
	//TO STRING
	@Override
	public String toString() {
		return "Event [Provider=" + Provider + ", Carrier=" + Carrier
				+ ", BK_Number=" + BK_Number + ", CNTR_Number=" + CNTR_Number
				+ ", CCC=" + CCC + ", LOA_on_CT=" + LOA_on_CT + ", Company="
				+ Company + ", ETA=" + ETA + ", ETD=" + ETD + ", ATA=" + ATA
				+ ", ATD=" + ATD + ", status=" + status + "]";
	}
	
	//GETTER AND SETTER
	public String getProvider() {
		return Provider;
	}
	public void setProvider(String provider) {
		Provider = provider;
	}
	public String getCarrier() {
		return Carrier;
	}
	public void setCarrier(String carrier) {
		Carrier = carrier;
	}
	public String getBK_Number() {
		return BK_Number;
	}
	public void setBK_Number(String bK_Number) {
		BK_Number = bK_Number;
	}
	public String getCNTR_Number() {
		return CNTR_Number;
	}
	public void setCNTR_Number(String cNTR_Number) {
		CNTR_Number = cNTR_Number;
	}
	public String getCCC() {
		return CCC;
	}
	public void setCCC(String cCC) {
		CCC = cCC;
	}
	public String getLOA_on_CT() {
		return LOA_on_CT;
	}
	public void setLOA_on_CT(String lOA_on_CT) {
		LOA_on_CT = lOA_on_CT;
	}
	public String getCompany() {
		return Company;
	}
	public void setCompany(String company) {
		Company = company;
	}
	public String getETA() {
		return ETA;
	}
	public void setETA(String eTA) {
		ETA = eTA;
	}
	public String getETD() {
		return ETD;
	}
	public void setETD(String eTD) {
		ETD = eTD;
	}
	public String getATA() {
		return ATA;
	}
	public void setATA(String aTA) {
		ATA = aTA;
	}
	public String getATD() {
		return ATD;
	}
	public void setATD(String aTD) {
		ATD = aTD;
	}
	public HashMap<String, StatusEntry> getStatus() {
		return status;
	}
	public void setStatus(HashMap<String, StatusEntry> status) {
		this.status = status;
	}
	
	
	
	
	
	
	
	
}
