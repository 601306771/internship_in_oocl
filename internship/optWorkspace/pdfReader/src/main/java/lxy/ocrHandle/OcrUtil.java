package lxy.ocrHandle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


public class OcrUtil {
	private static int IGNORE_PIXEL = 2;
	/**
	 * 逻辑上的分割
	 * @param imgFile
	 */
	public String split(String imgFile) {
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
		
		List<Integer> list = new ArrayList<Integer>();
		
		
		
		for(int w = 0; w < width  ; w ++){  //垂直方向分割
			int count_black = 0;
			int flag = 1;
			for(int h = 0; h < height  ; h ++ ){
				if(pixels[w + h*width] != -1){
					count_black ++;
					if(count_black > IGNORE_PIXEL){
						flag = 0;
						list.add(w);
						break;
					}
				}
			}
			if(flag == 1){
				for(int h = 0; h < height  ; h ++ ){
					pixels[w + h*width] = 0xF82619;
				}
			}
		}
		
		//右边倾斜分割
		for(int nums = 1; nums < 40 ; nums ++){
			List<Integer> listAngle = new ArrayList<Integer>();
			for(int w = 0; w < width  ; w ++){  //判断是否为线
				double x1 = w;
				double x2 = x1+nums;
				double y1 = height;
				double y2 = 0;
				double k = (y1-y2)/(x1-x2);
				double b = y1 - k*x1;
				
				int flag = 1;
//				int count_black = 0;
				for(int h = 0; h < height  ; h ++ ){
					double x = (h-b)/k;
					int xx = (int)x;
					if(pixels[xx + h*width] != -1){
//						count_black++;
//						if(count_black > 2){
							listAngle.add(w);
							flag = 0;
							break;
//						}
					}
				}
				
				if(flag == 1){
					for(int h = 0; h < height  ; h ++ ){
						double x = (h-b)/k;
						int xx = (int)x;
						pixels[xx + h*width] = 0xF82619;
					}
				}
			}
		}
		
		//左边边倾斜分隔
		for(int nums = 1; nums < 40 ; nums ++){
			List<Integer> listAngle = new ArrayList<Integer>();
			for(int w = 0; w < width  ; w ++){  //判断是否为线
				double x1 = w;
				double x2 = x1+nums;
				double y1 = 0;
				double y2 = height;
				double k = (y1-y2)/(x1-x2);
				double b = y1 - k*x1;
				
				int flag = 1;
				for(int h = 0; h < height  ; h ++ ){
					double x = (h-b)/k;
					int xx = (int)x;
					if(pixels[xx + h*width] != -1){
							listAngle.add(w);
							flag = 0;
							break;
					}
				}
				
				if(flag == 1){
					for(int h = 0; h < height  ; h ++ ){
						double x = (h-b)/k;
						int xx = (int)x;
						pixels[xx + h*width] = 0xF82619;
					}
				}
			}
		}
		
		
//		for(int h = 0; h < height  ; h ++ ){
//			for(int w = 0; w < width  ; w ++){ 
//			}
//		}
			

		// 基于 newPixels 构造一个 BufferedImage
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, pixels, 0, width);
		pixels = null;

		try {
			// 写入磁盘
			imgFile = imgFile.replace(".png", "Split.png");
			writeImageToFile(imgFile, bi);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("logic split..   ok---");
		return imgFile;
	}
	
	
	/**
	 * 分成几张图
	 * @param imgFile
	 */
	public List<String> split2Images(String imgFile) {
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
		
		Map<Integer,List<Integer>> imgs = new TreeMap<Integer,List<Integer>>();
		
		//第一行，初始化数组
		List<Integer> list = new ArrayList<Integer>();
		for(int w = 0; w < width  ; w ++){//获取所有非红色的点 
			if(pixels[w] != -514535){
				list.add(w);
			}
		}
		
		int imgIndex = 0;
		for(int w = 1; w < list.size(); w++){//计算有几组
			if(w == 1){
				List<Integer> temp = new ArrayList<Integer>();
				imgs.put(imgIndex, temp);
			}
			if(list.get(w) - list.get(w-1) > 1){
				imgIndex++;
				List<Integer> temp = new ArrayList<Integer>();
				imgs.put(imgIndex, temp);
			}
			imgs.get(imgIndex).add(list.get(w));
		}
		
		
		
		//接下来每一行
		for(int h = 1; h < height  ; h ++ ){
			List<Integer> listH = new ArrayList<Integer>();
			for(int w = 0; w < width  ; w ++){//获取所有非红色的点 
				if(pixels[w + h*width] != -514535){
					listH.add(w + h*width);
				}
			}
			int index = 0;
			for(int w = 1; w < listH.size(); w++){//本行点分组存入
				if(listH.get(w) - listH.get(w-1) > 1){
					index++;
				}
				if(index <= imgIndex){
					imgs.get(index).add(listH.get(w));
				}
				
			}
		}
		
		List<String> singleChar = new ArrayList<String>();
		for(Integer key : imgs.keySet()){
			int newPixels[] = new int[width * height];
			// 初始化 所有点为白色
			for (int i = 0; i < width * height; i++) {
				newPixels[i] = 0xffffff;
			}
			for(int i=0; i<imgs.get(key).size(); i++){//每组数据分别存入数组
				newPixels[imgs.get(key).get(i)] = pixels[imgs.get(key).get(i)];
			}
			
			// 基于 newPixels 构造一个 BufferedImage
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			bi.setRGB(0, 0, width, height, newPixels, 0, width);

			try {
				// 写入磁盘
				String imgFiles = imgFile.replace(".png", key+".png");
				singleChar.add(imgFiles);
				writeImageToFile(imgFiles, bi);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		pixels = null;
		System.out.println(imgFile + "  -----minsize  ok---");
		
		return singleChar;
			
	}
	
	/**
	 * 变成最小有效尺寸
	 * @param imgFile
	 */
	public void split2MinSize(String imgFile/*,int width,int height,int pixels[]*/) {
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
		
		List<Integer> listW = new ArrayList<Integer>();
		List<Integer> listH = new ArrayList<Integer>();
		for(int h = 0; h < height  ; h ++ ){
			for(int w = 0; w < width  ; w ++){ 
				if(pixels[w + h*width] != -1){
					listW.add(w);
					listH.add(h);
				}
			}
		}
		Collections.sort(listW);
		Collections.sort(listH);
		
		if(listW.size() > 0 && listH.size() > 0){
			int x1 = listW.get(0);
			int y1 = listH.get(0);
			int x2 = listW.get(listW.size()-1);
			int y2 = listH.get(listH.size()-1);
				
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
					String imgFileMin = imgFile.replace(".png", "Min.png");
					writeImageToFile(imgFileMin, bi);
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					File imgFilePng = new File(imgFile);
					if (imgFilePng.exists()) {
						imgFilePng.delete();
					}
				}
				System.out.println(imgFile + "minsize  ok---");
			}
		}
	}
	
