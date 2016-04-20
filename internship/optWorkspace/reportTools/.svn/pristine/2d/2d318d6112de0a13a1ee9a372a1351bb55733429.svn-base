package com.cargosmart.java.comm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import com.cargosmart.java.entity.CarrierEntity;
import com.cargosmart.java.entity.EventEntity;
import com.cargosmart.java.entity.StatusEntity;

public class WriteAllToExcel {
	public int receivedCount[] = new int[7];
	public int colorCount[] = new int[7];
	public int rowaccess=105;//内存中缓存记录行数/*keep 100 rowsin memory,exceeding rows will be flushed to disk*/
	public SXSSFWorkbook wb = new SXSSFWorkbook(rowaccess); 
	public Sheet sh;
	public Properties config;
	public WriteRules rules;
	public XSSFCellStyle styleYellow = (XSSFCellStyle) wb.createCellStyle();
	public XSSFCellStyle stylePink = (XSSFCellStyle) wb.createCellStyle();
	public Map<String,StringBuffer> summary = new TreeMap<String,StringBuffer>();
	/**
	 * initialize
	 */
	public WriteAllToExcel(){
        readConfig();
        rules = new  WriteRules();
        styleYellow.setFillPattern(XSSFCellStyle.FINE_DOTS );
		styleYellow.setFillForegroundColor(new XSSFColor(java.awt.Color.YELLOW ));
		styleYellow.setFillBackgroundColor(new XSSFColor(java.awt.Color.YELLOW));
		
		stylePink.setFillPattern(XSSFCellStyle.FINE_DOTS );
		stylePink.setFillForegroundColor(new XSSFColor(java.awt.Color.PINK ));
		stylePink.setFillBackgroundColor(new XSSFColor(java.awt.Color.PINK));
    	
		
	}
	
