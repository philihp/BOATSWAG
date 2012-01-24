<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html>
<html>
<head>
<title>Holy Graffiti</title>
</head>
<body>

	[
	<logic:empty name="facebook">
		<html:link action="/authenticate.do">Login</html:link>
	</logic:empty>
	<logic:notEmpty name="facebook">
		<bean:write name="facebook" property="firstName" />
		(#<bean:write name="facebook" property="id" />)
		<html:link action="/authenticateLogout.do">Logout</html:link>
	</logic:notEmpty>
	]
	
	<h2>Groups:</h2>
	<logic:iterate name="groups" id="group">
		<bean:write name="group" property="id" />
	:
	<bean:write name="group" property="name" />
		<br />
	</logic:iterate>

</body>
</html>