<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html>
<html>
<head>
<title>Airbnb Radar</title>
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

      var overlay;
      TitleOverlay.prototype = new google.maps.OverlayView();

      function initialize() {
    	  
        var myOptions = {
<logic:empty name="me" property="latitude">
					center: new google.maps.LatLng(38.2300706544397, -97.33423129957447),
</logic:empty>
<logic:notEmpty name="me" property="latitude">
					center: new google.maps.LatLng(<bean:write name="me" property="latitude" />, <bean:write name="me" property="longitude" />),
</logic:notEmpty>
          zoom: 4,
          mapTypeId: google.maps.MapTypeId.TERRAIN
        };
        var map = new google.maps.Map(document.getElementById("map_canvas"),
            myOptions);

        var swBound = new google.maps.LatLng(49.0,-122.0);
        var neBound = new google.maps.LatLng(54.0,-65.4);
        var bounds = new google.maps.LatLngBounds(swBound, neBound);
        var srcImage = 'overlay_title.png';

        //overlay = new TitleOverlay(bounds, srcImage, map);
        
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

    function TitleOverlay(bounds, image, map) {
      this.bounds_ = bounds;
      this.image_ = image;
      this.map_ = map;
      this.div_ = null;
      this.setMap(map);
    }
    TitleOverlay.prototype.onAdd = function() {
      var div = document.createElement('DIV');
      div.style.borderStyle = 'none';
      div.style.borderWidth = '0';
      div.style.position = 'absolute';

      var img = document.createElement('img');
      img.src = this.image_;
      img.style.width = '100%';
      img.style.height = '100%';
      div.appendChild(img);

      this.div_ = div;

      var panes = this.getPanes();
      panes.overlayImage.appendChild(div);
    }
    TitleOverlay.prototype.draw = function() {
      var overlayProjection = this.getProjection();
      var sw = overlayProjection.fromLatLngToDivPixel(this.bounds_.getSouthWest());
      var ne = overlayProjection.fromLatLngToDivPixel(this.bounds_.getNorthEast());
      var div = this.div_;
      div.style.left = sw.x + 'px';
      div.style.top = ne.y + 'px';
      div.style.width = (ne.x - sw.x) + 'px';
      div.style.height = (sw.y - ne.y) + 'px';
    }
    TitleOverlay.prototype.onRemove = function() {
      this.div_.parentNode.removeChild(this.div_);
      this.div_ = null;
    }
    </script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-27506789-3']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>
<body onload="initialize()">

  <div id="util">
	[
	<span title="Facebook ID #<bean:write name="me" property="facebookId" />"><bean:write name="me" property="name" /></span>
	| 
	<html:link action="/refreshGroup.do">Refresh Group</html:link>
	]
	</div>

  <div id="map_canvas" style="width:100%; height:100%"></div>

</body>
</html>
