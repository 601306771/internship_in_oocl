package lxy.ocrHandle;

public class CosineCompareTest {
	public static void main(String[] args) {
		String imgFileb2 = "C:\\Users\\LINI8\\Desktop\\repository\\b (2).png";
		String imgFilek5 = "C:\\Users\\LINI8\\Desktop\\repository\\k (5).png";
		String imgFile36 = "C:\\Users\\LINI8\\Desktop\\repository\\3 (6).png";
		String imgFile39 = "C:\\Users\\LINI8\\Desktop\\repository\\3 (9).png";
		
		String path1 = imgFileb2;
		String path2 = imgFilek5;
		
		CosineCompareService cc = new CosineCompareService();
		try {
			double  a = cc.CompareL(path1, path2);
			double  b = cc.CompareR(path1, path2);
			double  c = cc.CompareU(path1, path2);
			double  d = cc.CompareD(path1, path2);
			
			String key = path1.substring(path1.indexOf("repository")+11,path1.length());
			String key2 = path2.substring(path2.indexOf("repository")+11,path2.length());
			
			if(a > 0.85 && b > 0.85 && c > 0.85 && d > 0.85){
				System.out.println(key + " and " + key2 + "  are the same");
			}else{
				System.out.println(key + " and " + key2 + "  not  xxxxxxx");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void compare(String path1 ,String path2){
		
		CosineCompareService cc = new CosineCompareService();
		try {
			double  a = cc.CompareL(path1, path2);
			double  b = cc.CompareR(path1, path2);
			double  c = cc.CompareU(path1, path2);
			double  d = cc.CompareD(path1, path2);
			
			String key = path1.substring(path1.indexOf("repository")+11,path1.length());
			String key2 = path2.substring(path2.indexOf("repository")+11,path2.length());
			if(a > 0.75 && b > 0.75 && c > 0.75 && d > 0.75){
				System.out.println(key + " and " + key2 + "  are the same");
			}else{
//				System.out.println(key + " and " + key2 + "  not  xxxxxxx");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
