package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

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
			// HtmlAnchor submit = page.getAnchorByName("anchor_name");
			//final HtmlSubmitInput button = form.getInputByName("ctl00_PlaceHolderRightSideBar_ctl00_ctl06_btnLocationFind");
			final HtmlTextInput textField1 = form.getInputByName("ctl00$PlaceHolderRightSideBar$ctl00$schedulesWidget$findYourRouteNew2$txtFrom");
			final HtmlTextInput textField2 = form.getInputByName("ctl00$PlaceHolderRightSideBar$ctl00$schedulesWidget$findYourRouteNew2$txtTo");
			final HtmlHiddenInput textField3 = form.getInputByName("ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_txtFrom_Value");
			final HtmlHiddenInput textField4 = form.getInputByName("ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_txtTo_Value");
			textField1.setValueAttribute("Hong Kong -- Hong Kong");
			textField2.setValueAttribute("Singapore -- Singapore");
			//textField3.setAttribute("type", "text");
			//textField4.setAttribute("type", "text");
			textField3.setValueAttribute("HKHKG;1");
			textField4.setValueAttribute("SGSIN;1");
			page = ((HtmlElement) submit).click();
			String response = page.asXml();
			System.out.println(response);
			System.out.println("/n +++++++++++++++++++++++++++++++++++++++++++++++++++ /n");
			String table = response.substring(response.indexOf("<table>"), response.indexOf("</table>")+8);
			System.out.println(table);
//			String[] temp = response.split("<td>");
//			
//			for(int i=0;i<temp.length;i++){
//				if(temp[i].contains("</td>")){
//					String[] temptemp = temp[i].split("</td>");
//					for(int j=0;j<temptemp.length;j++){
//						if(temptemp[j].contains("<a")){
//							System.out.println("******************************");
//							System.out.println(temptemp[j]);
//						}
//					}
//					
//				}
//			}
			
			//System.out.println(response);
			
		    
			//System.out.println(page.asText());
			webClient.closeAllWindows();
		
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
