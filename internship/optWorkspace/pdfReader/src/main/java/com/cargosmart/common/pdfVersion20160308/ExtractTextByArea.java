package com.cargosmart.common.pdfVersion20160308;

import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.util.PDFTextStripperByArea;

import java.awt.Rectangle;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExtractTextByArea
{
    public ExtractTextByArea(){}

    
    /**
     * 目标区域的字符内容
     * @param file the path of pdf file
     * @param x_coordinate   
     * @param y_coordinate
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    public String extractTextByArea(String file ,int index , int x_coordinate, int y_coordinate, int width, int height) throws Exception
    {
   
            PDDocument document = null;
            try
            {
                document = PDDocument.load( file);
                if( document.isEncrypted() )
                {
                    try
                    {
                        document.decrypt( "" );
                    }
                    catch( Exception e )
                    {
                        System.err.println( "Error: Document is encrypted with a password." );
                        System.exit( 1 );
                    }
                }
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition( true );
                double scale = 0.5;
                int x = (int)(x_coordinate*scale);
                int y = (int)(y_coordinate*scale);
                int w = (int)(width*scale);
                int h = (int)(height*scale)+1;
                Rectangle rect = new Rectangle(x,y,w,h);//87, 101, 70, 10
                stripper.addRegion( "class1", rect );
                List allPages = document.getDocumentCatalog().getAllPages();
                PDPage firstPage = (PDPage)allPages.get( index );
//                PDRectangle mediaBox = firstPage.getMediaBox();//获得pdf的大小
//                System.out.println("Width:"+mediaBox.getWidth()+" , Hight:" + mediaBox.getHeight());
//                System.out.println( "Text in the area:" + rect );
//                System.out.println( stripper.getTextForRegion( "class1" ) );
//                String result = "Width:"+mediaBox.getWidth()+" , Hight:" + mediaBox.getHeight() + "/r/t" 
//                			+ "Text in the area:" + rect + "/r/t"
//                			+ stripper.getTextForRegion( "class1" );
                stripper.extractRegions( firstPage );
                String result = stripper.getTextForRegion( "class1" );
                return replaceBlank(result);

            }
            finally
            {
                if( document != null )
                {
                    document.close();
                }
            }
    }
    
    
    public  String replaceBlank(String str) {
		  String dest = "";
		  if (str!=null) {
		   Pattern p = Pattern.compile("\\|\t|\r|\n");
		   Matcher m = p.matcher(str);
		   dest = m.replaceAll("");
		  }
		  return dest;
	}

}
