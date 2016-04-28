package b2b.robot.repositoryHandle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class GetRepositoryConfigFromImg {
	
	
	public String getRepositoryConfig (String imgFile){
		//
		StringBuffer result = new StringBuffer();
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
		
		
		String key = imgFile.substring(imgFile.indexOf("repository")+11,imgFile.length());
		key = key.substring(0,key.length()-4);
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");      
        Matcher m = p.matcher(key);      
        key = m.replaceAll(""); 
		
		result.append(key + "&");
		
		double[] AarryLeft = getAarryLeft(pixels,width,height);
		double[] AarryRight = getAarryRight(pixels,width,height);
		double[] AarryUp = getAarryUp(pixels,width,height);
		double[] AarryDown = getAarryDown(pixels,width,height);
		
		for(int i = 0 ; i < AarryLeft.length ; i++){
			result.append(AarryLeft[i]);
			if(i != AarryLeft.length-1){
				result.append(",");
			}else{
				result.append("&");
			}
		}
		
		for(int i = 0 ; i < AarryRight.length ; i++){
			result.append(AarryRight[i]);
			if(i != AarryRight.length-1){
				result.append(",");
			}else{
				result.append("&");
			}
		}
		
		for(int i = 0 ; i < AarryUp.length ; i++){
			result.append(AarryUp[i]);
			if(i != AarryUp.length-1){
				result.append(",");
			}else{
				result.append("&");
			}
		}
		
		for(int i = 0 ; i < AarryDown.length ; i++){
			result.append(AarryDown[i]);
			if(i != AarryDown.length-1){
				result.append(",");
			}else{
				result.append("&");
			}
		}
		
		
		result.append(width+"&"+height);
		

		return result.toString();
	}
	
	/**
	 * Left Array 
	 * @param imgFile
	 * @return
	 */
	public double[] getAarryLeft(int pixels[],int width,int height){
		
		double[] array = new double[height];
		 for(int h = 0; h < height; h++){
			 int num = 0;
			 for(int w = 0; w < width; w++){
				 if(pixels[w + h*width] != -1){
					 break;
				 }
				 num++;
			 }
			 array[h] = num;
		 }
		 return array;
	}
	
	/**
	 * Right  array
	 * @param imgFile
	 * @return
	 */
	public double[] getAarryRight(int pixels[],int width,int height){
		
		double[] array = new double[height];
		 for(int h = 0; h < height; h++){
			 int num = 0;
			 for(int w = width-1; w >= 0; w--){
				 if(pixels[w + h*width] != -1){
					 break;
				 }
				 num++;
			 }
			 array[h] = num;
		 }
		 return array;
	}
	
	/**
	 * Up  array
	 * @param imgFile
	 * @return
	 */
	public double[] getAarryUp(int pixels[],int width,int height){
		
		double[] array = new double[width];
		 for(int w = 0; w < width; w++){
			 int num = 0;
			 for(int h = 0; h < height; h++){
				 if(pixels[w + h*width] != -1){
					 break;
				 }
				 num++;
			 }
			 array[w] = num;
		 }
		 return array;
	}
	
	/**
	 * Down  array
	 * @param imgFile
	 * @return
	 */
	public double[] getAarryDown(int pixels[],int width,int height){
		
		double[] array = new double[width];
		 for(int w = 0; w < width; w++){
			 int num = 0;
			 for(int h = height-1; h >=0; h--){
				 if(pixels[w + h*width] != -1){
					 break;
				 }
				 num++;
			 }
			 array[w] = num;
		 }
		 return array;
	}
}
