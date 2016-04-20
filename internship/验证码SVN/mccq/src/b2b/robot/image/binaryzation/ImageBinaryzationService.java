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

		// output new image 
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, newPixels, 0, width);
		newPixels = null;

		try {
			imgFile = imgFile.replace(".png", "2Gray.png");
			writeImageToFile(imgFile, bi);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgFile;

	}
	
	/**
	 * the image to disk file
	 * 
	 * @param imgFile
	 *            the file path
	 * @param bi
	 *            BufferedImage objects
	 * @return no
	 */
	public static void writeImageToFile(String imgFile, BufferedImage bi) throws IOException {
		// write image to disk
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(imgFile.substring(imgFile
				.lastIndexOf('.') + 1));
		ImageWriter writer = (ImageWriter) writers.next();
		// set the output source
		File f = new File(imgFile);
		ImageOutputStream ios;

		ios = ImageIO.createImageOutputStream(f);
		writer.setOutput(ios);
		// written to disk
		writer.write(bi);
		ios.close();
	}
}
