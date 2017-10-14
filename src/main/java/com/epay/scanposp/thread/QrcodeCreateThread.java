package com.epay.scanposp.thread;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.epay.scanposp.common.utils.ZXingQRCodeUtil;
import com.epay.scanposp.common.utils.ZxingHandler;

public class QrcodeCreateThread implements Runnable{
	
	private String qrUrl;
	private String logoPath;
	private String saveFilePath;
	private String code;
	private String defaultQrcodeBasePath;
	private String mImg;
	private String savePath;
	
	public QrcodeCreateThread(String qrUrl, String logoPath, String saveFilePath, String code, String defaultQrcodeBasePath, String mImg, String savePath) {
		this.qrUrl = qrUrl;
		this.logoPath = logoPath;
		this.saveFilePath = saveFilePath;
		this.code = code;
		this.defaultQrcodeBasePath = defaultQrcodeBasePath;
		this.mImg = mImg;
		this.savePath = savePath;
	}
	
	@Override
	public void run() {
		
		File filePath = new File(saveFilePath);
    	if (!filePath.getParentFile().exists()) {
			filePath.getParentFile().mkdirs();
		}
    	String contents = qrUrl+code;
		int width = 270, height = 270;
		ZxingHandler.encode2(contents, width, height, saveFilePath);
		//图片合成
		String fileName = code + ".png";
        File file1 = new File(savePath, fileName);
        File file2 = null;
        file2 = new File(defaultQrcodeBasePath, mImg); 
        try{
        	BufferedImage image1 = ImageIO.read(file1);  
            BufferedImage image2 = ImageIO.read(file2);  
            Graphics g = image2.getGraphics();
            g.drawImage(image1, 185, 490, null);
            Font font = new Font("华文彩云", Font.PLAIN, 40);
            g.setFont(font);
            g.setColor(Color.black);
            g.drawString(code, 210, 820);
            ImageIO.write(image2, "JPG", new File(savePath, fileName));
        }catch(IOException e){
        	e.printStackTrace();
        }
		//mergeImage(file1,file2,filePath.getParentFile(),fileName,code);
		//ZXingQRCodeUtil.getLogoQRCode(qrUrl, logoPath, saveFilePath);
        
	}
	
	
}
