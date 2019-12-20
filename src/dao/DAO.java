package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
	
	/**
	 * Method to get Products table results
	 * @param conn
	 * @return
	 * @throws SQLException 
	 */
	public static ResultSet getProductResultSet(Connection conn) throws SQLException {
		String querySearch = "SELECT * FROM products";
		return getQueryResultSet(conn, querySearch);
	}	
	/**
	 * Method to get lineitems table results
	 * @param conn
	 * @return
	 * @throws SQLException 
	 */
	
	public static ResultSet getLineItemsResultSet(Connection conn) throws SQLException {
		String querySearch = "SELECT * FROM lineitems";
		return getQueryResultSet(conn, querySearch);
	}	
	/**
	 * Method to get orders table results
	 * @param conn
	 * @return
	 * @throws SQLException 
	 */
	
	public static ResultSet getOrdersResultSet(Connection conn) throws SQLException {
		String querySearch = "SELECT * FROM orders";
		return getQueryResultSet(conn, querySearch);
	}	
	/**
	 * ethod to get result of given query
	 * @param conn
	 * @param querySearch
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getQueryResultSet(Connection conn, String querySearch) throws SQLException {
		Statement s = conn.createStatement();
		s.executeQuery(querySearch);
		return s.getResultSet();
	}	
}