	/**
	 * set common sheet Title
	 * @param wb
	 * @param sh
	 */
	public void commonTitleSet(SXSSFWorkbook wb,Sheet sh){
		   //styles
		   
		   XSSFCellStyle borderRight =  (XSSFCellStyle) wb.createCellStyle();   
	       borderRight.setBorderRight(HSSFCellStyle.BORDER_THIN);
	       XSSFCellStyle borderBottom =  (XSSFCellStyle) wb.createCellStyle(); 
	       borderBottom.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	       XSSFCellStyle align =  (XSSFCellStyle) wb.createCellStyle();
	       align.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	       
	       SXSSFCell cell;

	       Row titleRow1 = sh.createRow(0);
	       Row titleRow2 = sh.createRow(1);
		   Row titleRow3 = sh.createRow(2);
		   titleRow3.setHeight((short)800);
		   CellRangeAddress region = new CellRangeAddress(0, 0, (short) 7, (short) 8); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列      
		   sh.addMergedRegion(region);
		  
		   //Border bold
		   //row1
		   cell = (SXSSFCell) titleRow1.createCell(4);
		   cell.setCellStyle(borderRight);
		   cell = (SXSSFCell) titleRow1.createCell(6);
		   cell.setCellStyle(borderRight);
		   cell = (SXSSFCell) titleRow1.createCell(7);
		   cell.setCellValue("Route");
		   cell.setCellStyle(align);
		   cell = (SXSSFCell) titleRow1.createCell(8);
		   cell.setCellStyle(borderRight);
		   cell = (SXSSFCell) titleRow1.createCell(9);
		   cell.setCellStyle(borderRight);
		   cell = (SXSSFCell) titleRow1.createCell(10);
		   cell.setCellStyle(borderRight);
		   cell = (SXSSFCell) titleRow1.createCell(11);
		   cell.setCellValue("CT");
		   cell.setCellStyle(borderRight);
		   cell.setCellStyle(align);
		   
		   //row2
		   // Set Cell Style
		   XSSFCellStyle cellStyle =  (XSSFCellStyle) wb.createCellStyle();
	       cellStyle.setWrapText(true);		// Set Auto Wrap
	       cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	       cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
	       // Set Font Style
	       XSSFFont font = (XSSFFont) wb.createFont();
	       font.setBoldweight((short) 1);
	       font.setFontHeightInPoints((short) 9);
		   
	       cellStyle.setFont(font);
	       
	       cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		   
		   titleRow2.setHeightInPoints(35.0f);
		   cell = (SXSSFCell) titleRow2.createCell(4);
		   cell.setCellStyle(borderRight);
		   cell = (SXSSFCell) titleRow2.createCell(6);
		   cell.setCellStyle(borderRight);
		   cell = (SXSSFCell) titleRow2.createCell(8);
		   cell.setCellStyle(borderRight);
		   cell = (SXSSFCell) titleRow2.createCell(9);
		   cell.setCellStyle(borderRight);
		   cell = (SXSSFCell) titleRow2.createCell(10);
		   cell.setCellStyle(borderRight);
		   cell = (SXSSFCell) titleRow2.createCell(11);
		   cell.setCellStyle(borderRight);
		     
	       cell = (SXSSFCell) titleRow2.createCell(12);
		   cell.setCellValue(new XSSFRichTextString("EE / CS010 \r/ cntr_emp_pickup"));
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(13);
		   cell.setCellValue("EE / CS010 \r/ cntr_emp_pickup");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(14);
		   cell.setCellValue("AE / CS065 \r/ cntr_load_at_port");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(15);
		   cell.setCellValue("AE / CS065 \r/ cntr_load_at_port");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(16);
		   cell.setCellValue("VD / CS075 \r/ vsl_departure");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(17);
		   cell.setCellValue("VD / CS075 \r/ vsl_departure");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(18);
		   cell.setCellValue("VA / CS115 \r/ vsl_arrived");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(19);
		   cell.setCellValue("VA / CS115 \r/ vsl_arrived");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(20);
		   cell.setCellValue("UV / CS914 \r/ cntr_discharged");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(21);
		   cell.setCellValue("UV / CS914 \r/ cntr_discharged");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(22);
		   cell.setCellValue("OA / CS138 \r/ cntr_departed_from_fcl");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(23);
		   cell.setCellValue("OA / CS138 \r/ cntr_departed_from_fcl");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(24);
		   cell.setCellValue("RD / CS210 \r/ cntr_emp_return");
		   cell.setCellStyle(cellStyle);
		   
		   cell = (SXSSFCell) titleRow2.createCell(25);
		   cell.setCellValue("RD / CS210 \r/ cntr_emp_return");
		   cell.setCellStyle(cellStyle);
		   
		   //row3
		   
		   // Set Column Title Color
		   XSSFCellStyle cell3Style =  (XSSFCellStyle) wb.createCellStyle();
	       cell3Style.setFillPattern(XSSFCellStyle.FINE_DOTS );
	       cell3Style.setFillForegroundColor(new XSSFColor(java.awt.Color.LIGHT_GRAY));
	       cell3Style.setFillBackgroundColor(new XSSFColor(java.awt.Color.LIGHT_GRAY));
	       
	       cell3Style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	       cell3Style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	       
		   cell = (SXSSFCell) titleRow3.createCell(0);
		   cell.setCellValue("Provider");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(1);
		   cell.setCellValue("Carrier");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(2);
		   cell.setCellValue("BK Number");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(3);
		   cell.setCellValue("CNTR_Number");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(4);
		   cell.setCellValue("CCC");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(6);
		   cell.setCellValue("Company");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(8);
		   cell.setCellValue("ETA");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(9);
		   cell.setCellValue("ATD");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(10);
		   cell.setCellValue("ATA");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(11);
		   cell.setCellValue("Last CT Msg TStamp");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(13);
		   cell.setCellValue("Event Date");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(15);
		   cell.setCellValue("Event Date");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(17);
		   cell.setCellValue("Event Date");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(19);
		   cell.setCellValue("Event Date");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(21);
		   cell.setCellValue("Event Date");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(23);
		   cell.setCellValue("Event Date");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(25);
		   cell.setCellValue("Event Date");
		   cell.setCellStyle(cell3Style);
		   
		   cell = (SXSSFCell) titleRow3.createCell(5);
		   cell.setCellValue("LOA on CT");
		   cell.setCellStyle(cell3Style);
		   
		   cell = (SXSSFCell) titleRow3.createCell(7);
		   cell.setCellValue("ETD");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(12);
		   cell.setCellValue("Msg TStamp");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(14);
		   cell.setCellValue("Msg TStamp");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(16);
		   cell.setCellValue("Msg TStamp");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(18);
		   cell.setCellValue("Msg TStamp");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(20);
		   cell.setCellValue("Msg TStamp");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(22);
		   cell.setCellValue("Msg TStamp");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(24);
		   cell.setCellValue("Msg TStamp");
		   cell.setCellStyle(cell3Style);

	}
	
