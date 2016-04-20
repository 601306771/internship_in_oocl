package lxy.pdfReader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

//import org.apache.commons.io.FileUtils;
import com.google.common.io.Files;

public class PrintTextLocations1 extends PDFTextStripper {
    public PrintTextLocations1() throws IOException {
        super.setSortByPosition(true);
    }

    public void getPositions(String path) throws Exception {
//    	FileInputStream input = null;
//    	URL httpurl = new URL(url);
//    	File fileTempDir = Files.createTempDir();
//    	File fileTemp = File.createTempFile(UUID.randomUUID().toString(), ".pdf", fileTempDir);
//    	FileUtils.copyURLToFile(httpurl, fileTemp);

//    	input = new FileInputStream(fileTemp); 
    	 

        PDDocument document = null;
        try {
            document = PDDocument.load(path);
            if (document.isEncrypted()) {
                document.decrypt("");
            }
            PrintTextLocations1 printer = new PrintTextLocations1();
            List allPages = document.getDocumentCatalog().getAllPages();
            for (int i =0; i < 1; i++) {
                PDPage page = (PDPage) allPages.get(i);
//                System.out.println("Processing page: " + i);
                PDStream contents = page.getContents();
                if (contents != null) {
                    printer.processStream(page, page.findResources(), page.getContents().getStream());
                }
            }
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    protected void processTextPosition(TextPosition text) {
        System.out.println("String[" + text.getXDirAdj() + "," +
                text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale=" +
                text.getXScale() + " height=" + text.getHeightDir() + " space=" +
                text.getWidthOfSpace() + " width=" +
                text.getWidthDirAdj() + "]" + text.getCharacter());
//    	System.out.println( text.getXDirAdj() + "," +
//                text.getYDirAdj()  + text.getCharacter());
        String path = "C:\\Users\\LINI8\\Desktop\\pdfRead\\position.txt";
        
        try{
        	   FileOutputStream fos=new FileOutputStream(path,true);//true表明会追加内容
        	   PrintWriter pw=new PrintWriter(fos);
        	   String value = text.getCharacter();
        	   if(" ".equals(text.getCharacter())||text.getCharacter()==null){
        		   value = "#";
        	   }
        	   pw.write("x="+text.getXDirAdj() + ",y=" +text.getYDirAdj()+",value="+value+"&");
        	   pw.flush();
         }catch(FileNotFoundException e){
        	      e.printStackTrace();
         }
    }


}