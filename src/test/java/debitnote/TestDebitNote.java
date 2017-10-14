package debitnote;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.ui.Model;

import com.epay.scanposp.common.constant.MSConfig;
import com.epay.scanposp.common.utils.CommonUtil;
import com.epay.scanposp.common.utils.HttpUtil;
import com.epay.scanposp.common.utils.SendUrlUtil;
import com.epay.scanposp.common.utils.constant.MSPayWayConstant;
import com.epay.scanposp.entity.BusinessCategory;
import com.epay.scanposp.entity.MemberBindAcc;
import com.epay.scanposp.entity.MemberInfo;
import com.epay.scanposp.entity.MlTradeDetail;


public class TestDebitNote {

	@Test
	public void testWithDraw() {
		//获取绑卡信息
		JSONObject reqData=new JSONObject();
		JSONObject result;
		String tmp="";
/*		
		reqData.put("memberId", "3"); 		
		 result=JSONObject.fromObject(HttpUtil.sendPostRequest("http://localhost:8080/scanPosp/api/memberInfo/getMemberBindAccList", CommonUtil.createSecurityRequstData(reqData)));
		System.out.println(result.toString());*/
		//测试绑卡接口
/*		 	MemberBindAcc  memberBindAcc=new MemberBindAcc();
		memberBindAcc.setAcc("6229661822575222");
		memberBindAcc.setMemberId(1063);
		memberBindAcc.setBankId(105);
		memberBindAcc.setName("肖伟");
		memberBindAcc.setMobilePhone("18559852828");
		reqData.put("memberBindAcc", memberBindAcc); 
	    result=JSONObject.fromObject(HttpUtil.sendPostRequest("http://localhost:8080/scanPosp/api/memberInfo/memberBindAcc", CommonUtil.createSecurityRequstData(reqData)));
		System.out.println(result.toString()); */
		
	MlTradeDetail  MlTradeDetail=new MlTradeDetail();
		MlTradeDetail.setAcc("6229661822575222"); 
		MlTradeDetail.setMoney( new BigDecimal(10).setScale(2, BigDecimal.ROUND_DOWN));
		MlTradeDetail.setMemberId(1063); 
		MlTradeDetail.setIsJf("1");
		reqData.put("mlTradeDetail", MlTradeDetail); 
	    result=JSONObject.fromObject(HttpUtil.sendPostRequest("http://localhost:8080/scanPosp/api/memberInfo/memberQuickPay", CommonUtil.createSecurityRequstData(reqData)));
		System.out.println(result.toString());
		//System.out.println(URLDecoder.decode("%E7%AD%BE%E5%90%8D%E9%94%99%E8%AF%AF"));
		//回调结果
		/*String paramss=null;
		String respResult;
		SendUrlUtil sendUrlUtil=new SendUrlUtil();
		paramss="ORDER_ID=LE170417202326JOTL&ORDER_AMT=1350.0&REF_NO=&PAYCH_TIME=20170417202326&PAY_AMOUNT=1350.0&RESP_CODE=0000&RESP_DESC=%E6%94%AF%E4%BB%98%E6%88%90%E5%8A%9F%2C%E5%87%BA%E8%B4%A6%E6%88%90%E5%8A%9F&ORDER_TIME=20170417202326&USER_ID=990393000051009&SIGN=26F472AB2682631B&SIGN_TYPE=03&BUS_CODE=3002&CCT=CNY&ADD1=6253624044361929&ADD1=6229661822575222&ADD2=1063&ADD3=1010000037&ADD4=0.003200&ADD5=0.5";
		 respResult = sendUrlUtil.sendPost("http://localhost:8080/scanPosp/api/memberInfo/memberQuickPayCallBack",paramss);
	//	tmp=HttpUtil.sendPostRequest("http://localhost:8080/scanPosp/api/memberInfo/memberQuickPayCallBack?", CommonUtil.createSecurityRequstData(reqData));
		 
			System.out.println(URLDecoder.decode("%E6%94%AF%E4%BB%98%E6%88%90%E5%8A%9F%2C%E5%87%BA%E8%B4%A6%E6%88%90%E5%8A%9F"));
 
		System.out.println(tmp.toString());*/
	}
	
	public void test(Model model, HttpServletRequest request) {
//		MemberInfo memberInfo=memberInfoService.selectByPrimaryKey(1042);
//		String a=null;
//		BusinessCategory businessCategory=new BusinessCategory();
//		businessCategory.setZfbCategoryId("2016062900190142");
//		return registerMsAccount(MSPayWayConstant.ZFBZF, memberInfo, businessCategory, "105393000634", "中国建设银行股份有限公司厦门观音山支行");
	}

}
