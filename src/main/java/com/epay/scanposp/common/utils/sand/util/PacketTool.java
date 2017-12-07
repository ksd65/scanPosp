package com.epay.scanposp.common.utils.sand.util;

import com.epay.scanposp.common.utils.sand.SandpayRequestHead;

public class PacketTool {
	
	public static void setDefaultRequestHead(SandpayRequestHead head, String method, String productId, String mid)
	  {
	    head.setVersion("1.0");
	    head.setMethod(method);
	    head.setProductId(productId);

	    head.setAccessType("1");
	    head.setMid(mid);
	    head.setSubMid("");
	    head.setSubMidName("");
	    head.setSubMidAddr("");

	    head.setChannelType("07");
	    head.setReqTime(DateUtil.getCurrentDate14());
	  }

}
