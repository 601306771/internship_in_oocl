package com.cargosmart.java.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.cargosmart.java.entity.CarrierEntity;
import com.cargosmart.java.entity.EventEntity;
import com.cargosmart.java.entity.StatusEntity;

/**
 * @author LUJI
 *
 */

public class Event2CarrierEntry {

	//public static void main(String[] args) {}
	
	public List<CarrierEntity> convert2Carrier(List<EventEntity> events){
		
		List<CarrierEntity> list = new ArrayList<CarrierEntity>();
		Iterator<EventEntity> it_event = events.iterator();
		
		while(it_event.hasNext()){
			
			CarrierEntity carrierEntity = new CarrierEntity();
			EventEntity eventEntity = it_event.next();
			
			// Set part1
			carrierEntity.part1.setProvier(eventEntity.getProvider().trim());
			carrierEntity.part1.setCarrier(eventEntity.getCarrier().trim());
			carrierEntity.part1.setBkNumber(eventEntity.getBK_Number().trim());
			carrierEntity.part1.setCNTRNumber(eventEntity.getCNTR_Number().trim());
			carrierEntity.part1.setCCC(eventEntity.getCCC().trim());
			
			// Set part2
			carrierEntity.part2.setVisibility("");
			carrierEntity.part2.setAccount("");
			carrierEntity.part2.setLOAonBC("");
			
			if(eventEntity.getLOA_on_CT() != null){ carrierEntity.part2.setLOAonCT(eventEntity.getLOA_on_CT());}
			else{ carrierEntity.part2.setLOAonCT("");}
			if(eventEntity.getCompany() != null){ carrierEntity.part2.setCompany(eventEntity.getCompany());}
			else{ carrierEntity.part2.setCompany("");}
			
			// Set part3
			carrierEntity.part3.setATA(eventEntity.getATA());
			carrierEntity.part3.setATD(eventEntity.getATD());
			carrierEntity.part3.setETA(eventEntity.getETA());
			carrierEntity.part3.setETD(eventEntity.getETD());
			carrierEntity.part3.setLastBCMsgTStamp("");
			
			// Set part4
			Iterator<StatusEntity> it_status = eventEntity.getStatus().values().iterator();			
			List<Date> dateList = new ArrayList<Date>();
			
			while(it_status.hasNext()){
				StatusEntity se = it_status.next();
				CarrierEntity.GeneralPart4 cpart4 = new CarrierEntity().new GeneralPart4();
				cpart4.setMsg_TStamp(se.getInterchangeDate());
				cpart4.setEventDate(se.getEventDate());
				cpart4.setFilename(se.getFilename());
				
				cpart4.setB2bReceiveStatus(se.getB2bReceiveStatus());
				cpart4.setB2bProcessStatus(se.getB2bProcessStatus());
				cpart4.setB2bProcessDesc(se.getB2bProcessDesc());
				
				SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss aa");
				String date = se.getInterchangeDate();
				String timeFormat = date.substring(date.length()-2, date.length());
				Date interchangeDate = new Date();
				
				try {
					if(timeFormat.equals("PM")){ 
						interchangeDate = new Date(sdf.parse(date).getTime() + 12*1000*60*60);
					}
					else{ 
						
						interchangeDate = sdf.parse(date);
						
					}
				} 
				catch (ParseException e) { e.printStackTrace();}
				
				dateList.add(interchangeDate);
				
				String key = se.getStatusCode().trim();
				if(key.equals("27")){ key = "EE";}
				if(key.equals("48")){ key = "AE";}
				if(key.equals("24")){ key = "VD";}
				if(key.equals("1"))	{ key = "VA";}
				if(key.equals("29")){ key = "UV";}
				if(key.equals("74")){ key = "OA";}
				if(key.equals("82")){ key = "RD";}
				
				carrierEntity.part4.put(key, cpart4);
				
			}// End while it_status
			
			// Set part3 Last_CT_Msg_TStamp
			Date maxDate = dateList.get(0);
			for(int i = 1; i < dateList.size(); i++){
				if(maxDate.before(dateList.get(i))){ maxDate = dateList.get(i);}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss aa");
			carrierEntity.part3.setLastCTMsgTStamp(sdf.format(maxDate));

			
			list.add(carrierEntity);
		}// End while it_events
		
		return list;
		
	}
}
