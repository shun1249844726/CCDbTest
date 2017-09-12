package com.lexinsmart.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lexinsmart.dao.UserDao;
import com.lexinsmart.model.User;
import com.lexinsmart.utils.DBUtils;
import com.mysql.jdbc.PreparedStatement;

public class UserService {
	public String addUser(User user) throws SQLException{
		Connection conn = DBUtils.getConnection();
		PreparedStatement ptmt = null;
		conn.setAutoCommit(false);
		try{
			String sql;
			sql = "SELECT email,username FROM user where email=?  or username=? ";
			ptmt = (PreparedStatement) conn.prepareStatement(sql);
			ptmt.setString(1,user.getEmail());
			ptmt.setString(2, user.getUsername());
			
			ResultSet rs =ptmt.executeQuery();
			rs.last();
			if(rs.getRow() > 0){
				return "email 或用户名 重复";
			}else{
				UserDao ud = new UserDao();
				ud.addUser(user);
				conn.commit();
			}
			return "插入成功";
		}catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			return "插入失败！";
		}
	}
}
