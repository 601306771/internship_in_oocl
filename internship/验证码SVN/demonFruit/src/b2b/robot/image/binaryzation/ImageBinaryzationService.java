package b2b.robot.image.binaryzation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageBinaryzationService {
	
	
	/**
	 * black Image Binaryzation
	 * @param imgFile
	 * 				the file path of image
	 * @return
	 */
	public  String blackImageBinaryzation(String imgFile) {
		
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
		int newPixels[] = new int[width * height];
		
		/**the points, 5 is the center of around, like e.g below
		 * 1 2 3
		 * 4 5 6
		 * 7 8 9
		 */
		//get pixels around,like a matrix 3*3. when the contrast of  pixels around large the scale, this pixel is valid
		for(int h = 0; h < height  ; h ++ ){
			for(int w = 0; w < width  ; w ++){ 
				
				int point5 = w + h*width;//the center of the pixel around
				int point1 = w-1 + (h-1)*width;
				int point2 = w + (h-1)*width;
				int point3 = w+1 + (h-1)*width;
				int point4 = w-1 + h*width;
				int point6 = w+1 + h*width;
				int point7 = w-1 + (h+1)*width;
				int point8 = w + (h+1)*width;
				int point9 = w+1 + (h+1)*width;
				
				if(w==0){// w = 0 means the left edge of pictures
					point1 = -1;
					point4 = -1;
					point7 = -1;
				}
				
				int pixelSize = width * height;
				int r5 = (pixels[point5] >> 16) & 0xff;
				
				boolean flag = false;//judge whether the point is valid
				
				if(0<=point1 && point1<pixelSize){
					int r1 = (pixels[point1] >> 16) & 0xff;
					if(r1 - r5 > 5 && r1!=255 && r5!=255){
						flag = true;
					}
				}
				
				if(0<=point2 && point2<pixelSize){
					int r2 = (pixels[point2] >> 16) & 0xff;
					if(r2 - r5 > 5 && r2!=255 && r5!=255){
						flag = true;
					}
				}
				if(0<=point3 && point3<pixelSize){
					int r3 = (pixels[point3] >> 16) & 0xff;
					if(r3 - r5 > 5 && r3!=255 && r5!=255){
						flag = true;
					}
				}
				if(0<=point4 && point4<pixelSize){
					int r4 = (pixels[point4] >> 16) & 0xff;
					if(r4 - r5 > 5 && r4!=255 && r5!=255){
						flag = true;
					}
				}
				if(0<=point6 && point6<pixelSize){
					int r6 = (pixels[point6] >> 16) & 0xff;
					if(r6 - r5 > 5 && r6!=255 && r5!=255){
						flag = true;
					}
				}
				if(0<=point7 && point7<pixelSize){
					int r7 = (pixels[point7] >> 16) & 0xff;
					if(r7 - r5 > 5 && r7!=255 && r5!=255){
						flag = true;
					}
				}
				if(0<=point8 && point8<pixelSize){
					int r8 = (pixels[point8] >> 16) & 0xff;
					if(r8 - r5 > 5 && r8!=255 && r5!=255){
						flag = true;
					}
				}
				if(0<=point9 && point9<pixelSize){
					int r9 = (pixels[point9] >> 16) & 0xff;
					if(r9 - r5 > 10 && r9!=255 && r5!=255){
						flag = true;
					}
				}
				
				if (flag) {
					newPixels[point5] = 0;
				} else {
					newPixels[point5] = 0xffffff;
				}
				
			}
		}
		
		
		// 基于 newPixels 构造一个 BufferedImage
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, newPixels, 0, width);
		newPixels = null;

		try {
			// 写入磁盘
			imgFile = imgFile.replace(".png", "2Gray.png");
			writeImageToFile(imgFile, bi);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgFile;

	}
	
	
	/**
	 * colour Image Binaryzation 
	 * @param imgFile
	 * 				the file path of image
	 * @return
	 */
	public  String colourImageBinaryzation(String imgFile) {
		
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
		int newPixels[] = new int[width * height];

		
		//handle rgb , gray color to white , charts to black
		for (int i = 0; i < width * height; i++) {
			int r = (pixels[i] >> 16) & 0xff;
			int g = (pixels[i] >> 8) & 0xff;
			int b = (pixels[i]) & 0xff;

			if (r == g && r == b ) {
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
			imgFile = imgFile.replace(".png", "2Gray.png");
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
