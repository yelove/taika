package com.taika.bidding.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taika.bidding.bean.User;
import com.taika.bidding.dao.UserDao;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	private static ConcurrentHashMap<String,User> userCache =  new ConcurrentHashMap<String,User>();
	
	public boolean saveUser(User user){
		user.setId(System.currentTimeMillis());//临时ID
		user.setCreatetime(System.currentTimeMillis());
		userCache.put(user.getName(), user);
		userDao.saveUser(user);
		return true;
	}
	
	public User getUserByName(String name){
		User user = userDao.getUser(name);
		if(null!=user){
			userCache.put(user.getName(), user);
		}
		return user;
	}

	public void logout(User user) {
		user.setUpdatetime(System.currentTimeMillis());
		userCache.put(user.getName(), user);
	}
	
	public void updateUserForLastLog(User user){
		userDao.updateUserForLastLog(user.getId(), System.currentTimeMillis());
	}

}
