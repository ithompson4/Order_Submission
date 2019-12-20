<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Order Page</title>
</head>
<body>
	<s:form action="selectOrder">	
		<h1>Select Order</h1>
		<s:textfield name="orderId" id="orderId" label="Enter order id"></s:textfield>		 
		<s:property value = "msg"/>
		<s:submit value="View Order Details"></s:submit>			 
	</s:form>
</body>
</html>