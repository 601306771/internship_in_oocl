package com.cargosmart.java.comm;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import com.cargosmart.java.entity.EventEntity;
import com.cargosmart.java.entity.StatusEntity;




public class Cvs2EventEntry {
	
	private  StatusEntity getObject(ArrayList<ArrayList> array,int i){
		
		String statusCode = (String) array.get(i).get(13);
		String interchangeDate = (String) array.get(i).get(14);
		String eventDate = (String) array.get(i).get(15);
		
		String filename = "";
		String b2b_receive_status = "";
		String b2b_process_status = "";
		String b2b_process_desc = "";
		
		if(array.get(i).size() > 16){
			filename = (String) array.get(i).get(16);
			if(array.get(i).size() > 17){
				b2b_receive_status = (String) array.get(i).get(17);
				if(array.get(i).size() > 18){
					b2b_process_status = (String) array.get(i).get(18);
					if(array.get(i).size() > 19){
						b2b_process_desc = (String) array.get(i).get(19);
					}
				}	
			}
				
		}
		StatusEntity status;
		if(!filename.equals("") && !filename.equals("null") && filename != null){
			status = new StatusEntity(statusCode, interchangeDate, eventDate, filename, b2b_receive_status, b2b_process_status, b2b_process_desc);
		}
		else{
			status = new StatusEntity(statusCode,interchangeDate,eventDate);
		}
		
		
		return status;
	}
  
	public List<EventEntity> getRecords (String filepath){
		//String sourcePath = System.getProperty("user.dir") + File.separator + "source" +File.separator+CurrentTime.getCurrentFolder() + File.separator;	
		String sourcePath = "";
		ArrayList<ArrayList> array=null;
		try {
			array = new FileHelp().readBigFileCSV(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int num1 = array.size()-3;
		int num2 = array.size()-2;
		int num3 = array.size()-1;    
		System.out.println ("arraySize----------"+array.size());		
		Collections.sort(array, new Comparator<ArrayList>() {     
	 			  public int compare(ArrayList o1, ArrayList o2) {   	   
					  String a = (String) o1.get(3);
					  String b = (String) o2.get(3);  
					  int number=a.compareTo(b);
					  if(number==0){
						  String a1=(String)o1.get(4);
						  String b1=(String)o2.get(4);
						  int number1=a1.compareTo(b1);
						  if(number1==0){
							  String a2=(String)o1.get(5);
							  String b2=(String)o2.get(5);
							  return a2.compareTo(b2);
						  }
						  return number1;
		              }
					  return number;
				  }
			  });  
		List list = new ArrayList();
		for(int i=0;i<array.size()-2;i++){ 
			 
			EventEntity event =new EventEntity(array.get(i));			
			StatusEntity s=this.getObject(array,i); 
			event.status.put(s.getStatusCode(),s);
			
			String secondBK=String.valueOf(array.get(i).get(3));         
			String firstBK=String.valueOf(array.get(i+1).get(3));
			
			String secondC = String.valueOf(array.get(i).get(4));
			String firstC = String.valueOf(array.get(i+1).get(4));
			
			String firstCCC=String.valueOf(array.get(i).get(5));
			String secondCCC=String.valueOf(array.get(i+1).get(5));
			
			String firstD = String.valueOf(array.get(i+1).get(13));
			
			
			if((secondBK.equals(firstBK))&&(secondC.equals(firstC))&&(firstCCC.equals(secondCCC))){
				while(1==1){
					StatusEntity ss =this.getObject(array,i+1);
					event.status.put(ss.getStatusCode(),ss);  
					i++;
					String secondBK2=String.valueOf(array.get(i).get(3));   //bknumber
					String firstBK1=String.valueOf(array.get(i+1).get(3));   
					String secondC2 = String.valueOf(array.get(i).get(4));   //cntr
					String firstC1 = String.valueOf(array.get(i+1).get(4));  
					
                    String firstCCC1=String.valueOf(array.get(i).get(5));
					String secondCCC1=String.valueOf(array.get(i+1).get(5));
					
					if(!(secondBK2.equals(firstBK1))||!(secondC2.equals(firstC1))||!(firstCCC1.equals(secondCCC1))){
						break;
					}
					if(i==array.size()-2){
						break;
					}
				}
				list.add(event);
			}

			else{
				list.add(event);
			}	
		}

		  
		String firstBK1=String.valueOf(array.get(num1).get(3));
		String firstC1 = String.valueOf(array.get(num1).get(4));
		String firstCCC1 = String.valueOf(array.get(num1).get(5));
		
		String secondBK1=String.valueOf(array.get(num2).get(3));
		String secondC1 = String.valueOf(array.get(num2).get(4));
		String secondCCC1 = String.valueOf(array.get(num2).get(5));
		
		
		String thirdBK=String.valueOf(array.get(num3).get(3));
		String thirdC = String.valueOf(array.get(num3).get(4));
        String thirdCCC1=String.valueOf(array.get(num3).get(5));
		
		if((secondBK1.equals(firstBK1))&&(secondC1.equals(firstC1))&&(firstCCC1.equals(secondCCC1))){
			StatusEntity s1 =this.getObject(array,num2);
			EventEntity event = (EventEntity) list.get(list.size()-1);
			event.status.put(s1.getStatusCode(),s1);
			
			if((secondBK1.equals(thirdBK))&&(secondC1.equals(thirdC))&&(secondCCC1.equals(thirdCCC1))){
				StatusEntity s2 =this.getObject(array,num3);
				event.status.put(s2.getStatusCode(),s2);
			}

		}
		
		
		else{
			StatusEntity s1 =this.getObject(array,num2);
			EventEntity event =new EventEntity(array.get(num2));
			event.status.put(s1.getStatusCode(),s1);
			list.add(event);
			if((secondBK1.equals(thirdBK))&&(secondC1.equals(thirdC))&&(secondCCC1.equals(thirdCCC1))){
				StatusEntity ss =this.getObject(array,num3); 
				event.status.put(ss.getStatusCode(),ss);
			}
			else{
				EventEntity event1 =new EventEntity(array.get(num1));
				StatusEntity ss =this.getObject(array,num1);
				event1.status.put(ss.getStatusCode(),ss);
				list.add(event1);
			}
		}
		System.out.println( "convert 2 event completed");
		return list;
	}
	
//	static main(args) {
//		Converter con = new Converter()
//		Convert2Event enter = new Convert2Event()
//		List<CarrierGeneralBean> generalBeans = con.conver2CarrierBean(enter.getRecords())
//		WriteExcel tools = new APLU(1)
//		tools.writeAll2Execl(generalBeans)
//	}
}
