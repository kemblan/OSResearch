/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author mateusz
 */
public class OpenHubAPIConnector {

    private static final String OPENHUB_URL = "https://www.openhub.net";
    private ApiKeyVault apiKeyVault = null;

    private static OpenHubAPIConnector instance = null;

    public static OpenHubAPIConnector getInstance() throws Exception {
        if (instance == null) {
            instance = new OpenHubAPIConnector();
            instance.initialize();
        }
        return instance;
    }

    private OpenHubAPIConnector() {
        apiKeyVault = new ApiKeyVault();
    }

    public void initialize() throws Exception {
        apiKeyVault.initialize();
        Logger.getLogger(OpenHubCrawler.class.getName()).log(Level.INFO, apiKeyVault.getKey());
    }

    public Document getData(String urlQuery) throws IOException, ParserConfigurationException, SAXException, Exception {
        URL url = new URL(OPENHUB_URL + urlQuery + "api_key=" + apiKeyVault.getKey());
        URLConnection connection = url.openConnection();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        return builder.parse(connection.getInputStream());

    }
    
    

}
