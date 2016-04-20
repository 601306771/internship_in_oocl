package b2b.robot.image.denoising;

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

public class ImageDenoisingService {
	private static int IGNORE_PIXEL = 1;
	
	/**
	 * remove Interfering Line and the shadows
	 * @param imgFile
	 * @return
	 */
	public  String removeInterferingLine(String imgFile) {
		
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
		
		
		/**
		 * get pixels around,like a matrix 3*3. when the pixels is black, the pixel around will change to black
		 * the points, point 5 is the center of around, like e.g below
		 * 1 2 3
		 * 4 5 6
		 * 7 8 9
		 */
		List<Integer> interferingLineList = new ArrayList<Integer>();//lines shadow and  Interference lines shadow
		List<Integer> limitX = new ArrayList<Integer>(); //to find Interference lines shadow left edge
		for(int h = 0; h < height  ; h ++ ){
			for(int w = 0; w < width  ; w ++){ 
				int point5 = w + h*width;
				int point1 = w-1 + (h-1)*width;
				int point2 = w + (h-1)*width;
				int point3 = w+1 + (h-1)*width;
				int point4 = w-1 + h*width;
				int point6 = w+1 + h*width;
				int point7 = w-1 + (h+1)*width;
				int point8 = w + (h+1)*width;
				int point9 = w+1 + (h+1)*width;
				
				int pixelSize = width * height;
				int r5 = (pixels[point5] >> 16) & 0xff;
				
				
				if(r5 == 0){//if black,is  Interference lines pixels
					limitX.add(w);//add Interference lines pixels
					if(0<=point1 && point1<pixelSize){
						interferingLineList.add(point1);
					}
					
					if(0<=point2 && point2<pixelSize){
						interferingLineList.add(point2);
					}
					if(0<=point3 && point3<pixelSize){
						interferingLineList.add(point3);
					}
					if(0<=point4 && point4<pixelSize){
						interferingLineList.add(point4);
					}
					if(0<=point6 && point6<pixelSize){
						interferingLineList.add(point6);
					}
					if(0<=point7 && point7<pixelSize){
						interferingLineList.add(point7);
					}
					if(0<=point8 && point8<pixelSize){
						interferingLineList.add(point8);
					}
					if(0<=point9 && point9<pixelSize){
						interferingLineList.add(point9);
					}
				}
			}
		}
		
		Collections.sort(limitX);
		List<Integer> interferingLineLeftEdge = new ArrayList<Integer>(); 
		
		for(int i=0 ; i<interferingLineList.size(); i++ ){//change Interference lines all pixels to black
			
			if(interferingLineList.get(i)%width==limitX.get(0)){//get Interference lines shadow left edge
				interferingLineLeftEdge.add(interferingLineList.get(i));
			}
			
			pixels[interferingLineList.get(i)]=0xffffff;//interfering Line List remove
		}
		
		
		//remove the pixels of Interference lines shadow left edge around
		//matrix size 5*5
		for(int k = 0; k < interferingLineLeftEdge.size()  ; k ++ ){
			int w = interferingLineLeftEdge.get(k) % width;
			int h = (interferingLineLeftEdge.get(k) - w) / width;
			
			//get pixels around , matrix size 5*5
			Map<Integer,Integer> pointsMap =  new TreeMap<Integer,Integer>();
			int offsetH = -2;
			int pNum = 1; 
			for(int i=0; i<5 ; i++){
				int pixelsH = h + offsetH;
				int offsetW = -2;
				for(int j=0; j<5; j++){
					int pixelsW = w + offsetW;
					int result = pixelsW + pixelsH*width;
					pointsMap.put(pNum, result);
					offsetW ++;
					pNum++;
				}
				offsetH++;
			}	
			
			//pixels around remove
			for(Integer key : pointsMap.keySet()){
				if(0<pointsMap.get(key) && pointsMap.get(key)<width * height)
					pixels[pointsMap.get(key)] = 0xffffff;
			}
		}
		
		

		// 基于 newPixels 构造一个 BufferedImage
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, pixels, 0, width);
		pixels = null;

		try {
			// 写入磁盘
			imgFile = imgFile.replace(".png", "Denosing.png");
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
