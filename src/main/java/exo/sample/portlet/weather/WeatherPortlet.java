
package exo.sample.portlet.weather;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com Mar
 * 1, 2011
 */
public class WeatherPortlet extends GenericPortlet {

  public void init(PortletConfig config) throws PortletException {
    super.init(config);
  }

  public void doView(RenderRequest request, RenderResponse response) throws PortletException,
                                                                    IOException {
    response.setContentType("text/html");
    PortletPreferences prefs = request.getPreferences();
    String myprefs[] = (String[]) prefs.getValues("weatherprefs", null);
    String zipcode = "";
    String units = "";
    if (Validators.isValidPrefs(myprefs)) {
      zipcode = myprefs[0];
      units = myprefs[1];
      boolean isC = (units.equalsIgnoreCase("c") == true) ? true : false;
      String html = "";
      try {
        html = YahooWeatherServiceController.getInstance().getWeatherHtml(zipcode, isC);
      } catch (Exception e) {
        html = e.toString();
      }
      request.setAttribute("weatherReport", html);
    }

    PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/portlet/view.jsp");
    dispatcher.include(request, response);

  }

  public void doEdit(RenderRequest request, RenderResponse response) throws PortletException,
                                                                    IOException {
    response.setContentType("text/html");
    PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/portlet/edit.jsp");
    dispatcher.include(request, response);

  }

  public void doHelp(RenderRequest request, RenderResponse response) throws PortletException,
                                                                    IOException {
    response.setContentType("text/html");
    PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/portlet/help.jsp");
    dispatcher.include(request, response);

  }

  public void processAction(ActionRequest req, ActionResponse res) throws IOException,
                                                                  PortletException {
    String webMsg = "";
    PortletPreferences prefs = req.getPreferences();
    String myprefs[] = new String[3];
    String zipcode = req.getParameter("zipcode");
    String units = req.getParameter("units");
    String effectname = req.getParameter("effectname");

    if (!Validators.isValidZipCode(zipcode)) {
      res.setPortletMode(PortletMode.EDIT);
      webMsg = "Invalid Zip Code";
    } else {
      if (!Validators.isValidUnits(units)) {
        res.setPortletMode(PortletMode.EDIT);
        webMsg = "Invalid Units";
      } else {
        if (Validators.isValidEffectName(effectname)) {
          myprefs[0] = zipcode;
          myprefs[1] = units;
          myprefs[2] = effectname;
          prefs.setValues("weatherprefs", myprefs);
          try {
            prefs.store();
            res.setPortletMode(PortletMode.VIEW);
          } catch (ValidatorException ve) {
            webMsg = ve.getMessage();
            res.setPortletMode(PortletMode.EDIT);
          }
        } else {
          res.setPortletMode(PortletMode.EDIT);
          webMsg = "Invalid effect";
        }
      }
    }
    res.setRenderParameter("webMsg", webMsg);
  }

}
