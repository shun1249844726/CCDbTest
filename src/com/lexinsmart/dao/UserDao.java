package com.lexinsmart.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lexinsmart.model.User;
import com.lexinsmart.utils.DBCP;
import com.lexinsmart.utils.DBUtils;
import com.mysql.jdbc.PreparedStatement;

public class UserDao {

	public void addUser(User user) throws SQLException {
		Connection conn = DBUtils.getConnection();
		String sql = "" + "insert into user " + " (username,password_hash,level,mobile,email,nickname,created) " + "values("
				+ "?,?,?,?,?,?,?)";
		PreparedStatement ptmt = (PreparedStatement) conn.prepareStatement(sql);

		ptmt.setString(1, user.getUsername());
		ptmt.setString(2, user.getPassword());
		ptmt.setInt(3, user.getLevel());
		ptmt.setString(4,user.getMobile());
		ptmt.setString(5, user.getEmail());
		ptmt.setString(6, user.getNickname());
		ptmt.setObject(7,user.getCreated());
		ptmt.execute();
		
	}

	public List<User> query() throws SQLException {

		
		long s = System.currentTimeMillis();
		Connection conn = DBUtils.getConnection();
		Statement stmt = null;
		System.out.println(System.currentTimeMillis() - s+"\tms\t"+"two");

		// 执行查询
		System.out.println(" 实例化Statement对...");
		stmt = conn.createStatement();
		String sql;
		sql = "SELECT id, username,password_hash,level,mobile,email,nickname,CREATED FROM user";
		ResultSet rs = stmt.executeQuery(sql);

		List<User> users = new ArrayList<User>();
		User user = null;
		// 展开结果集数据库
		while (rs.next()) {
			user = new User();
			// 通过字段检索
			 int id = rs.getInt("ID");
			String name = rs.getString("USERNAME");
			String password = rs.getString("password_hash");
			int level = rs.getInt("level");
			String mobile = rs.getString("mobile");
			String email = rs.getString("email");
			String nickname = rs.getString("nickname");
			Timestamp create = rs.getTimestamp("created");  			
			user.setId(id);
			user.setUsername(name);
			user.setPassword(password);
			user.setLevel(level);
			user.setMobile(mobile);
			user.setEmail(email);
			user.setNickname(nickname);
			user.setCreated(create);
			
			users.add(user);
		}
		return users;
	}
	public List<User> queryByDbcp() throws SQLException {
		long s = System.currentTimeMillis();

		Connection conn = DBCP.getConnection();
		Statement stmt = null;
		System.out.println(System.currentTimeMillis() - s+"\tms\t"+"two");

		// 执行查询
		System.out.println(" 实例化Statement对...");
		stmt = conn.createStatement();
		String sql;
		sql = "SELECT id, username,password_hash,level,mobile,email,nickname,CREATED FROM user";
		ResultSet rs = stmt.executeQuery(sql);
		List<User> users = new ArrayList<User>();
		User user = null;
		// 展开结果集数据库
		while (rs.next()) {
			user = new User();
			// 通过字段检索
			 int id = rs.getInt("ID");
			String name = rs.getString("USERNAME");
			String password = rs.getString("password_hash");
			int level = rs.getInt("level");
			String mobile = rs.getString("mobile");
			String email = rs.getString("email");
			String nickname = rs.getString("nickname");
			Timestamp create = rs.getTimestamp("created");  			
			user.setId(id);
			user.setUsername(name);
			user.setPassword(password);
			user.setLevel(level);
			user.setMobile(mobile);
			user.setEmail(email);
			user.setNickname(nickname);
			user.setCreated(create);
			
			users.add(user);
		}
		conn.close();

		return users;
	}
}
