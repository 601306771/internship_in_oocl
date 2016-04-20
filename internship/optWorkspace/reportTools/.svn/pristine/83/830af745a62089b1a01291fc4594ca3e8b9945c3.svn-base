package com.cargosmart.test;

import java.util.List;

import com.cargosmart.java.comm.Cvs2EventEntry;
import com.cargosmart.java.entry.EventEntity;

public class Cvs2eventTest {
	public static void main(String[] args){
		Cvs2EventEntry c2e = new Cvs2EventEntry(); 
		List<EventEntity> listE = c2e.getRecords("D:\\Nigel\\optWorkSpace\\HLCU");
		for(int i = 0;i<listE.size();i++){
			System.out.println(listE.get(i));
			System.out.println("");
		}
		System.out.println(listE.size());
	}
}
