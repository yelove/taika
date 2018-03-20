package com.taika.bidding.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.taika.bidding.bean.User;
import com.taika.bidding.util.MD5Tools;

@Service
public class UserDao {
	
	@Autowired
	private JdbcTemplate template;
	
	public long saveUser(final User user){
		KeyHolder kh = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"INSERT into `user` (name,password,namemd5,departmentid,department,realname,state,createtime,updatetime) VALUES (?,md5(?),md5(?),?,?,?,?,?,?);");
				ps.setString(1, user.getName());
				ps.setString(2, user.getPassword());
				ps.setString(3,  user.getName());
				ps.setLong(4, user.getDepartmentid());
				ps.setString(5, user.getDepartment());
				ps.setString(6, user.getRealname());
				ps.setInt(7, user.getState());
				ps.setLong(8, user.getCreatetime());
				ps.setLong(9, user.getUpdatetime());
				return ps;
			}
		}, kh);
		return kh.getKey().longValue();
	}
	
	public User getUser(Long id){
		return template.queryForObject("select * from `user` where id = ?", User.class, id);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public User getUser(String name){
		return (User) template.queryForObject("select * from `user` where namemd5 = ?", new Object[]{MD5Tools.MD5(name)},new BeanPropertyRowMapper(User.class));
	}
	
	public boolean updateUserForLastLog(Long id,Long updateTime){
		int i = template.update("UPDATE `user` set updatetime=? where id=?", updateTime,id);
		if(i>0){
			return true;
		}
		return false;
	}

}
