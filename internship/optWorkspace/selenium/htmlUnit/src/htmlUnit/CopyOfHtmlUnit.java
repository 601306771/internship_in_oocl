package htmlUnit;

import java.io.FileOutputStream;
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
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
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
public class CopyOfHtmlUnit {
	public static void main(String[] args){
		try {
			final WebClient webClient=new WebClient(BrowserVersion.FIREFOX_38);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setTimeout(10000);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			 
			HtmlPage page = webClient.getPage("http://www.zim.com/pages/findyourroute.aspx?origincode=HKHKG;1&origincodetext=Hong%20Kong%20--%20Hong%20Kong&destcode=SGSIN;1&destcodetext=Singapore%20--%20Singapore&fromdate=05/01/2016&todate=30/03/2016&mode=1&searchdimention=0&schedule=view0");
			String response = page.asXml();
//			outputfile(response,"C:/Users/LINI8/Desktop/htmlunitpage1.html");
//			System.out.print(response);
			try{
				DomElement lis = (DomElement) page.getByXPath("//tr[@class='gpager']").get(0);
				List<HtmlElement> allLis = lis.getElementsByTagName("li");
				// WebElement lis = driver.findElement(By.className("gpager"));
				// List<WebElement> allLis = lis.findElements(By.tagName("li"));
				int detailNum = allLis.size();
				for (int i = 1; i < detailNum; i++) {
					List<HtmlElement> ele = allLis.get(i).getElementsByTagName("a");
					if (ele != null && ele.size() != 0) {
						HtmlPage page2 = (HtmlPage) ele.get(0).click();
//						webClient.waitForBackgroundJavaScript(1000*6);  
//				        webClient.setJavaScriptTimeout(0);  
						String liResponse = page2.asXml();
//						System.out.print(liResponse);
						outputfile(liResponse,"C:/Users/LINI8/Desktop/htmlunitpage2.html");
					}
					/*response = page.asXml();
					responseList.add(response);*/
					lis = (DomElement) page.getByXPath("//tr[@class='gpager']").get(0);
					allLis = lis.getElementsByTagName("li");
				}
			} catch (Exception e) {
				System.out.println("get fail");
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
	
	public static void outputfile(String str,String path){
		 byte[] buff=new byte[]{};    
        buff=str.getBytes();  
        FileOutputStream out;
		try {
			out = new FileOutputStream(path);
			out.write(buff,0,buff.length);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	

}
