package b2b.robot.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


import b2b.robot.image.binaryzation.ImageBinaryzationService;
import b2b.robot.match.scan.ImageScanMatchService;
import b2b.robot.util.ImageMessageUtil;


public class VerificationController {
	public static int MAX_CHART_WIDTH = 40;
	public static int MIN_CHART_WIDTH = 11;
	private static String PROPERTIES_PATH = "/b2b/robot/resouces/MCCQrepositoryConfig.properties";
	private static boolean DELETE_IMAGES = true;
	private Map<String,int[]> REPOSITORY_RECORD;
	private Map<String,Integer> CHARTS_WEIGHT;
	
	/**
	 * get charts from image
	 * @param path
	 * @return
	 */
	public String getCharts(String imgFile){
		String chartsResult = "";//save charts result
		
		//binaryzation the image
		ImageBinaryzationService ibs = new ImageBinaryzationService();
		imgFile = ibs.colourImageBinaryzation(imgFile);
		
		//get coordinate axis x of the first chart pixels and last chart pixels
		ImageMessageUtil iu = new ImageMessageUtil(imgFile); 
		int firstW = iu.getFirstW();
		int lastW = iu.getLastW();
		//get this image's ：width, height and pixels
		int imgWidth = iu.getImgWidth();
		int imgHeight = iu.getImgHeight();
		int imgPixels[] = iu.getImgPixels();
		
		//The location of the scanning
		int startScanW = firstW;
		int endScanW = lastW - MIN_CHART_WIDTH + 1;
		
		//Initializes the scan class
		ImageScanMatchService is = new ImageScanMatchService();
		
		//Start scanning
		while(startScanW < endScanW){
			//two map to put the result of chart and chart message
			Map<String,Integer> chartAndWidth = new HashMap<String,Integer>();
			Map<Integer,String> weightAndchart = new TreeMap<Integer,String>();
			
			//compare with repository
			for (String keyStr : REPOSITORY_RECORD.keySet() ) {
				//get one repository record
				String key = keyStr.split(",")[0];
				int width = Integer.parseInt(keyStr.split(",")[1]);
				int height = Integer.parseInt(keyStr.split(",")[2]);
				int pixel[] =  REPOSITORY_RECORD.get(keyStr);
				
				//scanning result
				String resultStr = is.scanMatch(startScanW,imgFile,imgWidth,imgHeight,imgPixels,key,width,height,pixel);
				
				//if result exist，put into Map
				if(!"".equals(resultStr)){
					
					String chart = resultStr.split(",")[0];
					int chartWidth = Integer.parseInt(resultStr.split(",")[1]);
					int weight = CHARTS_WEIGHT.get(chart);
					
					//two map save result message(1.The characters and the corresponding width)&&(2.Weights and the corresponding characters)
					chartAndWidth.put(chart, chartWidth);
					weightAndchart.put(weight, chart);
				}
			}
			
			//Screening results
			if(weightAndchart.size()>0){//have result
				//print every single chart
				System.out.print("--- " + startScanW + " : ");
				for(String key : chartAndWidth.keySet()){
					System.out.print(key);
				}
				System.out.println();
				
				//The  character which have The biggest weight
				String thisChart = "";
				for(Integer weight : weightAndchart.keySet()){
					thisChart = weightAndchart.get(weight);
				}
				
				//The results add characters
				chartsResult = chartsResult + thisChart;
				
				//The width of the characters
				int width= chartAndWidth.get(thisChart);
				//Then, set up under a scanning spot
				startScanW = startScanW + width - 8;
				
			}else{//have not result
				
				//Then, set up under a scanning spot
				startScanW = startScanW + 1;
			}
		}
		
		System.out.println();
		
		if(DELETE_IMAGES){
			deleteFile(imgFile);
		}
		
		return chartsResult;
	}
	
	
	//Initialize the Library and weight
	public VerificationController(){
		//read repository form properties  
		Map<String,int[]> repository_record = new HashMap<String,int[]>();
		try {
			InputStream configpath =  Object.class.getResourceAsStream(PROPERTIES_PATH);
			InputStreamReader read = new InputStreamReader(configpath,"GB2312");//使用io字节流，指定encoding
			BufferedReader br = new BufferedReader(read);
			String temp;
			while((temp=br.readLine())!=null){
				String record[] = temp.split("&");
				
				String key = record[0] + "," + record[1] + "," + record[2];
				
				String array[] = record[3].split(",");
				int pixels[] = new int[array.length]; 
				for(int i=0 ; i<array.length ; i++){
					pixels[i] = Integer.parseInt(array[i]);
				}
				
				repository_record.put(key, pixels);
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.REPOSITORY_RECORD = repository_record;
		
		
		
		//set chart weight
		Map<String,Integer> weightMap = new HashMap<String,Integer>();
		weightMap.put("A", 2);
		weightMap.put("B", 5);
		weightMap.put("C", 2);
		weightMap.put("D", 4);
		weightMap.put("E", 5);
		weightMap.put("F", 4);
		weightMap.put("G", 4);
		weightMap.put("H", 3);
		weightMap.put("I", 2);
		weightMap.put("J", 2);
		weightMap.put("K", 5);
		weightMap.put("L", 2);
		weightMap.put("M", 3);
		weightMap.put("N", 2);
		weightMap.put("O", 2);
		weightMap.put("P", 5);
		weightMap.put("Q", 3);
		weightMap.put("R", 4);
		weightMap.put("S", 2);
		weightMap.put("T", 2);
		weightMap.put("U", 3);
		weightMap.put("V", 3);
		weightMap.put("W", 4);
		weightMap.put("X", 4);
		weightMap.put("Y", 2);
		weightMap.put("Z", 2);
		
		weightMap.put("a", 1);
		weightMap.put("b", 3);
		weightMap.put("c", 0);
		weightMap.put("d", 2);
		weightMap.put("e", 1);
		weightMap.put("f", 1);
		weightMap.put("g", 3);
		weightMap.put("h", 2);
		weightMap.put("i", 1);
		weightMap.put("j", 2);
		weightMap.put("k", 4);
		weightMap.put("l", 1);
		weightMap.put("m", 4);
		weightMap.put("n", 1);
		weightMap.put("o", 1);
		weightMap.put("p", 3);
		weightMap.put("q", 2);
		weightMap.put("r", 1);
		weightMap.put("s", 1);
		weightMap.put("t", 3);
		weightMap.put("u", 1);
		weightMap.put("v", 2);
		weightMap.put("w", 3);
		weightMap.put("x", 2);
		weightMap.put("y", 1);
		weightMap.put("z", 1);
		weightMap.put("0", 1);
		
		weightMap.put("1", 1);
		weightMap.put("2", 1);
		weightMap.put("3", 2);
		weightMap.put("4", 2);
		weightMap.put("5", 2);
		weightMap.put("6", 1);
		weightMap.put("7", 1);
		weightMap.put("8", 2);
		weightMap.put("9", 2);
		
		CHARTS_WEIGHT = weightMap;
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
