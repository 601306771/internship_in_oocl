/*package b2b.robot.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import b2b.robot.image.binaryzation.ImageBinaryzationService;
import b2b.robot.image.split.ImageSplitService;
import b2b.robot.match.scan.ImageScanMatchService;

public class VerificationController {
	private static String PROPERTIES_PATH = "C:\\Users\\LINI8\\Desktop\\MCCQrepository";
	private static boolean DELETE_IMAGES = true;
	private Map<String,List<double[]>> REPOSITORY_RECORD;
	private Map<String,Integer> CHARTS_WEIGHT;
	
	public VerificationController(){
		Map<String,Integer> weightMap = new HashMap<String,Integer>();
		weightMap.put("A", 2);
		weightMap.put("B", 3);
		weightMap.put("C", 2);
		weightMap.put("D", 3);
		weightMap.put("E", 4);
		weightMap.put("F", 2);
		weightMap.put("G", 4);
		weightMap.put("H", 3);
		weightMap.put("I", 2);
		weightMap.put("J", 2);
		weightMap.put("K", 5);
		weightMap.put("L", 2);
		weightMap.put("M", 3);
		weightMap.put("N", 2);
		weightMap.put("O", 2);
		weightMap.put("P", 3);
		weightMap.put("Q", 3);
		weightMap.put("R", 3);
		weightMap.put("S", 2);
		weightMap.put("T", 2);
		weightMap.put("U", 2);
		weightMap.put("V", 3);
		weightMap.put("W", 4);
		weightMap.put("X", 4);
		weightMap.put("Y", 2);
		weightMap.put("Z", 2);
		
		weightMap.put("a", 1);
		weightMap.put("b", 2);
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
		weightMap.put("m", 2);
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
		
		this.CHARTS_WEIGHT = weightMap;
	}
	
	*//**
	 * get charts from image
	 * @param path
	 * @return
	 *//*
	public String getCharts(String imgFile){
		ImageBinaryzationService ibs = new ImageBinaryzationService();
		imgFile = ibs.colourImageBinaryzation(imgFile);
		
		
		String chartsResult = "";//save charts result
		
		ImageScanMatchService is = new ImageScanMatchService();
		Map<Integer,String> results = new TreeMap<Integer,String>();
		List<Integer> keyNumbers = new ArrayList<Integer>();
		
		File[] array = getFile(PROPERTIES_PATH);
		for (int j = 0; j < array.length; j++) {
			
			String resultStr = is.scanMatch(imgFile,array[j].getPath());
			
			if(!"".equals(resultStr)){
				int key = Integer.parseInt(resultStr.split(",")[0]);
				String value = resultStr.split(",")[1] + "," + resultStr.split(",")[2];
				
				if(results.get(key)==null){
					results.put(key,value);
					keyNumbers.add(key);
				}else{
					String oldKey = results.get(key).substring(0,results.get(key).indexOf(","));
					String newKey = value.substring(0,value.indexOf(","));
					if(CHARTS_WEIGHT.get(oldKey) < CHARTS_WEIGHT.get(newKey)){
						results.put(key,value);
						keyNumbers.add(key);
					}
				}
			}
		}
		
		//////////
		for(Integer key : results.keySet()){
			System.out.print(key + "=" + results.get(key) + " & " );
		}
		System.out.println();
		///////////
		
		Collections.sort(keyNumbers);
		
		if(keyNumbers.size() > 0){
			StringBuffer screenNumbers = new StringBuffer();
			screenNumbers.append(keyNumbers.get(0));
			for(int i=1 ; i<keyNumbers.size(); i++){
				
				if(keyNumbers.get(i)-keyNumbers.get(i-1) < 8){
					screenNumbers.append("," + keyNumbers.get(i));
				}else{
					screenNumbers.append("&" + keyNumbers.get(i));
				}
			}
			String keyNums[] = screenNumbers.toString().split("&");
			
			int startPosiont = 0;//下一个判断点
			for(int i = 0; i<keyNums.length; i++){
				
				if(keyNums.length>1){
					//把字符变成一个对比的数组
					String[] groupStr = keyNums[i].split(",");
					int groupArray[] = new int[groupStr.length];
					for(int g = 0 ; g<groupArray.length; g++){
						groupArray[g] = Integer.parseInt(groupStr[g]);
					}
					//找出对比数组中权重最大的
					List<Integer> weights =  new ArrayList<Integer>();
					for(int g = 0 ; g<groupArray.length; g++){
						if(groupArray[g] > startPosiont){
							weights.add(CHARTS_WEIGHT.get(results.get(groupArray[g]).substring(0,results.get(groupArray[g]).indexOf(","))));
						}
					}
					Collections.sort(weights);
					
					
					
					//大于对比点，而且权重等于最大权重的结果
					Map<Integer,String> thisCharts = new TreeMap<Integer,String>();
					for(int g = 0 ; g<groupArray.length; g++){
						if( groupArray[g] > startPosiont && weights.get(weights.size()-1) == CHARTS_WEIGHT.get(results.get(groupArray[g]).substring(0,results.get(groupArray[g]).indexOf(",")))){
							thisCharts.put(groupArray[g], results.get(groupArray[g]).substring(0,results.get(groupArray[g]).indexOf(",")));
						}
					}
					if(thisCharts.size()>0){//这个结果中，找出最靠左边的那个
						int firstKey = 0;
						for(Integer key  : thisCharts.keySet()){
							firstKey = key;
							break;
						}
						String thisChart = "";
						thisChart =  thisCharts.get(firstKey);
						chartsResult = chartsResult + thisChart;
						
						String value  = results.get(firstKey);
						startPosiont = firstKey + Integer.parseInt(value.substring(value.indexOf(",")+1,value.length())) - 5;
					
					}
				}else{
					int keynum = Integer.parseInt(keyNums[0]);
					if(keynum > startPosiont){
						chartsResult = chartsResult + results.get(keynum).substring(0,results.get(keynum).indexOf(","));
						String value  = results.get(keynum);
						startPosiont = keynum + Integer.parseInt(value.substring(value.indexOf(","),value.length())) - 5;
					}
				}
			}
		}
		
	
		
		
//		int oldPosition = -1;
//		Map<Integer,String> chartsResultMap =  new TreeMap<Integer,String>();
//		for(Integer position : results.keySet() ){
//			if(oldPosition != -1){
//				if(position - oldPosition <= 10 ){
//					if(CHARTS_WEIGHT.get(results.get(position)) > CHARTS_WEIGHT.get(results.get(oldPosition))){
//						chartsResult = chartsResult.substring(0,chartsResult.length()-1) + results.get(position);
//						chartsResultMap.put(oldPosition,  results.get(position));
//					}
//				}else{
// 					chartsResult = chartsResult + results.get(position);
// 					chartsResultMap.put(position,  results.get(position));
// 					oldPosition = position;
//				}
//			}else{
//				chartsResult = chartsResult + results.get(position);
//				chartsResultMap.put(position,  results.get(position));
//				oldPosition = position;
//			}
//			
//		}
//		if(chartsResult.contains("W")&&chartsResult.contains("v")){
//			chartsResult = "";
//			int oldP = -1;
//			for(Integer position : chartsResultMap.keySet()){
//				if(oldP != -1 ){
//					if(position - oldP > 12 ){
//						chartsResult = chartsResult + chartsResultMap.get(position);
//					}
//				}else{
//					chartsResult = chartsResult + chartsResultMap.get(position);
//				}
//				oldP = position;
//			}	
//		}
		
		if(DELETE_IMAGES){
			File delfile = new File(imgFile);
			if (delfile.exists()) {
				delfile.delete();
			}
		}
		return chartsResult;
	}
		
		
	
	
	public static File[] getFile(String path) {
		// get file list where the path has
		File file = new File(path);
		// get the folder list
		File[] array = file.listFiles();
		return array;
	}
	
	
	
	
	*//**
	 * delete File
	 * @param filePath
	 *//*
	public void deleteFile(String filePath){
		File delfile = new File(filePath);
		if (delfile.exists()) {
			delfile.delete();
		}
	}
	
}
*/