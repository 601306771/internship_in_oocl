package b2b.robot.image.thin;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ImageThin {

	int pixel[][];// 像素

	File fin = new File("C:\\Users\\LINI8\\Desktop\\thin\\before.png");

	BufferedImage bi;

	File fout = new File("C:\\Users\\LINI8\\Desktop\\thin\\after.png");

	int width;

	int height;

	int minx;

	int miny;

	void init() throws IOException {

		bi = ImageIO.read(fin);

		width = bi.getWidth();

		height = bi.getHeight();

		minx = bi.getMinX();

		miny = bi.getMinY();

		System.out.println("width=" + width + ",height=" + height + ".");

		System.out.println("minx=" + minx + ",miniy=" + miny + ".");

		pixel = new int[width][height];

		for (int i = minx; i < width; i++) {

			for (int j = miny; j < height; j++) {

				pixel[i][j] = (bi.getRGB(i, j) == -1) ? 0 : 1;// -1为白色

			}

		}

	}

	void ZhangThinning(String imgPath) throws IOException {
		int pixel[][];// 像素

		File fin = new File(imgPath);

		BufferedImage bi;
		
		imgPath = imgPath.replace(".png", "Thin.png");
		
		File fout = new File(imgPath);

		int width;

		int height;

		int minx;

		int miny;
		
		bi = ImageIO.read(fin);

		width = bi.getWidth();

		height = bi.getHeight();

		minx = bi.getMinX();

		miny = bi.getMinY();

		System.out.println("width=" + width + ",height=" + height + ".");

		System.out.println("minx=" + minx + ",miniy=" + miny + ".");

		pixel = new int[width][height];

		for (int i = minx; i < width; i++) {

			for (int j = miny; j < height; j++) {

				pixel[i][j] = (bi.getRGB(i, j) == -1) ? 1 : 0;// -1为白色

			}

		}

		int neighbor[] = new int[8];// 8领域

		int markNum = 0;

		int mark[][] = new int[width][height];

		boolean loop = true;

		while (loop)

		{

			loop = false;

			markNum = 0;

			for (int y = miny + 1; y + 1 < height; y++) {

				for (int x = minx + 1; x + 1 < width; x++) {

					if (pixel[x][y] == 0)

						continue;

					neighbor[0] = pixel[x + 1][y];

					neighbor[1] = pixel[x + 1][y - 1];

					neighbor[2] = pixel[x][y - 1];

					neighbor[3] = pixel[x - 1][y - 1];

					neighbor[4] = pixel[x - 1][y];

					neighbor[5] = pixel[x - 1][y + 1];

					neighbor[6] = pixel[x][y + 1];

					neighbor[7] = pixel[x + 1][y + 1];

					// 条件2：2<=N(p）<=6

					int np = neighbor[0] + neighbor[1] + neighbor[2]

					+ neighbor[3] + neighbor[4] + neighbor[5]

					+ neighbor[6] + neighbor[7];

					if (np < 2 || np > 6)

						continue;

					// 条件3：S(p）=1

					int sp = 0;

					for (int i = 1; i < 8; i++)

					{

						if (neighbor[i] - neighbor[i - 1] == 1)

							sp++;

					}

					if (neighbor[0] - neighbor[7] == 1)

						sp++;

					if (sp != 1)

						continue;

					// 条件4：p2*p0*p6=0

					if (neighbor[2] * neighbor[0] * neighbor[6] != 0)

						continue;

					// 条件5：p0*p6*p4=0

					if (neighbor[0] * neighbor[6] * neighbor[4] != 0)

						continue;

					// 标记删除

					mark[x][y] = 1;

					markNum++;

					loop = true;

				}

			}

			if (markNum > 0)

			{

				for (int y = miny; y + 1 < height; y++) {

					for (int x = minx; x + 1 < width; x++) {

						// 删除标记点

						if (mark[x][y] == 1)

						{

							pixel[x][y] = 0;

							mark[x][y] = 0;

							bi.setRGB(x, y, 0);

						}

					}

				}

			}

			markNum = 0;

			for (int y = miny + 1; y + 1 < height; y++) {

				for (int x = minx + 1; x + 1 < width; x++) {

					if (pixel[x][y] == 0)

						continue;

					neighbor[0] = pixel[x + 1][y];

					neighbor[1] = pixel[x + 1][y - 1];

					neighbor[2] = pixel[x][y - 1];

					neighbor[3] = pixel[x - 1][y - 1];

					neighbor[4] = pixel[x - 1][y];

					neighbor[5] = pixel[x - 1][y + 1];

					neighbor[6] = pixel[x][y + 1];

					neighbor[7] = pixel[x + 1][y + 1];

					// 条件2：2<=N(p）<=6

					int np = neighbor[0] + neighbor[1] + neighbor[2]

					+ neighbor[3] + neighbor[4] + neighbor[5]

					+ neighbor[6] + neighbor[7];

					if (np < 2 || np > 6)

						continue;

					// 条3：S(p）=1

					int sp = 0;

					for (int i = 1; i < 8; i++)

					{

						if (neighbor[i] - neighbor[i - 1] == 1)

							sp++;

					}

					if (neighbor[0] - neighbor[7] == 1)

						sp++;

					if (sp != 1)

						continue;

					// 条件4：p2*p0*p4==0

					if (neighbor[2] * neighbor[0] * neighbor[4] != 0)

						continue;

					// 条件5：p2*p6*p4==0

					if (neighbor[2] * neighbor[6] * neighbor[4] != 0)

						continue;

					// 标记删除

					mark[x][y] = 1;

					markNum++;

					loop = true;

				}

			}

			if (markNum > 0)

			{

				for (int y = miny; y < height; y++) {

					for (int x = minx; x < width; x++) {

						// 删除标记点

						if (mark[x][y] == 1)

						{

							pixel[x][y] = 0;

							bi.setRGB(x, y, 0);

						}

					}

				}

			}

		}

		ImageIO.write(bi, "png", fout);

	}

	

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param args
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {

		// TODO Auto-generated method stub
		String imgPath = "C:\\Users\\LINI8\\Desktop\\archive2";
		
		File[] array = getFile(imgPath);
		
		ImageThin temp = new ImageThin();
		
		for (int j = 0; j < array.length; j++) {
			
			temp.ZhangThinning(array[j].getPath());
			
		}
		
		

//		temp.show();

	}
	
	
	public static File[] getFile(String path) {
		// get file list where the path has
		File file = new File(path);
		// get the folder list
		File[] array = file.listFiles();
		return array;
	}

}
