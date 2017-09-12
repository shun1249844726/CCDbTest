package com.lexinsmart.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {

	// JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://mysql.tickrobot.com:3306/cc_entrance_guard?characterEncoding=utf-8&useSSL=false";

	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "cc_test";
	static final String PASS = "cc_test";

	private static Connection conn = null;
	private static Statement stmt = null;
	static {
			// 注册 JDBC 驱动
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	public static Connection getConnection() {
		boolean flag = false;
		while(!flag){
			System.out.println("连接数据库...");
			try {
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				flag = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("连接数据库失败,1分钟后重新连接");
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		System.out.println("连接数据库ok");
		return conn;
	};
}