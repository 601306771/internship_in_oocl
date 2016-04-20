package lxy.pdfReader;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdfviewer.MapEntry;
import org.apache.pdfbox.pdfviewer.PDFTreeModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.util.PDFTextStripper;
import org.w3c.dom.Document;

public class pdfTree {
	public static void main(String[] args) throws IOException {
        PDDocument doc = PDDocument.load("C:\\Users\\LINI8\\Desktop\\pdfRead\\a.pdf");
        PDDocumentOutline root = doc.getDocumentCatalog().getDocumentOutline();
        PDOutlineItem item = root.getFirstChild();
        
        while(item != null){
        	System.out.print("itme:" + item.getTitle());
        	PDOutlineItem child = item.getFirstChild();
        	while(item != null){
            	System.out.print("itme:" + item.getTitle());
            	 child = item.getNextSibling();
            }
        	item =item.getNextSibling();
        }
//        PDFTextStripper stripper = new PDFTextStripper();
//        System.out.println(stripper.getText(doc));
        doc.close();
    }

}
