package lxy.verificationeParser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CosineCompareService {

		/** left
		 * 
		 * */
	public double CompareL(int pixels1[],int width1,int height1,double curve_2[]) throws Exception {
		double[] curve_1 = getAarryLeft( pixels1, width1, height1); //坐标
		
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
	 * */
	public double CompareR(int pixels[],int width,int height,double[] curve_2) throws Exception {
		double[] curve_1 = getAarryRight( pixels, width, height); //坐标
		
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
	 * */
	public double CompareU(int pixels[],int width,int height,double[] curve_2) throws Exception {
		double[] curve_1 = getAarryUp( pixels, width, height); //坐标
		
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
	 * */
	public double CompareD(int pixels[],int width,int height,double[] curve_2) throws Exception {
		double[] curve_1 = getAarryDown( pixels, width, height); //坐标
		
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
