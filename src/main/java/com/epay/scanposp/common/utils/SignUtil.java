package com.epay.scanposp.common.utils;


import com.epay.scanposp.common.constant.MSConfig;
import  com.epay.scanposp.entity.RequestMsg;

/** 
 *@author Lao.ZH 
 *@date 2017-2-13 上午9:33:42 
 *@version V1.0
 */
public class SignUtil {
	String key ;
	
	/**
	 * 校验签名
	 *@param RM
	 *@return 
	 *@author Lao.ZH
	 */
    public Boolean checkSIGN(RequestMsg RM){
		
		String signStr;
		String singMsg;
		
		if(RM.getMSG_TYPE().equals("3003")){
			signStr = RM.getORDER_ID()+RM.getTRANS_AMT1()+RM.getUSER_ID()+RM.getMSG_TYPE()+
					  RM.getRESP_CODE()+RM.getRESP_DESCR();
		}else{
			signStr = RM.getORDER_ID()+RM.getTRANS_AMT1()+RM.getTRANS_ATIME()+RM.getUSER_ID()+RM.getMSG_TYPE()+
					  RM.getRESP_CODE()+RM.getRESP_DESCR();
		}		
		System.out.println("签名字符串=["+signStr+"]");
		String singMsg1 =null;
    	singMsg1 = EncryptUtil.MD5(signStr, 1).toUpperCase();
    	System.out.println("第一次MD5："+singMsg1);
		String signMsg2 = singMsg1+key;
		singMsg = EncryptUtil.MD5(signMsg2, 0).toUpperCase();;
		System.out.println("第二次MD5："+singMsg);    	
    	if(singMsg.equals(RM.getSIGN())){
    		return true;
    	}else{
    		return false;
    	}
		
	}
    
    /**
     * 签名
     *@param RM
     *@return 
     *@author Lao.ZH
     */
    public  String makeSign(String signStr,String signType,String bind_type){
		//String key ="0123456789ABCDEF0123456789ABCDEF"; 
	             //ORDER_ID ，ORDER_AMT ， ORDER_TIME，PAY_TYPE ，USER_TYPE,USER_ID,BUS_CODE
 	    String singMsg1 = null;
		String signMsg = null ;
		if(bind_type.equals("1")){
			key=MSConfig.jfsignKey;
		}else{
			key=MSConfig.wjfsignKey;
		}
		System.out.println("signStr="+signStr);
		System.out.println("key="+key);
	    System.out.println("bind_type="+bind_type);
    	if(signType.equals("03")){
    		singMsg1 = EncryptUtil.MD5(signStr, 1).toUpperCase();
			String signMsg2 = singMsg1+key;
			signMsg = EncryptUtil.MD5(signMsg2, 0).toUpperCase();;

		}else{  //签名类型为01、02
			singMsg1 = EncryptUtil.MD5(signStr, 0);    
			signMsg = EncryptUtil.DES_3(singMsg1, key, 0);
		}
	    
		return signMsg;
	}
   public static void  main(String args[]) { 
	   SignUtil SignUtil=new SignUtil();
	   String t="";
	   t="201705161403156430.012017051614031513029903910000110093001";
	   System.out.println(SignUtil.makeSign(t,"03","0"));

   }
    
}