	/**
	 * set custView title
	 * @param wb
	 * @param sh
	 */
	public void custViewTitleSet(SXSSFWorkbook wb,Sheet sh){
		   //styles
		   XSSFCellStyle border_thin =  (XSSFCellStyle) wb.createCellStyle();   
	       border_thin.setBorderRight(HSSFCellStyle.BORDER_THIN);
	       XSSFCellStyle borderBottom =  (XSSFCellStyle) wb.createCellStyle(); 
	       borderBottom.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	       XSSFCellStyle align =  (XSSFCellStyle) wb.createCellStyle();
	       align.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	       SXSSFCell cell;
	        
	       Row titleRow1 = sh.createRow(0);
	       Row titleRow2 = sh.createRow(1);
		   Row titleRow3 = sh.createRow(2);
		   titleRow3.setHeight((short)800);
		   CellRangeAddress region1 = new CellRangeAddress(0, 0, (short) 7, (short) 8); //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列      
		   sh.addMergedRegion(region1);
		  
		  
		   //Border bold
		   //row1
		   cell = (SXSSFCell) titleRow1.createCell(4);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow1.createCell(6);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow1.createCell(7);
		   cell.setCellValue("Route");
		   cell.setCellStyle(align);
		   cell = (SXSSFCell) titleRow1.createCell(8);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow1.createCell(9);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow1.createCell(10);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow1.createCell(11);
		   cell.setCellValue("CT");
		   cell.setCellStyle(border_thin);
		   cell.setCellStyle(align);
		   cell = (SXSSFCell) titleRow1.createCell(12);
		   cell.setCellStyle(border_thin);
		   //row2
		   cell = (SXSSFCell) titleRow2.createCell(4);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow2.createCell(6);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow2.createCell(8);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow2.createCell(9);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow2.createCell(10);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow2.createCell(11);
		   cell.setCellStyle(border_thin);
		   cell = (SXSSFCell) titleRow2.createCell(12);
		   cell.setCellStyle(border_thin);
		   
		   XSSFCellStyle cell3Style =  (XSSFCellStyle) wb.createCellStyle();
		   cell3Style.setFillPattern(XSSFCellStyle.FINE_DOTS );
		   cell3Style.setFillForegroundColor(new XSSFColor(java.awt.Color.LIGHT_GRAY));
		   cell3Style.setFillBackgroundColor(new XSSFColor(java.awt.Color.LIGHT_GRAY));
		       
		   cell3Style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		   cell3Style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		   
		   //row3
		   cell = (SXSSFCell) titleRow3.createCell(0);
		   cell.setCellValue("Provider");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(1);
		   cell.setCellValue("Carrier");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(2);
		   cell.setCellValue("BK Number");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(3);
		   cell.setCellValue("CNTR_Number");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(4);
		   cell.setCellValue("CCC");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(6);
		   cell.setCellValue("Company");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(8);
		   cell.setCellValue("ETA");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(9);
		   cell.setCellValue("ATD");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(10);
		   cell.setCellValue("ATA");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(11);
		   cell.setCellValue("Last CT Msg TStamp");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(12);
		   cell.setCellValue("Event");
		   cell.setCellStyle(cell3Style);
		   
		   
		   cell = (SXSSFCell) titleRow3.createCell(5);
		   cell.setCellValue("LOA on CT");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(7);
		   cell.setCellValue("ETD");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(13);
		   cell.setCellValue("Event Status");
		   cell.setCellStyle(cell3Style);
		   cell = (SXSSFCell) titleRow3.createCell(14);
		   cell.setCellValue("FileName");
		   cell.setCellStyle(cell3Style);
		   
		   //	add new 3 cols Jiaxin - 2015/12/24
		   cell = (SXSSFCell) titleRow3.createCell(15);
		   cell.setCellValue("B2B_RECEIVE_STATUS");
		   cell.setCellStyle(cell3Style);
		   
		   cell = (SXSSFCell) titleRow3.createCell(16);
		   cell.setCellValue("B2B_PROCESS_STATUS");
		   cell.setCellStyle(cell3Style);
		   
		   cell = (SXSSFCell) titleRow3.createCell(17);
		   cell.setCellValue("B2B_PROCESS_DESC");
		   cell.setCellStyle(cell3Style);
	}
	
