package com.cargosmart.java.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LUJI
 *
 */

public class CarrierEntity {

	public GeneralPart1 part1 = new GeneralPart1();
	public GeneralPart2 part2 = new GeneralPart2();
	public GeneralPart3 part3 = new GeneralPart3();
	
	public Map<String, GeneralPart4> part4 = new HashMap<String, GeneralPart4>();
	
	//public static void main(String[] args) {}

	public CarrierEntity(){}
	
	// Inner Classes
	public class GeneralPart1{
		String Provider;
		String Carrier;
		String BK_Number;
		String CNTR_Number;
		String CCC;
		
		// GETTER AND SETTER
		public String getProvider(){ return Provider;}
		public void setProvier(String provider){ this.Provider = provider;}
		
		public String getCarrier(){ return Carrier;}
		public void setCarrier(String carrier){ this.Carrier = carrier;}
		
		public String getBkNumber(){ return BK_Number;}
		public void setBkNumber(String bknumber){ this.BK_Number = bknumber;}
		
		public String getCNTRNumber(){ return CNTR_Number;}
		public void setCNTRNumber(String cntrnumber){ this.CNTR_Number = cntrnumber;}
		
		public String getCCC(){ return CCC;}
		public void setCCC(String ccc){ this.CCC = ccc;}
	}
	
	public class GeneralPart2 {
		String Visibility;
		String LOA_on_BC;
		String LOA_on_CT;
		String Company;
		String Account;
		
		// GETTER AND SETTER
		public String getVisibility(){ return Visibility;}
		public void setVisibility(String visibility){ this.Visibility = visibility;}
		
		public String getLOAonBC(){ return LOA_on_BC;}
		public void setLOAonBC(String loaonbc){ this.LOA_on_BC = loaonbc;}
		
		public String getLOAonCT(){ return LOA_on_CT;}
		public void setLOAonCT(String loaonct){ this.LOA_on_CT = loaonct;}
		
		public String getCompany(){ return Company;}
		public void setCompany(String company){ this.Company = company;}
		
		public String getAccount(){ return Account;}
		public void setAccount(String account){ this.Account = account;}
	}

	public class GeneralPart3 {
		String ETD;
		String ETA;
		String ATA;
		String ATD;
		String Last_BC_Msg_TStamp;
		String Last_CT_Msg_TStamp;
		
		// GETTER AND SETTER
		public String getETD(){ return ETD;}
		public void setETD(String etd){ this.ETD = etd;}
		
		public String getETA(){ return ETA;}
		public void setETA(String eta){ this.ETA = eta;}
		
		public String getATA(){ return ATA;}
		public void setATA(String ata){ this.ATA = ata;}
		
		public String getATD(){ return ATD;}
		public void setATD(String atd){ this.ATD = atd;}
		
		public String getLastBCMsgTStamp(){ return Last_BC_Msg_TStamp;}
		public void setLastBCMsgTStamp(String lastbcmsgtstamp){ this.Last_BC_Msg_TStamp = lastbcmsgtstamp;}
		
		public String getLastCTMsgTStamp(){ return Last_CT_Msg_TStamp;}
		public void setLastCTMsgTStamp(String lastctmsgtstamp){ this.Last_CT_Msg_TStamp = lastctmsgtstamp;}
		
	}
	
	public class GeneralPart4 {
		String Msg_TStamp;
		String Event_Date;
		String filename;
		
		String b2b_receive_status;
		String b2b_process_status;
		String b2b_process_desc;
		
		public GeneralPart4(){}
		
		// GETTER AND SETTER
		public String getMsg_TStamp(){ return Msg_TStamp;}
		public void setMsg_TStamp(String msgtstamp){ this.Msg_TStamp = msgtstamp;}
		
		public String getEventDate(){ return Event_Date;}
		public void setEventDate(String eventdate){ this.Event_Date = eventdate;}
		
		public String getFilename(){ return filename;}
		public void setFilename(String filename){ this.filename = filename;}
		
		public String getB2bReceiveStatus(){ return b2b_receive_status;}
		public void setB2bReceiveStatus(String b2brs){ this.b2b_receive_status = b2brs;}
		
		public String getB2bProcessStatus(){ return b2b_process_status;}
		public void setB2bProcessStatus(String b2bps){ this.b2b_process_status = b2bps;}
		
		public String getB2bProcessDesc(){ return b2b_process_desc;}
		public void setB2bProcessDesc(String b2bpd){ this.b2b_process_desc = b2bpd;}

	}


}
