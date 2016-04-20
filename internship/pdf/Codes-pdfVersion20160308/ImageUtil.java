package com.cargosmart.common.pdfVersion20160308;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


public class ImageUtil {
	private Properties config;
	private  String[] RGBIGNORE;
	private  int R ;
	private  int G ;
	private  int B ;
	private  int VERTICAL_LENGTH;// 40为设定值 连续竖向 有效像素点长度     用于筛选有效的垂直线长度
	private  int HORIZONTAL_LENGTH;// 250为设定值 连续横向  有效像素点长度   用于筛选有效的水平线长度
	private  int VERTICAL_SPLIT_TABLE_SCALE;//5为设定值 第一列垂直线 之间的距离 大于 this value 为不同表格   用于分割表格
	private  int REMOVE_PIXELS_STANDARD;// 如果两个像素距离太近，则移除后一个   用于筛选每个td的起点,筛选X,Y标准
	private  int CHOOSE_PIXELS_STANDARD;// 修正坐标   在标准值范围内的数值，修改为标准值 
	
	public ImageUtil() {
		readConfig();
		this.R = Integer.parseInt(config.getProperty("R"));
		this.B = Integer.parseInt(config.getProperty("B"));
		this.G = Integer.parseInt(config.getProperty("G"));
		this.VERTICAL_LENGTH = Integer.parseInt(config.getProperty("VERTICAL_LENGTH"));
		this.HORIZONTAL_LENGTH = Integer.parseInt(config.getProperty("HORIZONTAL_LENGTH"));
		this.VERTICAL_SPLIT_TABLE_SCALE = Integer.parseInt(config.getProperty("VERTICAL_SPLIT_TABLE_SCALE"));
		this.REMOVE_PIXELS_STANDARD = Integer.parseInt(config.getProperty("REMOVE_PIXELS_STANDARD"));
		this.CHOOSE_PIXELS_STANDARD = Integer.parseInt(config.getProperty("CHOOSE_PIXELS_STANDARD"));
		String ri = config.getProperty("RGBIGNORE");
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(ri);
		ri = m.replaceAll("");
		this.RGBIGNORE = ri.split("&");
		
	}
	
