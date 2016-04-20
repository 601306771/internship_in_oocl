package htmlUnit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.parser.Parser;

import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.util.NodeList;
import org.w3c.dom.NamedNodeMap;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Document;
/**
 * Belfast, Northern Ireland -- United Kingdom
 * Ashdod -- Israel
 * GBBFS;1
 * ILASH;1
 * @author LINI8
 *
 */
public class HtmlUnit {
	public static void main(String[] args){
		try {
			final WebClient webClient=new WebClient(BrowserVersion.FIREFOX_38);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			 
			HtmlPage page = webClient.getPage("http://www.zim.com/pages/findyourroute.aspx");
			//page.executeJavaScript("");
			final HtmlForm form = page.getFormByName("aspnetForm");
			DomElement  submit = page.getElementById("ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_btnFind");
			final HtmlTextInput textField1 = form.getInputByName("ctl00$PlaceHolderRightSideBar$ctl00$schedulesWidget$findYourRouteNew2$txtFrom");
			final HtmlTextInput textField2 = form.getInputByName("ctl00$PlaceHolderRightSideBar$ctl00$schedulesWidget$findYourRouteNew2$txtTo");
			final HtmlHiddenInput textField3 = form.getInputByName("ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_txtFrom_Value");
			final HtmlHiddenInput textField4 = form.getInputByName("ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_txtTo_Value");
			textField1.setValueAttribute("Hong Kong -- Hong Kong");
			textField2.setValueAttribute("Singapore -- Singapore");
			textField3.setValueAttribute("HKHKG;1");
			textField4.setValueAttribute("SGSIN;1");
			page = ((HtmlElement) submit).click();
			String response = page.asXml();
			
			/*//成功捕获目标url  法1
			String table  = response.substring(response.indexOf("<table>"), response.indexOf("Summary View")+8);
			String[] slipt = table.split(" ");
			for(int i=0;i<slipt.length;i++){
				if(slipt[i].contains("www.zim.com/services/pages/zimlinenetwork.aspx")){
					System.out.println("***************");
					System.out.println(slipt[i]);
				}
			}*/
			
	
			
		
			
			//成功捕获目标url  法2
			java.util.List<HtmlAnchor> achList = page.getAnchors();  
			List<String> citycode = new ArrayList<String>();
            for (HtmlAnchor ach : achList) { 
            	//获取url
                String url = ach.getHrefAttribute();  
                if(url.contains("http://www.zim.com/pages/findanagent.aspx")&&url.contains("citycode")){
                	String[] temp = url.split("&"); 
                	for(int i=0;i<temp.length;i++){
                		if(temp[i].contains("citycode")){
                			citycode.add(temp[i]);
                		}
                	}
                	System.out.println(url);
                }
            }
            
            Map<Integer,Map> rows = new HashMap<Integer,Map>();
            String tempDate = null;
            String tempCitycode = null;
            int rowNumber = 0;
            int flag = 0;
            //获取表格元素 row cell
			DomNodeList<DomElement> tables = page.getElementsByTagName("table");
			final HtmlTable table = (HtmlTable) tables.get(tables.size() - 1);
         	for (final HtmlTableRow row : table.getBodies().get(0).getRows()) {
				int cellNum = 0;
				Map<Integer,String> cells = new HashMap<Integer,String>();
				for (final HtmlTableCell cell : row.getCells()) {
					String str = cell.asText();
					Pattern p = Pattern.compile("\t|\r|\n");//"\\s*|\t|\r|\n"
		            Matcher m = p.matcher(str);
		            str = m.replaceAll(" ");
		            if(str.contains(",")){
		            	String[] temp = str.split(" ");
		            	str = temp[0] + temp[1];
		            }
					if(rowNumber%2!=0&&str.contains("Feeder")){
						tempDate = row.getCells().get(6).asText();
						flag = 1;
					}
					if(flag == 0&&rowNumber >= 1){
						cells.put(7, citycode.get(rowNumber-1));
					}else if(flag == 1&&rowNumber >= 1){
						if(rowNumber%2!=0){
							cells.put(7, citycode.get((rowNumber+1)/2));
						}else{
							cells.put(7, citycode.get(rowNumber/2));
						}
					}
					
					cells.put(cellNum,str);
					
					
					cellNum++;
				} 	
				if(rowNumber >= 1&&rowNumber%2==0)
				{
					cells.put(6,tempDate);
				}
				rows.put(rowNumber,cells);
				rowNumber++;
			}
	
			
			for(int key : rows.keySet()){
				System.out.println("");
				System.out.println("------------------------------------------");
				for(int j=0;j<rows.get(key).size();j++){
					System.out.print(rows.get(key).get(j)+" | ");
				}
			}
			
			webClient.closeAllWindows();
		
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
