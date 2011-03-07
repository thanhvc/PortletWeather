package exo.sample.portlet.weather;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 1, 2011  
 */
public class Validators {

  public static final String valid_units = "fcFC";
  public static final String valid_effectnames[] = {"Appear", "BlindDown", "SlideDown", "Grow"};
  /** Creates a new instance of Validators */
  public Validators() {
  }

  public static boolean isValidEffectName(String name) {
      boolean valid = false;
      for (String s : valid_effectnames) {
          if (s.equalsIgnoreCase(name)) {
              valid = true;
              break;
          }
      }
      return valid;
  }

  public static boolean isValidPrefs(String[] myprefs) {
      boolean valid = false;
      if ((myprefs != null) && (myprefs.length == 3)) {
          // zip code
          if ((myprefs[0]!=null) && (myprefs[0].length() >= 0)) {
              // units
              if ((myprefs[1]!=null) && (myprefs[1].length()==1)) {
                  // must have a valid unit
                  if (valid_units.indexOf(myprefs[1])!= -1) {
                      // visual effect name
                      if ((myprefs[2]!=null) && (myprefs[2].length()>0)) {
                          if (isValidEffectName(myprefs[2])) {
                              valid = true;
                          }
                      }
                  }
              }
          }
      }
      return valid;
  }

  public static boolean isValidUnits(String units) {
      boolean valid  = true;
      valid = (units == null) ? false : true;
      Pattern pattern = Pattern.compile("[cCfF]{1}");
      Matcher matcher = pattern.matcher(units);
      if (!matcher.find()) {
          valid = false;
      }
      return valid;
  }

  public static boolean isValidZipCode(String zipcode) {
      boolean valid = true;
      valid = (zipcode == null) ? false : true;
      return valid;
  }

}
