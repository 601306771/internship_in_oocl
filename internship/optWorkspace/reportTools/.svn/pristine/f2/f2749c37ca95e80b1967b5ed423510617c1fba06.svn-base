package com.cargosmart.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cargosmart.java.api.ReportProducer;
import com.cargosmart.java.comm.Cvs2EventEntry;
import com.cargosmart.java.comm.PropertiesHandlers;
import com.cargosmart.java.comm.Event2CarrierEntry;
import com.cargosmart.java.comm.WriteAllToExcel;
import com.cargosmart.java.entity.CarrierEntity;
import com.cargosmart.java.entity.EventEntity;

public class WriteAllTest {
public static void main(String[] args){
		PropertiesHandlers propertiesHandlers = new PropertiesHandlers();
		Map<Integer,String> fileNames = propertiesHandlers.getFileName();
		
		String filePath = "C:\\Users\\LUJI\\workspace\\EclipseG\\poiExcelTools\\source\\2015-11\\";
	
		Cvs2EventEntry entry = new Cvs2EventEntry();
		Event2CarrierEntry e2c = new Event2CarrierEntry();
		WriteAllToExcel all = new WriteAllToExcel();
		
		//write common
		System.out.println("Writing common:");
		for(int i=0;i<fileNames.size();i++){
			File csv = new File(filePath+fileNames.get(i)+".csv");
			if(!csv.exists()){
				System.out.println(fileNames.get(i)+".CSV  NOT EXISTS");
			}else{
				System.out.print(fileNames.get(i)+"......");
				List<EventEntity> eventEntry = entry.getRecords(filePath+fileNames.get(i)+".csv");//get enven Entry list
				List<CarrierEntity> c = e2c.convert2Carrier(eventEntry);
				all.WriteCommon(c,fileNames.get(i));
				System.out.println("     ok!");
			}
	    }
		
		System.out.println("");
		System.out.println("===============================");
		System.out.println("");
		
		//write summary
		System.out.println("Writing summary:");
		all.writeSummary();
		System.out.println("");
		System.out.println("===============================");
		System.out.println("");
		
		//write cust
		System.out.println("Writing Cust:");
        all.startWriteCust();
		int countRow =3;
        for(int i=0;i<fileNames.size();i++){
			File csv = new File(filePath+fileNames.get(i)+".csv");
			if(!csv.exists()){
				System.out.println(fileNames.get(i)+".CSV  NOT EXISTS");
				}else{
				System.out.print(fileNames.get(i)+"......");
					List<EventEntity> eventEntry =   entry.getRecords(filePath+fileNames.get(i)+".csv");//get enven Entry list
					List<CarrierEntity> c = e2c.convert2Carrier(eventEntry);
					countRow = all.WriteCustView(c,countRow);
				System.out.println("     ok!");
				}
        }
		
		
		//all.closeWriterCustView();
		System.out.println(">>>>>>>>>>><<<<<<<<<<<<");
	}

	/*public static void deleteOldFile(){
		String reportFolder = System.getProperty("user.dir") + File.separator + "report" +File.separator;
		File old = new File(reportFolder+"All.xlsx");
		if(old.exists()){
		     boolean del = old.delete();
		}
	}*/
	}
