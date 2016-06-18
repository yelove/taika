/**
 * Copyright©2016 zsy. all rights reserved.
 *
 * @Title PageAction.java
 * @Prject bidding
 * @Package com.taika.bidding.action
 * @author 张绍云
 * @date 2016年6月16日 下午8:25:54
 * @Description  
 */
package com.taika.bidding.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taika.bidding.bean.Msg;
import com.taika.bidding.common.CommonStr;
import com.taika.bidding.service.MsgService;

/**
 * @ClassName PageAction
 * @Description
 * @author 张绍云
 * @date 2016年6月16日 下午8:25:54
 */
@Controller
public class PageAction {

	@Autowired
	private MsgService msgService;

	@RequestMapping(value = "mgorder", method = RequestMethod.GET)
	public String order(HttpServletRequest request) {
		request.setAttribute("flag", true); 
		request.setAttribute("list", msgService.getNewMsg()); 
		return "myorder";
	}

	@RequestMapping(value = "allcansee", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> allcansee() {
		Map<String, Object> rm = new HashMap<String, Object>();
		rm.put(CommonStr.STATUS, 1000);
		rm.put(CommonStr.DESC, msgService.getNewMsg());
		return rm;
	}
	@RequestMapping(value = "allcanseestr", method = RequestMethod.GET)
	@ResponseBody
	public String allcanseeStr(){
		return msgService.getMsgStr();
	}
	
	@RequestMapping(value = "addmsg", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String addMsg(HttpServletRequest request){
		String msg = request.getParameter("msg");
		String msgdesc = request.getParameter("msgdesc");
		Msg msgo = new Msg();
		msgo.setMsg(msg);
		msgo.setMsgDesc(msgdesc);
		request.setAttribute("flag", msgService.saveMsg(msgo)); 
		request.setAttribute("list", msgService.getNewMsg()); 
		return "myorder";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String updateMsg(HttpServletRequest request,@PathVariable("id") Long id){
		request.setAttribute("flag", msgService.updateMsg(id)); 
		request.setAttribute("list", msgService.getNewMsg()); 
		return "myorder";
	}

}
