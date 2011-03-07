package exo.sample.portlet.weather;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 1, 2011  
 */
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author ed
 */
public class YahooWeatherServiceController {
    private static DocumentBuilderFactory domBuilderFactory = null;
    private static YahooWeatherServiceController instance = null;

    /** Creates a new instance of YahooWeatherServiceController */
    private YahooWeatherServiceController() {
        domBuilderFactory = DocumentBuilderFactory.newInstance();
    }

    public static synchronized YahooWeatherServiceController getInstance() {
        if (instance == null) {
            instance = new YahooWeatherServiceController();
        }
        return instance;
    }

    public String getWeatherHtml(String zip, boolean isCelsius) {
        Document dom=null;
        String result="";
        try {
            dom = getWeatherRssDocument(zip,isCelsius);
            result = getWeatherHtml(dom);
        } catch (Exception e) {
            // some logging needed
        }
        return result;
    }

    private String getWeatherHtml(Document dom) {
        Element item = (Element)dom.getElementsByTagName("item").item(0);
        Element desc = (Element)item.getElementsByTagName("description").item(0);
        return desc.getFirstChild().getNodeValue();
    }

    private String getRequestUrl(String zip, boolean isCelsius) {
        final String base= "http://xml.weather.yahoo.com/forecastrss?";
        return base + "p=" + zip + "&u=" + (isCelsius?"c":"f");
    }

    private Document getWeatherRssDocument(String zip, boolean isCelsius) throws IOException, ParserConfigurationException, SAXException {

        String url = getRequestUrl(zip, isCelsius);

        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(url);
        try {

            // execute method and handle any error responses.
            int resultCode = client.executeMethod(get);

            if (resultCode == 200) {
                InputStream in = get.getResponseBodyAsStream();
                DocumentBuilder builder = domBuilderFactory.newDocumentBuilder();
                return builder.parse(in);
            } else {
                throw new IOException("HTTP Communication problem, response code: "+resultCode);
            }
        } finally {

            // Process the data from the input stream.
            get.releaseConnection();
        }
    }

}
