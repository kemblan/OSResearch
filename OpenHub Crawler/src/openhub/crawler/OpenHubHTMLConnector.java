/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author mateusz
 */
public class OpenHubHTMLConnector {

    private static final String OPENHUB_URL = "https://www.openhub.net";

    private static OpenHubHTMLConnector instance = null;

    public static OpenHubHTMLConnector getInstance() {
        if (instance == null) {
            instance = new OpenHubHTMLConnector();
        }
        return instance;
    }

    private OpenHubHTMLConnector() {
    }

    public BufferedReader getData(String urlQuery) throws IOException {
        URL url = new URL(OPENHUB_URL + urlQuery);
        URLConnection connection = url.openConnection();

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        return inputReader;
    }

    public Document getData2(String urlQuery) throws IOException {
        String url = OPENHUB_URL + urlQuery;
        System.out.println(url);
        return Jsoup.connect(url).timeout(100*1000).get();
    }

}
