package lxy.ocrHandle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CosineCompareService {

		/** left
		 * 
		 * 余弦相似度算法*/
	public double CompareL(String imgFile1,String imgFile2) throws Exception {
		double[] curve_1 = getAarryLeft(imgFile1); //坐标
		double[] curve_2 = getAarryLeft(imgFile2); //坐标
		
		if(curve_1.length>curve_2.length){
			double[] curve_3 = new double[curve_1.length];
			for(int i = 0;i<curve_2.length;i++){
				curve_3[i] = curve_2[i];
			}
			for(int i = curve_2.length ; i  <curve_1.length ; i++){
				curve_3[i] = 0;
			}
			curve_2 = curve_3;
		}
		
		if(curve_1.length<curve_2.length){
			double[] curve_3 = new double[curve_2.length];
			for(int i = 0;i<curve_1.length;i++){
				curve_3[i] = curve_1[i];
			}
			for(int i = curve_1.length ; i  <curve_2.length ; i++){
				curve_3[i] = 0;
			}
			curve_1 = curve_3;
		}
		
		
		double x = 0, y = 0, z = 0;
		for (int i = 0; i < curve_1.length; i++){ 
			x += curve_1[i] * curve_1[i];
			y += curve_2[i] * curve_2[i];
			z += curve_1[i] * curve_2[i];
		}
		x = Math.sqrt(x);
		y = Math.sqrt(y);
		return (z / (x * y));
	}
	
	/**right
	 * 余弦相似度算法*/
	public double CompareR(String imgFile1,String imgFile2) throws Exception {
		double[] curve_1 = getAarryRight(imgFile1); //坐标
		double[] curve_2 = getAarryRight(imgFile2); //坐标
		
		if(curve_1.length>curve_2.length){
			double[] curve_3 = new double[curve_1.length];
			for(int i = 0;i<curve_2.length;i++){
				curve_3[i] = curve_2[i];
			}
			for(int i = curve_2.length ; i  <curve_1.length ; i++){
				curve_3[i] = 0;
			}
			curve_2 = curve_3;
		}
		
		if(curve_1.length<curve_2.length){
			double[] curve_3 = new double[curve_2.length];
			for(int i = 0;i<curve_1.length;i++){
				curve_3[i] = curve_1[i];
			}
			for(int i = curve_1.length ; i  <curve_2.length ; i++){
				curve_3[i] = 0;
			}
			curve_1 = curve_3;
		}
		
		double x = 0, y = 0, z = 0;
		for (int i = 0; i < curve_1.length; i++){ 
			x += curve_1[i] * curve_1[i];
			y += curve_2[i] * curve_2[i];
			z += curve_1[i] * curve_2[i];
		}
		x = Math.sqrt(x);
		y = Math.sqrt(y);
		return (z / (x * y));
	}
	
	/**up
	 * 余弦相似度算法*/
	public double CompareU(String imgFile1,String imgFile2) throws Exception {
		double[] curve_1 = getAarryUp(imgFile1); //坐标
		double[] curve_2 = getAarryUp(imgFile2); //坐标
		
		if(curve_1.length>curve_2.length){
			double[] curve_3 = new double[curve_1.length];
			for(int i = 0;i<curve_2.length;i++){
				curve_3[i] = curve_2[i];
			}
			for(int i = curve_2.length ; i  <curve_1.length ; i++){
				curve_3[i] = 0;
			}
			curve_2 = curve_3;
		}
		
		if(curve_1.length<curve_2.length){
			double[] curve_3 = new double[curve_2.length];
			for(int i = 0;i<curve_1.length;i++){
				curve_3[i] = curve_1[i];
			}
			for(int i = curve_1.length ; i  <curve_2.length ; i++){
				curve_3[i] = 0;
			}
			curve_1 = curve_3;
		}
		
		double x = 0, y = 0, z = 0;
		for (int i = 0; i < curve_1.length; i++){ 
			x += curve_1[i] * curve_1[i];
			y += curve_2[i] * curve_2[i];
			z += curve_1[i] * curve_2[i];
		}
		x = Math.sqrt(x);
		y = Math.sqrt(y);
		return (z / (x * y));
	}
	
	/**DOWN
	 * 余弦相似度算法*/
	public double CompareD(String imgFile1,String imgFile2) throws Exception {
		double[] curve_1 = getAarryDown(imgFile1); //坐标
		double[] curve_2 = getAarryDown(imgFile2); //坐标
		
		if(curve_1.length>curve_2.length){
			double[] curve_3 = new double[curve_1.length];
			for(int i = 0;i<curve_2.length;i++){
				curve_3[i] = curve_2[i];
			}
			for(int i = curve_2.length ; i  <curve_1.length ; i++){
				curve_3[i] = 0;
			}
			curve_2 = curve_3;
		}
		
		if(curve_1.length<curve_2.length){
			double[] curve_3 = new double[curve_2.length];
			for(int i = 0;i<curve_1.length;i++){
				curve_3[i] = curve_1[i];
			}
			for(int i = curve_1.length ; i  <curve_2.length ; i++){
				curve_3[i] = 0;
			}
			curve_1 = curve_3;
		}
		
		double x = 0, y = 0, z = 0;
		for (int i = 0; i < curve_1.length; i++){ 
			x += curve_1[i] * curve_1[i];
			y += curve_2[i] * curve_2[i];
			z += curve_1[i] * curve_2[i];
		}
		x = Math.sqrt(x);
		y = Math.sqrt(y);
		return (z / (x * y));
	}
	
	/**
	 * Left Array 
	 * @param imgFile
	 * @return
	 */
	public double[] getAarryLeft(String imgFile){
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
	public double[] getAarryRight(String imgFile){
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
	public double[] getAarryUp(String imgFile){
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
	public double[] getAarryDown(String imgFile){
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
