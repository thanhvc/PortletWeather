<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="javax.portlet.*"%>
<%@page import="exo.sample.portlet.weather.Validators"%>
<%@page import="exo.sample.portlet.weather.WeatherPortlet"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<portlet:defineObjects />
<script src="<%=renderResponse.encodeURL("/portletweather-1.0-SNAPSHOT/portlet/js/prototype.js")%>" type="text/javascript"></script>
<script src="<%=renderResponse.encodeURL("/portletweather-1.0-SNAPSHOT/portlet/js/scriptaculous.js?load=effects")%>" type="text/javascript"></script>
<%
PortletPreferences prefs = renderRequest.getPreferences();
String myprefs[] = (String[])prefs.getValues("weatherprefs",null);
String zipcode = "";
String units = "";
String effectname="";
String html = "";
boolean validPrefs = false;
if (Validators.isValidPrefs(myprefs)) {
    zipcode = myprefs[0];
    units = myprefs[1];
    effectname = myprefs[2];
    validPrefs = true;
    html = (String)renderRequest.getAttribute("weatherReport");
    if (html == null) {
        html = "null";
    }
}
%>
<%if (validPrefs) { %>
<div id="reportTitle">
    <p>Weather Report for zipcode: <%=zipcode%></p>
    <div id="reportPanel" style="display: none"><%=html%></div>
</div>
<script type="text/javascript">
    var effname = '<%=effectname%>';
    var effectMethod = Effect[effname];
    if (effectMethod) {
        effectMethod('reportPanel');
    }
    //Effect.Appear('reportPanel',{duration:1.0});
</script>
<% } else { %>
<div id="reportInvalidPrefsTitle">
    <p>Preferences for zipcode and/or units are not set yet. Please Edit and set the preferences first!</p>
</div>
<% } %>

