package com.lexinsmart.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBManager {
	protected static final Log log = LogFactory.getLog(DBManager.class.getName());
	private static DBManager _instance = null;
	private static DataSource ds;
	private static final String DBCP_FILE_PATH = "conf/dbcp.properties";

	public static DBManager getInstance() {
		if (_instance == null) {
			_instance = new DBManager();
		}
		return _instance;
	}

	static {
		try {
			Properties prop = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(DBCP_FILE_PATH));
			prop.load(in);
			ds = BasicDataSourceFactory.createDataSource(prop);
			log.info("databasepool init success!idle=" + ((BasicDataSource) ds).getNumIdle());
		} catch (Exception e) {
			log.info("databasepool init error:", e);
			e.printStackTrace();
			throw new ExceptionInInitializerError("databasepool init error!");
		}
	}

	/**
	 * get a connection from the pool
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		try {
			Connection con = ds.getConnection();
			return con;
		} catch (Exception e) {
			log.info("getConnection fail:", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * release the connection
	 * 
	 * @param con
	 */
	public static void releaseConnection(Connection con) {
		try {
			if (con != null && !con.isClosed())
				con.close();
		} catch (SQLException ex) {
			log.info("releaseConnection fail:", ex);
			ex.printStackTrace();
		}
	}

	/**
	 * close statement and resultset
	 * 
	 * @param stm
	 * @param result
	 */
	public static void closeStatement(Statement stm, ResultSet result) {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException e) {
			log.error("closeStatement error:", e);
			e.printStackTrace();
		}

		try {
			if (stm != null) {
				stm.close();
			}
		} catch (SQLException e) {
			log.error("closeStatement error:", e);
			e.printStackTrace();
		}
	}

	/**
	 * close statement
	 * 
	 * @param st
	 */
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				log.error("closeStatement error:", e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * generic query method
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet executeQuery(String sql) {
		ResultSet rset = null;
		Statement _stmt = null;
		Connection con = getConnection();
		try {
			_stmt = con.createStatement();
			rset = _stmt.executeQuery(sql);
		} catch (Exception e) {
			log.error("executeQuery error:", e);
			e.printStackTrace();
		} finally {
			closeStatement(_stmt);
			releaseConnection(con);
		}
		return rset;
	}

	/**
	 * generic update method
	 * 
	 * @param sql
	 */
	public void updateDB(String sql) {
		Connection con = getConnection();
		Statement _stmt = null;
		try {
			_stmt = con.createStatement();
			_stmt.executeUpdate(sql);
		} catch (Exception e) {
			log.error("updateDB error:", e);
			e.printStackTrace();
		} finally {
			closeStatement(_stmt);
			releaseConnection(con);
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 30; i++) {
			long s = System.currentTimeMillis();
			Connection con = getConnection();

			System.out.println(con.toString());
			releaseConnection(con);
			System.out.println(System.currentTimeMillis() - s);
		}

	}
}