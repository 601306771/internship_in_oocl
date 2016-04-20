package b2b.robot.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import b2b.robot.image.binaryzation.ImageBinaryzationService;
import b2b.robot.image.denoising.ImageDenoisingService;
import b2b.robot.image.split.ImageSplitService;
import b2b.robot.match.curve.MatchCurveService;
import b2b.robot.util.CurveArrays;

public class VerificationController {
	private static String PROPERTIES_PATH = "/b2b/robot/resouces/RepositoryConfig.properties";
	private static boolean DELETE_IMAGES = true;
	private Map<String,List<double[]>> REPOSITORY_RECORD;
	
	
	/**
	 * get charts from image
	 * @param path
	 * @return
	 */
	public String getCharts(String imgFile){
		String chartsResult = "";//save charts result
		
		
		
		//image binaryzation
		String denoisingImg = "";
		String binaryzationImg;
		ImageBinaryzationService imageBinaryzation = new ImageBinaryzationService();
		if("gray".equals(judgeImageColor(imgFile))){
			//image denoising
			ImageDenoisingService  imageDenoising = new ImageDenoisingService();
			denoisingImg = imageDenoising.removeInterferingLine(imgFile);
			binaryzationImg = imageBinaryzation.blackImageBinaryzation(denoisingImg);
		}else{
			binaryzationImg = imageBinaryzation.colourImageBinaryzation(imgFile);
		}
		
		//image split to single chart 
		ImageSplitService imageSplit = new ImageSplitService();
		String logicSingleChartImg = imageSplit.splitSingleChart2Logic(binaryzationImg);//split in logic
		List<String> singleCharsImgFile  = imageSplit.splitSingleChart2image(logicSingleChartImg);//split to different image
		
		List<String> minSingleCharsImgFile = new ArrayList<String>();
		for(int i = 0; i<singleCharsImgFile.size(); i++){
			String ifile = imageSplit.splitSingleChart2MinSize(singleCharsImgFile.get(i));//split different image to min size
			minSingleCharsImgFile.add(ifile);
		}
		
		//every single chart matching with repository
		MatchCurveService matchCurveService = new MatchCurveService();
		for(int i=0; i<minSingleCharsImgFile.size();i++){
			//get this chart image's curveArrays
			CurveArrays curveArrays = new CurveArrays(minSingleCharsImgFile.get(i));
			double arrayLeft1[] = curveArrays.getAarryLeft();
			double arrayRight1[] = curveArrays.getAarryRight();
			double arrayUp1[] = curveArrays.getAarryUp();
			double arrayDown1[] = curveArrays.getAarryDown();
			//match results from repository
			Map<Double,String> matchResults = new TreeMap<Double,String>();
			for(String key : REPOSITORY_RECORD.keySet()){
					double arrayLeft2[] = REPOSITORY_RECORD.get(key).get(0);
					double arrayRight2[] = REPOSITORY_RECORD.get(key).get(1);
					double arrayUp2[] = REPOSITORY_RECORD.get(key).get(2);
					double arrayDown2[] = REPOSITORY_RECORD.get(key).get(3);
					key = key.substring(0,1);
					
					Double keyMatchResult = matchCurveService.matching(arrayLeft1, arrayRight1, arrayUp1, arrayDown1, arrayLeft2, arrayRight2, arrayUp2, arrayDown2);
					if(keyMatchResult>0){
						matchResults.put(keyMatchResult,key);
					}

			}
			//find best match result
			if(matchResults.size() > 0){
				double maxResult = 0;
				for(Double key:matchResults.keySet()){
					maxResult = key;
				}
				
				if("h".equals(matchResults.get(maxResult)) || "k".equals(matchResults.get(maxResult)) || "b".equals(matchResults.get(maxResult))){
					String str = judgeHKB(minSingleCharsImgFile.get(i),matchResults.get(maxResult));
					matchResults.put(maxResult, str);
				}
				chartsResult = chartsResult +matchResults.get(maxResult);
			}
		}
		
		//delete temporary images
		if(DELETE_IMAGES){
			deleteFile(denoisingImg);
			deleteFile(binaryzationImg);
			deleteFile(logicSingleChartImg);
			for(int i=0; i<singleCharsImgFile.size(); i++){
				deleteFile(singleCharsImgFile.get(i));
			}
			for(int i=0; i<minSingleCharsImgFile.size(); i++){
//				deleteFile(minSingleCharsImgFile.get(i));
			}
		}
		
		return chartsResult;
	}
	
	
	/**
	 * initialization repository record
	 */
	public VerificationController(){
		Map<String,List<double[]>> repository_record = new HashMap<String,List<double[]>>();
		try {
			InputStream configpath =  Object.class.getResourceAsStream(PROPERTIES_PATH);
			InputStreamReader read = new InputStreamReader(configpath,"GB2312");//使用io字节流，指定encoding
			BufferedReader br = new BufferedReader(read);
			StringBuffer sb=new StringBuffer();
			String temp;
			while((temp=br.readLine())!=null){
				String record[] = temp.split("&");
				
				String key = record[0];
				List<double[]> arrayLRUD = new ArrayList<double[]>();
				
				//Left
				String arrayL[] = record[1].split(",");
				double arrayLeft[] = new double[arrayL.length]; 
				for(int i=0 ; i<arrayL.length ; i++){
					arrayLeft[i] = Double.parseDouble(arrayL[i]);
				}
				arrayLRUD.add(arrayLeft);
				
				//Right
				String arrayR[] = record[2].split(",");
				double arrayRight[] = new double[arrayR.length]; 
				for(int i=0 ; i<arrayR.length ; i++){
					arrayRight[i] = Double.parseDouble(arrayR[i]);
				}
				arrayLRUD.add(arrayRight);
				
				//Up
				String arrayU[] = record[3].split(",");
				double arrayUp[] = new double[arrayU.length]; 
				for(int i=0 ; i<arrayU.length ; i++){
					arrayUp[i] = Double.parseDouble(arrayU[i]);
				}
				arrayLRUD.add(arrayUp);
				
				//Down
				String arrayD[] = record[4].split(",");
				double arrayDown[] = new double[arrayD.length]; 
				for(int i=0 ; i<arrayD.length ; i++){
					arrayDown[i] = Double.parseDouble(arrayD[i]);
				}
				arrayLRUD.add(arrayDown);
				
				repository_record.put(key, arrayLRUD);
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.REPOSITORY_RECORD = repository_record;
	}
	
	
	
	
	/**
	 * judge chats is H or K or B
	 * @param pixel
	 * @param width
	 * @param height
	 * @param chart
	 * @return
	 */
	public String judgeHKB(String imgFile,String chart){
		//the io of images 
		File file = new File(imgFile);
		FileInputStream fis = null;
		BufferedImage bufferedImg = null;
		try {
			fis = new FileInputStream(file);
			bufferedImg = ImageIO.read(fis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int width = bufferedImg.getWidth();
		int height = bufferedImg.getHeight();
		int pixels[] = new int[width * height];
		bufferedImg.getRGB(0, 0, width, height, pixels, 0, width);
		
		//if corner has black pixel, around 3*3
		int flag_Corner = 0;
		int offsetH = -3;
		for(int i=0; i<3 ; i++){
			int offsetW = -3;
			for(int j=0; j<3; j++){
				if(pixels[width + offsetW + (height + offsetH) * width] != -1){
					flag_Corner = 1;
				}
				offsetW++;
			}
			offsetH++;
		}
		
		int flag_right_centre = 0;
		int startHeight = height/2 - 3;
		for(int i=0 ; i<8 ; i++){
			if(pixels[width -1 + (startHeight + i) * width] != -1){
				flag_right_centre = 1;
			}
		}
		
		if(flag_right_centre != 1 /*&& flag_Corner ==1*/){
			chart = "k";
		}
		if(flag_right_centre == 1 && flag_Corner ==1){
			chart = "h";
		}
		if(flag_right_centre == 1 && flag_Corner !=1){
			chart = "b";
		}
		
		
		return chart;
	}
	
	/**
	 * judge image color
	 * @param imgFile
	 *            img path
	 * @return
	 */
	public  String judgeImageColor(String imgFile) {
		File file = new File(imgFile);
		FileInputStream fis = null;
		BufferedImage bufferedImg = null;
		try {
			fis = new FileInputStream(file);
			bufferedImg = ImageIO.read(fis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int width = bufferedImg.getWidth();
		int height = bufferedImg.getHeight();

		int pixels[] = new int[width * height];
		bufferedImg.getRGB(0, 0, width, height, pixels, 0, width);

		for (int i = 0; i < width * height; i++) {
			int r = (pixels[i] >> 16) & 0xff;
			int g = (pixels[i] >> 8) & 0xff;
			int b = (pixels[i]) & 0xff;

			if (r == g && r == b ) {
				
			} else {
				return "colours";
			}
		}
		return "gray";
	}
	
	
	/**
	 * delete File
	 * @param filePath
	 */
	public void deleteFile(String filePath){
		File delfile = new File(filePath);
		if (delfile.exists()) {
			delfile.delete();
		}
	}
	
}
