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

	<h2>You aren't a survivor!</h2>

	<p>
		You need to be in the <a href="https://www.facebook.com/groups/260515844014310/">Holy Ship Maiden Voyage</a> group to do stuff here. Get someone to let you in.
	</p>

	<ul>
		<logic:iterate name="groups" id="group">
			<li><bean:write name="group" property="id" />: <bean:write name="group" property="name" /></li>
		</logic:iterate>
	</ul>

</body>
</html>