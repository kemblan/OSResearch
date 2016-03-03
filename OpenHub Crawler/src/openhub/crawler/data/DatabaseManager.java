/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler.data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * This is single thread version!
 * 
 * @author mateusz
 */
public class DatabaseManager {

    private static DatabaseManager instance = null;

    private Connection connection = null;
    private String databaseUrl = null;
    private String userName = null;
    private String password = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public synchronized boolean connect() {
        if (this.connection != null) {
            return true;
        }
        Logger.getLogger(DatabaseManager.class.getName()).log(Level.INFO, "Loading database configuration");
        try {
            loadConfiguration();
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(DatabaseManager.class.getName()).log(Level.INFO, "Initializing driver.");
        try {
            initialize();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        Logger.getLogger(DatabaseManager.class.getName()).log(Level.INFO, databaseUrl);

        Logger.getLogger(DatabaseManager.class.getName()).log(Level.INFO, "Connecting to database.");
        try {
            connection = DriverManager.getConnection(databaseUrl, userName, password);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public synchronized boolean disconnect() {
        try {
            connection.close();
            connection = null;
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    private DatabaseManager() {

    }

    public synchronized void loadConfiguration() throws SAXException, IOException, ParserConfigurationException, Exception {
        File xmlFile = new File("databaseConfig.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName().equals("databaseUrl")) {
                databaseUrl = "jdbc:mysql://" + nodeList.item(i).getTextContent();
            }
            if (nodeList.item(i).getNodeName().equals("userName")) {
                userName = nodeList.item(i).getTextContent();
            }
            if (nodeList.item(i).getNodeName().equals("password")) {
                password = nodeList.item(i).getTextContent();
            }
        }
        if (databaseUrl == null || userName == null || password == null) {
            throw new Exception("Cannot retrieve database configuration");
        }

    }

    private synchronized void initialize() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    public ResultSet executeQuery(String query) throws SQLException {
        statement = null;
        statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public synchronized ResultSet executeQuery(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    public synchronized int executeUpdate(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeUpdate();
    }

    public synchronized PreparedStatement getPreparedStatement(String query) throws SQLException {
        if (this.preparedStatement != null) {
            this.preparedStatement.close();
            this.preparedStatement = null;
        }
        this.preparedStatement = this.connection.prepareStatement(query);
        return this.preparedStatement;
    }

    public void executeBatch(PreparedStatement preparedStatement) throws SQLException {
    preparedStatement.executeBatch();
    }

}
