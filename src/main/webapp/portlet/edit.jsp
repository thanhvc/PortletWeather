<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="exo.sample.portlet.weather.Validators"%>
<%@page import="javax.portlet.*"%>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<portlet:defineObjects />
<script src="<%=renderResponse.encodeURL("/portletweather-1.0-SNAPSHOT/portlet/js/prototype.js")%>" type="text/javascript"></script>
<script src="<%=renderResponse.encodeURL("/portletweather-1.0-SNAPSHOT/portlet/js/scriptaculous.js?load=effects")%>" type="text/javascript"></script>
<script type="text/javascript">
    function demo_effect(name) {
        var demoarea = document.getElementById('effectdemodiv');
        if (demoarea) {
            if (name == 'Appear') {
                demoarea.style.display = 'none';
            }
            Effect[name](demoarea);
        }
    }
</script>
<%
PortletPreferences prefs = renderRequest.getPreferences();
String myprefs[] = (String[])prefs.getValues("weatherprefs",null);
String zipcode = "";
String units = "c";
String effectname = "Appear";
boolean validPrefs = Validators.isValidPrefs(myprefs);
if (validPrefs) {
    zipcode = myprefs[0];
    units = myprefs[1];
    effectname = myprefs[2];
} else {
    myprefs = new String[3];
}
%>
<portlet:actionURL var="actionURL">
</portlet:actionURL>
<%
    String msg = (String)renderRequest.getParameter("webMsg");
    if (msg != null) {
%>
<div id="message" style="Color:#ff0000;">
    <p><%=msg%></p>
</div>
<%  } %>
<div id="title"><p>Edit Weather Portlet Preferences</p></div>
<div id="inputPanel">
    <form name="zipform" target="_self" method="POST" action="<%=actionURL.toString()%>">
        <p>ZIP code or Location ID: <input type=text" name="zipcode" value="<%=zipcode%>" maxlength="10" size="8"></p>
        <div id="unitsPanel">
            <p>Units:
            <% if (units.equalsIgnoreCase("c")) { %>
            <input type="radio" name="units" value="c" checked>Celsius
            <% } else { %>
            <input type="radio" name="units" value="c">Celsius
            <% } %>
            <% if (units.equalsIgnoreCase("f")) { %>
            <input type="radio" name="units" value="f" checked>Fahrenheit</p>
            <% } else { %>
            <input type="radio" name="units" value="f">Fahrenheit</p>
            <% } %>
            <p>Effect:
            <%if (effectname.equalsIgnoreCase("Appear")) {%>
            <input type="radio" name="effectname" value="Appear" onclick="demo_effect('Appear')" checked>Appear
            <% } else {%>
            <input type="radio" name="effectname" value="Appear" onclick="demo_effect('Appear')">Appear
            <% } %>
            <%if (effectname.equalsIgnoreCase("BlindDown")) {%>
            <input type="radio" name="effectname" value="BlindDown" onclick="demo_effect('BlindDown')" checked>BlindDown
            <% } else {%>
            <input type="radio" name="effectname" value="BlindDown" onclick="demo_effect('BlindDown')">BlindDown
            <% } %>
            <%if (effectname.equalsIgnoreCase("SlideDown")) {%>
            <input type="radio" name="effectname" value="SlideDown" onclick="demo_effect('SlideDown')" checked>SlideDown
            <% } else {%>
            <input type="radio" name="effectname" value="SlideDown" onclick="demo_effect('SlideDown')">SlideDown
            <% } %>
            <%if (effectname.equalsIgnoreCase("Grow")) {%>
            <input type="radio" name="effectname" value="Grow" checked onclick="demo_effect('Grow')">Grow
            <% } else {%>
            <input type="radio" name="effectname" value="Grow" onclick="demo_effect('Grow')">Grow
            <% } %>
                <p style="position:absolute; float:right; right:15%">
                    <div style="position: relative; top: 10%; left: 20%;">
                        <span>Effect Demo</span>
                        <div id="effectdemodiv">
                            <img id="dukeimg" src="<%= renderResponse.encodeURL("/QuickWCM/portlets/weather/images/duke.jpeg") %>" />
                        </div>
                    </div>
                </p>
            </p>
        </div>
        <p><input type="submit" name="save" value="Save"></p>
    </form>
</div>


