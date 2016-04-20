package PdfReader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;

public class ExtractImages 
{
    public static void main(String[] args) throws IOException { 
        PDDocument doc = PDDocument.load("C:\\Users\\LINI8\\Desktop\\pdfRead\\11.pdf");
        int pageCount = doc.getPageCount(); 
        System.out.println(pageCount); 
        List pages = doc.getDocumentCatalog().getAllPages(); 
        for(int i=0;i<pages.size();i++){
            PDPage page = (PDPage)pages.get(i); 
            BufferedImage image = page.convertToImage(); 
            Iterator iter = ImageIO.getImageWritersBySuffix("jpg"); 
            ImageWriter writer = (ImageWriter)iter.next(); 
            File outFile = new File("C:\\Users\\LINI8\\Desktop\\pdfRead\\"+i+".png"); 
            FileOutputStream out = new FileOutputStream(outFile); 
            ImageOutputStream outImage = ImageIO.createImageOutputStream(out); 
            writer.setOutput(outImage); 
            writer.write(new IIOImage(image,null,null)); 
        }
        doc.close(); 
        System.out.println("over"); 
    }

}