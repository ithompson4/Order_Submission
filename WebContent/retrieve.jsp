<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	     pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Order Page</title>
<script src="script.js"></script> 
</head>
<body>
    <a href="selectAction">Submit an order</a><br>
    <a href="SelectOrder.jsp">Retrieve an order</a><br>
	<s:form action="submitOrder">
		<h1>Update order</h1>
		<s:textfield readonly="true" name="uorderId" id="orderId" label="Order number" value="%{orderId}" ></s:textfield>
		<h2>Customer information</h2>
		<br>
		<table id="t1">
			<tr>
				<td>
				   <s:textfield name="fname" id="fname" label="First name" value="%{fname}"></s:textfield>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
				   <s:textfield name="lname" id="lname" label="Last name" value="%{lname}"></s:textfield>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
				   <s:textfield name="address" id="address" label="Address" value="%{address}"></s:textfield>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
				   <s:textfield name="city" id="city" label="City" value="%{city}"></s:textfield>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
				   <s:textfield name="state" id="state" label="State" value="%{state}" ></s:textfield>
				</td>
				<td></td>
			</tr>
			
			<s:iterator value="orderInformationByid"> 
			
			<tr>
				<td>
				   <s:select label="Select product" list="productList" name="description" id="description1" value="%{optionDescription}" /> 
				</td>
				<td>
				   <s:select label="Quantity"
						     list="#{'1':'1', '2':'2', '3':'3', '4':'4', '5':'5', '6':'6', '7':'7', '8':'8', '9':'9', '10':'10', '11':'11', '12':'12'}"
						     name="quantity" id="quantity1"  value="%{quantity}" />
			    </td>
			</tr>
			</s:iterator>
			<tr>
				<td>
				   <s:submit type="button" value="New Order Item" id="submitOrder" onclick="addRow('t1');return false;" />
			    </td>
				<td><s:submit value="Update Order"></s:submit></td>
			</tr>			
		</table>
	</s:form>
</body>
</html>