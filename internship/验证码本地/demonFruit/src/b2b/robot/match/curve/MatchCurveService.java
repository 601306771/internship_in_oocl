package b2b.robot.match.curve;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MatchCurveService {
	private static double LEFT_SCALE = 0.6; 
	private static double RIGHT_SCALE = 0.6;
	private static double UP_SCALE = 0.6;
	private static double DOWN_SCALE = 0.6;
	
	/**
	 * 
	 * @param pixels
	 * @param width
	 * @param height
	 * @param arrayLeft
	 * @param arrayRight
	 * @param arrayUp
	 * @param arrayDown
	 * @return
	 */
	public double matching(double arrayLeft1[],double arrayRight1[],double arrayUp1[],double arrayDown1[],double arrayLeft2[],double arrayRight2[],double arrayUp2[],double arrayDown2[]){
		MatchCurveService cc = new MatchCurveService();
		try {
			double  leftCurve = cc.matchCurve(arrayLeft1, arrayLeft2);
			double  rightCurve = cc.matchCurve(arrayRight1, arrayRight2);
			double  upCurve = cc.matchCurve(arrayUp1, arrayUp2);
			double  downCurve = cc.matchCurve(arrayDown1, arrayDown2);
			if(leftCurve > LEFT_SCALE && rightCurve > RIGHT_SCALE && upCurve > UP_SCALE && downCurve > DOWN_SCALE){
				double sumCurve = leftCurve + rightCurve + upCurve + downCurve;
				return sumCurve;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	

	/** left
	 * 
	 * 余弦相似度算法*/
	public double matchCurve(double curve_1[],double curve_2[]) throws Exception {
		
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
