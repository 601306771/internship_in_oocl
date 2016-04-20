package com.cargosmart.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cargosmart.java.comm.Cvs2EventEntry;
import com.cargosmart.java.comm.WriteComm;
import com.cargosmart.java.comm.WriteCustView;
import com.cargosmart.java.entry.EventEntity;

public class baseResultBeanTest {
	public static void main(String[] args){
		
		Map<Integer,String> fileNames= new HashMap<Integer,String>();
		fileNames.put(0,"APLU");
		fileNames.put(1,"ANNU");
		fileNames.put(2,"CHNJ");
		fileNames.put(3,"CHNL");
		fileNames.put(7,"HDMU");
		fileNames.put(8,"HJSC");
		fileNames.put(9,"HLCU");
		fileNames.put(10,"MAEU");
		fileNames.put(11,"MATS");
		fileNames.put(12,"MCCQ");
		fileNames.put(13,"MOLU");
		fileNames.put(14,"NYKS");
		fileNames.put(15,"SAFM");
		fileNames.put(16,"SEAU");
		fileNames.put(18,"UASC");
		fileNames.put(19,"YMLU");
		fileNames.put(20,"ZIMU");
		fileNames.put(4,"CMDU");
		String filePath = "D:\\Nigel\\optWorkSpace\\";
		Cvs2EventEntry entry = new Cvs2EventEntry();
		
		//write custView
		WriteCustView poiTest = new WriteCustView();
		int countRow =3;
        for(int i=0;i<=20;i++){
				if(i==5||i==6||i==17){
					//NULL
				}else{
					List<EventEntity> eventEntry =   entry.getRecords(filePath+fileNames.get(i)+".csv");//get enven Entry list
					countRow = poiTest.WriteCustView(eventEntry,countRow);
				}
        }
		poiTest.closeWriterCustView();
		
		//write comm
		WriteComm comm = new WriteComm();
		for(int i=0;i<=20;i++){
			if(i==5||i==6||i==17){
				//NULL
			}else{
				List<EventEntity> eventEntry =   entry.getRecords(filePath+fileNames.get(i)+".csv");//get enven Entry list
				comm.WriteCommon(eventEntry,fileNames.get(i));
			}
	    }
		comm.closeWriterCustView();
		System.out.println(">>>>>>>>>>><<<<<<<<<<<<");
	}
}
