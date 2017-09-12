package com.lexinsmart.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lexinsmart.dao.UserDao;
import com.lexinsmart.model.User;
import com.lexinsmart.service.UserService;
import com.lexinsmart.utils.DBUtils;

public class UserAction {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		UserDao ud = new UserDao();

		User user = new User();
		user.setUsername("徐d顺");
		user.setPassword("123");
		user.setLevel(1);
		user.setMobile("15962982827");
		user.setEmail("12d43@qq.com");
		user.setNickname("aa");

		Date javaDate = new Date();
		long javaTime = javaDate.getTime();
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);
		// System.out.println("The SQL TIMESTAMP is: " +
		// sqlTimestamp.toString());

		user.setCreated(sqlTimestamp);

		// UserService us = new UserService();
		// System.out.println(us.addUser(user));

//		// 查询测试
//		for (int i = 0; i < 10; i++) {
//			Date c = new Date();
//			List<User> users = ud.query();
//			for (User queryuser : users) {
//				System.out.println(queryuser.toString());
//			}
//			Date d = new Date();
////			System.out.println("usetime_:"+i + "\t" + (d.getTime() - c.getTime()));
//		}
//		
		// 查询测试
		for (int i = 0; i < 20; i++) {
			Date c = new Date();
			List<User> users = ud.queryByDbcp();
			for (User queryuser : users) {
				System.out.println(queryuser.toString());
			}
			Date d = new Date();
			System.out.println("usetime_:"+i + "\t" + (d.getTime() - c.getTime()));
		}
	}

}
