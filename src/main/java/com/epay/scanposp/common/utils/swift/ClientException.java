package com.epay.scanposp.common.utils.swift;

/**
 * 客户端异常类
 * @author liuhaibo
 * @since 2013-12-18
 */
public class ClientException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2155382375819767054L;

	private String errorCode;

	public ClientException(String errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public ClientException(Throwable cause, String errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
}
