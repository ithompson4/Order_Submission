<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	     pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Submit Order Page</title>
<script src="script.js"></script>
</head>
<body>
<s:actionerror />
	<s:form action="submitOrder">
		<h1>Order Input</h1>
		<h2>Customer information</h2>
		<br>
		<table id="t1">
		    <tr>
				<td><s:textfield name="fname" id="fname" label="First name" required="true"></s:textfield>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><s:textfield name="lname" id="lname" label="Last name" required="true"></s:textfield></td>
				<td></td>
			</tr>			
			<tr>
				<td><s:textfield name="address" id="address" label="Address" required="true"></s:textfield>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><s:textfield name="city" id="city" label="City" required="true"></s:textfield>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><s:textfield name="state" id="state" label="State" required="true"></s:textfield>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><s:select label="Select product" list="productList"
						name="description" id="description1" value="defaultSelection" />
				</td>
				<td><s:select label="Quantity"
						list="#{'1':'1', '2':'2', '3':'3', '4':'4','5':'5', '6':'6', '7':'7', '8':'8','9':'9', '10':'10', '11':'11', '12':'12'}"
						name="quantity" id="quantity1" value="1" /></td>
			</tr>
			<tr>
				<td><s:submit type="button" value="New order item"
						onclick="addRow('t1');return false;" /></td>
				<td><s:submit value="Submit"></s:submit></td>
			</tr>
		</table>
	</s:form>
</body>
</html>