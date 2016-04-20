package PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentCatalog;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.pdmodel.PDResources;
import org.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.pdfbox.util.PDFTextStripper;

public class PdfImgReader {
	

	public void readFdf(String file) throws Exception {
		// 是否排序
		boolean sort = false;
		// pdf文件名
		String pdfFile = file;
		// 输入文本文件名称
		String textFile = null;
		// 编码方式
		String encoding = "UTF-8";
		// 开始提取页数
		int startPage = 1;
		// 结束提取页数
		int endPage = Integer.MAX_VALUE;
		// 文件输入流，生成文本文件
		Writer output = null;
		// 内存中存储的PDF Document
		PDDocument document = null;
		try {
			try {
				// 首先当作一个URL来装载文件，如果得到异常再从本地文件系统//去装载文件
				URL url = new URL(pdfFile);
				// 注意参数已不是以前版本中的URL.而是File。
				document = PDDocument.load(pdfFile);
				// 获取PDF的文件名
				String fileName = url.getFile();
				// 以原来PDF的名称来命名新产生的txt文件
				if (fileName.length() > 4) {
					File outputFile = new File(fileName.substring(0,
							fileName.length() - 4)
							+ ".txt");
					textFile = outputFile.getName();
				}
			} catch (MalformedURLException e) {
				// 如果作为URL装载得到异常则从文件系统装载
				// 注意参数已不是以前版本中的URL.而是File。
				document = PDDocument.load(pdfFile);
				if (pdfFile.length() > 4) {
					textFile = pdfFile.substring(0, pdfFile.length() - 4)
							+ ".txt";
				}
			}
			// 文件输入流，写入文件倒textFile
			output = new OutputStreamWriter(new FileOutputStream(textFile),
					encoding);
			
			PDDocumentCatalog cata = document.getDocumentCatalog();
            List pages = cata.getAllPages();
            int count = 1;
            for( int i = 0; i < pages.size(); i++ )
            {
                PDPage page = ( PDPage ) pages.get( i );
                if( null != page )
                {
                    PDResources res = page.findResources();
                     
                    //获取页面图片信息
//                    Map imgs = res.getImages();
//                    if( null != imgs )
//                    {
//                        Set keySet = imgs.keySet();
//                        Iterator it = keySet.iterator();
//                        while( it.hasNext() )
//                        {
//                            Object obj =  it.next();
//                            PDXObjectImage img = ( PDXObjectImage ) imgs.get( obj );
//                            img.write2file( textFile + count );
//                            count++;
//                        }
//                        System.out.print(count);
//                    }
                    
                    Map fonts = res.getFonts();
                    if( null != fonts )
                    {
                        Set keySet = fonts.keySet();
                        Iterator it = keySet.iterator();
                        while( it.hasNext() )
                        {
                            Object obj =  it.next();
//                            PDXObjectImage img = ( PDXObjectImage ) fonts.get( obj );
//                            img.write2file( textFile + count );
                            System.out.print(it);
                            count++;
                        }
                        System.out.print(count);
                    }
                }
            }
			
		} finally {
			if (output != null) {
				// 关闭输出流
				output.close();
			}
			if (document != null) {
				// 关闭PDF Document
				document.close();
			}
			System.out.print("--finish--");
		}
	}

	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PdfImgReader pdfReader = new PdfImgReader();
		try {
			// 取得E盘下的SpringGuide.pdf的内容
			pdfReader.readFdf("C:\\Users\\LINI8\\Desktop\\pdfRead\\icl-sailing-schedule-21216.pdf");
			System.out.print("--finish--");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}