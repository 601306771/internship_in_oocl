package com.cargosmart.test;

import java.io.IOException;
import java.util.ArrayList;

import com.cargosmart.java.comm.FileHelp;

public class FileHelpTest {
	public static void main(String[] args){
		FileHelp file = new FileHelp();
		try {
			ArrayList<ArrayList> result =  file.readBigFileCSV("D:\\Nigel\\optWorkSpace\\HLCU.csv");
			for(int i=0;i<result.size();i++){
				for(int j=0;j<result.get(i).size();j++){
					System.out.print(result.get(i).get(j)+"||");
					if(j==result.get(i).size()-1)	
						System.out.println("");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
