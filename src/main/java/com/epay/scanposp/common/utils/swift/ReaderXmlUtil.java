package com.epay.scanposp.common.utils.swift;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReaderXmlUtil {

	private static final String path_xml_file = "src/cn/swiftpass/wop/client/xml/queryCash.xml";
	private static final String path_json_file = "src/cn/swiftpass/wop/client/xml/JsonFile.xml";
	
	private static final String path_xml_file_path = "src/cn/swiftpass/wop/client/xml/";
	
	
	private static final String DATA_TYPE_XML = "xml";
	private static final String DATA_TYPE_JSON = "json";

	public static String readXml(String dataTpye){
		String result = "";
		SAXReader sr = new SAXReader();
		try {
			if(DATA_TYPE_XML.equals(dataTpye)){
				Document doc = sr.read(path_xml_file);
				Element elRoot = doc.getRootElement();
				result = elRoot.asXML();
			}else if(DATA_TYPE_JSON.equals(dataTpye)){
				Document doc = sr.read(path_json_file);
				Element elRoot = doc.getRootElement();
				result = elRoot.getText();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String readXml(String serviceName, String dataType){
		String result = "";

		String path_file = path_xml_file_path + "/" + serviceName + "." + dataType;

		try {
			if(DATA_TYPE_XML.equals(dataType)){

				SAXReader sr = new SAXReader();
				Document doc = sr.read(path_file);

				Element elRoot = doc.getRootElement();

				result = elRoot.asXML();

			}else if(DATA_TYPE_JSON.equals(dataType)){

				File file = null;
				InputStream inputStream = null;

				try{
					file = new File(path_file);
					inputStream = new FileInputStream(file);
					List<String> list = IOUtils.readLines(inputStream);
					
					if(null != list){
						StringBuilder builder = new StringBuilder();
						for(String line : list){
							builder.append(line);
						}
						
						result = builder.toString();
					}
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					if(null != inputStream){
						inputStream.close();
						inputStream = null;
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
