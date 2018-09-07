package homework.pkg3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

import javax.imageio.ImageIO;

@SuppressWarnings("unused")
public class Demo_1 {

	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long startTime = System.nanoTime();
		File input = new File("Teds daughter.bmp");
		BufferedImage img = ImageIO.read(input);
		int w = img.getWidth();
		int h = img.getHeight();
		int[][] pixel = new int[w][h];
		int[][] red = new int[w][h];
		int[][] green = new int[w][h];
		int[][] blue = new int[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				pixel[i][j] = img.getRGB(i, j);
				if (pixel[i][j] >= 251) {
					pixel[i][j] = 250;
				}
				Color mycolor = new Color(img.getRGB(i, j));
				red[i][j] = mycolor.getRed();
				if (red[i][j] >= 251)
					red[i][j] = 250;
				green[i][j] = mycolor.getGreen();
				if (green[i][j] >= 251)
					green[i][j] = 250;
				blue[i][j] = mycolor.getBlue();
				if (blue[i][j] >= 251)
					blue[i][j] = 250;
			}
		}

		int k, n;
		System.out.println("Enter The values for N ");
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
		System.out.println("Enter the value of K");
		k = in.nextInt();
		if (k < n) {
			int[] a = new int[k];
			int max = 251, min = 0;

			int[] x = new int[n];
			for (int i = 0; i < n; i++) {
				x[i] = i + 1;
			}

			int[][] sRed = new int[w][h];
			int[][] sGreen = new int[w][h];
			int[][] sBlue = new int[w][h];
			int[][][] rShare = new int[w][h][n + 1];
			int[][][] gShare = new int[w][h][n + 1];
			int[][][] bShare = new int[w][h][n + 1];
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					Random rand = new Random();
					for (int i1 = 1; i1 < k; i1++) {
						a[i1] = rand.nextInt(250);
					}
					sRed[i][j] = red[i][j];
					rShare[i][j] = shareFunction(k, n, sRed[i][j], a, x);
					sBlue[i][j] = blue[i][j];
					bShare[i][j] = shareFunction(k, n, sBlue[i][j], a, x);
					sGreen[i][j] = green[i][j];
					gShare[i][j] = shareFunction(k, n, sGreen[i][j], a, x);
				}
			}


			for (int k1 = 1; k1 <= n; k1++) {
				BufferedImage imagek1 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				for (int i = 0; i < w; i++) {
					for (int j = 0; j < h; j++) {
						int rgb = rShare[i][j][k1];
						rgb = (rgb << 8) + gShare[i][j][k1];
						rgb = (rgb << 8) + bShare[i][j][k1];
						imagek1.setRGB(i, j, rgb);
					}
				}
				try {
					// int k2 = k1 + 1;
					File f = new File("Ted's Daughter's Secret Share " + k1 + ".bmp");
					ImageIO.write(imagek1, "bmp", f);
				} catch (IOException e) {
					System.out.println("Error: " + e);
				}
			}
			rotation(n, k, a, w, h, x);
		} else {
			System.out.println("Enter number of key value lesser than the number of share value");
		}
	}

	private static void rotation(int n, int k, int[] a, int w, int h, int[] x) throws IOException {
		// TODO Auto-generated method stub
		int angle;
		System.out.println("Enter angle between 0 to 360");
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		angle = in.nextInt();

		for (int p = 1; p <= n; p++) {
			File input = new File("Ted's Daughter's Secret Share " + p + ".bmp");
			BufferedImage img = ImageIO.read(input);
			BufferedImage rotate = rotate(img, angle);

			File f = new File("Rotated Share Image " + p +".bmp");
			try {
				ImageIO.write(rotate, "bmp", f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		combineFunction(n, k, a, w, h, x);
				
	}
	
	private static BufferedImage rotate(BufferedImage img, int angle) {
		// TODO Auto-generated method stub
		Math.abs(Math.sin(Math.toRadians(angle)));
		Math.abs(Math.cos(Math.toRadians(angle)));

		int w = img.getWidth();
		int h = img.getHeight();


		BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = bimg.createGraphics();

		g.translate(0, 0);
		g.rotate(Math.toRadians(angle), w / 2, h / 2);
		
		
		g.translate(-w / 2, -h / 2);
		g.scale(2, 2);
		
		
		g.drawRenderedImage(img, null);
		g.dispose();

		return bimg;
	}
	

	private static void combineFunction(int n, int k, int[] a, int w, int h, int[] x) throws IOException {
		// TODO Auto-generated method stub
		int[][][] rShare = new int[w][h][n + 1];
		int[][][] gShare = new int[w][h][n + 1];
		int[][][] bShare = new int[w][h][n + 1];

		for(int p = 1; p <= n; p++) {
			File input1 = new File("Rotated Share Image " + p +".bmp");
			BufferedImage img1 = ImageIO.read(input1);
			
			for(int i = 0; i < w; i++) {
				for(int j = 0; j < h; j++) {
					Color mycolor1 = new Color(img1.getRGB(i, j));
					rShare[i][j][p] = mycolor1.getRed();
					gShare[i][j][p] = mycolor1.getGreen();
					bShare[i][j][p] = mycolor1.getBlue();
				}
			}
		}
		
		System.out.println("Select " + k + " shares from 1 to " + n + " to reconstruct the image");
		int[] choice = new int[k];
		for (int i = 0; i < k; i++) {
			@SuppressWarnings("resource")
			Scanner in = new Scanner(System.in);
			choice[i] = in.nextInt();
		}
		int[] v = new int[k + k];
		for (int i = 0; i < k; i++) {
			v[i] = x[choice[i] - 1];
			v[i + k] = x[choice[i] - 1];
		}
		
		
		int[][] redval = new int[w][h];
		int[][] greenval = new int[w][h];
		int[][] blueval = new int[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				redval[i][j] = Lagranges(rShare[i][j], v, choice, k, x);
				greenval[i][j] = Lagranges(gShare[i][j], v, choice, k, x);
				blueval[i][j] = Lagranges(bShare[i][j], v, choice, k, x);
			}
		}

		BufferedImage imagek2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int rgb = redval[i][j];
				rgb = (rgb << 8) + greenval[i][j];
				rgb = (rgb << 8) + blueval[i][j];
				imagek2.setRGB(i, j, rgb);
			}
		}

		try {
			File f = new File("Ted's Daughter's reconstructed.bmp");
			ImageIO.write(imagek2, "bmp", f);
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	private static int Lagranges(int[] Share, int[] v, int[] choice, int k, int[] x) {
		// TODO Auto-generated method stub
		int value = 0;
		for (int i = 0; i < k; i++) {
			BigInteger numerator = BigInteger.ONE;
			BigInteger denominator = BigInteger.ONE;
			for (int j = 0; j < k; j++) {
				if (i != j) {
					numerator = numerator.multiply(BigInteger.valueOf(0 - v[j]));
					denominator = (denominator.multiply(BigInteger.valueOf(v[i] - v[j])));
				}
			}
			int n1 = numerator.intValue();
			int d = denominator.intValue();
			d = BigInteger.valueOf(d).modInverse(BigInteger.valueOf(251)).intValue();
			value = value + (Share[choice[i]] * n1 * d);
		}
		value = value % 251;
		return value;
	}

	private static int[] shareFunction(int k, int n, int secret, int[] a, int[] x) {
		int[] share = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			share[i] = secret;
			for (int j = 1; j < k; j++) {
				share[i] = share[i] + (a[j] * power(x[i - 1], j));
			}
			share[i] = share[i] % 251;
		}
		return share;

	}

	private static int power(int i, int j) {
		// TODO Auto-generated method stub
		int value = 1;
		for (int k = 0; k < j; k++) {
			value = value * i;
		}
		return value;
	}

}
