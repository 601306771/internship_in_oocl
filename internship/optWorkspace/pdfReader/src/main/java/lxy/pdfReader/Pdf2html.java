package lxy.pdfReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.spi.DirStateFactory.Result;


public class Pdf2html {
	
	public String pdfToHtml(String path)throws IOException{
//		System.out.println(readTxt(path));
		String[] data =  readTxt(path).toString().split("&");
		ArrayList<Double> row = new ArrayList<Double>();
		row.add(Double.parseDouble(data[0].substring(data[0].indexOf("y")+2, data[0].indexOf("value")-1)));
		int index = 0;
		for(int i=0;i<data.length;i++){//获取所有不重复y坐标
			Double y = Double.parseDouble(data[i].substring(data[i].indexOf("y")+2, data[i].indexOf("value")-1));
			if(y>row.get(index)){
				row.add(y);
				index++;
			}
//			Double x = Double.parseDouble(data[i].substring(data[i].indexOf("x")+2, data[i].indexOf(",y")));
//			String value = data[i].substring(data[i].indexOf("value=")+1, data[i].length());
//			System.out.print(data[i]+"|");
//			System.out.println(x+","+value);
		}
		
		//以第二行为标准获取所有字符x坐标
		Map<Double,String> map = new TreeMap<Double,String>(); 
		StringBuffer row2 = new StringBuffer();
		for(int i=0;i<data.length;i++){
			Double y = Double.parseDouble(data[i].substring(data[i].indexOf("y")+2, data[i].indexOf("value")-1));
			Double x = Double.parseDouble(data[i].substring(data[i].indexOf("x")+2, data[i].indexOf(",y")));
			String value = data[i].substring(data[i].indexOf("value=")+6, data[i].length());
			if((double)y==(double)row.get(1)){
				map.put(x, value);
//				row2.append(x+","+value+"&");
			}
		}
		int num = 0;
		int times = 0;
		double second = 0;
		double second2 = 0;
		int flag = 0;
		double temp = 0;
		double[] area = new double[map.size()+10];
		for(Double key : map.keySet()){
			if(flag==1){
				area[num] = temp;
				area[num+1] = key;
				num=num+2;
				if(times==11){
					second2=key;
				}
			}
			flag = 0;
			if("#".equals(map.get(key).trim())){
				times++;
				if(times==10){
					second=key;
				}
				
				flag = 1;
			}
			temp = key;
//			System.out.println(key+","+map.get(key));
		}
		
//		System.out.println(second);
//		for(int i=0;i<area.length;i++){
//			System.out.println(area[i]);
//		}
		
		//对x坐标划分区域
		double[]  areas = new double[20];
		areas[0]=0;
		areas[1]=area[1];
		areas[9]=second;
		areas[10]=second2;
		areas[19]=1000;
		int n = 2;
		for(int i=2;i<=8;i++){
			areas[i] = (area[n]+area[n+1])/2;
			n=n+2;
		}
		n=22;
		for(int i=11;i<=18;i++){
			areas[i] = (area[n]+area[n+1])/2;
			n=n+2;
		}
		
		for(int i=0;i<areas.length;i++){
			System.out.println(areas[i]);
		}
		
		//row行，areas[l列]，data数据
		StringBuffer html = new StringBuffer();
		html.append("<head></head><body><table border=\"1\">");
		for(int j=0;j<row.size();j++){//判断它是哪一行
			StringBuffer area1 = new StringBuffer();
			StringBuffer area2 = new StringBuffer();
			StringBuffer area3 = new StringBuffer();
			StringBuffer area4 = new StringBuffer();
			StringBuffer area5 = new StringBuffer();
			StringBuffer area6 = new StringBuffer();
			StringBuffer area7 = new StringBuffer();
			StringBuffer area8 = new StringBuffer();
			StringBuffer area9 = new StringBuffer();
			StringBuffer area10 = new StringBuffer();
			StringBuffer area11 = new StringBuffer();
			StringBuffer area12 = new StringBuffer();
			for(int i=0;i<data.length;i++){//遍历所有数据
				Double y = Double.parseDouble(data[i].substring(data[i].indexOf("y")+2, data[i].indexOf("value")-1));
				Double x = Double.parseDouble(data[i].substring(data[i].indexOf("x")+2, data[i].indexOf(",y")));
				String value = data[i].substring(data[i].indexOf("value=")+6, data[i].length());
				if(row.get(j).equals(y)){//首先是当前行的数据
					int areanum = selector(areas,x);
					switch(areanum){//当前行数据的那个区域
					case 1:
						area1.append(value);
						break;
					case 2:
						area2.append(value);
						break;
					case 3:
						area3.append(value);
						break;
					case 4:
						area4.append(value);
						break;
					case 5:
						area5.append(value);
						break;
					case 6:
						area6.append(value);
						break;
					case 7:
						area7.append(value);
						break;
					case 8:
						area8.append(value);
						break;
					case 9:
						area9.append(value);
						break;
					case 10:
						area10.append(value);
						break;
					case 11:
						area11.append(value);
						break;
					case 12:
						area12.append(value);
						break;
					}
				}
			}
			String rowData = "<tr>"+"<td>"+area1+"</td>"
					+"<td>"+area2+"</td>"
					+"<td>"+area3+"</td>"
					+"<td>"+area4+"</td>"
					+"<td>"+area5+"</td>"
					+"<td>"+area6+"</td>"
					+"<td>"+area7+"</td>"
					+"<td>"+area8+"</td>"
					+"<td>"+area9+"</td>"
					+"<td>"+area10+"</td>"
					+"<td>"+area11+"</td>"
					+"<td>"+area12+"</td>"
					+"</tr>";
			html.append(rowData);
		}
		html.append("</table></body>");
		String result = html.toString();//.replaceAll("#", " ");
//		System.out.println(html);
		return result;
//		for(int i=0;i<row.size();i++){
//			System.out.println(row.get(i));
//		}
		
		
	}
	
	//判断x坐标区域，共划分12个
	public int selector(double[] areas,double x){
		if(areas[0]<x&&areas[1]>x){
			return 1;
		}
		if(areas[1]<=x&&areas[2]>x){
			return 2;
		}
		if(areas[2]<=x&&areas[4]>x){
			return 3;
		}
		if(areas[4]<=x&&areas[6]>x){
			return 4;
		}
		if(areas[6]<=x&&areas[8]>x){
			return 5;
		}
		if(areas[8]<=x&&areas[9]>=x){
			return 6;
		}
		if(areas[9]<x&&areas[10]>x){
			return 7;
		}
		if(areas[10]<=x&&areas[11]>x){
			return 8;
		}
		if(areas[11]<=x&&areas[13]>x){
			return 9;
		}
		if(areas[13]<=x&&areas[15]>x){
			return 10;
		}
		if(areas[15]<=x&&areas[17]>x){
			return 11;
		}
		if(areas[17]<=x&&areas[19]>x){
			return 12;
		}
		
		
		return -1;
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
