/**
 * Copyright©2016 zsy. all rights reserved.
 *
 * @Title MsgService.java
 * @Prject bidding
 * @Package com.taika.bidding.service
 * @author 张绍云
 * @date 2016年6月17日 下午8:58:55
 * @Description  
 */
package com.taika.bidding.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.taika.bidding.bean.Msg;
import com.taika.bidding.bean.Pager;
import com.taika.bidding.common.CommonStr;
import com.taika.bidding.dao.MsgDao;

/**
 * @ClassName MsgService
 * @Description
 * @author 张绍云
 * @date 2016年6月17日 下午8:58:55
 */
@Service
public class MsgService {

	@Autowired
	private MsgDao msgDao;

	private static List<Msg> msglist = new ArrayList<Msg>();
	
	private static String msgstr = "";

	@PostConstruct
	public void intiMsgList() {
		List<Msg> lm = msgDao.getNewMsg();
		msglist.addAll(lm);
		createMsgStr();
	}
	
	private void createMsgStr(){
		msgstr =JSON.toJSONString(msglist);
	}
	
	public String getMsgStr(){
		return msgstr;
	}

	public List<Msg> getNewMsg() {
		return msglist;
	}

	public boolean saveMsg(Msg msg) {
		Long id = msgDao.saveMsg(msg);
		if (id > 0) {
			msg.setId(id);
			msglist.add(msg);
			createMsgStr();
		}
		return true;
	}

	public boolean updateMsg(Long id) {
		boolean flag = msgDao.updateMsg(id);
		if (flag) {
			for (int i = 0; i < msglist.size(); i++) {
				if (msglist.get(i).getId() == id) {
					msglist.remove(i);
					break;
				}
			}
			createMsgStr();
		}
		return flag;
	}

	public Pager<Msg> queryOldMsg(String qstr, int currentPage) {
		Pager<Msg> msgpager = new Pager<Msg>();
		msgpager.setCurrentPage(currentPage);
		msgpager.setTotalSize(msgDao.count(qstr));
		msgpager.setReList(msgDao.getOldMsg(currentPage, CommonStr.PAGESIZE, qstr));
		return msgpager;
	}

}
