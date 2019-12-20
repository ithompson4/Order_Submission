package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.*;
import javax.servlet.*;
import com.opensymphony.xwork2.ActionSupport;
import dao.ConnectionUtil;
import dao.DAO;
import dao.OrderInformation;
import dao.Product;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;

import java.sql.*;

public class SelectOrderAction extends ActionSupport implements SessionAware, ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	
	private int orderId = 0;
	
	private String lname;
	private String fname;
	private String address;
	private String city;
	private String state;
	private Date date;
	private List<String> productList;
	 
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

	private String msg = "";
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String execute() throws Exception {
		
		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			conn = ConnectionUtil.getPoolConnection();
			String query = "SELECT * FROM orders WHERE OrderID = " + orderId;
			rs = DAO.getQueryResultSet(conn, query);	
			if (rs.next()) {
				fname = rs.getString("FirstName");
				lname = rs.getString("LastName");
				address= rs.getString("Address");
				state = rs.getString("State");
				city= rs.getString("City");
				date = rs.getDate("SubmissionDate");
			}
			else {
				msg = "**Order id does not exists**";
				return "noorderid";
			}
			
			OrderInformation oi = new OrderInformation();
			orderInformationByid = oi.getOrderInformationByid(conn, orderId);
			ProductSelectAction psa = new ProductSelectAction();

			psa.setServletRequest(request);
			psa.setSession(getSession());
			psa.display();
			productList = psa.getProductList();
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
}
