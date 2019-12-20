package dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionUtil extends Object {

	private static ConnectionUtil instance;
	private static DataSource ds = null;
	private static final String dbName = "jdbc/productdb";

	ConnectionUtil() throws SQLException {
		init();
	}

	public static ConnectionUtil getInstance() throws SQLException {
		if (instance == null)
			instance = new ConnectionUtil();
		return instance;
	}

	public void init() throws SQLException {
		InitialContext ctx;

		try {
			ctx = new InitialContext();

			// The JDBC Data source that we have just created
			ds = (DataSource) ctx.lookup("jdbc/productdb");

		} catch (NamingException e) {
			System.err.println("Problem looking up " + dbName + ": " + e);
		}
	}

	public static Connection getPoolConnection() throws SQLException {
		instance = ConnectionUtil.getInstance();
		Connection conn = ds.getConnection();
		if (conn == null)
			throw new SQLException("Maximum number ofconnections in pool exceeded.");
		return conn;
	}
}
