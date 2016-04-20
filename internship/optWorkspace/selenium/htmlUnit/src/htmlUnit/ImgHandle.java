package htmlUnit;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
public class ImgHandle {
	public static void main(String[] args){
		
		try {
			for(int i = 0; i<50; i++){
				imghandle();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void imghandle() throws Exception{
//		String imageUrl = "https://ecom.hamburgsud.com/ecom/hscaptcha.png";
//		String imageUrl = "https://my.mcc.com.sg/captchaapp/captcha?id=edcadf49-c8cd-4cd7-aa5b-310a4545c532";
		String imageUrl = "http://my.maerskline.com/captchaapp/captcha?id=6df357b6-ff10-4326-9d2c-fdaa5796532a";
        URL url = new URL(imageUrl);
        //打开网络输入流
        DataInputStream dis = new DataInputStream(url.openStream());
        String newImageName="C:\\Users\\LINI8\\Desktop\\MAEU\\" + UUID.randomUUID().toString() + ".png";
        //建立一个新的文件
        FileOutputStream fos = new FileOutputStream(new File(newImageName));
        byte[] buffer = new byte[1024];
        int length;
        //开始填充数据
        while( (length = dis.read(buffer))>0){
            fos.write(buffer,0,length);
        }
        dis.close();
        fos.close();
	}
}
