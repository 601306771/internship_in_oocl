package com.cargosmart.java.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.cargosmart.java.comm.Cvs2EventEntry;
import com.cargosmart.java.comm.Event2CarrierEntry;
import com.cargosmart.java.comm.PropertiesHandlers;
import com.cargosmart.java.comm.WriteAllToExcel;
import com.cargosmart.java.entity.CarrierEntity;
import com.cargosmart.java.entity.EventEntity;

public class ReportProducer {
	private String inputPath;
	private String outputPath;
	public ReportProducer(String input,String output){
		this.inputPath = input+"\\";
		this.outputPath = output+"\\";
	}
	
	public void startProducing(){
		System.out.println(inputPath);
		System.out.println(outputPath);
		PropertiesHandlers propertiesHandlers = new PropertiesHandlers();
		Map<Integer,String> fileNames = propertiesHandlers.getFileName();
	
		Cvs2EventEntry entry = new Cvs2EventEntry();
		WriteAllToExcel all = new WriteAllToExcel();
		
		//write common
		System.out.println("Writing common:");
		for(int i=0;i<fileNames.size();i++){
			File csv = new File(inputPath+fileNames.get(i)+".csv");
			if(!csv.exists()){
				System.out.println(fileNames.get(i)+".CSV  NOT EXISTS");
			}else{
				System.out.print(fileNames.get(i)+"......");
				List<EventEntity> eventEntry =   entry.getRecords(inputPath+fileNames.get(i)+".csv");//get enven Entry list
				List<CarrierEntity> carrierEntity = new Event2CarrierEntry().convert2Carrier(eventEntry);
				all.WriteCommon(carrierEntity,fileNames.get(i));
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
			File csv = new File(inputPath+fileNames.get(i)+".csv");
			if(!csv.exists()){
				System.out.println(fileNames.get(i)+".CSV  NOT EXISTS");
			}else{
				System.out.print(fileNames.get(i)+"......");
				List<EventEntity> eventEntry =   entry.getRecords(inputPath+fileNames.get(i)+".csv");//get enven Entry list
				
				List<CarrierEntity> carrierEntity = new Event2CarrierEntry().convert2Carrier(eventEntry);
				countRow = all.WriteCustView(carrierEntity,countRow);
				System.out.println("     ok!");
			}
        }
		
		all.closeWriterCustView(outputPath);
		System.out.println(">>>>>>>>>>><<<<<<<<<<<<");
	}
	
}
