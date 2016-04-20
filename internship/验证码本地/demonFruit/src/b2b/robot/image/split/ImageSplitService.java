package b2b.robot.image.split;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageSplitService {
	private static int IGNORE_PIXEL = 1;
	
	/**
	 * split Single Chart image 2 MinSize
	 * @param imgFile
	 * @return
	 */
	public String splitSingleChart2MinSize(String imgFile) {
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
		
		//get valid x Coordinates and y Coordinates 
		List<Integer> xCoordinates = new ArrayList<Integer>();
		List<Integer> yCoordinates = new ArrayList<Integer>();
		for(int h = 0; h < height  ; h ++ ){
			for(int w = 0; w < width  ; w ++){ 
				if(pixels[w + h*width] != -1){
					xCoordinates.add(w);
					yCoordinates.add(h);
				}
			}
		}
		Collections.sort(xCoordinates);
		Collections.sort(yCoordinates);
		
		
		String minImgFilePath = null;//
		
		if(xCoordinates.size() > 0 && yCoordinates.size() > 0){
			int x1 = xCoordinates.get(0);
			int y1 = yCoordinates.get(0);
			int x2 = xCoordinates.get(xCoordinates.size()-1);
			int y2 = yCoordinates.get(yCoordinates.size()-1);
			
			//Initialize new min image
			int minw = x2 -x1;
			int minh = y2 -y1;
			int Minpixels[] = new int[minw * minh];
			for(int h = 0; h < minh  ; h ++ ){
				for(int w = 0; w < minw  ; w ++){ 
					Minpixels[w + h*minw] = pixels[w + x1 + (h+y1)*width];
				}
			}
			
			
			if(minw > 0 && minh > 0){
				// 基于 newPixels 构造一个 BufferedImage
				BufferedImage bi = new BufferedImage(minw, minh, BufferedImage.TYPE_INT_RGB);
				bi.setRGB(0, 0, minw, minh, Minpixels, 0, minw);
				pixels = null;
				Minpixels = null;
	
				try {
					// 写入磁盘
					minImgFilePath = imgFile.replace(".png", "2Min.png");
					writeImageToFile(minImgFilePath, bi);
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
//					File imgFilePng = new File(imgFile);
//					if (imgFilePng.exists()) {
//						imgFilePng.delete();
//					}
				}
			}
		}
		return minImgFilePath;
	}
	
	/**
	 * split Single Chart 2 images
	 * @param imgFile
	 * @return
	 */
	public List<String> splitSingleChart2image(String imgFile) {
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
		
		
		Map<Integer,List<Integer>> imgsPixelArray = new TreeMap<Integer,List<Integer>>();
		
		//According to the first line of calculate the number of characters,to Initialize the imgsPixelArray
		List<Integer> countCharts = new ArrayList<Integer>();
		for(int w = 0; w < width  ; w ++){//first line not red pixels
			if(pixels[w] != -514535){
				countCharts.add(w);
			}
		}
		
		int imgIndex = 0;
		for(int w = 1; w < countCharts.size(); w++){//Initialize the imgsPixelArray
			
			if(w == 1){//when w=1 , is first chart image ,Initialize the imgsPixelArray
				List<Integer> temp = new ArrayList<Integer>();
				imgsPixelArray.put(imgIndex, temp);
			}
			
			if(countCharts.get(w) - countCharts.get(w-1) > 1){//when w!=1 , is other chart images , Initialize the imgsPixelArray  
				imgIndex++;
				List<Integer> temp = new ArrayList<Integer>();
				imgsPixelArray.put(imgIndex, temp);
			}
			
			imgsPixelArray.get(imgIndex).add(countCharts.get(w));
		}
		
		
		
		//add everyLine to imgsPixelArray
		for(int h = 1; h < height  ; h ++ ){
			List<Integer> pixlesThisLine = new ArrayList<Integer>();
			for(int w = 0; w < width  ; w ++){//add not red to pixlesThisLine
				if(pixels[w + h*width] != -514535){
					pixlesThisLine.add(w + h*width);
				}
			}
			
			int index = 0;
			for(int w = 1; w < pixlesThisLine.size(); w++){//pixlesThisLine's pixel add to imgsPixelArray
				if(pixlesThisLine.get(w) - pixlesThisLine.get(w-1) > 1){
					index++;
				}
				if(index <= imgIndex){
					imgsPixelArray.get(index).add(pixlesThisLine.get(w));
				}
			}
			
		}
		
		//write Images To File
		List<String> singleCharImgPath = new ArrayList<String>();
		for(Integer oneImage : imgsPixelArray.keySet()){
			if(imgsPixelArray.get(oneImage).size()/height>2){// if image's size < 2 ,forgive this image
				
				int newPixels[] = new int[width * height];// Initialize one new image
				for (int i = 0; i < width * height; i++) {
					newPixels[i] = 0xffffff;
				}
				
				for(int i=0; i<imgsPixelArray.get(oneImage).size(); i++){//add pixels to new image
					newPixels[imgsPixelArray.get(oneImage).get(i)] = pixels[imgsPixelArray.get(oneImage).get(i)];
				}
				
				// 基于 newPixels 构造一个 BufferedImage
				BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				bi.setRGB(0, 0, width, height, newPixels, 0, width);

				try {
					// 写入磁盘
					String imgFiles = imgFile.replace(".png", oneImage+".png");
					singleCharImgPath.add(imgFiles);
					writeImageToFile(imgFiles, bi);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		pixels = null;
		
		return singleCharImgPath;
			
	}
	
	
	/**
	 * split SingleChart 2 Logic
	 * @param imgFile
	 */
	public String splitSingleChart2Logic(String imgFile) {
		
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
		
		
		//The vertical cutting : count black of the current column, if < IGNORE_PIXEL , is the position to split 
		for(int w = 0; w < width  ; w ++){
			
			int count_black = 0;
			boolean cutFlag = true;
			
			for(int h = 0; h < height  ; h ++ ){
				if(pixels[w + h*width] != -1){
					count_black ++;
					if(count_black > IGNORE_PIXEL){
						cutFlag = false;
						break;
					}
				}
			}
			
			if(cutFlag){// if cutFlag is true , change this column to red
				for(int h = 0; h < height  ; h ++ ){
					pixels[w + h*width] = 0xF82619;
				}
			}
		}
		
		
		
		// The Right slash cutting : Divided according to different slope, inclination  to right
		// Slash a formula to calculate : y = kx + b
		for(int inclinationScale = 1; inclinationScale < 40 ; inclinationScale ++){
			for(int w = 0; w < width  ; w ++){ 
				double x1 = w;
				double x2 = x1+inclinationScale;
				double y1 = height;
				double y2 = 0;
				double k = (y1-y2)/(x1-x2);
				double b = y1 - k*x1;
				
				boolean cutFlag = true;
				for(int h = 0; h < height  ; h ++ ){
					double x = (h-b)/k;
					int pixelX = (int)x;
					if(pixels[pixelX + h*width] != -1){
							cutFlag = false;
							break;
					}
				}
				
				if(cutFlag){
					for(int h = 0; h < height  ; h ++ ){
						double x = (h-b)/k;
						int xx = (int)x;
						pixels[xx + h*width] = 0xF82619;
					}
				}
			}
		}
		
		
		
		// The Left slash cutting : Divided according to different slope, inclination  to Left
		// Slash a formula to calculate : y = kx + b
		for(int inclinationScale = 1; inclinationScale < 40 ; inclinationScale ++){
			for(int w = 0; w < width  ; w ++){  //判断是否为线
				double x1 = w;
				double x2 = x1+inclinationScale;
				double y1 = 0;
				double y2 = height;
				double k = (y1-y2)/(x1-x2);
				double b = y1 - k*x1;
				
				boolean cutFlag = true;
				for(int h = 0; h < height  ; h ++ ){
					double x = (h-b)/k;
					int xx = (int)x;
					if(pixels[xx + h*width] != -1){
							cutFlag = false;
							break;
					}
				}
				
				if(cutFlag){
					for(int h = 0; h < height  ; h ++ ){
						double x = (h-b)/k;
						int xx = (int)x;
						pixels[xx + h*width] = 0xF82619;
					}
				}
			}
		}
		
		

			

		//write Images To File
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, pixels, 0, width);
		pixels = null;

		try {
			// 写入磁盘
			imgFile = imgFile.replace(".png", "2Split.png");
			writeImageToFile(imgFile, bi);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgFile;
	}
	
	
	/**
	 * 将图片写入磁盘文件
	 * 
	 * @param imgFile
	 *            文件路径
	 * @param bi
	 *            BufferedImage 对象
	 * @return 无
	 */
	public static void writeImageToFile(String imgFile, BufferedImage bi) throws IOException {
		// 写图片到磁盘上
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(imgFile.substring(imgFile
				.lastIndexOf('.') + 1));
		ImageWriter writer = (ImageWriter) writers.next();
		// 设置输出源
		File f = new File(imgFile);
		ImageOutputStream ios;

		ios = ImageIO.createImageOutputStream(f);
		writer.setOutput(ios);
		// 写入到磁盘
		writer.write(bi);
		ios.close();
	}
}
