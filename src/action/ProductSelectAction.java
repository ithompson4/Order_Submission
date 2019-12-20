package action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import dao.ConnectionUtil;
import dao.DAO;

public class ProductSelectAction extends ActionSupport implements SessionAware, ServletRequestAware, Preparable {
	
	private static final long serialVersionUID = 1L;
	private List<String> productList;

	public List<String> getProductList() {
		return productList;
	}

	public void setProductList(List<String> productList) {
		this.productList = productList;
	}

	private String id;
	private String description;
	private String defaultSelection;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDefaultSelection() {
		return defaultSelection;
	}
	
	public ProductSelectAction() {
		productList = new ArrayList<String>();		
	}

	public String execute() {
		return SUCCESS;
	}
	
	public String display() {
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = ConnectionUtil.getPoolConnection();
			rs = DAO.getProductResultSet(conn);
			productList = new ArrayList<String>();
			int i = 0;
			while (rs.next()) {
				//setting default selection for the drop down
				if (i == 0) {
					defaultSelection = rs.getString("ProductId") + "-" + rs.getString("Description");
					i = 1;
				}
				//adding all the products from database to the list to display in drop down on order page
				productList.add(rs.getString("ProductId") + "-" + rs.getString("Description"));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}		
		return NONE;
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

	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		display();
	}
}
