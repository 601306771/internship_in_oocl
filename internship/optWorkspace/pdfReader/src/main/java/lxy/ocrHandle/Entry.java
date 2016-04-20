package lxy.ocrHandle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entry {
	public double getEigenValue(String imgFile){
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
		String key = imgFile.substring(imgFile.indexOf("repository")+11,imgFile.length());
		System.out.println(key+ " ------w= " + width + " , h:" + height);
		int pixels[] = new int[width * height];
		bufferedImg.getRGB(0, 0, width, height, pixels, 0, width);
		
		EigenValue ev = new EigenValue();
		double eigenValue = /*ev.countPixels(pixels);*/ ev.getEigenValue(pixels, width, height);
		
		return eigenValue;
	} 
	
	
}
