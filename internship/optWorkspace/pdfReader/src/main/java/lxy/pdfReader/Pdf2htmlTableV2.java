package lxy.pdfReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Pdf2htmlTableV2 {
	private String result;
	public static int ROW_BEGIN = 0;
	public static int ROW_END = -1;  // -1 means rows.size
	public static double VALID_SIGN_SPACE = 0;//valid # space
	public static double VALID_SPACE = 8;//valid  space
	public static int NUMBER_OF_SECTION_REPLICATIONS = 8;//section appear times
	public static double MERGE_Y_SECTION = 1;//when  | this.y - last.y |< MERGE_Y_SECTION, means they will be merged
	
	public static String[] ADD_SECTION = {"no need","1,90","7,330"/*"6,500"*/};//format:  ADD_SECTION={"index,double","index2,double2"} if not exist set ADD_SECTION = {"no need"};
	public static String[] REMOVE_SECTION = {"no need"};//format:  REMOVE_SECTION={index,index2} if not exist set REMOVE_SECTION = {"no need"};
	
	
	
	public String pdfToHtml(String path)throws IOException{
		String read = readTxt(path).toString().replaceAll("&&&", "&").replaceAll("&&", "&");
		String[] data =  read.split("&");
		
		
		Map<Double,String> rows =new TreeMap<Double,String>(); //all  y
		for(int i=0;i<data.length;i++){//获取所有不重复y坐标
			Double y = Double.parseDouble(data[i].substring(data[i].indexOf("y")+2, data[i].indexOf("value")-1));
			rows.put(y, "0");
		}
		
		ArrayList<Double> row = new ArrayList<Double>();//select y
		int index = 0;
		for(Double key : rows.keySet()){
			ROW_END = ROW_END==-1?rows.size():ROW_END;
			if(ROW_BEGIN<index&&index<ROW_END){
				row.add(key);
			}
			index++;
		}
		
		
		
		
		List<String> list = new ArrayList<String>(); //get spaces
		for(int i=0;i<data.length;i++){
			String x = data[i].substring(data[i].indexOf("x")+2, data[i].indexOf(",y"));
			Double y = Double.parseDouble(data[i].substring(data[i].indexOf("y")+2, data[i].indexOf("value")-1));
			String xNext = String.valueOf(0);
			if(i<data.length-1){
				xNext = data[i+1].substring(data[i+1].indexOf("x")+2, data[i+1].indexOf(",y"));
			}
			String value = data[i].substring(data[i].indexOf("value=")+6, data[i].length());
			double space = Double.parseDouble(xNext)-Double.parseDouble(x);
			
				
			if(row.contains(y)){
				if("#".equals(value.trim())&&space>VALID_SIGN_SPACE){
					String str = x +","+ xNext;
					list.add(str);
				}
				
				if(space>=VALID_SPACE){
					String str = x +","+ xNext;
					list.add(str);
				}
			}
		}
		
	
		
		
		Map<String,Integer> map =new TreeMap<String,Integer>(); //sort spaces
		for(int i=0;i<list.size();i++){
			String section = list.get(i);
			
			for(int j=0;j<list.size();j++){
				String temp = compareWith(section,list.get(j));
				if(i!=j&&temp!=null){
					section =  temp;
				}
			}
			
			if(map.containsKey(section)){
				int val = map.get(section)+1; 
				map.put(section, val);
			}else{
				map.put(section, 1);
			}
		}
		
		List<Double> sections = new ArrayList<Double>(); //set sections
		sections.add((double) 0);
		double temp = 0;
		for(String key:map.keySet()){
			if(map.get(key)>row.size()-NUMBER_OF_SECTION_REPLICATIONS){
				double a = Double.parseDouble(key.substring(0, key.indexOf(",")));
				double b = Double.parseDouble(key.substring(key.indexOf(",")+1,key.length()));
				double c = (a+b)/2;
				if(c>temp){
					int flag = 1;
					for(int i=0;i<sections.size();i++){
						if(c<sections.get(i)){
							flag = 0;
						}
					}
					if(flag == 1){
//						System.out.println(a+","+b);
						sections.add(c);
					}
				}
				temp=c;
			}
		}
		sections.add((double) 1000);
		
		
		if(!"no need".equals(ADD_SECTION[0])){//add section
			for(int i=0;i<ADD_SECTION.length;i++){
				int addIndex = Integer.parseInt(ADD_SECTION[i].substring(0,ADD_SECTION[i].indexOf(",")));
				double addNum = Double.parseDouble(ADD_SECTION[i].substring(ADD_SECTION[i].indexOf(",")+1,ADD_SECTION[i].length()));
				sections.add(i>0?(addIndex+1):addIndex, addNum);
			}
		}
		
		if(!"no need".equals(REMOVE_SECTION[0])){//remove section
			for(int i=0;i<REMOVE_SECTION.length;i++){
				int removeIndex = Integer.parseInt(REMOVE_SECTION[i]);
				sections.remove(removeIndex-i);
			}
		}
		

		
		StringBuffer html = new StringBuffer();
		
		//first row is the message about x section
		StringBuffer row1 = new StringBuffer();
		row1.append("<td>"+"y\\x"+"</td>");
		for(int key=1;key<sections.size();key++){
			String num1 = sections.get(key-1).toString().substring(0,sections.get(key-1).toString().indexOf(".")+2);
			String num2 = sections.get(key).toString().substring(0,sections.get(key).toString().indexOf(".")+2);
			float s1 = Float.parseFloat(num1);
			float s2 = Float.parseFloat(num2);
			row1.append("<td>"+s1+"~"+s2+"</td>");
		}
		
		
		html.append("<head></head><body><table border=\"1\"><tr>"+row1+"</tr>");
		String compareStr = "";
		
		//select sections to input data
		for(int j=0;j<row.size();j++){//判断它是哪一行
			StringBuffer[] areas = new StringBuffer[sections.size()-1]; 
			
			for(int i=0;i<areas.length;i++){
				areas[i] = new StringBuffer();
			}
			for(int i=0;i<data.length;i++){//遍历所有数据
				Double y = Double.parseDouble(data[i].substring(data[i].indexOf("y")+2, data[i].indexOf("value")-1));
				Double x = Double.parseDouble(data[i].substring(data[i].indexOf("x")+2, data[i].indexOf(",y")));
				String value = data[i].substring(data[i].indexOf("value=")+6, data[i].length());

				if(row.get(j).equals(y)||(row.get(j)-y<MERGE_Y_SECTION&&row.get(j)-y>-MERGE_Y_SECTION)){//首先是当前行的数据
					for(int key=1;key<sections.size();key++){
						if(x>=sections.get(key-1)&&x<sections.get(key)){
							areas[key-1].append(value);
						}
					}
				}
			} 
			
			StringBuffer rowStr = new StringBuffer();
			for(int i=0;i<areas.length;i++){
				rowStr.append("<td>"+areas[i]+"</td>");
			}
			
			//if this rowStr and last rowStr are the same ,remove 
			if(!compareStr.equals(rowStr.toString())){
				html.append("<tr><td>"+j+"--"+row.get(j)+"</td>"+rowStr+"</tr>");
			}
			
			compareStr = rowStr.toString();
		}
		
		
		
		html.append("</table></body>");
		String result = html.toString();
		
		
		
		//print sections
		for(int i=0;i<sections.size();i++){
			System.out.println(sections.get(i));
			
		}
		
		
		
		return result;
	}
	
	
	
	
	/**
	 * compare 2 sections
	 * @param str1
	 * @param str2
	 * @return
	 */
	public String compareWith(String str1,String str2){
		double a1 = Double.parseDouble(str1.substring(0, str1.indexOf(",")));
		double b1 = Double.parseDouble(str1.substring(str1.indexOf(",")+1,str1.length()));
		double a2 = Double.parseDouble(str2.substring(0, str2.indexOf(",")));
		double b2 = Double.parseDouble(str2.substring(str2.indexOf(",")+1,str2.length()));
		
		double min = a1>=a2 ? a1:a2; 
		double max = b1<=b2 ? b1:b2; 
		String section = null;
		if(max-min>0){
			section = min+","+max;
		}
		return section;
	}
	
	
	/**
	 * read txt about positions
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public StringBuffer readTxt(String path) throws IOException{
		InputStreamReader read = new InputStreamReader(new FileInputStream(path),"GB2312");//使用io字节流，指定encoding
		BufferedReader br = new BufferedReader(read);
		StringBuffer sb=new StringBuffer();
		String  temp = null;
		while((temp=br.readLine())!=null){
		      sb.append(temp);
		}
		br.close();
		return sb;
	}
}
