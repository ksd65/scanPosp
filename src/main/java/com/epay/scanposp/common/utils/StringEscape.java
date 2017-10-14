package com.epay.scanposp.common.utils;

import java.beans.PropertyEditorSupport;
import org.apache.commons.lang.StringEscapeUtils;

public class StringEscape extends PropertyEditorSupport {
	private boolean escapeHTML;
	private boolean escapeSQL;
	public final static String[][] filterCharacter={
		{";","&#59;","%3B",""},
		{"\"","&#34;","%22","&quot;"},
		{"%","&#37;","%25",""},
		{"'","&#39;","%27",""},
		{"(","&#40;","%28",""},
		{")","&#41;","%29",""},
		{"<","&#60;","%3C","&lt;"},
		{">","&#62;","%3E","&gt;"},
		};
	public StringEscape() {
		super();
	}

	public StringEscape(boolean escapeHTML, boolean escapeSQL) {
		this.escapeHTML = escapeHTML;
		this.escapeSQL = escapeSQL;
	}

	@Override
	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		} else {
			if(escapeHTML)text=escapeXss(text);
			if(escapeSQL)text=escapeSql(text);
			setValue(text);
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return getValue() != null ? value.toString() : "";
	}
	
	public static String escapeXss(String value){
		//StringEscapeUtils.escapeHtml(value);中文乱码，太严格
		for(int i=0;i<filterCharacter.length;i++){
			value = value.replace(filterCharacter[i][0], filterCharacter[i][1]);
		}
		return value;
	}
	
	public static String escapeSql(String value){
		return StringEscapeUtils.escapeSql(value);
	}

	public static String escapeString(boolean escapeHTML,boolean escapeSQL ,String value){
		if (escapeHTML) {
			value = escapeXss(value);
		}
		if (escapeSQL) {
			value = StringEscapeUtils.escapeSql(value);
		}
		return value;
	}
	
}
