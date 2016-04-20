package htmlUnit;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SourceResearch {
	public static void main(String[] args){
		try {
			final WebClient webClient=new WebClient(BrowserVersion.FIREFOX_38);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			HtmlPage page = webClient.getPage("http://eleave.oocl.com/e-leave/js/index.html;jsessionid=WpMZjlmFWdEl-x6NowhTW-MC");
			String response = page.asXml();
			System.out.println(response);
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
