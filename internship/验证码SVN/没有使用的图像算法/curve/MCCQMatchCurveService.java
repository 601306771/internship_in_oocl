package b2b.robot.match.curve;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MCCQMatchCurveService {
	private static double LEFT_SCALE = 0.6; 
	private static double RIGHT_SCALE = 0.6;
	private static double UP_SCALE = 0.6;
	private static double DOWN_SCALE = 0.6;
	
	

	/** matchCurve
	 * 
	 * 余弦相似度算法*/
	public double matchCurve(double curve_1[],double curve_2[]) {
		
		if(curve_1.length>curve_2.length){
			double[] curve_3 = new double[curve_1.length];
			for(int i = 0;i<curve_2.length;i++){
				curve_3[i] = curve_2[i];
			}
			for(int i = curve_2.length ; i  <curve_1.length ; i++){
				curve_3[i] = 0;
			}
			curve_2 = curve_3;
		}
		
		if(curve_1.length<curve_2.length){
			double[] curve_3 = new double[curve_2.length];
			for(int i = 0;i<curve_1.length;i++){
				curve_3[i] = curve_1[i];
			}
			for(int i = curve_1.length ; i  <curve_2.length ; i++){
				curve_3[i] = 0;
			}
			curve_1 = curve_3;
		}
		
		
		double x = 0, y = 0, z = 0;
		for (int i = 0; i < curve_1.length; i++){ 
			x += curve_1[i] * curve_1[i];
			y += curve_2[i] * curve_2[i];
			z += curve_1[i] * curve_2[i];
		}
		x = Math.sqrt(x);
		y = Math.sqrt(y);
		return (z / (x * y));
	}
	
}
