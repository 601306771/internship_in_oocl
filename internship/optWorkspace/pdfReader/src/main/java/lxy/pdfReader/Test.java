package lxy.pdfReader;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfviewer.MapEntry;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.util.PDFTextStripper;

public class Test {
	public static void main(String[] args) throws IOException {
		String path = "C:\\Users\\LINI8\\Desktop\\pdfRead\\a.pdf";
		String url = "https://www.icl-ltd.com/media/1437/icl-sailing-schedule-21816.pdf";
		String positionFile = "C:\\Users\\LINI8\\Desktop\\pdfRead\\position.txt";
		
		File oldSonfile = new File(positionFile);
		if (oldSonfile.exists()) {
			oldSonfile.delete();
		}
		
		PrintTextLocations1 tp = new  PrintTextLocations1();
//    	Pdf2html p2h = new  Pdf2html();
//		Pdf2htmlTable p2h = new  Pdf2htmlTable();
//		Pdf2htmlTableV2 p2h = new  Pdf2htmlTableV2();
		try {
			tp.getPositions(path);
//			String html = p2h.pdfToHtml(positionFile);
//			System.out.print(html);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
