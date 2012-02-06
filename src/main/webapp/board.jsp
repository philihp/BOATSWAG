<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html>
<html>
<head>
<title>#BOATSWAG</title>
<style>
body {
	font: 81.25%/1.5 Arial, Helvetica, sans-serif;
}
</style>
<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0;
	padding: 0
}

.infoPicture {
  float: left;
  margin-right: 10px;
}
.infoDetails {
}
.infoLocation {
  display: block;
  font-style: italic;
}

#map_canvas {
	height: 100%
	z-index: 1;
}
#util {

display: none;

  position: fixed;
  bottom: 1.5em;
  right: 0.5em;
  padding: 5px;
  border: 1px solid yellow;
  border-radius: 5px;
  background-color: rgba(255,255,255,0.75);
  z-index: 1;
}
</style>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <script type="text/javascript"
      src="http://maps.googleapis.com/maps/api/js?key=AIzaSyClU2_S9sMyhaSFjunq9GSZg91b_-pnjWI&sensor=false">
    </script>
    <script type="text/javascript">
      function initialize() {
    	  
        var myOptions = {
          center: new google.maps.LatLng(<bean:write name="me" property="latitude" />, <bean:write name="me" property="longitude" />),
          zoom: 4,
          mapTypeId: google.maps.MapTypeId.TERRAIN
        };
        var map = new google.maps.Map(document.getElementById("map_canvas"),
            myOptions);
        
        var infoWindow = new google.maps.InfoWindow({ });
        
<logic:iterate name="users" id="user">
				var pin<bean:write name="user" property="facebookId" ignore="true" /> = new google.maps.Marker({
					position: new google.maps.LatLng(<bean:write name="user" property="latitudeWithVariance" ignore="true" />,<bean:write name="user" property="longitudeWithVariance" ignore="true" />),
					map: map,
					title:"<bean:write name="user" property="nameEscapedForJavascript" filter="false" />"
				});
				google.maps.event.addListener(pin<bean:write name="user" property="facebookId" ignore="true" />, 'click', function() {
					infoWindow.setContent('<div class="infoPicture"><a href="<bean:write name="user" property="link" ignore="true" />"><img src="https://graph.facebook.com/<bean:write name="user" property="facebookId" />/picture" /></a></div><span class="infoDetails"><a href="<bean:write name="user" property="link" ignore="true" />"><bean:write name="user" property="nameEscapedForJavascript" filter="false" /></a></span><span class="infoLocation"><bean:write name="user" property="locationNameEscapedForJavascript" filter="false" /></span>');
					infoWindow.open(map,pin<bean:write name="user" property="facebookId" ignore="true" />);
				});
</logic:iterate>

        
      }
    </script>
</head>
<body onload="initialize()">

  <div id="util">
	[
	<span title="Facebook ID #<bean:write name="me" property="facebookId" />"><bean:write name="me" property="name" /></span> | 
	<html:link action="/refresh.do">Refresh</html:link>
	<logic:equal name="me" property="facebookId" value="11803542">
  |
	<html:link action="/refreshGroup.do">Group</html:link>
	</logic:equal>
	]
	</div>

  <div id="map_canvas" style="width:100%; height:100%"></div>

</body>
</html>