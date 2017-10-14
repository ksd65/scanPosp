package com.epay.scanposp.common.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 简单的验证码工具类
 * 
 * @author ghq
 *
 */
public class CaptchaUtils {

	public static String FONT_FAMILY = "Arial Black";

	public static Integer FONT_SIZE = 16;
	
	private static final String RANDOM_NUMS = "0123456789";
	
	/**
	 * 生成GIF动画验证码
	 * 
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param num
	 *            验证码位数
	 * @param out
	 *            OutputStream
	 * @param delay
	 *            没播放一张图片的演示速度,以毫秒为单位
	 * 
	 * @return String
	 */
	public static String getGifCaptcha(int width, int height, int num, OutputStream out, int delay) {
		// 防止非负数
		width = width > 0 ? width : 60;
		height = height > 0 ? height : 18;

		// 该变量用于保存系统生成的随机字符串
		String sRand = "";
		for (int i = 0; i < num; i++) {
			// 取得一个随机字符
			String tmp = getRandomChar();
			sRand += tmp;
		}
		AnimatedGifEncoder e = new AnimatedGifEncoder();

		e.setDelay(delay);
		e.start(out);
		e.setRepeat(0);

		Font font = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE);

		for (int i = 0; i < sRand.length(); i++) {
			// 生成一张新图片
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 绘制图片
			Random random = new Random();
			Graphics2D g = image.createGraphics();
			g.setColor(getRandColor(220, 250));
			g.fillRect(1, 1, width - 1, height - 1);
			g.setColor(new Color(102, 102, 103));
			g.drawRect(0, 0, width - 1, height - 1);
			// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.2f));
			g.setFont(font);
			// 随机生成线条，让图片看起来更加杂乱
			g.setColor(getRandColor(160, 200));

			for (int j = 0; j < 25; j++) {
				int x = random.nextInt(width - 1);
				int y = random.nextInt(height - 1);
				int x1 = random.nextInt(6) + 1;
				int y1 = random.nextInt(12) + 1;
				g.drawLine(x, y, x + x1, y + y1);
			}

			// 将系统随机字符添加到图形验证码图片上
			for (int k = 0; k < sRand.length(); k++) {
				// 透明度
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, getAlpha(k, i)));
				// 字体颜色
				g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
				g.drawString(String.valueOf(sRand.charAt(k)), FONT_SIZE * k + 5 + i, FONT_SIZE + 5);
				// g.drawString(sRand, FONT_SIZE - i,FONT_SIZE + 5);
			}

			g.dispose();

			e.addFrame(image);

			image.flush();

		}

		e.finish();

		return sRand;
	}

	public static float getAlpha(int i, int j) {
		if ((i + j) > 5) {
			return ((i + j) * 0.2f - 1.2f);
		} else {
			return (i + j) * 0.2f;
		}
	}

	/**
	 * 生成图形验证码，并返回code值
	 * 
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param num
	 *            验证码位数
	 * @param out
	 *            OutputStream
	 * 
	 * @return String
	 */
	public static String getCaptcha(int width, int height, int num,
			OutputStream out) {
		// 防止非负数
		width = width > 0 ? width : 60;
		height = height > 0 ? height : 18;
		// 生成一张新图片
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Font font = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE);
		// 绘制图片
		Random random = new Random();
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(1, 1, width - 1, height - 1);
		g.setColor(new Color(102, 102, 103));
		g.drawRect(0, 0, width - 1, height - 1);
		g.setFont(font);
		// 随机生成线条，让图片看起来更加杂乱
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int x1 = random.nextInt(6) + 1;
			int y1 = random.nextInt(12) + 1;
			g.drawLine(x, y, x + x1, y + y1);
		}
		// 该变量用于保存系统生成的随机字符串
		String sRand = "";
		for (int i = 0; i < num; i++) {
			// 取得一个随机字符
			String tmp = getRandomChar();
			sRand += tmp;
			// 将系统随机字符添加到图形验证码图片上
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(tmp, FONT_SIZE * i + 5, FONT_SIZE + 5);
		}
		g.dispose();
		try {
			ImageIO.write(image, "JPEG", out);
			out.close();
		} catch (IOException e) {
			sRand = null;
			e.printStackTrace();
		}
		return sRand;
	}

	/**
	 * 生成随机颜色
	 * 
	 * @param fc
	 *            从随机的颜色位置 (0-255)
	 * @param bc
	 *            到随机的颜色位置（0-255）
	 * 
	 * @return {@link Color}
	 */
	public static Color getRandColor(int fc, int bc) {
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

	/**
	 * 生成随机字符的方法
	 */
	public static String getRandomChar() {
		int rand = (int) Math.round(Math.random() * 2);
		long itmp = 0;
		char ctmp = '\u0000';
		// 根据rand的值来决定是生成一个大写字母，小写字母和数字
		switch (rand) {
		// 生成大写字母的情形
		case 1:
			itmp = Math.round(Math.random() * 25 + 65);
			ctmp = (char) itmp;
			return String.valueOf(ctmp);
			// 生成小写字母的情形
		case 2:
			itmp = Math.round(Math.random() * 25 + 97);
			ctmp = (char) itmp;
			return String.valueOf(ctmp);
			// 生成数字的情形
		default:
			itmp = Math.round(Math.random() * 9);
			return String.valueOf(itmp);
		}

	}
	
	public static String getRandomNum(Integer length) {
		StringBuffer numCode = new StringBuffer("");
		for (int i = 0; i < length; i++) {
			numCode.append(RANDOM_NUMS.charAt(new Random().nextInt(RANDOM_NUMS.length())));
		}
		return numCode.toString();
	}

}
