package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//class to encapsulate whole order information
public class OrderInformation {
	public OrderInformation() {
		super();
	}
	/**
	 * method to get order information by id
	 * @param conn
	 * @param orderId
	 * @return
	 * @throws SQLException 
	 */
	public  ArrayList<Product> getOrderInformationByid(Connection conn, int orderId) throws SQLException {
		
		ArrayList<Product> productList = new ArrayList<Product> ();
		String query = "SELECT l.OrderId, l.ProductId, l.Quantity, l.TotalPrice, p.Description, p.Price "
				     + "FROM LineItems l INNER JOIN products p ON l.ProductId = p.ProductId "
				     + "WHERE l.OrderID = " + orderId;
		ResultSet rs = DAO.getQueryResultSet(conn, query);	
		while(rs.next()) {
			Product p = new Product();
			p.setOrderId(orderId);
			p.setId(rs.getInt("ProductId"));
			p.setDescription(rs.getString("description"));
			p.setPrice(rs.getDouble("price"));
			p.setQuantity(rs.getInt("quantity"));
			productList.add(p);
		}
		return productList;
	}
}
