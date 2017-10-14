package com.epay.scanposp.common.utils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 注意：列头请采用LinkedHashMap设置，对应上数据列表顺序 excel数据导出
 * 
 * @author ghq
 * 
 */
public class ExportExcelUtil{
	private HttpServletResponse response;
	private HttpServletRequest request;
	private String fileName = "快递清单";// 导出的文件名
	private List<Map<String,Object>> reportList;// 要导出的报表列表
	private Map<String, String> columnTitle;// 要导出的列头
	private WritableWorkbook book;
	private String title = "快递清单";// 表格标题
	public static int MAX_SIZE = 50000;// 导出条数最大限制

	// private int isSucc=0;//导出状态
	public ExportExcelUtil(HttpServletResponse response) {
		this.response = response;
	}

	public ExportExcelUtil(HttpServletResponse response,
			HttpServletRequest request) {
		this.response = response;
		this.request = request;
	}

	/**
	 * excel表格构建
	 * 
	 * @throws Exception
	 *             表格填充数据不合格时抛出异常
	 */
	public void outPutExcel() throws Exception {
		checkData();
		initExcelFormat();
		WritableSheet sheet = book.createSheet(fileName, 0);
		sheet.setColumnView(2, 20); // 设置宽度
		WritableFont titleFont = new WritableFont(
				WritableFont.createFont("宋体"), 11, WritableFont.BOLD);// 设置字体大小
		WritableFont titleFont1 = new WritableFont(
				WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);// 设置字体大小
		WritableFont titleFont2 = new WritableFont(
				WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);// 设置字体大小
		// 标题样式
		WritableCellFormat format = new WritableCellFormat(titleFont,
				NumberFormats.TEXT);
		format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK); // 设置边框
		format.setAlignment(Alignment.CENTRE); // 水平居中
		format.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
		format.setBackground(Colour.AQUA);// 设置单元格背景色
		// 列头样式
		WritableCellFormat format2 = new WritableCellFormat(titleFont1,
				NumberFormats.TEXT);
		format2.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		format2.setAlignment(Alignment.CENTRE);
		format2.setVerticalAlignment(VerticalAlignment.CENTRE);
		// 填充数据样式
		WritableCellFormat format1 = new WritableCellFormat(titleFont2,
				NumberFormats.TEXT);
		format1.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		format1.setAlignment(Alignment.CENTRE);
		format1.setVerticalAlignment(VerticalAlignment.CENTRE);

		// 创建表格标题
		int size = columnTitle.size();// 列数
		Label labelTitle1 = new Label(1, 1, title, format);
		sheet.addCell(labelTitle1);
		for (int j = 2; j < size + 1; j++) {
			Label labelTitle = new Label(j, 1, "", format);
			sheet.addCell(labelTitle);
		}
		sheet.mergeCells(1, 1, size, 1);

		// 创建表格列头
		int i = 1;
		for (Object key : columnTitle.keySet()) {// 列标题
			Label labelTitle = new Label(i, 2, columnTitle.get(key), format1);
			sheet.setColumnView(i, 20); // 设置宽度
			sheet.setRowView(1, 400);// 设置高度
			sheet.addCell(labelTitle);
			i++;
		}
		// 将数据导入表格
		int row = 3;
		for (Map<String,Object> item: reportList) {// 数据
			i = 1;
			for (Object key : columnTitle.keySet()) {
				Object o = item.get(key);
				String value = String.valueOf(o==null?"":o);
				Label labelTitle = new Label(i, row, value, format2);
				sheet.addCell(labelTitle);
				i++;
			}
			row++;
		}

		book.write();
		book.close();

	}

	/**
	 * 初始化excel
	 */
	private void initExcelFormat() {
		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel; charset=gbk");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String((fileName + ".xls").getBytes("gb2312"),
							"iso8859-1"));
			book = Workbook.createWorkbook(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 校验数据
	 * 
	 * @throws Exception
	 */
	private void checkData() throws Exception {
		if (null == response) {
			throw new Exception("response不能为空!");
		}
		if (null == columnTitle) {
			throw new Exception("columnTitle不能为空!");
		}
		if (null == reportList) {
			throw new Exception("reportList不能为空!");
		}
		if (reportList.size() > MAX_SIZE) {
			throw new Exception("导出数据量过大,不支持!");
		}
		// Map<String,String> rMap=reportList.get(0);
		// if(columnTitle.size()!=rMap.size()){
		// throw new Exception("columnTitle列数与reportList列数不一致！");
		// }
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public List<Map<String, Object>> getReportList() {
		return reportList;
	}

	public void setReportList(List<Map<String, Object>> reportList) {
		this.reportList = reportList;
	}

	public Map<String, String> getColumnTitle() {
		return columnTitle;
	}

	public void setColumnTitle(Map<String, String> columnTitle) {
		this.columnTitle = columnTitle;
	}

	public WritableWorkbook getBook() {
		return book;
	}

	public void setBook(WritableWorkbook book) {
		this.book = book;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}