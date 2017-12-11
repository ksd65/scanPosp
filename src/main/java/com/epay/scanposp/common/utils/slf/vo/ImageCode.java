package com.epay.scanposp.common.utils.slf.vo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageCode {

	static String[] cmb = { "2", "3", "4", "5", "6", "7", "8", "9", "a", "b",
			"d", "e", "f", "g", "h", "i", "j", "k", "m", "n", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E",
			"F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	private static int getXPosition4Twist(double dPhase, double dMultValue,
			int height, int xPosition, int yPosition) {
		double PI = 3.1416D;
		double dx = PI * yPosition / height + dPhase;
		double dy = Math.sin(dx);
		return xPosition + (int) (dy * dMultValue);
	}

	private static BufferedImage twistImage(BufferedImage buffImg) {
		Random random = new Random();
		double dMultValue = random.nextInt(3) + 4;
		dMultValue = random.nextInt(2) % 2 == 0 ? 0.0D - dMultValue
				: dMultValue;
		double dPhase = random.nextInt(3) - 1;
		BufferedImage destBi = new BufferedImage(buffImg.getWidth(),
				buffImg.getHeight(), 1);
		for (int i = 0; i < destBi.getWidth(); i++) {
			for (int j = 0; j < destBi.getHeight(); j++) {
				int nOldX = getXPosition4Twist(dPhase, dMultValue,
						destBi.getHeight(), i, j);
				int nOldY = j;
				if ((nOldX >= 0) && (nOldX < destBi.getWidth()) && (nOldY >= 0)
						&& (nOldY < destBi.getHeight())) {
					destBi.setRGB(nOldX, nOldY, buffImg.getRGB(i, j));
				}
			}
		}
		return destBi;
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static String getRandImageCode() {
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(cmb[random.nextInt(cmb.length)]);
			sRand = sRand + rand;
		}
		return sRand;
	}

	public static BufferedImage createImageCode(String sRand) {
		int width = 65;
		int height = 20;
		BufferedImage image = new BufferedImage(width, height, 1);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", 0, 18));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(20);
			int yl = random.nextInt(10);
			g.drawLine(x, y, x + xl, y + yl);
		}
		for (int i = 0; i < sRand.length(); i++) {
			String rand = sRand.substring(i, i + 1);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 5, 16);
		}
		return twistImage(image);
	}
}