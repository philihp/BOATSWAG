<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html>
<html>
<head>
<title>#BOATSWAG</title>
</head>
<body>

	[
	<span title="Facebook ID #<bean:write name="facebook" property="id" />"><bean:write name="facebook"
			property="name" /></span> | 
	<html:link action="/authenticateLogout.do">Logout</html:link>
	]
	
	<p>You are a Holy Ship survivor. Righteous.</p>

</body>
</html>