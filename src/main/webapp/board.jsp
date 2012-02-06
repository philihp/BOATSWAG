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
	<span title="Facebook ID #<bean:write name="credentials" property="id" />"><bean:write name="credentials"
			property="name" /></span>
	|
	Refresh: <html:link action="/refreshLocation.do">Location</html:link>
	|
	<html:link action="/refreshFriends.do">My Friends</html:link>
<logic:equal name="credentials" property="id" value="11803542">
  |
	<html:link action="/refreshGroup.do">The Group</html:link>
</logic:equal>
	]
	
	<p>Longitude: <bean:write name="location" property="longitude" ignore="true" /></p>
	<p>Latitude: <bean:write name="location" property="latitude" ignore="true"  /></p>

</body>
</html>