	/**write summary sheet's title
	 * 
	 */
	public void summaryTitleSet(){
		 XSSFCellStyle cell3Style =  (XSSFCellStyle) wb.createCellStyle();
	     cell3Style.setFillPattern(XSSFCellStyle.FINE_DOTS );
	     cell3Style.setFillForegroundColor(new XSSFColor(java.awt.Color.LIGHT_GRAY));
	     cell3Style.setFillBackgroundColor(new XSSFColor(java.awt.Color.LIGHT_GRAY));
	       
	     cell3Style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	     cell3Style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	     
		 Row titleRow = sh.createRow(0);
		 SXSSFCell cell;
		 
		 cell = (SXSSFCell) titleRow.createCell(0);
		 cell.setCellValue("SCAC");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(1);
		 cell.setCellValue("EE (received)");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(2);
		 cell.setCellValue("EE (expected)"); 
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(3);
		 cell.setCellValue("EE (%)");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(4);
		 cell.setCellValue("AE (received)");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(5);
		 cell.setCellValue("AE (expected)"); 
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(6);
		 cell.setCellValue("AE (%)");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(7);
		 cell.setCellValue("VD (received)");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(8);
		 cell.setCellValue("VD (expected)"); 
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(9);
		 cell.setCellValue("VD (%)");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(10);
		 cell.setCellValue("VA (received)");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(11);
		 cell.setCellValue("VA (expected)"); 
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(12);
		 cell.setCellValue("VA (%)");
		 cell.setCellStyle(cell3Style);

		 cell = (SXSSFCell) titleRow.createCell(13);
		 cell.setCellValue("UV (received)");
		 cell.setCellStyle(cell3Style);
		 cell = (SXSSFCell) titleRow.createCell(14);
		 cell.setCellValue("UV (expected)"); 
		 cell.setCellStyle(cell3Style);
		 cell = (SXSSFCell) titleRow.createCell(15);
		 cell.setCellValue("UV (%)");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(16);
		 cell.setCellValue("OA (received)");
		 cell.setCellStyle(cell3Style);
		 cell = (SXSSFCell) titleRow.createCell(17);
		 cell.setCellValue("OA (expected)"); 
		 cell.setCellStyle(cell3Style);
		 cell = (SXSSFCell) titleRow.createCell(18);
		 cell.setCellValue("OA (%)");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(19);
		 cell.setCellValue("RD (received)");
		 cell.setCellStyle(cell3Style);
		 cell = (SXSSFCell) titleRow.createCell(20);
		 cell.setCellValue("RD (expected)"); 
		 cell.setCellStyle(cell3Style);
		 cell = (SXSSFCell) titleRow.createCell(21);
		 cell.setCellValue("RD (%)");
		 cell.setCellStyle(cell3Style);
		 
		 cell = (SXSSFCell) titleRow.createCell(22);
		 cell.setCellValue("Total (received)"); 
		 cell.setCellStyle(cell3Style);
		 cell = (SXSSFCell) titleRow.createCell(23);
		 cell.setCellValue("Total (expected)"); 
		 cell.setCellStyle(cell3Style);
		 cell = (SXSSFCell) titleRow.createCell(24);
		 cell.setCellValue("Total(%)");
		 cell.setCellStyle(cell3Style);
	}
	
