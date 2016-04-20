package b2b.robot.match.scan;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


public class ImageScanMatchService {
	public static boolean PRINT_SCAN_RESULT_IMAGE = false;

	
	/**
	 * Fixed x coordinate to scan, the width of the characters and character are obtained
	 * @param startW
	 * @param imgFileIn
	 * @param imgFileRepository
	 * @return
	 */
	public String scanMatch(int startW, String imgFileIn,int width1, int height1,int pixels1[],String key,int width2, int height2,int pixels2[]/*String imgFileRepository*/){
		
		String resultStr = "";
		if(startW + width2 > width1){
			return resultStr;
		}
		
		//Number of efficient point library images
		double sumRepositoryPixels = 0;
		for(int w2 = 0; w2 < width2; w2++){
			for(int h2 = 0; h2 < height2; h2++ ){
				if(pixels2[w2 + h2*width2] != -1){
					sumRepositoryPixels++;
				}
			}
		}
		
		double sumInPixels = 0;
		//Artwork in the width of the range of the number of valid points
		for(int w = startW; w < startW + width2; w++){
			for(int h = 0; h < height1; h++ ){
				if(pixels1[w + h*width1] != -1){
					sumInPixels++;
				}
			}
		}
		
		
		
		//First, the width of the range, ineffective black spots cannot too much
		if(sumRepositoryPixels / sumInPixels > 0.85){
			
			for(int h = 0; h < height1 - height2; h++){
				//Next, want to and the library of effective point is similar
				double countSame = 0;
				for(int wR = 0 ; wR < width2 ; wR++ ){
					for(int hR = 0; hR < height2; hR++){
						if(pixels1[startW + wR + (h + hR)*width1] == pixels2[ wR + hR*width2] && pixels2[ wR + hR*width2] != -1){
							countSame ++;
						}
					}
				}
				
				//The valid similarity comparison and libraries
				if(countSame/sumRepositoryPixels > 0.97){
					String keyStr = key.substring(0,1);
					resultStr = keyStr + "," + width2;
					
					if(PRINT_SCAN_RESULT_IMAGE){
						printImages(imgFileIn,resultStr ,h ,startW , width1, height1,pixels1,width2,height2, pixels2);
					}
					
					return resultStr;
				}
			}
		}
		
		return resultStr;
	}
	
	
	
	
	/**
	 * 
	 * @param imgFileIn
	 * @param resultStr
	 * @param h
	 * @param startW
	 * @param width1
	 * @param height1
	 * @param pixels1
	 * @param width2
	 * @param height2
	 * @param pixels2
	 */
	public void printImages(String imgFileIn,String resultStr ,int h ,int startW , int width1, int height1,int pixels1[],int width2,int height2,int pixels2[]){
		int[] newPixels = new int[width1*height1];
		for(int i = 0; i < pixels1.length; i++){
			newPixels[i] = pixels1[i];
		}
		
		for(int wR = 0 ; wR < width2 ; wR++ ){
			for(int hR = 0; hR < height2; hR++){
				if(pixels1[startW + wR + (h + hR)*width1] == pixels2[ wR + hR*width2] && pixels2[ wR + hR*width2] != -1){
					newPixels[startW + wR + (h + hR)*width1]  = 0xF82619;
				}
			}
		}
		
		BufferedImage bi = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width1, height1, newPixels, 0, width1);
		newPixels = null;

		try {
			String imgFile = imgFileIn.replace(".png",  resultStr + ".png");
			writeImageToFile(imgFile, bi);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
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
