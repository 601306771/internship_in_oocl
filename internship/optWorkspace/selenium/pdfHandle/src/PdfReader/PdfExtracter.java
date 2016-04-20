package PdfReader;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdfparser.PDFParser;
import java.io.*;
import org.pdfbox.util.PDFTextStripper;
import java.util.Date;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.IOException;

/**
 * <p>
 * Title: pdf extraction
 * </p>
 * <p>
 * Description: email:chris@matrix.org.cn
 * </p>
 * <p>
 * Copyright: Matrix Copyright (c) 2003
 * </p>
 * <p>
 * Company: Matrix.org.cn
 * </p>
 * 
 * @author chris
 * @version 1.0,who use this example pls remain the declare
 */

public class PdfExtracter {

	public PdfExtracter() {
	}

	public String GetTextFromPdf(String filename) throws Exception {
		String temp = null;
		PDDocument pdfdocument = null;
		FileInputStream is = new FileInputStream(filename);
		PDFParser parser = new PDFParser(is);
		parser.parse();
		pdfdocument = parser.getPDDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(out);
		PDFTextStripper stripper = new PDFTextStripper();
		stripper.writeText(pdfdocument.getDocument(), writer);
		writer.close();
		byte[] contents = out.toByteArray();

		String ts = new String(contents);
		System.out.println("the string length is" + contents.length + "\n");
		return ts;
	}

	public static void main(String args[]) {
		PdfExtracter pf = new PdfExtracter();
		PDDocument pdfDocument = null;

		try {
			String ts = pf.GetTextFromPdf("C:\\Users\\LINI8\\Desktop\\pdfRead\\rrr.pdf");
			System.out.println(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}