	/**
	 * read config properties
	 */
	public void readConfig(){
		config = new Properties(); 
		InputStream configpath = Object.class.getResourceAsStream("/config/Common-StatusLoc.properties"); 
		try {
			config.load(configpath); 
			configpath.close();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}
	
	/**
	 * set custView sheet
	 */
	public void startWriteCust(){
		sh = wb.createSheet("CustomerView");
		 custViewTitleSet(wb,sh);
	}
	
	/**
	 * Write data to CustView
	 * @param carrierEntities    
	 * 			input carrier list
	 * @param countRow		
	 * 			input rowIndex
	 * @return
	 */
	public int WriteCustView(List<CarrierEntity> carrierEntities,int countRow) {
      
       try{
					for(int k=0;k<carrierEntities.size();k++){
						if(rules.isAbleToWriteRow(carrierEntities.get(k))==true){
							
							createCustomerRowCell(sh.createRow(countRow), 	"EE", carrierEntities.get(k));
							createCustomerRowCell(sh.createRow(countRow+1), "AE", carrierEntities.get(k));
							createCustomerRowCell(sh.createRow(countRow+2), "VD", carrierEntities.get(k));
							createCustomerRowCell(sh.createRow(countRow+3), "VA", carrierEntities.get(k));
							createCustomerRowCell(sh.createRow(countRow+4), "UV", carrierEntities.get(k));
							createCustomerRowCell(sh.createRow(countRow+5), "OA", carrierEntities.get(k));
							createCustomerRowCell(sh.createRow(countRow+6), "RD", carrierEntities.get(k));
		
							countRow=countRow+7;
	
							//每当行数达到设置的值就刷新数据到硬盘,以清理内存
							if(countRow%rowaccess==0){
								((SXSSFSheet)sh).flushRows();
							}
						}
					}
			
       } catch(Exception e) {
           e.printStackTrace();
       }
      // System.out.println(countRow);
       return countRow;
	}
	

	/**
	 * Write data to WriteCommon
	 * @param carrierEntities
	 * 			 input carrier list     
	 * @param sheetName
	 * 			 input sheetName you will create
	 * @return
	 */
	
	public int WriteCommon(List<CarrierEntity> carrierEntities,String sheetName) {
	   for(int i=0;i<7;i++){
		   receivedCount[i] = 0;
		   colorCount[i] = 0;
	   }
	   int countRow = 3;
	   sh = wb.createSheet(sheetName);
	   commonTitleSet(wb,sh);
	   int printIndex=0;
       try{
					for(int k=0;k<carrierEntities.size();k++){
						if(rules.isAbleToWriteRow(carrierEntities.get(k))==true){	
							
							Row row = sh.createRow(countRow);
							
							createRow_part123(row, carrierEntities.get(k));
							
							Map<String,Boolean> missing = rules.missingRules(
									carrierEntities.get(k).part3.getETD(), 
									carrierEntities.get(k).part3.getETA(), 
									carrierEntities.get(k).part3.getATD(),
									carrierEntities.get(k).part3.getATA()
									);
							Map<String,Boolean> coloring = rules.coloringRules(carrierEntities.get(k));

							CarrierEntity.GeneralPart4 ee = carrierEntities.get(k).part4.get("EE");							
							CarrierEntity.GeneralPart4 ae = carrierEntities.get(k).part4.get("AE");
							CarrierEntity.GeneralPart4 vd = carrierEntities.get(k).part4.get("VD");
							CarrierEntity.GeneralPart4 va = carrierEntities.get(k).part4.get("VA");
							CarrierEntity.GeneralPart4 uv = carrierEntities.get(k).part4.get("UV");
							CarrierEntity.GeneralPart4 oa = carrierEntities.get(k).part4.get("OA");
							CarrierEntity.GeneralPart4 rd = carrierEntities.get(k).part4.get("RD");
							
							followRule(row,coloring, missing,ee,"ee",0);
							followRule(row,coloring, missing,ae,"ae",1);
							followRule(row,coloring, missing,vd,"vd",2);
							followRule(row,coloring, missing,va,"va",3);
							followRule(row,coloring, missing,uv,"uv",4);
							followRule(row,coloring, missing,oa,"oa",5);
							followRule(row,coloring, missing,rd,"rd",6);

							countRow++;
	
							//每当行数达到设置的值就刷新数据到硬盘,以清理内存
							if(countRow%rowaccess==0){
								((SXSSFSheet)sh).flushRows();
							}
						}	
					}
			
       } catch(Exception e) {
           e.printStackTrace();
       }
       
       //counts
       StringBuffer sum = new StringBuffer();
       for(int i=0;i<colorCount.length;i++){
    	   printIndex+=colorCount[i];
       }
	   
	   int count=0;
	   for(int i=0;i<receivedCount.length;i++){
			if((receivedCount[i]+colorCount[i])==0){
				colorCount[i]=1;
			}		
	        int expected = receivedCount[i]+colorCount[i]; 		
			int received = receivedCount[i];
			String percent = getPercent(received,expected);
			sum.append(received+","+expected+","+percent+",");
			count+=receivedCount[i];
	   }
	   
	   int totleReceived = count;
	   int totleExpected = count+printIndex;
	   String totlePercent = getPercent(totleReceived,totleExpected);
	   sum.append(totleReceived+","+totleExpected+","+totlePercent);
	   summary.put(sheetName,sum);
	   
       return countRow;
	}
	
	/**
	 * write summary sheet
	 */
	public void writeSummary(){
		 int rowIndex = 1;
		 sh = wb.createSheet("summary");
		 summaryTitleSet();
		 for(String key : summary.keySet()){
			String str = summary.get(key).toString();
	  		String sumStr[] = str.split(",");
	  		Row row = sh.createRow(rowIndex);
	  		row.createCell(0).setCellValue(key);
	  		for(int k=0;k<sumStr.length;k++){
	  			row.createCell(k+1).setCellValue(Double.parseDouble(sumStr[k]));
	  	    }
	  		rowIndex++;
		 }
	}
	
	
	
	public void closeWriterCustView(String outputPath){
		 /*写数据到文件中*/
	    FileOutputStream os;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String filePath = outputPath+"All"+sdf.format(new java.util.Date())+".xlsx";
			os = new FileOutputStream(filePath);
			wb.write(os);
		    os.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}  
	}

