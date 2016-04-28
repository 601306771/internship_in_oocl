package nigel.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RemoveDuplicates {
	public boolean isDuplicatesImg(String pathFirst,String pathSecond){
		File file = new File(pathFirst);
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
		
		
		//////////////////////////////////////
		File fileSecond = new File(pathSecond);
		FileInputStream fisSecond = null;
		BufferedImage bufferedImgSecond = null;
		try {
			fisSecond = new FileInputStream(fileSecond);
			bufferedImgSecond = ImageIO.read(fisSecond);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				fisSecond.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int widthSecond = bufferedImgSecond.getWidth();
		int heightSecond = bufferedImgSecond.getHeight();

		int pixelsSecond[] = new int[widthSecond * heightSecond];
		bufferedImgSecond.getRGB(0, 0, widthSecond, heightSecond, pixelsSecond, 0, widthSecond);
		
		
		boolean flag = true;
		if(pixelsSecond.length == pixels.length){
			for(int i=0; i<pixelsSecond.length;i++){
				if(pixelsSecond[i] != pixels[i]){
					flag = false;
					break;
				}
			}
		}else{
			return false;
		}
		
		return flag;
	}
}
