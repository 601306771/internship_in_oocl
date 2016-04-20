package lxy.verificationeParser;

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
import java.util.Collection;
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


public class OcrService {
	private static int IGNORE_PIXEL = 1;
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
			if(imgs.get(key).size()/height>2){
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
			
		}
		pixels = null;
		System.out.println(imgFile + "  -----minsize  ok---");
		
		return singleChar;
			
	}
	
	/**
	 * 变成最小有效尺寸
	 * @param imgFile
	 */
	public String split2MinSize(String imgFile) {
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
		String imgFileMin = null;
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
					imgFileMin = imgFile.replace(".png", "Min.png");
					writeImageToFile(imgFileMin, bi);
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					File imgFilePng = new File(imgFile);
					if (imgFilePng.exists()) {
						imgFilePng.delete();
					}
				}
//				System.out.println(imgFile + "minsize  ok---");
			}
		}
		return imgFileMin;
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
	public  String blackImg2Gray(String imgFile) {
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
		
		//干扰线去阴影,把干扰线周围的阴影变纯黑
		List<Integer> change = new ArrayList<Integer>();
		for(int h = 0; h < height  ; h ++ ){
			for(int w = 0; w < width  ; w ++){ 
				int p5 = w + h*width;//一个点周围8个点
				int p1 = w-1 + (h-1)*width;
				int p2 = w + (h-1)*width;
				int p3 = w+1 + (h-1)*width;
				int p4 = w-1 + h*width;
				int p6 = w+1 + h*width;
				int p7 = w-1 + (h+1)*width;
				int p8 = w + (h+1)*width;
				int p9 = w+1 + (h+1)*width;
				
				
				int max = width * height;
				int r5 = (pixels[p5] >> 16) & 0xff;
				boolean flag = false;
				
				
				
				if(r5 == 0){
					if(0<=p1 && p1<max){
						change.add(p1);
					}
					
					if(0<=p2 && p2<max){
						change.add(p2);
					}
					if(0<=p3 && p3<max){
						change.add(p3);
					}
					if(0<=p4 && p4<max){
						change.add(p4);
					}
					if(0<=p6 && p6<max){
						change.add(p6);
					}
					if(0<=p7 && p7<max){
						change.add(p7);
					}
					if(0<=p8 && p8<max){
						change.add(p8);
					}
					if(0<=p9 && p9<max){
						change.add(p9);
					}
					
					
				}
				
			}
			
			
		}
		
		for(int i=0 ; i<change.size(); i++ ){
			pixels[change.get(i)]=0;
		}
		
		
		//根据rgb对比度，筛选出字符的像素
		for(int h = 0; h < height  ; h ++ ){
			for(int w = 0; w < width  ; w ++){ 
				int p5 = w + h*width;//一个点周围8个点
				int p1 = w-1 + (h-1)*width;
				int p2 = w + (h-1)*width;
				int p3 = w+1 + (h-1)*width;
				int p4 = w-1 + h*width;
				int p6 = w+1 + h*width;
				int p7 = w-1 + (h+1)*width;
				int p8 = w + (h+1)*width;
				int p9 = w+1 + (h+1)*width;
				
				if(w==0){
					p1 = -1;
					p4 = -1;
					p7 = -1;
				}
				
				int max = width * height;
				int r5 = (pixels[p5] >> 16) & 0xff;
				boolean flag = false;
				
				if(0<=p1 && p1<max){
					int r1 = (pixels[p1] >> 16) & 0xff;
					if(r1 - r5 > 5 && r1!=0 && r5!=0){
						flag = true;
					}
				}
				
				if(0<=p2 && p2<max){
					int r2 = (pixels[p2] >> 16) & 0xff;
					if(r2 - r5 > 5 && r2!=0 && r5!=0){
						flag = true;
					}
				}
				if(0<=p3 && p3<max){
					int r3 = (pixels[p3] >> 16) & 0xff;
					if(r3 - r5 > 5 && r3!=0 && r5!=0){
						flag = true;
					}
				}
				if(0<=p4 && p4<max){
					int r4 = (pixels[p4] >> 16) & 0xff;
					if(r4 - r5 > 5 && r4!=0 && r5!=0){
						flag = true;
					}
				}
				if(0<=p6 && p6<max){
					int r6 = (pixels[p6] >> 16) & 0xff;
					if(r6 - r5 > 5 && r6!=0 && r5!=0){
						flag = true;
					}
				}
				if(0<=p7 && p7<max){
					int r7 = (pixels[p7] >> 16) & 0xff;
					if(r7 - r5 > 5 && r7!=0 && r5!=0){
						flag = true;
					}
				}
				if(0<=p8 && p8<max){
					int r8 = (pixels[p8] >> 16) & 0xff;
					if(r8 - r5 > 5 && r8!=0 && r5!=0){
						flag = true;
					}
				}
				if(0<=p9 && p9<max){
					int r9 = (pixels[p9] >> 16) & 0xff;
					if(r9 - r5 > 10 && r9!=0 && r5!=0){
						flag = true;
					}
				}
				
				if (flag) {
					newPixels[p5] = 0;
					
				} else {
					newPixels[p5] = 0xffffff;
				}
				
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
		System.out.println(/*"change one img to gray..   ok---"*/);
		return imgFile;

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
		System.out.println(/*"change one img to gray..   ok---"*/);
		return imgFile;

	}
	
	/**
	 * 判断图片颜色
	 * 
	 * @param imgFile
	 *            img path
	 * @return
	 */
	public  String judgeImgType(String imgFile) {
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

		for (int i = 0; i < width * height; i++) {
			int r = (pixels[i] >> 16) & 0xff;
			int g = (pixels[i] >> 8) & 0xff;
			int b = (pixels[i]) & 0xff;

			if (r == g && r == b ) {
				
			} else {
				return "colours";
			}
			
		}

		
		return "gray";

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