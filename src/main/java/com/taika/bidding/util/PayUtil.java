package com.taika.bidding.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taika.bidding.bean.PaySaPi;

public class PayUtil {

	private static Logger logger = LogManager.getLogger();

	public static String UID = "061352332b12ad0558cda594";

	public static String NOTIFY_URL = "http://www.zsy666.com/bidding/paysapi/notifyPay";

	public static String RETURN_URL = "http://www.zsy666.com/bidding/paysapi/returnPay";

	public static String BASE_URL = "https://pay.paysapi.com";

	public static String TOKEN = "a4bbe5e3e8d1d03c29db02d68d5c19db";

	public static Map<String, Object> payOrder(Map<String, Object> remoteMap) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uid", UID);
		paramMap.put("notify_url", NOTIFY_URL);
		paramMap.put("return_url", RETURN_URL);
		paramMap.putAll(remoteMap);
		paramMap.put("key", getKey(paramMap));
		return paramMap;
	}

	public static String getKey(Map<String, Object> remoteMap) {
		String key = "";
		if (null != remoteMap.get("goodsname")) {
			key += remoteMap.get("goodsname");
		}
		if (null != remoteMap.get("istype")) {
			key += remoteMap.get("istype");
		}
		if (null != remoteMap.get("notify_url")) {
			key += remoteMap.get("notify_url");
		}
		if (null != remoteMap.get("orderid")) {
			key += remoteMap.get("orderid");
		}
		if (null != remoteMap.get("orderuid")) {
			key += remoteMap.get("orderuid");
		}
		if (null != remoteMap.get("price")) {
			key += remoteMap.get("price");
		}
		if (null != remoteMap.get("return_url")) {
			key += remoteMap.get("return_url");
		}
		key += TOKEN;
		if (null != remoteMap.get("uid")) {
			key += remoteMap.get("uid");
		}
		try {
			return MD5Util.encryption(key);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean checkPayKey(PaySaPi paySaPi) {
		String key = "";
		if (!StringUtils.isBlank(paySaPi.getOrderid())) {
			logger.info("支付回来的订单号：" + paySaPi.getOrderid());
			key += paySaPi.getOrderid();
		}
		if (!StringUtils.isBlank(paySaPi.getOrderuid())) {
			logger.info("支付回来的支付记录的ID：" + paySaPi.getOrderuid());
			key += paySaPi.getOrderuid();
		}
		if (!StringUtils.isBlank(paySaPi.getPaysapi_id())) {
			logger.info("支付回来的平台订单号：" + paySaPi.getPaysapi_id());
			key += paySaPi.getPaysapi_id();
		}
		if (!StringUtils.isBlank(paySaPi.getPrice())) {
			logger.info("支付回来的价格：" + paySaPi.getPrice());
			key += paySaPi.getPrice();
		}
		if (!StringUtils.isBlank(paySaPi.getRealprice())) {
			logger.info("支付回来的真实价格：" + paySaPi.getRealprice());
			key += paySaPi.getRealprice();
		}
		logger.info("支付回来的Key：" + paySaPi.getKey());
		key += TOKEN;
		String x = "";
		try {
			 x = MD5Util.encryption(key);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("我们自己拼接的Key：" + x);
		return paySaPi.getKey().equals(x);
	}

	public static String getOrderIdByUUId() {
		int machineId = 1;// 最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {// 有可能是负数
			hashCodeV = -hashCodeV;
		}
		// 0 代表前面补充0;d 代表参数为正数型
		return machineId + String.format("%01d", hashCodeV);
	}

}