	/**
	 * read config properties
	 */
	public void readConfig(){
		String filePath = (System.getProperty("user.dir") + "/").replace("\\", "/");  
		String CONFIG_PATH = filePath + "PdfImgConfig.properties";
		config = new Properties(); 
		try {
			InputStream configpath = new FileInputStream(CONFIG_PATH);
			config.load(configpath); 
			configpath.close();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}
	
	

	/**
	 * convert img to gray
	 * 
	 * @param imgFile
	 *            img path
	 * @return
	 */
	public  BufferedImage convert2gray(String imgFile) {
		File file = new File(imgFile);
		FileInputStream fis = null;
		BufferedImage bufferedImg = null;
		try {
			fis = new FileInputStream(file);
			bufferedImg = ImageIO.read(fis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int width = bufferedImg.getWidth();
		int height = bufferedImg.getHeight();

		int pixels[] = new int[width * height];
		bufferedImg.getRGB(0, 0, width, height, pixels, 0, width);
		int newPixels[] = new int[width * height];

		for (int i = 0; i < width * height; i++) {
			int r = (pixels[i] >> 16) & 0xff;
			int g = (pixels[i] >> 8) & 0xff;
			int b = (pixels[i]) & 0xff;

			// 方法1，图像很奇怪
			int gray = (int) (0.229 * r + 0.587 * g + 0.114 * b);
			newPixels[i] = (gray << 16) + (gray << 8) + gray;
			// 方法2，图像偏蓝，不是灰度图
			// newPixels[i] = gray;
			for (int key = 0; key < RGBIGNORE.length; key++) {
				int ri = Integer.parseInt(RGBIGNORE[key].split(",")[0]);
				int gi = Integer.parseInt(RGBIGNORE[key].split(",")[1]);
				int bi = Integer.parseInt(RGBIGNORE[key].split(",")[2]);
				if (r == ri && g == gi && b == bi) {
					newPixels[i] = 0xffffff;
				}
			}
		}

		// 基于 newPixels 构造一个 BufferedImage
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, newPixels, 0, width);
		newPixels = null;

		try {
			// 写入磁盘
			imgFile = imgFile.replace(".png", "Gray.png");
			writeImageToFile(imgFile, bi);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("change one img to gray..   ok---");
		return bi;

	}

	/**
	 * convert img to black
	 * 
	 * @param imgFile
	 * @return
	 */
	public  BufferedImage convert2black(String imgFile) {
		File file = new File(imgFile);
		FileInputStream fis = null;
		BufferedImage bufferedImg = null;
		try {
			fis = new FileInputStream(file);
			bufferedImg = ImageIO.read(fis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int width = bufferedImg.getWidth();
		int height = bufferedImg.getHeight();

		int pixels[] = new int[width * height];
		bufferedImg.getRGB(0, 0, width, height, pixels, 0, width);
		int newPixels[] = new int[width * height];

		for (int i = 0; i < width * height; i++) {
			int r = (pixels[i] >> 16) & 0xff;
			int g = (pixels[i] >> 8) & 0xff;
			int b = (pixels[i]) & 0xff;

			if (r < R && g < G && b < B) {
				/**
				 * 这里是判断通过，则把该像素换成白色
				 */
				newPixels[i] = 0xffffff;
			} else {
				newPixels[i] = 0;
			}
		}

		// 基于 newPixels 构造一个 BufferedImage
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, newPixels, 0, width);
		newPixels = null;

		try {
			// 写入磁盘
			writeImageToFile(imgFile, bi);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("change one img to black..  ok--");
		return bi;
	}


	/**
	 * convert 2 Html
	 * 
	 * @param imgFile
	 * @return
	 */
	public  String convert2Html(String imgFile) {
		File file = new File(imgFile);
		FileInputStream fis = null;
		BufferedImage bufferedImg = null;
		try {
			fis = new FileInputStream(file);
			bufferedImg = ImageIO.read(fis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int width = bufferedImg.getWidth();
		int height = bufferedImg.getHeight();

		int pixels[] = new int[width * height];
		bufferedImg.getRGB(0, 0, width, height, pixels, 0, width);
		int newPixels[] = new int[width * height];
		List white = new ArrayList();
		StringBuffer linePosition = new StringBuffer();

		// rgb 获取所有竖线
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				int i = h * width + w;
				int r = (pixels[h * width + w] >> 16) & 0xff;
				int g = (pixels[h * width + w] >> 8) & 0xff;
				int b = (pixels[h * width + w]) & 0xff;

				if (r > 0 && g > 0 && b > 0) {
					if (h > 0 && pixels[i] == pixels[(h - 1) * width + w]) {// 连续的点为直线
						if ("&".equals(linePosition.substring(linePosition.length() - 1,
								linePosition.length()))) {
							linePosition.append((h - 1) * width + w + ",");
						}
						linePosition.append(i + ",");
					} else {
						linePosition.append("&");
					}
				}
			}
		}
		// 判断每一连续点长度，大于某一设定值为有效数据
		String[] lines = linePosition.toString().split("&");
		for (int i = 0; i < lines.length; i++) {
			String[] posh = lines[i].split(",");
			if (posh.length > VERTICAL_LENGTH) {// 40为设定值 连续竖向像素点长度
				for (int j = 0; j < posh.length; j++) {
					int num = Integer.parseInt(posh[j]);
					white.add(num);
				}
			}
		}

		
		// rgb 获取所有横线
		StringBuffer linePosition2 = new StringBuffer();
		for (int i = 0; i < width * height; i++) {
			int r = (pixels[i] >> 16) & 0xff;
			int g = (pixels[i] >> 8) & 0xff;
			int b = (pixels[i]) & 0xff;
			if (r > 0 && g > 0 && b > 0) {
				if (i > 0 && pixels[i] == pixels[i - 1]) {// 连续的点为横线
					if ("&".equals(linePosition2.substring(linePosition2.length() - 1, linePosition2.length()))) {
						linePosition2.append(i - 1 + ",");
					}
					linePosition2.append(i + ",");
				} else {
					linePosition2.append("&");
				}
			}
		}
		// 判断每一连续点长度，大于某一设定值为有效数据
		String[] lines2 = linePosition2.toString().split("&");
		for (int i = 0; i < lines2.length; i++) {
			String[] posw = lines2[i].split(",");
			if (posw.length > HORIZONTAL_LENGTH) {// 250为设定值 连续横向像素点长度
				for (int j = 0; j < posw.length; j++) {
					int num = Integer.parseInt(posw[j]);
					white.add(num);
				}
			}
		}

		
		// 初始化 所有点为黑色
		for (int i = 0; i < width * height; i++) {
			newPixels[i] = 0;
		}

		
		// 处理方法，所有横线和竖线的交叉点为白色，获得所有交点坐标
		for (int i = 0; i < white.size(); i++) {// 横线竖线为白色，交点染红
			if (newPixels[(Integer) white.get(i)] == 0xffffff) {
				newPixels[(Integer) white.get(i)] = 0xF82619;
			} else {
				newPixels[(Integer) white.get(i)] = 0xffffff;
			}
		}
		for (int i = 0; i < width * height; i++) {// 交点染白，其他变黑
			if (newPixels[i] == 0xF82619) {
				newPixels[i] = 0xffffff;
			} else {
				newPixels[i] = 0;
			}
		}
		

		
		// 两次去重，保证点为单个点
		// 第一次去重复 把相邻的点转为一点
		int[] points = SinglePoints(newPixels, width);

		for (int i = 0; i < width * height; i++) {
			newPixels[i] = 0;
		}
		for (int i = 0; i < points.length; i++) {
			newPixels[points[i]] = 0xffffff;
		}
		// 第二次去重复 把相邻的点转为一点
		int[] newPoints = SinglePoints(newPixels, width);

		for (int i = 0; i < width * height; i++) {
			newPixels[i] = 0;
		}
		for (int i = 0; i < newPoints.length; i++) {
			newPixels[newPoints[i]] = 0xffffff;
		}

		// 分割表格 把不同表格分割开来
		List<Integer> ySplit = splitForms(linePosition, width);
		ySplit.add(0, 0);// 最小区域由0开始
		ySplit.add(ySplit.size(), height);// 最大区域是图片的最大高度

		// 转化为表格 带有位置参数
		String table = toTableAreas(newPoints, width, ySplit);

		// 基于 newPixels 构造一个 BufferedImage output as pionts img , use this when
		// test
//		 BufferedImage bi = new BufferedImage(width, height,
//		 BufferedImage.TYPE_INT_RGB);
//		 bi.setRGB(0, 0, width, height, newPixels, 0, width);
//		 newPixels = null;
//		
//		 try {
//		 // 写入磁盘
//		 imgFile = "C:/Users/LINI8/Desktop/pdfTemp/Joints.png";
//		 writeImageToFile(imgFile, bi);
//		 } catch (IOException e) {
//		 e.printStackTrace();
//		 }

		return table;
	}

	
	/**
	 * use lines to split different tables
	 * 
	 * @param linePosition
	 * @param width
	 * @return
	 */
	public  List<Integer> splitForms(StringBuffer linePosition, int width) {
		Map<Integer, String> yCoordinates = new TreeMap<Integer, String>();
		// 获取数据
		String[] lines = linePosition.toString().split("&");
		for (int i = 0; i < lines.length; i++) {
			String[] posh = lines[i].split(",");

			if (posh.length > VERTICAL_LENGTH) {//  40为参数 超过这个值为有效的线
				for (int j = 0; j < posh.length; j++) {
					int num = Integer.parseInt(posh[j]);
					int x = num % width;
					int y = (num - x) / width;
					yCoordinates.put(y, "");
				}
			}
		}

		// 不连续点 距离超过 参数
		List<Integer> ySplit = new ArrayList<Integer>();
		int last = -1;
		for (Integer key : yCoordinates.keySet()) {
			if (last != -1 && key - last > VERTICAL_SPLIT_TABLE_SCALE) { 
				ySplit.add((key + last) / 2);
			}
			last = key;
		}

		return ySplit;
	}

	// ////////////////////////to table areas/////////////////////
	/**
	 * append table with positions
	 * 
	 * @param newPoints
	 *            the final points to get datas
	 * @param width
	 *            img width
	 * @param ySplit
	 *            split to different table
	 * @return
	 */
	public  String toTableAreas(int[] newPoints, int width, List<Integer> ySplit) {

		// 转化为（x，y）坐标形式
		String[] coordinates = new String[newPoints.length];
		int[] xs = new int[newPoints.length];
		int[] ys = new int[newPoints.length];
		for (int i = 0; i < newPoints.length; i++) {
			int x = newPoints[i] % width;
			int y = (newPoints[i] - x) / width;
			xs[i] = x;
			ys[i] = y;
			coordinates[i] = x + "," + y;
		}

		// 筛选x标准  获得x坐标的标准
		for (int i = 0; i < xs.length; i++) {
			for (int j = i + 1; j < xs.length; j++) {  
				if (xs[j] != -1 && Math.abs(xs[i] - xs[j]) < REMOVE_PIXELS_STANDARD) { // 如果两个像素距离太近，则移除后一个
					xs[j] = -1;
				}
			}
		}
		List<Integer> xStandard = new ArrayList<Integer>();
		for (int i = 0; i < xs.length; i++) {
			if (xs[i] != -1) {
				xStandard.add(xs[i]);
			}
		}

		// 筛选y标准   获得y坐标的标准
		for (int i = 0; i < ys.length; i++) {
			for (int j = i + 1; j < ys.length; j++) {
				if (ys[j] != -1 && Math.abs(ys[i] - ys[j]) < REMOVE_PIXELS_STANDARD) { // 如果两个像素距离太近，则移除后一个
					ys[j] = -1;
				}
			}
		}
		List<Integer> yStandard = new ArrayList<Integer>();
		for (int i = 0; i < ys.length; i++) {
			if (ys[i] != -1) {
				yStandard.add(ys[i]);
			}
		}
		Collections.sort(yStandard);

		
		// 修正坐标   在标准值范围内的数值，修改为标准值  
		for (int i = 0; i < coordinates.length; i++) {
			int x = Integer.parseInt(coordinates[i].split(",")[0]);
			int y = Integer.parseInt(coordinates[i].split(",")[1]);
			for (int k = 0; k < xStandard.size(); k++) {
				if (Math.abs(xStandard.get(k) - x) < CHOOSE_PIXELS_STANDARD) {// x标准
					x = xStandard.get(k);
				}
			}

			for (int k = 0; k < yStandard.size(); k++) {
				if (Math.abs(yStandard.get(k) - y) < CHOOSE_PIXELS_STANDARD) {// y标准
					y = yStandard.get(k);
				}
			}
			coordinates[i] = x + "," + y;
		}

		// 所有同一水平线上的点为一个数组， 即y值相同 。 放入组的map
		Map<Integer, List<String>> coordinateMap = new TreeMap<Integer, List<String>>();
		for (int i = 0; i < yStandard.size(); i++) {
			List<String> coordinateGroup = new ArrayList<String>();
			for (int j = 0; j < coordinates.length; j++) {
				int y = Integer.parseInt(coordinates[j].split(",")[1]);
				if (yStandard.get(i) == y) {
					coordinateGroup.add(coordinates[j]);
				}
			}
			coordinateMap.put(i, coordinateGroup);
		}

		// 获取每一区域的x分割点，即每个表格 （y的一个区间）分开来处理
		Map<Integer, Map<Integer, Integer>> ySplit_xmap = new TreeMap<Integer, Map<Integer, Integer>>();// <组，
																										// <坐标，合并格数>
																										// >
		for (int num = 0; num < ySplit.size() - 1; num++) {// 每个区域
															// 有ySplit.size() -
															// 1 个组

			Map<Integer, Integer> xmap = new TreeMap<Integer, Integer>();// 当前区域所有x

			for (int i = 0; i < coordinateMap.size() - 1; i++) {// 所有坐标组，遍历所有行
				if (coordinateMap.get(i).size() > 1) {
					int yPoint = Integer.parseInt(coordinateMap.get(i).get(0).split(",")[1]);// 每一组的y坐标
					if (ySplit.get(num) < yPoint && yPoint < ySplit.get(num + 1)) {
						// 当前行的x数组排序
						for (int m = 0; m < coordinateMap.get(i).size(); m++) {
							int x = Integer.parseInt(coordinateMap.get(i).get(m).split(",")[0]);
							xmap.put(x, 0);
						}
					}
				}
			}// end for 所有坐标组，遍历所有行
			int i = 1;
			for (Integer key : xmap.keySet()) {
				xmap.put(key, i);
				i++;
			}
			ySplit_xmap.put(num, xmap);
		}

		// 上下对比x个数，以x少的为标准画区域
		StringBuffer table = new StringBuffer();
		table.append("<head></head>\r\n<body>\r\n");
		for (int num = 0; num < ySplit.size() - 1; num++) {// 划分表格的个数
			table.append("\r\n**************************Table : " + (num + 1) + "******************** \r\n");
			table.append("<table  border=\"1\">\r\n");

			for (int i = 0; i < coordinateMap.size() - 1; i++) {// 遍历每一组
				if (coordinateMap.get(i).size() > 0 && coordinateMap.get(i + 1).size() > 0) {// 确保组存在
					int yPoint = Integer.parseInt(coordinateMap.get(i).get(0).split(",")[1]);// 每一组的y坐标
					int yPoint2 = Integer.parseInt(coordinateMap.get(i + 1).get(0).split(",")[1]);// 每一组的y坐标

					if (ySplit.get(num) < yPoint && yPoint < ySplit.get(num + 1) &&
							ySplit.get(num) < yPoint2 && yPoint2 < ySplit.get(num + 1)) {// 当前组（当前行）在第n个表格范围内
						table.append("<tr>");
						if (coordinateMap.get(i).size() <= coordinateMap.get(i + 1).size()) {// i
																								// 往
																								// 下
																								// 划线
							int y1 = Integer.parseInt(coordinateMap.get(i).get(0).split(",")[1]);
							int y2 = Integer.parseInt(coordinateMap.get(i + 1).get(0).split(",")[1]);
							int y = y2 - y1;// y的范围

							// 当前行的x数组排序
							List<Integer> xGroup = new ArrayList<Integer>();
							for (int m = 0; m < coordinateMap.get(i).size(); m++) {
								int x = Integer.parseInt(coordinateMap.get(i).get(m).split(",")[0]);
								xGroup.add(x);
							}
							Collections.sort(xGroup);

							// 每一点的下一点，右一点，右下一点都有数据，为一格
							for (int n = 0; n < xGroup.size() - 1; n++) {
								int x = xGroup.get(n + 1) - xGroup.get(n);
								if (coordinateMap.get(i + 1).contains(xGroup.get(n) + "," + y2) &&
										coordinateMap.get(i + 1).contains(xGroup.get(n + 1) + "," + y2) &&
										x > 0) {
									int colspan = ySplit_xmap.get(num).get(xGroup.get(n + 1)) -
											ySplit_xmap.get(num).get(xGroup.get(n));
									table.append("<td  colspan=" + colspan + ">" + "[" + xGroup.get(n) + "," +
											y1 + "," + x + "," + y + "]" + "</td>");
								}
							}
						} else {// i+1 往上 划线
							int y1 = Integer.parseInt(coordinateMap.get(i).get(0).split(",")[1]);
							int y2 = Integer.parseInt(coordinateMap.get(i + 1).get(0).split(",")[1]);
							int y = y2 - y1;// y的范围

							// 当前行的x数组排序
							List<Integer> xGroup = new ArrayList<Integer>();
							for (int m = 0; m < coordinateMap.get(i + 1).size(); m++) {
								int x = Integer.parseInt(coordinateMap.get(i + 1).get(m).split(",")[0]);
								xGroup.add(x);
							}
							Collections.sort(xGroup);

							// 每一点的上一点，右一点，右上一点都有数据，为一格
							for (int n = 0; n < xGroup.size() - 1; n++) {
								int x = xGroup.get(n + 1) - xGroup.get(n);
								if (coordinateMap.get(i).contains(xGroup.get(n) + "," + y1) &&
										coordinateMap.get(i).contains(xGroup.get(n + 1) + "," + y1) && x > 0) {
									int colspan = ySplit_xmap.get(num).get(xGroup.get(n + 1)) -
											ySplit_xmap.get(num).get(xGroup.get(n));
									table.append("<td colspan=" + colspan + ">" + "[" + xGroup.get(n) + "," +
											y1 + "," + x + "," + y + "]" + "</td>");
								}
							}
						}
						table.append("</tr>\r\n");
					}// end if
				}// end if 确保组存在
			}// end for 遍历每一组
			table.append("</table>\r\n<br><br><br>\r\n");

		}

		table.append("</body>");
		return table.toString();
	}

	
	
	/**
	 * change points to Single Point, the points around a points will be union
	 * to one
	 * 
	 * @param pixels
	 * @param width
	 * @return
	 */
	
	public static int[] SinglePoints(int[] pixels, int width) {
		// 获取所有有效点
		List<Integer> points = new ArrayList<Integer>();
		for (int i = 0; i < pixels.length; i++) {
			if (pixels[i] == 0xffffff) {
				points.add(i);
			}
		}

		List<List<Integer>> arrays = new ArrayList<List<Integer>>();
		// 获取每个点周围所有有效点，加入数组
		for (int i = 0; i < points.size(); i++) {
			List<Integer> around = new ArrayList<Integer>();
			int p = points.get(i);
			around.add(p);

			int p0 = p - 1 - width;
			if (p0 > 0 && p0 < pixels.length && pixels[p0] == 0xffffff) {
				around.add(p0);
			}

			int p1 = p - width;
			if (p1 > 0 && p1 < pixels.length && pixels[p1] == 0xffffff) {
				around.add(p1);
			}

			int p2 = p + 1 - width;
			if (p2 > 0 && p2 < pixels.length && pixels[p2] == 0xffffff) {
				around.add(p2);
			}

			int p3 = p - 1;
			if (p3 > 0 && p3 < pixels.length && pixels[p3] == 0xffffff) {
				around.add(p3);
			}

			int p5 = p + 1;
			if (p5 > 0 && p5 < pixels.length && pixels[p5] == 0xffffff) {
				around.add(p5);
			}

			int p6 = p - 1 + width;
			if (p6 > 0 && p6 < pixels.length && pixels[p6] == 0xffffff) {
				around.add(p6);
			}

			int p7 = p + width;
			if (p7 > 0 && p7 < pixels.length && pixels[p7] == 0xffffff) {
				around.add(p7);
			}

			int p8 = p + 1 + width;
			if (p8 > 0 && p8 < pixels.length && pixels[p8] == 0xffffff) {
				around.add(p8);
			}

			arrays.add(around);
		}

		// 每个有重复数据的数组合并成一个新数组
		for (int i = 0; i < arrays.size(); i++) {
			for (int j = i + 1; j < arrays.size(); j++) {// 每两个对比
				if (i != j && intersect(arrays.get(i), arrays.get(j))) {// 有交集
					arrays.set(i, union(arrays.get(i), arrays.get(j)));
					arrays.remove(j);
					j--;
				}
			}
		}

		// 计算每一个数组的均值
		int[] newPixels = new int[arrays.size()];
		for (int i = 0; i < arrays.size(); i++) {
			int x = 0;
			int y = 0;
			for (int j = 0; j < arrays.get(i).size(); j++) {
				int thisX = arrays.get(i).get(j) % width;
				int thisY = (arrays.get(i).get(j) - thisX) / width;
				x += thisX;
				y += thisY;
			}

			x = x / arrays.get(i).size();
			y = y / arrays.get(i).size();

			newPixels[i] = x + y * width;

		}

		return newPixels;
	}

	
	/**
	 * 数组的并集 求两个字符串数组的并集，利用set的元素唯一性
	 * 
	 * @param arrayList1
	 * @param arrayList2
	 * @return
	 */
	public static List<Integer> union(List<Integer> arrayList1, List<Integer> arrayList2) {

		Set<Integer> set = new HashSet<Integer>();
		for (Integer in : arrayList1) {
			set.add(in);
		}

		for (Integer in : arrayList2) {
			set.add(in);
		}
		List<Integer> list = new ArrayList<Integer>(set);
		return list;

	}

	/**
	 * 数组的交集
	 * 
	 * @param list1
	 * @param list2
	 */
	private static boolean intersect(List<Integer> list1, List<Integer> list2) {
		int size1 = list1.size();
		int size2 = list2.size();

		if (size1 > size2) {
			for (int i = 0; i < size1; i++) {
				for (int k = 0; k < size2; k++) {
					if (list1.get(i) == list2.get(k)) {
						return true;
					}
				}
			}

		} else {
			for (int i = 0; i < size2; i++) {
				for (int k = 0; k < size1; k++) {
					if (list2.get(i) == list1.get(k)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 将图片写入磁盘文件
	 * 
	 * @param imgFile
	 *            文件路径
	 * @param bi
	 *            BufferedImage 对象
	 * @return 无
	 */
	public static void writeImageToFile(String imgFile, BufferedImage bi) throws IOException {
		// 写图片到磁盘上
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(imgFile.substring(imgFile
				.lastIndexOf('.') + 1));
		ImageWriter writer = (ImageWriter) writers.next();
		// 设置输出源
		File f = new File(imgFile);
		ImageOutputStream ios;

		ios = ImageIO.createImageOutputStream(f);
		writer.setOutput(ios);
		// 写入到磁盘
		writer.write(bi);
	}

	
	
	/******************************* under methods user when test ************************************************************/

	/**
	 * get horizontal line img
	 * 
	 * @param imgFile
	 */
	public static void convert2Horizontal(String imgFile) {
		File file = new File(imgFile);
		FileInputStream fis = null;
		BufferedImage bufferedImg = null;
		try {
			fis = new FileInputStream(file);
			bufferedImg = ImageIO.read(fis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int width = bufferedImg.getWidth();
		int height = bufferedImg.getHeight();

		int pixels[] = new int[width * height];
		bufferedImg.getRGB(0, 0, width, height, pixels, 0, width);
		int newPixels[] = new int[width * height];
		List white = new ArrayList();
		StringBuffer linePosition = new StringBuffer();
		for (int i = 0; i < width * height; i++) {
			int r = (pixels[i] >> 16) & 0xff;
			int g = (pixels[i] >> 8) & 0xff;
			int b = (pixels[i]) & 0xff;

			if (r > 0 && g > 0 && b > 0) {
				if (i > 0 && pixels[i] == pixels[i - 1]) {
					linePosition.append(i + ",");
				} else {
					linePosition.append("&");
				}
			}
		}
		String[] lines = linePosition.toString().split("&");
		for (int i = 0; i < lines.length; i++) {
			String[] pos = lines[i].split(",");
			if (pos.length > 20) {
				for (int j = 0; j < pos.length; j++) {
					int num = Integer.parseInt(pos[j]);
					white.add(num);
				}
			}
		}

		for (int i = 0; i < width * height; i++) {
			if (white.contains(i)) {
				newPixels[i] = 0xffffff;
			} else {
				newPixels[i] = 0;
			}

		}
		// 基于 newPixels 构造一个 BufferedImage
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, newPixels, 0, width);
		newPixels = null;

		try {
			// 写入磁盘
			imgFile = "C:/Users/LINI8/Desktop/pdfTemp/Horizontal.png";
			writeImageToFile(imgFile, bi);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * get vertical line img
	 * 
	 * @param imgFile
	 */
	public  void convert2Vertical(String imgFile) {
		File file = new File(imgFile);
		FileInputStream fis = null;
		BufferedImage bufferedImg = null;
		try {
			fis = new FileInputStream(file);
			bufferedImg = ImageIO.read(fis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int width = bufferedImg.getWidth();
		int height = bufferedImg.getHeight();

		int pixels[] = new int[width * height];
		bufferedImg.getRGB(0, 0, width, height, pixels, 0, width);
		int newPixels[] = new int[width * height];
		List white = new ArrayList();
		StringBuffer linePosition = new StringBuffer();

		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				int i = h * width + w;
				int r = (pixels[h * width + w] >> 16) & 0xff;
				int g = (pixels[h * width + w] >> 8) & 0xff;
				int b = (pixels[h * width + w]) & 0xff;

				if (r > 0 && g > 0 && b > 0) {
					if (h > 0 && pixels[i] == pixels[(h - 1) * width + w]) {
						linePosition.append(i + ",");
					} else {
						linePosition.append("&");
					}
				}
			}
		}

		// 分割表格
		List<Integer> ySplit = splitForms(linePosition, width);
		for (int i = 0; i < ySplit.size(); i++) {
			System.out.println(ySplit.get(i));
		}

		String[] lines = linePosition.toString().split("&");
		for (int i = 0; i < lines.length; i++) {
			String[] pos = lines[i].split(",");
			if (pos.length > 15) {
				for (int j = 0; j < pos.length; j++) {
					int num = Integer.parseInt(pos[j]);
					white.add(num);
				}
			}
		}

		for (int i = 0; i < width * height; i++) {
			if (white.contains(i)) {
				newPixels[i] = 0xffffff;
			} else {
				newPixels[i] = 0;
			}

		}
		// 基于 newPixels 构造一个 BufferedImage
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, newPixels, 0, width);
		newPixels = null;

		try {
			// 写入磁盘
			imgFile = "C:/Users/LINI8/Desktop/pdfTemp/Vertical.png";
			writeImageToFile(imgFile, bi);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * convert 2 TableModel img
	 * 
	 * @param imgFile
	 */
	public  void convert2TableModel(String imgFile) {
		File file = new File(imgFile);
		FileInputStream fis = null;
		BufferedImage bufferedImg = null;
		try {
			fis = new FileInputStream(file);
			bufferedImg = ImageIO.read(fis);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		int width = bufferedImg.getWidth();
		int height = bufferedImg.getHeight();

		int pixels[] = new int[width * height];
		bufferedImg.getRGB(0, 0, width, height, pixels, 0, width);
		int newPixels[] = new int[width * height];
		List white = new ArrayList();

		// rgb 获取所有竖线
		StringBuffer linePosition = new StringBuffer();
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				int i = h * width + w;
				int r = (pixels[h * width + w] >> 16) & 0xff;
				int g = (pixels[h * width + w] >> 8) & 0xff;
				int b = (pixels[h * width + w]) & 0xff;

				if (r > 0 && g > 0 && b > 0) {
					if (h > 0 && pixels[i] == pixels[(h - 1) * width + w]) {// 连续的点为直线
						if ("&".equals(linePosition.substring(linePosition.length() - 1,
								linePosition.length()))) {
							linePosition.append((h - 1) * width + w + ",");
						}
						linePosition.append(i + ",");
					} else {
						linePosition.append("&");
					}
				}
			}
		}

		// 判断每一连续点长度，大于某一设定值为有效数据
		String[] lines = linePosition.toString().split("&");
		for (int i = 0; i < lines.length; i++) {
			String[] posh = lines[i].split(",");
			if (posh.length > VERTICAL_LENGTH) {// 40为设定值
				for (int j = 0; j < posh.length; j++) {
					int num = Integer.parseInt(posh[j]);
					white.add(num);
				}
			}
		}

		// rgb 获取所有横线
		StringBuffer linePosition2 = new StringBuffer();
		for (int i = 0; i < width * height; i++) {
			int r = (pixels[i] >> 16) & 0xff;
			int g = (pixels[i] >> 8) & 0xff;
			int b = (pixels[i]) & 0xff;

			if (r > 0 && g > 0 && b > 0) {
				if (i > 0 && pixels[i] == pixels[i - 1]) {// 连续的点为横线
					if ("&".equals(linePosition2.substring(linePosition2.length() - 1, linePosition2.length()))) {
						linePosition2.append(i - 1 + ",");
					}
					linePosition2.append(i + ",");
				} else {
					linePosition2.append("&");
				}
			}
		}

		// 判断每一连续点长度，大于某一设定值为有效数据
		String[] lines2 = linePosition2.toString().split("&");
		for (int i = 0; i < lines2.length; i++) {
			String[] posw = lines2[i].split(",");
			if (posw.length > HORIZONTAL_LENGTH) {// 250为设定值 连续横向像素点长度
				for (int j = 0; j < posw.length; j++) {
					int num = Integer.parseInt(posw[j]);
					white.add(num);
				}
			}
		}

		// 初始化 所有点为黑色
		for (int i = 0; i < width * height; i++) {
			newPixels[i] = 0;
		}

		// 是直线或横线上的像素点，为白色
		for (int i = 0; i < white.size(); i++) {
			newPixels[(Integer) white.get(i)] = 0xffffff;
		}

		// 基于 newPixels 构造一个 BufferedImage
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, newPixels, 0, width);
		newPixels = null;

		try {
			// 写入磁盘
			imgFile = "C:/Users/LINI8/Desktop/pdfTemp/Table.png";
			writeImageToFile(imgFile, bi);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ///////////////////////////under methods haven't be
	// used/////////////////////////////////////////
	/**
	 * find Contours
	 * 
	 * @param pixels
	 * @param width
	 * @param height
	 * @return
	 */
	// public static Map<Integer,String> findContours(int[] pixels,int width,int
	// height){
	// Map<Integer,String> map = new TreeMap<Integer,String>();
	// for(int h = 0; h < height; h++){
	// for(int w = 0; w < width; w++){
	// int position = h*width+w;
	//
	// int wNext = -1;
	// int hNext = -1;
	// int h2Next = -1;
	//
	// //下方的点
	// for(int i = scale;i < scaleLine; i++){
	// int[] hNexts = getNearby(position + width*i,width);
	// for(int nb=0;nb<hNexts.length;nb++){
	// if(hNexts[nb] > 0 && hNexts[nb] < width*height && pixels[hNexts[nb]] ==
	// 0xffffff){
	// hNext = hNexts[nb];
	// break;
	// }
	// }
	// }
	//
	//
	// if(hNext != -1){
	// for(int i = scale; i < width - w; i++){//右方的点
	// int[] wNexts = getNearby(position + i,width);
	// for(int nb=0;nb<wNexts.length;nb++){
	// if(wNexts[nb] > 0 && wNexts[nb] < width*height && pixels[wNexts[nb]] ==
	// 0xffffff){
	// wNext = wNexts[nb];
	// break;
	// }
	// }
	// }
	// if(wNext != -1){
	// // if(pixels[hNext + wNext - position] == 0xffffff){
	// h2Next = wNext + width*((hNext - position)/width);
	// int[] h2Nexts = getNearby(h2Next,width);
	// int flag = 0;
	// for(int nb=0;nb<h2Nexts.length;nb++){
	// if(h2Nexts[nb] > 0 && h2Nexts[nb] < width*height && pixels[h2Nexts[nb]]
	// == 0xffffff){
	// flag = 1;
	// }
	// }
	// if(flag == 1){
	// int wid = wNext - position;
	// int hig = (hNext - position)/width;
	// String value = w+","+h+","+wid+","+hig;
	// map.put(position, value);
	// }
	// }
	//
	// }
	//
	// }
	// }
	//
	//
	// List removes = new ArrayList();
	// // Map<Integer,String> contours = new TreeMap<Integer,String>();
	// for(Integer key: map.keySet()){
	// boolean go = true;
	// for(int i = 0;i<removes.size();i++){
	// if(key ==
	// (Integer)removes.get(i)||key.toString().equals(removes.get(i).toString())){
	// go = false;
	// System.out.println("ignore");
	// }
	// }
	//
	// if(go){
	// int[] around = getNearby(key,width);
	// for(int i = 0; i < around.length ; i++){
	// if(around[i]!=key &&around[i] > 0 && around[i] < width*height &&
	// map.get(around[i])!=null){
	// // int x_x = Integer.parseInt(map.get(key).split(",")[0]) -
	// Integer.parseInt(map.get(around[i]).split(",")[0]);
	// // int y_y = Integer.parseInt(map.get(key).split(",")[1]) -
	// Integer.parseInt(map.get(around[i]).split(",")[1]);
	// // if(Math.abs(x_x)< 10&& Math.abs(y_y)<10){
	// removes.add(around[i]);
	// // }
	// }
	// }
	// }
	// // for(int i = 1; i < scale ; i++){
	// // if(map.get(key +i)!=null){
	// // removes.add(key+i);
	// // }
	// // if(map.get(key + width*i)!=null){
	// // removes.add(key + width*i);
	// // }
	// // }
	// }
	//
	// for(int i = 0;i < removes.size();i++){
	// map.remove(removes.get(i));
	// }
	// System.out.print(map.size());
	//
	// return map;
	//
	// }
	//

	/**
	 * get Nearby
	 * 
	 * @param position
	 * @param width
	 * @return
	 */
	// public static int[] getNearby(int position,int width){
	// int[] nearbys = new int[(nearby*2+1)*(nearby*2+1)];
	// int num = 0;
	// for(int i=0;i<=nearby;i++){
	// if(i == 0){
	// int pos = position;
	// nearbys[num++] = pos;
	// for(int j=1;j<=nearby;j++){
	// int posUP = pos - width*j;
	// int posDown = pos + width*j;
	// nearbys[num++] = posUP;
	// nearbys[num++] = posDown;
	// }
	// }else{
	// int posLeft = position - i;
	// int posRight = position + i;
	// nearbys[num++] = posLeft;
	// nearbys[num++] = posRight;
	// for(int j=1;j<=nearby;j++){
	// int posLUp = posLeft - width*j;
	// int posLDown = posLeft + width*j;
	// nearbys[num++] = posLUp;
	// nearbys[num++] = posLDown;
	// }
	// for(int j=1;j<=nearby;j++){
	// int posRUp = posRight - width*j;
	// int posRDown = posRight + width*j;
	// nearbys[num++] = posRUp;
	// nearbys[num++] = posRDown;
	// }
	// }
	// }
	//
	// return nearbys;
	// }
	//

}