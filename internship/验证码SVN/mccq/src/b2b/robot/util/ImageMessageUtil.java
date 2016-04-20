package b2b.robot.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageMessageUtil {
	private int firstW;
	private int lastW;
	private int imgWidth;
	private int imgHeight;
	private int[] imgPixels;
	
	public ImageMessageUtil(String imgFile){
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
		
		List<Integer> ws = new ArrayList<Integer>();
		for(int w = 0; w < width; w++){
			for(int h = 0; h < height; h++){
				if(pixels[w + h*width] != -1){
					ws.add(w);
				}
			}
		}
		
		Collections.sort(ws);
		
		this.firstW = ws.get(0);
		this.lastW = ws.get(ws.size()-1);
		this.imgWidth = width;
		this.imgHeight = height;
		this.imgPixels = pixels;
	}
	
	public int getFirstW(){
		return this.firstW;
	}
	
	public int getLastW(){
		return this.lastW;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public int[] getImgPixels() {
		return imgPixels;
	}
	
	
}
