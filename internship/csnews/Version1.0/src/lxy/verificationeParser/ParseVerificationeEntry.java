package lxy.verificationeParser;

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

public class ParseVerificationeEntry{
	private static String PROPERTIES_PATH = "/lxy/verificationeParser/RepositoryConfigConfig.properties";
	private static boolean DELETE_IMGS = true;
	private Map<String,List<double[]>> REPOSITORY_RECORD;
	
	/**
	 * Initialize from Config
	 */
	public ParseVerificationeEntry(){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.REPOSITORY_RECORD = repository_record;
	}
	
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public String getCharts(String path){
		String charts = "";//save charts result
		
		//获取要读取的图片的分割后的字符
		OcrController oc = new OcrController();
		List<String> chartPath = oc.getCharImgs(path);
		
		CosineCompareController ct = new CosineCompareController();
		
		//every single chart compareWith repository
		for(int i=0; i<chartPath.size();i++){
			
			//img in info
			int[] pixel = getPixels(chartPath.get(i));
			int width = Integer.parseInt(getHeithtAndWidth(chartPath.get(i)).split(",")[0]);
			int height = Integer.parseInt(getHeithtAndWidth(chartPath.get(i)).split(",")[1]);

			boolean del = DELETE_IMGS;
			
			for(String key : REPOSITORY_RECORD.keySet()){
					double arrayLeft[] = REPOSITORY_RECORD.get(key).get(0);
					double arrayRight[] = REPOSITORY_RECORD.get(key).get(1);
					double arrayUp[] = REPOSITORY_RECORD.get(key).get(2);
					double arrayDown[] = REPOSITORY_RECORD.get(key).get(3);
					key = key.substring(0,1);
					boolean flag = ct.compare(pixel,width,height,arrayLeft,arrayRight,arrayUp,arrayDown); 
					if(flag){
						charts = charts + key;
						del = true;
						break;
					}
			}
			if(del){
				File delfile = new File(chartPath.get(i));
				if (delfile.exists()) {
					delfile.delete();
				}
			}
		}
		return charts;
	}
	
	
	public int[] getPixels(String imgFile){
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
		
		return pixels;
	}
	
	public String getHeithtAndWidth(String imgFile){
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

		String result = width + "," + height;
		
		return result;
	}
	
	
	
	/**
	 * read files from forder
	 * @param path
	 * @return
	 */
	public static  File[] getFile(String path){   
        // get file list where the path has   
        File file = new File(path);   
        // get the folder list   
        File[] array = file.listFiles();   
          
        return array;
    }
}
