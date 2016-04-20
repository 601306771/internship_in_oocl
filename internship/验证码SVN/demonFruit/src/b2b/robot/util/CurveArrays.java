package b2b.robot.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CurveArrays {
	private int[] pixels;
	private int width;
	private int height;
	
	public CurveArrays(String imgFile) {
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
		int imgWidth = bufferedImg.getWidth();
		int imgHeight = bufferedImg.getHeight();
		int imgPixels[] = new int[imgWidth * imgHeight];
		bufferedImg.getRGB(0, 0, imgWidth, imgHeight, imgPixels, 0, imgWidth);
		
		this.width = imgWidth;
		this.height = imgHeight;
		this.pixels = imgPixels;
	}
	
	/**
	 * Left Array 
	 * @param imgFile
	 * @return
	 */
	public double[] getAarryLeft(){
		
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
	public double[] getAarryRight(){
		
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
	public double[] getAarryUp(){
		
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
	public double[] getAarryDown(){
		
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
