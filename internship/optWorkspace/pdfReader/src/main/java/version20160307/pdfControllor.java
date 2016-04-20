package version20160307;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class pdfControllor {
	public String[] getPageTables(String pdfPath){
		
		//获得图片 每一页为一张图片
		ExtractImages ei = new ExtractImages();
		int pageCount = 0;
		try {
			pageCount = ei.extractImages(pdfPath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String[] tableArray = new String[pageCount];
		
		//获取每一页（一张图）的表格
		for(int index=0 ; index<pageCount ; index++){
			
			File file=new File(pdfPath);
	        String pngPath = file.getParent() + "\\" + index + ".png";
			//PNG 获得区域
	        ImageUtil img = new  ImageUtil();
	        img.convert2gray(pngPath);
	        img.convert2black(pngPath);
	        String table = img.convert2Html(pngPath); 
	        
	        //获取所有 位置 数据
	        List<String> datasStr = new ArrayList<String>();
	        String[] datas = table.split("\\[");
	        for(int i = 0 ; i < datas.length ; i++ ){
	        	if(datas[i].contains("]")){
	        		String[] data  = datas[i].split("\\]");
	        		for(int j = 0; j < data.length ; j++){
	        			if(data[j].contains(",")){
	        				datasStr.add(data[j]);
	        			}
	        		}
	        	}
	        }
	        
	        //替换位置数据 为 目标数据
	        ExtractTextByArea etext = new ExtractTextByArea();
	        for(int i = 0 ; i < datasStr.size() ; i++ ){
	        	int x = Integer.parseInt(datasStr.get(i).split(",")[0]);
	        	int y = Integer.parseInt(datasStr.get(i).split(",")[1]);
	        	int w = Integer.parseInt(datasStr.get(i).split(",")[2]);
	        	int h = Integer.parseInt(datasStr.get(i).split(",")[3]);
	        	String td = null;
	        	try {
					td = etext.extractTextByArea(pdfPath,index,x,y,w,h); 
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	String oldTd = "\\[" + datasStr.get(i) + "]" ;
	        	table = table.replaceAll(oldTd, td);
	        }
	        
	        
	        System.out.println(table);
		}
		
		return tableArray;
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args){
		String pdfPath = "C:\\Users\\LINI8\\Desktop\\pdfRead\\c.pdf";
		String pngPath = "C:/Users/LINI8/Desktop/pdfRead/0.png";
		
		
		
		//获得图片
		ExtractImages ei = new ExtractImages();
		try {
			ei.extractImages(pdfPath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//PNG 获得区域
        ImageUtil img = new  ImageUtil();
        img.convert2gray(pngPath);
        img.convert2black(pngPath);
        String table = img.convert2Html(pngPath); 

        
        
        //获取所有 位置 数据
        List<String> datasStr = new ArrayList<String>();
        String[] datas = table.split("\\[");
        for(int i = 0 ; i < datas.length ; i++ ){
        	if(datas[i].contains("]")){
        		String[] data  = datas[i].split("\\]");
        		for(int j = 0; j < data.length ; j++){
        			if(data[j].contains(",")){
        				datasStr.add(data[j]);
        			}
        		}
        	}
        }
        
        //替换位置数据 为 目标数据
        ExtractTextByArea etext = new ExtractTextByArea();
        for(int i = 0 ; i < datasStr.size() ; i++ ){
        	int x = Integer.parseInt(datasStr.get(i).split(",")[0]);
        	int y = Integer.parseInt(datasStr.get(i).split(",")[1]);
        	int w = Integer.parseInt(datasStr.get(i).split(",")[2]);
        	int h = Integer.parseInt(datasStr.get(i).split(",")[3]);
        	String td = null;
        	try {
				td = etext.extractTextByArea(pdfPath,0,x,y,w,h); 
			} catch (Exception e) {
				e.printStackTrace();
			}
        	String oldTd = "\\[" + datasStr.get(i) + "]" ;
        	table = table.replaceAll(oldTd, td);
        }
        
        
      System.out.println(table);
        
//		 ExtractTextByArea etext = new ExtractTextByArea();
//		 try {
//			String data = etext.extractTextByArea(pdfPath,250,637,60,15);
//			System.out.println(data);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}//end main
	
}
