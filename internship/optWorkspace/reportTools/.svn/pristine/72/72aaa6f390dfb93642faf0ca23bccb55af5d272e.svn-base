package com.cargosmart.java.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cargosmart.java.entity.CarrierEntity;
import com.cargosmart.java.entity.EventEntity;
import com.cargosmart.java.entity.StatusEntity;

public class WriteRules {
	
	/**
	 * @author LUJI
	 * 
	 * @param CarrierEntity
	 * @return true/false
	 * */
	public boolean isAbleToWriteRow(CarrierEntity carrierEntity){
		
		GregorianCalendar GnowDate = new GregorianCalendar();
		GnowDate.setTime(new Date());
		GnowDate.add(2,-3);	//set 3 months ago              
		
		Date ETADate = null, ATADate = null;
		
		
		// Set ETADate
		if(isNotNull(carrierEntity.part3.getETA())){
			ETADate = setDate(carrierEntity.part3.getETA());
		}
		
		// Set ATADate
		if(isNotNull(carrierEntity.part3.getATA())){
			ATADate = setDate(carrierEntity.part3.getATA());
		}
		
		List<Date> datelist = new ArrayList<Date>();
		for(String key : carrierEntity.part4.keySet())
		{
			if(key.equals("EE") || key.equals("AE") || key.equals("VD") || key.equals("VA") || key.equals("UV") || key.equals("OA") || key.equals("RD")){
				if(!carrierEntity.part4.get(key).getEventDate().equals("") && carrierEntity.part4.get(key).getEventDate() != null){
					SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy KK:mm:ss a");
					Date date = null;
					try {
					     date = format.parse(carrierEntity.part4.get(key).getEventDate());						    
				    } 
					catch (ParseException e) {
					     e.printStackTrace();
					}			
					datelist.add(date);
				}
			}
		}
		Date temp = null;
		if(datelist.size() > 0){
			temp = datelist.get(0);
			for(int i=0;i < datelist.size();i++){
				if(!(datelist.get(i).before(temp))){
					temp = datelist.get(i);
				}
			}
		}
		else return false;
		
		Date cePart3_lastCTDate = temp;
		

		// Interchange Time within 3 months
		if(!cePart3_lastCTDate.before(GnowDate.getTime())){
			// ATADate 1
			if(isNotNull(ATADate)){
				if(!ATADate.before(GnowDate.getTime())){
					return true;
				}
			}
			// ATADate 0 ETADate 1
			else if(isNotNull(ETADate)){
				if(!ETADate.before(GnowDate.getTime())){
					return true;
				}
			
			}
			// ATADate 0 ETADate 0
			else return true;
		}
		return false;
	}
	
	public boolean isNotNull(Object obj){
		if(obj != null && !obj.equals("null") && !obj.equals("")){ return true;}
		return false;
	}
	
		
	Date setDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss aa"); 
		String timeFormatD = date.substring(date.length() - 2, date.length());
		if(timeFormatD.equals("PM")){
			try {
				Long DateAfterMili = sdf.parse(date).getTime()+12*1000*60*60;
				return new Date(DateAfterMili);
			} 
			catch (ParseException e) {
				e.printStackTrace();
			}
		
		}
		else {
			try {
				return sdf.parse(date);
			} 
		
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		return null;
	}

