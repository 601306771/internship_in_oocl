package lxy.pdfReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

public class TextPositions  extends PDFTextStripper {
	public StringBuffer html;
    public TextPositions() throws IOException {
        super.setSortByPosition(true);
    }

    public void pdfToHtml(String path,String positionFile) throws Exception {
    	positionFile = "C:\\Users\\LINI8\\Desktop\\pdfRead\\position.txt";
    	File old = new File(positionFile);
		if(old.exists()){
		     old.delete();
		}
        PDDocument document = null;
        try {
            document = PDDocument.load(path);
            if (document.isEncrypted()) {
                document.decrypt("");
            }
            PrintTextLocations1 printer = new PrintTextLocations1();
            List allPages = document.getDocumentCatalog().getAllPages();
            for (int i =0; i < allPages.size(); i++) {
                PDPage page = (PDPage) allPages.get(i);
                System.out.println("Processing page: " + i);
                PDStream contents = page.getContents();
                if (contents != null) {
                    printer.processStream(page, page.findResources(), page.getContents().getStream());
                }
            }
        } finally {
            if (document != null) {
                document.close();
                System.out.println("----finish---");
            }
        }
    }

//    protected void processTextPosition(TextPosition text) {
//        System.out.println("String[" + text.getXDirAdj() + "," +
//                text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale=" +
//                text.getXScale() + " height=" + text.getHeightDir() + " space=" +
//                text.getWidthOfSpace() + " width=" +
//                text.getWidthDirAdj() + "]" + text.getCharacter());
//        html.append(text.getCharacter());
//    }


}