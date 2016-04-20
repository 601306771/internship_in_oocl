package com.cargosmart.common.pdfVersion20160308;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;


import com.google.common.io.Files;

public class Test {
	public static void main(String[] args) {
		String pdfPath = "C:\\Users\\LINI8\\Desktop\\pdfTemp\\b.pdf";
		PdfControllor pc = new PdfControllor();
		String[] tableArray = pc.getPageTables(pdfPath);

		for (int i = 0; i < tableArray.length; i++) {
			System.out.println(tableArray[i]);
		}
		
		
		
	}

}
