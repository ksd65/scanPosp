package com.epay.scanposp.common.utils;

/**
 * json返回消息
 * @author ghq
 *
 */
public class JsonMsg {
	private boolean success;
	private String msg;
	
	public JsonMsg() {
	}
	public JsonMsg(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public static JsonMsg newInstance(boolean success, String msg){
		JsonMsg jsonMsg = new JsonMsg();
		jsonMsg.setSuccess(success);
		jsonMsg.setMsg(msg);
		return jsonMsg;
	}
	@Override
	public String toString() {
		return "{\"success\":" + success + ",\"msg\":\"" + msg + "\"}";
	}
}
