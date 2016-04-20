package PdfReader;

import java.io.IOException;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

public class test {
    public static void main(String[] args) throws IOException {
        PDDocument doc = PDDocument.load("C:\\Users\\LINI8\\Desktop\\pdfRead\\222.pdf");
        PDFTextStripper stripper = new PDFTextStripper();
        System.out.println(stripper.getText(doc));
        doc.close();
    }
}
