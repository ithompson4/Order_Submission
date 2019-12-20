package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.*;
import javax.servlet.*;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import dao.ConnectionUtil;
import dao.DAO;
import dao.OrderInformation;
import dao.Product;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import java.sql.*;

public class SubmitOrderAction extends ActionSupport implements SessionAware, ServletRequestAware, Preparable {
	
	private static final long serialVersionUID = 1L;
	private String lname;
	private String fname;
	private String address;
	private String city;
	private String state;
	private Date date;
	private List<String> productList;

	private String uorderId;

	public String getUorderId() {
		return uorderId;
	}
	public void setUorderId(String uorderId) {
		this.uorderId = uorderId;
	}

	public List<String> getProductList() {
		return productList;
	}
	public void setProductList(List<String> productList) {
		this.productList = productList;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	private int orderId = 0;
	private ArrayList<Product> orderInformationByid;

	public ArrayList<Product> getOrderInformationByid() {
		return orderInformationByid;
	}

	public void setOrderInformationByid(ArrayList<Product> orderInformationByid) {
		this.orderInformationByid = orderInformationByid;
	}

	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	private HttpServletRequest request;

	// Method to set HttpServletRequest
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	// Method to set session Map
	Map session;

	public void setSession(Map session) {
		this.session = session;
	}
	public Map getSession() {
		return session;
	}

	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String execute() throws Exception {
		ProductSelectAction psa = new ProductSelectAction();
		
		psa.setServletRequest(request);
		psa.setSession(getSession());
		psa.display();
		productList = psa.getProductList();
		
		// getting all selected products and quantities
		String description[] = (String[]) request.getParameterValues("description");
		String quantity[] = (String[]) request.getParameterValues("quantity");

		// gathering all selected products in Hashmap as user can select same product in different dropdowns
		HashMap<String, Product> productMap = new HashMap<String, Product>();
		for (int i = 0; i < description.length; i++) {
			String key = description[i].substring(0, description[i].indexOf("-"));
			String des = description[i].substring(description[i].indexOf("-") + 1);
			int q = Integer.parseInt(quantity[i]);
			// if product already added
			if (productMap.containsKey(key)) {
				productMap.get(key).setQuantity(q + productMap.get(key).getQuantity());
			}
			else { 
				// add it to map
				Product p = new Product();
				p.setId(Integer.parseInt(key));
				p.setDescription(des);
				p.setQuantity(q);
				productMap.put(key, p);
			}
		}
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			conn = ConnectionUtil.getPoolConnection();
			rs = DAO.getProductResultSet(conn);
			while (rs.next()) {
				String key = (rs.getString("productid"));
				if (productMap.containsKey(key)) {
					productMap.get(key).setPrice(rs.getDouble("Price"));
				}
			}
			PreparedStatement ps = null;
			// if update order
			if (uorderId != null && uorderId.length() > 0) {
				// user is updating the orders so update customer and order information
				ps = conn.prepareStatement("DELETE FROM LineItems WHERE OrderID = "+uorderId);
				ps.executeUpdate();
				ps = conn.prepareStatement("UPDATE orders SET FirstName =?, LastName=?, Address=?, City=?, State=?, SubmissionDate=? WHERE OrderID = "+uorderId);
			}
			else {
				// inserting order information information
				ps = conn.prepareStatement("INSERT INTO orders(FirstName, LastName, Address, City, State, SubmissionDate) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			}
			ps.setString(1, getFname());
			ps.setString(2, getLname());
			ps.setString(3, getAddress());
			ps.setString(4, getCity());
			ps.setString(5, getState());
			date = new java.sql.Date(System.currentTimeMillis());
			ps.setDate(6, date);
			int i = ps.executeUpdate();

			if (uorderId != null && uorderId.length() > 0) {
				orderId = Integer.parseInt(uorderId);
			}
			else if (i > 0) {
				rs = ps.getGeneratedKeys();
				rs.next();
				orderId = rs.getInt(1);
			}
			// update lineitem table using this order id
			Set<String> keys = productMap.keySet();
			Iterator<String> itr = keys.iterator();
			while (itr.hasNext()) {
				Product p = productMap.get(itr.next());
				ps = conn.prepareStatement("INSERT INTO LineItems(OrderID, ProductId, Quantity, TotalPrice) VALUES(?,?,?,?)");
				ps.setInt(1, orderId);
				ps.setInt(2, p.getId());
				ps.setInt(3, p.getQuantity());
				ps.setDouble(4, p.getCost());
				ps.executeUpdate();
			}
			OrderInformation oi = new OrderInformation();
			orderInformationByid = oi.getOrderInformationByid(conn, orderId);
			
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	public void prepare() throws Exception {
		ProductSelectAction psa = new ProductSelectAction();
		psa.setServletRequest(request);
		psa.setSession(getSession());
		psa.display();
		productList = psa.getProductList();		
	}
}
