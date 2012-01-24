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
	<logic:empty name="facebook">
		<html:link action="/authenticate.do">Login</html:link>
	</logic:empty>
	]
	
	This is the Holy Ship Survivor page. You need to login.

</body>
</html>