	/**
	 * java 获取百分比
	 * @param num1
	 * @param num2
	 * @return
	 */
	public String getPercent(int num1,int num2){  
		   NumberFormat numberFormat = NumberFormat.getInstance();  
	       numberFormat.setMaximumFractionDigits(3);  
	       String result = numberFormat.format((float) num1 / (float) num2 * 100);  
		   return result;  
	}

	/**
	 * @author LUJI
	 * */
	void createCustomerRowCell(Row row, String name, CarrierEntity ce){	
		createRow_part123(row, ce);
		row.createCell(12).setCellValue(name);
		createEventStatusCell(row, name, ce);
		createFilenameAndB2bCell(row, name, ce);
	}
	

	void createRow_part123(Row row, CarrierEntity ce){
		row.createCell(Integer.parseInt((String) config.getProperty("provider.col"))).setCellValue(ce.part1.getProvider());
		row.createCell(Integer.parseInt((String) config.getProperty("carrier.col"))).setCellValue(ce.part1.getCarrier());
		row.createCell(Integer.parseInt((String) config.getProperty("bk_number.col"))).setCellValue(ce.part1.getBkNumber());
		row.createCell(Integer.parseInt((String) config.getProperty("cntr_number.col"))).setCellValue(ce.part1.getCNTRNumber());
		row.createCell(Integer.parseInt((String) config.getProperty("ccc.col"))).setCellValue(ce.part1.getCCC());	
		row.createCell(Integer.parseInt((String) config.getProperty("LOA_on_CT.col"))).setCellValue(ce.part2.getLOAonCT());
		row.createCell(Integer.parseInt((String) config.getProperty("Company.col"))).setCellValue(ce.part2.getCompany());
		row.createCell(Integer.parseInt((String) config.getProperty("ETD.col"))).setCellValue(ce.part3.getETD());
		row.createCell(Integer.parseInt((String) config.getProperty("ETA.col"))).setCellValue(ce.part3.getETA());
		row.createCell(Integer.parseInt((String) config.getProperty("ATD.col"))).setCellValue(ce.part3.getATD());
		row.createCell(Integer.parseInt((String) config.getProperty("ATA.col"))).setCellValue(ce.part3.getATA());
		
		// transform CT_time format
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy KK:mm:ss a");
		Date date = new Date();
		try {
			date = format.parse(ce.part3.getLastCTMsgTStamp());
			Long ld = date.getTime();
			date = new Date(ld);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		row.createCell(Integer.parseInt((String) config.getProperty("Last_CT_Msg_TStamp.col"))).setCellValue(date.toString());
	}
	
	
	/**custView Event logic
	 * exist put :      Y
	 * coloring put:    N
	 * missing put:     not happen yet
	 */
	void createEventStatusCell(Row row, String name, CarrierEntity ce){
		Map<String,Boolean> missing = rules.missingRules(ce.part3.getETD(), ce.part3.getETA(), ce.part3.getATD(), ce.part3.getATA());
		Map<String,Boolean> coloring = rules.coloringRules(ce);
		
		if(ce.part4.get(name) == null){
			if(coloring.get(name)){	// yellow
				if(missing.get(name)){
					row.createCell(13).setCellValue("not happen yet");
				}
				else{
					row.createCell(13).setCellValue("N");
				}
			}
			else{	// pink
				row.createCell(13).setCellValue("N");
			}
		}
		else{
			row.createCell(13).setCellValue("Y");
		}
	}
	void followRule(Row row,  Map<String,Boolean> coloring, Map<String,Boolean> missing,CarrierEntity.GeneralPart4 rowname,String name,int num){
		String EE = name.toUpperCase();
		String eecol = name.toLowerCase()+".col";

		if(rowname == null){//if ee is null
			//coloring
			if(coloring.get(EE)==true){//if true, coloring Yellow,means follow rule1,judge put "not happen yet" or coloring
				int col = Integer.parseInt((String) config.getProperty(eecol));
				if(missing.get(EE)==true){//missing
					row.createCell(col).setCellValue("not happen yet");
					row.createCell(col+1).setCellValue("not happen yet");
				}
				else{
					row.createCell(col).setCellStyle(styleYellow);
					row.createCell(col+1).setCellStyle(styleYellow);
				}
			}
			else{// if false, coloring pink,means fall rule1
				int col = Integer.parseInt((String) config.getProperty(eecol));
				row.createCell(col).setCellStyle(stylePink);
				row.createCell(col+1).setCellStyle(stylePink);
			}
			colorCount[num]++;
			
		}
		else{//if not null,write to excel
			int col = Integer.parseInt((String) config.getProperty(eecol));
			row.createCell(col).setCellValue(rowname.getMsg_TStamp());
			row.createCell(col+1).setCellValue(rowname.getEventDate());
			receivedCount[num]++;
		}
	}
	void createFilenameAndB2bCell(Row row, String name, CarrierEntity ce){
		if(ce.part4.get(name) != null)
		if(new WriteRules().isNotNull(ce.part4.get(name).getFilename())){
			row.createCell(14).setCellValue(ce.part4.get(name).getFilename());
			
			row.createCell(15).setCellValue(ce.part4.get(name).getB2bReceiveStatus());
			row.createCell(16).setCellValue(ce.part4.get(name).getB2bProcessStatus());
			row.createCell(17).setCellValue(ce.part4.get(name).getB2bProcessDesc());
		}
	}
}
