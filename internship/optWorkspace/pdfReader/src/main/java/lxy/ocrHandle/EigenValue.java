package lxy.ocrHandle;

public class EigenValue {
	public double getEigenValue(int pixels[],int width,int height){
		int x1 = left_right(pixels,width,height);
		int x2 = up_down(pixels,width,height);
		int x3 = slash_left(pixels,width,height);
		int x4 = slash_right(pixels,width,height);
		double[] array = { x1 , x2 , x3 , x4};
		return maths(array);
	}
	
	public double countPixels(int pixels[]){
		double count = 0;
		for(int i = 0; i < pixels.length; i++){
			 if(pixels[i] != -1){
				 count ++;
			 }
		 }
		
		return  count;
	}
	
	public int left_right(int pixels[],int width,int height){
		int result = 0;
		int left = 0;
		int right = 0;
		int num = width/2;
		 if(width%2 ==0 ){
			
			 for(int w = 0; w < num; w++){
				 for(int h = 0; h < height; h++){
					 if(pixels[w + h*width] != -1){
						 left ++;
					 }
				 }
			 }
			 
			
			 for(int w = num; w< width; w++ ){
				 for(int h = 0; h < height; h++){
					 if(pixels[w + h*width] != -1){
						 right ++;
					 }
				 }
			 }
			 
			 
		 }else{
			 for(int w = 0; w < num; w++){
				 for(int h = 0; h < height; h++){
					 if(pixels[w + h*width] != -1){
						 left ++;
					 }
				 }
			 }
			 
			 for(int w = num+1; w< width; w++ ){
				 for(int h = 0; h < height; h++){
					 if(pixels[w + h*width] != -1){
						 right ++;
					 }
				 }
			 }
			 
			 
		 }
//		 System.out.println("left:"+left + "*** right:" + right);
		 result = Math.abs(left - right);
		 
		return  result;
	}
	
	public int up_down(int pixels[],int width,int height){
		int result = 0;
		int up = 0;
		int down = 0;
		int num = height/2;
		 if(height%2 ==0 ){
			
			 for(int h = 0; h < num; h++){
				 for(int w = 0; w < width; w++){
					 if(pixels[w + h*width] != -1){
						 up ++;
					 }
				 }
			 }
			 
			
			 for(int h = num; h< height; h++ ){
				 for(int w = 0; w < width; w++){
					 if(pixels[w + h*width] != -1){
						 down ++;
					 }
				 }
			 }
			 
			 
		 }else{
			 
			 for(int h = 0; h < num; h++){
				 for(int w = 0; w < width; w++){
					 if(pixels[w + h*width] != -1){
						 up ++;
					 }
				 }
			 }
			 
			
			 for(int h = num+1; h< height; h++ ){
				 for(int w = 0; w < width; w++){
					 if(pixels[w + h*width] != -1){
						 down ++;
					 }
				 }
			 }
			 
			 
		 }
		 
 		 result = Math.abs(up - down);
		 
		return  result;
	}
	
	
	public int slash_left(int pixels[],int width,int height){
		double x1 = 0;
		double x2 = width;
		double y1 = 0;
		double y2 = height;
		double k = (y1-y2)/(x1-x2);
		double b = y1 - k*x1;
		int countL = 0;
		int countR = 0;
		for(int x = 0; x < width; x++){
			 for(int y = 0; y < height; y++){
				 if(y > (k*x + b)){
					 countL++;
				 }else if(y < (k*x + b)){
					 countR++;
				 }
			}
		}
		int result = Math.abs(countL-countR);
		return result;
	}
	
	public int slash_right(int pixels[],int width,int height){
		double x1 = width;
		double x2 = 0;
		double y1 = 0;
		double y2 = height;
		double k = (y1-y2)/(x1-x2);
		double b = y1 - k*x1;
		int countL = 0;
		int countR = 0;
		for(int x = 0; x < width; x++){
			 for(int y = 0; y < height; y++){
				 if(y > (k*x + b)){
					 countL++;
				 }else if(y < (k*x + b)){
					 countR++;
				 }
			}
		}
		int result = Math.abs(countL-countR);
		return result;
	}
	/**
	 * 方差计算
	 * @param array
	 */
	public double maths(double[] array){
	        
	        double ave = 0;
	        for (int i = 0; i < array.length; i++){
	        	  ave += array[i];
	        }
	          
	        ave /= array.length;
	        
	        double sum = 0;
	        for(int i = 0;i<array.length;i++){
	        	 sum += (array[i] - ave)  * (array[i] - ave) ;
	        }
	        sum /= array.length;
	        
	        return sum;
	   }
}
