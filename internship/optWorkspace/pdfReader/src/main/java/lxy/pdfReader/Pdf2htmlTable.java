package lxy.pdfReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Pdf2htmlTable {
	private String result;
	
	public String pdfToHtml(String path)throws IOException{
//		System.out.println(readTxt(path));
		String[] data =  readTxt(path).toString().split("&");
//		ArrayList<Double> row = new ArrayList<Double>();
		Map<Double,String> rows =new TreeMap<Double,String>(); 
//		row.add(Double.parseDouble(data[1].substring(data[1].indexOf("y")+2, data[1].indexOf("value")-1)));
		
		int index = 0;
		for(int i=0;i<data.length;i++){//获取所有不重复y坐标
			Double y = Double.parseDouble(data[i].substring(data[i].indexOf("y")+2, data[i].indexOf("value")-1));
//			if(y>row.get(index)){
//				row.add(y);
//				index++;
//			}
			rows.put(y, "0");
		}
		
		ArrayList<Double> row = new ArrayList<Double>();
		for(Double key : rows.keySet()){
			row.add(key);
		}
		
		
		List<String> list = new ArrayList<String>(); 
		for(int i=0;i<data.length;i++){
			String x = data[i].substring(data[i].indexOf("x")+2, data[i].indexOf(",y"));
			String xNext = String.valueOf(0);
			if(i<data.length-1){
				xNext = data[i+1].substring(data[i+1].indexOf("x")+2, data[i+1].indexOf(",y"));
			}
			String value = data[i].substring(data[i].indexOf("value=")+6, data[i].length());
			if("#".equals(value.trim())){
				if(0<i&&i<data.length)
				System.out.println(data[i-1].substring(data[i-1].indexOf("value=")+6, data[i-1].length())+value+data[i+1].substring(data[i+1].indexOf("value=")+6, data[i+1].length()));
				String str = x +","+ xNext;
				list.add(str);
			}
		}
		
	
		
		
		Map<String,Integer> map =new TreeMap<String,Integer>(); 
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
		
		List<Double> sections = new ArrayList<Double>(); 
		sections.add((double) 0);
		for(String key:map.keySet()){
			if(map.get(key)>=3){
				double a = Double.parseDouble(key.substring(0, key.indexOf(",")));
				double b = Double.parseDouble(key.substring(key.indexOf(",")+1,key.length()));
				double c = (a+b)/2;
				sections.add(c);
			}
		}
		sections.add((double) 1000);
		
		
		StringBuffer html = new StringBuffer();
		html.append("<head></head><body><table border=\"1\">");
		for(int j=0;j<row.size();j++){//判断它是哪一行
			StringBuffer[] areas = new StringBuffer[sections.size()-1]; 
			
			for(int i=0;i<areas.length;i++){
				areas[i] = new StringBuffer();
			}
			for(int i=0;i<data.length;i++){//遍历所有数据
				Double y = Double.parseDouble(data[i].substring(data[i].indexOf("y")+2, data[i].indexOf("value")-1));
				Double x = Double.parseDouble(data[i].substring(data[i].indexOf("x")+2, data[i].indexOf(",y")));
				String value = data[i].substring(data[i].indexOf("value=")+6, data[i].length());
				if(row.get(j).equals(y)){//首先是当前行的数据
					for(int key=1;key<sections.size();key++){
						if(x>sections.get(key-1)&&x<sections.get(key)){
							areas[key-1].append(value);
						}
					}
				}
			}
			
			html.append("<tr>");
			for(int i=0;i<areas.length;i++){
				html.append("<td>"+areas[i]+"</td>");
			}
			html.append("</tr>");
		}
		html.append("</table></body>");
		String result = html.toString();
//		for(int i=1;i<sections.size();i++){
//			System.out.println(sections.get(i));
//			
//		}
		return result;
	}
	
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
