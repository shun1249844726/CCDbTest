package com.lexinsmart.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import com.lexinsmart.dao.UserDao;
import com.lexinsmart.model.User;

public class DBCP {
	// 原理：让数据源动态生成连接
	private static DataSource myDataSource;
	private static Connection conn;
	private static Properties prop;

	// 单例模式
	private DBCP() {

	}

	// 在静态代码块中进行配置文件与程序的关联
	static {
		prop = new Properties();
		String commConfigFilePath = "./conf/dbcp.properties";
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(commConfigFilePath));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			prop.load(in);
			myDataSource = BasicDataSourceFactory.createDataSource(prop);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取连接
	public static Connection getConnection() throws SQLException {
		try {
			conn = myDataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	// 断开连接
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 测试
	public static void main(String[] args) throws SQLException {

		// 查询测试
		for (int i = 0; i < 50; i++) {
			Date c = new Date();
			List<User> users = queryByDbcp();
			for (User queryuser : users) {
				System.out.println(queryuser.toString());
			}
			Date d = new Date();
			System.out.println("usetime_:" + i + "\t" + (d.getTime() - c.getTime()));
		}

	}

	public static List<User> queryByDbcp() throws SQLException {

		Connection conn = DBCP.getConnection();
		Statement stmt = null;
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