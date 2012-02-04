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
	
	<p>You're a survivor.</p>
	
	<p>Longitude: <bean:write name="location" property="longitude" ignore="true" /></p>
	<p>Latitude: <bean:write name="location" property="latitude" ignore="true"  /></p>

  <p><html:link action="/refreshMyFriends.do">Refresh my Friends</html:link></p>

</body>
</html>