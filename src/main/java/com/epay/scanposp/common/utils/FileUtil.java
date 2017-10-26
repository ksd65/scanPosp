package com.epay.scanposp.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.bouncycastle.util.encoders.Base64;

public class FileUtil {
	
	/**
	 * 对文件输入流进行base64加密
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public String ConvertFileToBase64(InputStream inputStream) throws IOException {
		
		byte[] bytes = read(inputStream);
		return ConvertFileToBase64(bytes);

	}
	
	

	public String ConvertFileToBase64(String filePath) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File(filePath));
		return this.ConvertFileToBase64(inputStream);
	}
	
	public String ConvertFileToBase64(byte[] bytes) throws UnsupportedEncodingException {
		byte[] bytesEncode = Base64.encode(bytes);
		return new String(bytesEncode, "utf-8");
	}
	
	/**
	 * 将流转换成字符数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public byte[] read(InputStream inputStream) throws IOException {
		byte[] bytes = new byte[1024];
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int b = 0;
		while ((b = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, b);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();
		return outputStream.toByteArray();
	}

}
