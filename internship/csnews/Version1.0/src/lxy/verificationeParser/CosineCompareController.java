package lxy.verificationeParser;

public class CosineCompareController {
	private static double L = 0.78; 
	private static double R = 0.78;
	private static double U = 0.78;
	private static double D = 0.78;
	
	public boolean compare(int pixels[],int width,int height,double arrayLeft[],double arrayRight[],double arrayUp[],double arrayDown[]){
		CosineCompareService cc = new CosineCompareService();
		try {
			double  l = cc.CompareL(pixels, width, height, arrayLeft);
			double  r = cc.CompareR(pixels, width, height, arrayRight);
			double  u = cc.CompareU(pixels, width, height, arrayUp);
			double  d = cc.CompareD(pixels, width, height, arrayDown);
			if(l > L && r > R && u > U && d > D){
				double cosin = l + r + u + d;
				return true;
			}
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
}
