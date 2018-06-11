package com.epay.scanposp.common.utils.sm;

public interface CryptInf {
	public boolean VerifyMsg(String TobeVerified, String PlainText, String CertFile) throws Exception;

	public String SignMsg(String TobeSigned, String KeyFile, String PassWord) throws Exception;

}
