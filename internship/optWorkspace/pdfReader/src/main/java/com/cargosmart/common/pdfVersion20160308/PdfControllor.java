package com.cargosmart.common.pdfVersion20160308;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PdfControllor {
	private Properties config;
	
	public String[] getPageTables(String pdfPath) {
		//initialize config properties , if config not exist
		InitializeConfig ic = new InitializeConfig();
		ic.initializeConfig();

		// 获得图片 每一页为一张图片  
		//extract images for pdf , one page to one image
		ExtractImages ei = new ExtractImages();
		int pageCount = 0;
		try {
			pageCount = ei.extractImages(pdfPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		
		String[] tableArray = new String[pageCount];
		String[] imgs = new String[pageCount];

		ImageUtil img = new ImageUtil();
		// 获取每一页（一张图）的表格  
		//get tables from one page
		for (int index = 0; index < pageCount; index++) {

			File file = new File(pdfPath);
			String pngPath = file.getParent() + "\\" + index + ".png";
			imgs[index] = pngPath;
			// PNG 获得区域  
			//get td areas from png , format is html
			img.convert2gray(pngPath);
			img.convert2black(pngPath.replace(".png", "Gray.png"));
			String table = img.convert2Html(pngPath.replace(".png", "Gray.png"));

			// 获取所有 位置 数据 
			//read position data from td areas 
			List<String> datasStr = new ArrayList<String>();
			String[] datas = table.split("\\[");
			for (int i = 0; i < datas.length; i++) {
				if (datas[i].contains("]")) {
					String[] data = datas[i].split("\\]");
					for (int j = 0; j < data.length; j++) {
						if (data[j].contains(",")) {
							datasStr.add(data[j]);
						}
					}
				}
			}

			// 替换位置数据 为 目标数据  
			//replace position data to information from pdf , base on position data
			ExtractTextByArea etext = new ExtractTextByArea();
			for (int i = 0; i < datasStr.size(); i++) {
				int x = Integer.parseInt(datasStr.get(i).split(",")[0]);
				int y = Integer.parseInt(datasStr.get(i).split(",")[1]);
				int w = Integer.parseInt(datasStr.get(i).split(",")[2]);
				int h = Integer.parseInt(datasStr.get(i).split(",")[3]);
				String td = null;
				try {
					td = etext.extractTextByArea(pdfPath, index, x, y, w, h);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String oldTd = "\\[" + datasStr.get(i) + "]";
				table = table.replaceAll(oldTd, td);
			}
			
			//add one table in table array
			tableArray[index] = table;
		}
		
		readConfig();
		
		// 删除读取过的 png文件,如果设定为ture
		if("TRUE".equals(config.getProperty("DELETE_PNG"))){
			for (int i = 0; i < imgs.length; i++) {
				File oldSonfile = new File(imgs[i]);
				File oldSonfileGray = new File(imgs[i].replace(".png", "Gray.png"));
				if (oldSonfile.exists()) {
					oldSonfile.delete();
				}
				if (oldSonfileGray.exists()) {
					oldSonfileGray.delete();
				}
			}
		}
		
		// delete temporary pdf files 删除读取完成的pdf文件
		if("TRUE".equals(config.getProperty("DELETE_PDF"))){
			File oldSonfile = new File(pdfPath);
			if (oldSonfile.exists()) {
				oldSonfile.delete();
			}
		}
		
		// 删除文件夹
		if("TRUE".equals(config.getProperty("DELETE_FOLDER"))){
			File file = new File(pdfPath);
			String oldpath = file.getParent();
			File oldfile = new File(oldpath);
			if (oldfile.exists()) {
				oldfile.delete();
			}
		}
		
		return tableArray;
	}
	
	
	/**
	 * read config properties
	 */
	public void readConfig(){
		String filePath = (System.getProperty("user.dir") + "/").replace("\\", "/");  
		String CONFIG_PATH = filePath + "PdfImgConfig.properties";
		config = new Properties(); 
		try {
			InputStream configpath = new FileInputStream(CONFIG_PATH);
			config.load(configpath); 
			configpath.close();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}

}
