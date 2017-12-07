package com.epay.scanposp.common.utils.sand.util;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {
	
	public static String buildQuery(Map<String, String> params, String charset)throws IOException
	{
	    List nvps = new LinkedList();

	    Set<Map.Entry<String, String>> entries = params.entrySet();
	    for (Map.Entry entry : entries) {
	      nvps.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
	    }
	    String str = URLEncodedUtils.format(nvps, charset);

	    return str;
	}

  public static URL buildGetUrl(String strUrl, String query) throws IOException {
    URL url = new URL(strUrl);
    if (StringUtils.isEmpty(query)) {
      return url;
    }

    if (StringUtils.isEmpty(url.getQuery())) {
      if (strUrl.endsWith("?"))
        strUrl = strUrl + query;
      else {
        strUrl = strUrl + "?" + query;
      }
    }
    else if (strUrl.endsWith("&"))
      strUrl = strUrl + query;
    else {
      strUrl = strUrl + "&" + query;
    }

    return new URL(strUrl);
  }

}