	/**
	 * 变成最小有效尺寸
	 * @param imgFile
	 */
	public void split2MinSizeInner(String imgFile,int width,int height,int pixels[]) {
		List<Integer> listW = new ArrayList<Integer>();
		List<Integer> listH = new ArrayList<Integer>();
		for(int h = 0; h < height  ; h ++ ){
			for(int w = 0; w < width  ; w ++){ 
				if(pixels[w + h*width] == -1){
					listW.add(w);
					listH.add(h);
				}
			}
		}
		Collections.sort(listW);
		Collections.sort(listH);
		
		int x1 = listW.get(0);
		int y1 = listH.get(0);
		int x2 = listW.get(listW.size()-1);
		int y2 = listH.get(listH.size()-1);
			
		int minw = x2 -x1;
		int minh = y2 -y1;
		int Minpixels[] = new int[minw * minh];
		for(int h = 0; h < minh  ; h ++ ){
			for(int w = 0; w < minw  ; w ++){ 
				Minpixels[w + h*minw] = pixels[w + x1 + (h+y1)*width];
			}
		}
		
		// 基于 newPixels 构造一个 BufferedImage
				BufferedImage bi = new BufferedImage(minw, minh, BufferedImage.TYPE_INT_RGB);
				bi.setRGB(0, 0, minw, minh, Minpixels, 0, minw);
				pixels = null;
				Minpixels = null;

				try {
					// 写入磁盘
					imgFile = imgFile.replace(".png", "Min.png");
					writeImageToFile(imgFile, bi);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(imgFile + "minsize  ok---");
		
		
	}
	
	
	
	/**峰值方法  暂时没用上
	 * 用红线划分区域
	 * @param imgFile
	 */
	public void splitV2(String imgFile) {
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
		
		int[] yCount = new int[width];
		
		for(int w = 0; w < width  ; w ++){
			int count = 0;
			for(int h = 0; h < height  ; h ++ ){
				if(pixels[w + h*width] != -1){
					count++;
				}
			}
			yCount[w] = count; 
		}
		
		List<Integer> listPoints = new ArrayList<Integer>();
		for(int i = 1 ; i < yCount.length; i++ ){
			if(yCount[i] < 4){
				listPoints.add(i);
			}
		}
		
		for(int i = 0; i < listPoints.size()  ; i ++){
			for(int h = 0; h < height  ; h ++ ){
				pixels[listPoints.get(i) + h*width] = 0xF82619;
			}
		}

		// 基于 newPixels 构造一个 BufferedImage
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, pixels, 0, width);
		pixels = null;

		try {
			// 写入磁盘
			imgFile = imgFile.replace(".png", "Split.png");
			writeImageToFile(imgFile, bi);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("change one img to split..   ok---");

	}
	
	/**
	 * 去除杂色
	 * 
	 * @param imgFile
	 *            img path
	 * @return
	 */
	public  String img2Gray(String imgFile) {
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
		int newPixels[] = new int[width * height];

		for (int i = 0; i < width * height; i++) {
			int r = (pixels[i] >> 16) & 0xff;
			int g = (pixels[i] >> 8) & 0xff;
			int b = (pixels[i]) & 0xff;

			if (r == g && r == b ) {
				/**
				 * 这里是判断通过，则把该像素换成白色
				 */
				newPixels[i] = 0xffffff;
			} else {
				newPixels[i] = 0;
			}
			
		}

		// 基于 newPixels 构造一个 BufferedImage
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, newPixels, 0, width);
		newPixels = null;

		try {
			// 写入磁盘
			imgFile = imgFile.replace(".png", "Gray.png");
			writeImageToFile(imgFile, bi);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("change one img to gray..   ok---");
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