	/**
	 * missing logic ,when EE,AE,VD....RD not exist
	 * if true, put "not happen yet"
	 * if false, coloring 
	 * @param ETD
	 * @param ETA
	 * @param ATD
	 * @param ATA
	 * @return
	 */
	public Map<String,Boolean> missingRules(String ETD,String ETA,String ATD,String ATA){
		Map<String,Boolean> miss = new HashMap<String,Boolean>();
		Date TD = null,TA = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss aa");	
		
		//set TD date
		if(ATD!=null&&!ATD.equals("null")){//ATD exist
			String TimeFormatA = ATD.substring(ATD.length()-2, ATD.length());
			if(TimeFormatA.equals("PM")){
				try {
					TD = sdf.parse(ATD);
					Long DateAfterMili=TD.getTime()+12*1000*60*60;
					TD=new Date(DateAfterMili);
				} catch (ParseException e) {
					e.printStackTrace();
				}		
			}
			else {
				try {
					TD = sdf.parse(ATD);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(ETD!=null&&!ETD.equals("null")){//ATD not exist,ETD exist
			String TimeFormatA = ETD.substring(ETD.length()-2, ETD.length());
			if(TimeFormatA.equals("PM")){
				try {
					TD = sdf.parse(ETD);
					Long DateAfterMili=TD.getTime()+12*1000*60*60;
					TD=new Date(DateAfterMili);
				} catch (ParseException e) {
					e.printStackTrace();
				}		
			}
			else {
				try {
					TD = sdf.parse(ETD);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}		
		//set EE AE VD rule's date
		GregorianCalendar EEDateAfter=new GregorianCalendar();
		GregorianCalendar AEDateAfter=new GregorianCalendar();
		GregorianCalendar VDDateAfter=new GregorianCalendar();
		Date nowDate=new Date();
		EEDateAfter.setTime(nowDate);
		AEDateAfter.setTime(nowDate);
		VDDateAfter.setTime(nowDate);
		/**
		 * a. EE (Empty Container Pickup): must exist if ETD - 2 >= Current Date
		 * b. AE (Loaded on Board): must exists if ETD + 1 >= Current Date
		 * c. VD (Vessel Departure): must exists if ETD + 1 > = Current Date
		 */
		EEDateAfter.add(5,+2);
		AEDateAfter.add(5,-1);
		VDDateAfter.add(5,-1);
		
		if(TD!=null&&!TD.before(EEDateAfter.getTime())){//EE
			miss.put("EE", true);
		}else{
			miss.put("EE", false);
		}
		if(TD!=null&&!TD.before(AEDateAfter.getTime())){//AE
			miss.put("AE", true);
		}else{
			miss.put("AE", false);
		}
		if(TD!=null&&!TD.before(VDDateAfter.getTime())){//VD
			miss.put("VD", true);
		}else{
			miss.put("VD", false);
		}
	
		//set TA date
		if(ATA!=null&&!ATA.equals("null")){//ATA exist
			String TimeFormatA = ATA.substring(ATA.length()-2, ATA.length());
			if(TimeFormatA.equals("PM")){
				try {
					TA = sdf.parse(ATA);
					Long DateAfterMili=TA.getTime()+12*1000*60*60;
					TA=new Date(DateAfterMili);
				} catch (ParseException e) {
					e.printStackTrace();
				}		
			}
			else {
				try {
					TA = sdf.parse(ATA);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(ETA!=null&&!ETA.equals("null")){//ATA not exist,ETA exist
			String TimeFormatA = ETA.substring(ETA.length()-2, ETA.length());
			if(TimeFormatA.equals("PM")){
				try {
					TA = sdf.parse(ETA);
					Long DateAfterMili=TA.getTime()+12*1000*60*60;
					TA=new Date(DateAfterMili);
				} catch (ParseException e) {
					e.printStackTrace();
				}		
			}
			else {
				try {
					TA = sdf.parse(ETA);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		//set VA UV OA RD rule's date
		GregorianCalendar VADateAfter=new GregorianCalendar();
		GregorianCalendar UVDateAfter=new GregorianCalendar();
		GregorianCalendar OADateAfter=new GregorianCalendar();
		GregorianCalendar RDDateAfter=new GregorianCalendar();
		VADateAfter.setTime(nowDate);
		UVDateAfter.setTime(nowDate);
		OADateAfter.setTime(nowDate);
		RDDateAfter.setTime(nowDate);
		/**
		 *d. VA (Vessel Arrived): must exists if ETA + 1 > Current Date
		 *e. UV (Unloaded from Vessel): must exists if ETA + 2 > Current Date
		 *f. OA (Out Gate): must exists if ETA + 16 > Current Date
		 *g. RD (Empty Container Returned): must exist if ETA + 31 > Current Date
		 */
		VADateAfter.add(5,-1);
		UVDateAfter.add(5,-2);
		OADateAfter.add(5,-16);
		RDDateAfter.add(5,-31);
	
		if(TA!=null&&!TA.before(VADateAfter.getTime())){//EE
			miss.put("VA", true);
		}else{
			miss.put("VA", false);
		}
		if(TA!=null&&!TA.before(UVDateAfter.getTime())){//AE
			miss.put("UV", true);
		}else{
			miss.put("UV", false);
		}
		if(TA!=null&&!TA.before(OADateAfter.getTime())){//VD
			miss.put("OA", true);
		}else{
			miss.put("OA", false);
		}if(TA!=null&&!TA.before(RDDateAfter.getTime())){//VD
			miss.put("RD", true);
		}else{
			miss.put("RD", false);
		}
		
		return miss;
	}
	
	
	
	/**
	 * coloring logic ,base rule "If the event on the right exists, then the events on its left must exist." 
	 * if true, coloring Yellow,means follow rule
	 * if false, coloring pink,means fall rule
	 * @param status
	 * @return
	 */
	public Map<String,Boolean> coloringRules(CarrierEntity ce){
		Map<String,Boolean> coloring = new HashMap<String,Boolean>();
		
		CarrierEntity.GeneralPart4 ae = ce.part4.get("AE");
		CarrierEntity.GeneralPart4 vd = ce.part4.get("VD");
		CarrierEntity.GeneralPart4 va = ce.part4.get("VA");
		CarrierEntity.GeneralPart4 uv = ce.part4.get("UV");
		CarrierEntity.GeneralPart4 oa = ce.part4.get("OA");
		CarrierEntity.GeneralPart4 rd = ce.part4.get("RD");
		
		//EE follow rule
		if(ae == null && vd == null && va == null && uv == null && oa == null && rd == null){
			coloring.put("EE", true);
		}
		else{
			coloring.put("EE", false);
		}

		//AE follow rule
		if(vd == null && va == null && uv == null && oa == null && rd == null){
			coloring.put("AE", true);
		}
		else{
			coloring.put("AE", false);
		}
		
		//VD follow rule
		if(va == null && uv == null && oa == null && rd == null){
			coloring.put("VD", true);
		}
		else{
			coloring.put("VD", false);
		}
		
		//VA follow rule
		if(uv == null && oa == null && rd == null){
			coloring.put("VA", true);
		}
		else{
			coloring.put("VA", false);
		}
		
		//UV follow rule
		if(oa == null && rd == null){
			coloring.put("UV", true);
		}
		else{
			coloring.put("UV", false);
		}
		
		//OA follow rule
		if(rd == null){
			coloring.put("OA", true);
		}
		else{
			coloring.put("OA", false);
		}

		coloring.put("RD", true);
		
		return coloring;
	}
	
}
