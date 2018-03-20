/**
 * 
 */
package com.taika.bidding.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taika.bidding.bean.PaySaPi;
import com.taika.bidding.util.PayUtil;

/**
 * @author zsy
 *
 */
@Controller
@RequestMapping("paysapi")
public class PaysAction {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final Map<String,Object> resmap = new HashMap<String,Object>();
	
	@RequestMapping("/pay")
	@ResponseBody
	public Map<String, Object> pay(HttpServletRequest request, float price, int istype) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> remoteMap = new HashMap<String, Object>();
		remoteMap.put("price", price);
		remoteMap.put("istype", istype);
		remoteMap.put("orderid", PayUtil.getOrderIdByUUId());
		remoteMap.put("orderuid", "zsy");
		remoteMap.put("goodsname", "");
		resultMap.put("data", PayUtil.payOrder(remoteMap));
		resultMap.put("code", 200);
		return resultMap;
	}
	
	@RequestMapping("/pay4t")
	@ResponseBody
	public Map<String, Object> payfortest(HttpServletRequest request, int istype) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> remoteMap = new HashMap<String, Object>();
		remoteMap.put("price", 0.10f);
		remoteMap.put("istype", istype);
		remoteMap.put("orderid", PayUtil.getOrderIdByUUId());
		remoteMap.put("orderuid", "zsy");
		remoteMap.put("goodsname", "测试商品1");
		resultMap.put("data", PayUtil.payOrder(remoteMap));
		resultMap.put("code", 200);
		return resultMap;
	}

	@RequestMapping("/notifyPay")
	public void notifyPay(HttpServletRequest request, HttpServletResponse response, PaySaPi paySaPi) {
		// 保证密钥一致性
		logger.info(paySaPi.toString());
		if (PayUtil.checkPayKey(paySaPi)) {
			resmap.put(paySaPi.getOrderid(), paySaPi);
			logger.error("支付成功");
		} else {
			resmap.put(paySaPi.getOrderid(), false);
			logger.error("支付成功");
		}
	}

	@RequestMapping("/returnPay")
	public String returnPay(HttpServletRequest request, HttpServletResponse response, String orderid) {
		// 根据订单号查找相应的记录:根据结果跳转到不同的页面
		Object res = resmap.get(orderid);
		int i = 0;
		while(null==res){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			res = resmap.get(orderid);
			i++;
			if(i>3){
				break;
			}
		}
		if(null==res){
			return "payerr";
		}
		if (res instanceof PaySaPi) {
			return "paysuc";
		} else {
			return "payerr";
		}
	}
}
