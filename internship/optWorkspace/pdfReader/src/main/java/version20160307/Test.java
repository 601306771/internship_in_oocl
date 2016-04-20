package version20160307;

public class Test {
	public static void main(String[] args){
		String pdfPath = "C:\\Users\\LINI8\\Desktop\\pdfRead\\b.pdf";
		pdfControllor pc = new pdfControllor();
		String[] tableArray = pc.getPageTables(pdfPath);
		
		for(int i = 0; i < tableArray.length; i++){
			System.out.println(tableArray[i]);
		}
	}
}
