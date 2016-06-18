package com.taika.bidding.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taika.bidding.bean.User;
import com.taika.bidding.common.CommonStr;
import com.taika.bidding.service.UserService;
import com.taika.bidding.util.MD5Tools;

@RequestMapping(value = "user/")
@Controller
public class LogInAction {

	@Autowired
	private UserService uService;

	@RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody Map<String, Object> login(User user, ModelMap mdmap, HttpServletRequest request) {
		Map<String, Object> rm = new HashMap<String, Object>();
		rm.put(CommonStr.STATUS, 1002);
		if (StringUtils.isEmpty(user.getName())) {
			rm.put(CommonStr.STATUS, 1009);
			return rm;
		}
		User aluser = uService.getUserByName(user.getName());
		if (null == aluser) {
			rm.put(CommonStr.STATUS, 1009);
			return rm;
		}
		if (!aluser.getPassword().equals(MD5Tools.MD5(user.getPassword()))) {
			rm.put(CommonStr.STATUS, 1004);
			return rm;
		}
		else {
			request.getSession(true).setAttribute(CommonStr.USERNAME, user.getName());
			request.getSession(true).setAttribute(CommonStr.TKUSER, aluser);
			rm.put(CommonStr.STATUS, 1000);
		}
		return rm;
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logOut(ModelMap mdmap, HttpServletRequest request) {
		request.getSession(true).removeAttribute(CommonStr.USERNAME);
		User user = (User) request.getSession(true).getAttribute(CommonStr.TKUSER);
		if (null != user) {
			uService.logout(user);
		}
		request.getSession(true).removeAttribute(CommonStr.TKUSER);
		return "redirect:/";
	}
}
