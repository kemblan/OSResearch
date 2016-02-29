/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import openhub.crawler.data.DatabaseManager;

/**
 *
 * @author mateusz
 */
public class ApiKeyVault {
    
    private List<ApiKey> keyList;
    private ApiKey currentKey = null;
    private DatabaseManager databaseManager;
    
    public void initialize() throws SQLException, Exception {
        keyList = new ArrayList<>();
        databaseManager = DatabaseManager.getInstance();
        databaseManager.connect();
        String query = "SELECT * FROM api_keys";
        
        PreparedStatement preparedStatement = databaseManager.getPreparedStatement(query);
        ResultSet resultSet = databaseManager.executeQuery(preparedStatement);
        
        while (resultSet.next()) {
            String apiKey = resultSet.getString("key_value");
            int usedNo = resultSet.getInt("used");
            Date lastUsed = resultSet.getDate("last_used");
            ApiKey temp = new ApiKey(apiKey, usedNo, lastUsed);
            if (!keyList.add(temp)) {
                throw new Exception("Error on loading API Keys.");
            }
        }
        if (keyList.size() > 0) {
            currentKey = keyList.get(0);
        } else {
            throw new Exception("There are no API Keys");
        }
    }
    
    public String getKey() throws Exception {
        if (currentKey.usedNo >= 1000) {
            currentKey = null;
            Iterator<ApiKey> iterator = keyList.iterator();
            while (iterator.hasNext()) {
                ApiKey temp = iterator.next();
                if (temp.usedNo < 1000);
                currentKey = temp;
            }
            if (currentKey != null) {
                throw new Exception("No active API Key.");
            }
        }
        currentKey.usedNo++;
        updateKeys();
        return currentKey.key;
    }
    
    public boolean updateKeys() throws SQLException {
        Iterator<ApiKey> iterator = keyList.iterator();
        while (iterator.hasNext()) {
            ApiKey temp = iterator.next();
            String query = "UPDATE api_keys SET used=?, last_used=? WHERE key_value=?";
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(query);
            preparedStatement.setInt(1, temp.usedNo);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(3, temp.key);
            System.out.println(preparedStatement.toString());
            databaseManager.executeUpdate(preparedStatement);
        }
        
        return false;
    }
    
    public class ApiKey {
        
        public int usedNo;
        public String key;
        public Date lastUsed;
        
        public ApiKey(String key, int usedNo, Date lastUsed) {
            this.key = key;
            this.lastUsed = lastUsed;
            if (lastUsed.getDay()<Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                this.usedNo = 0;
            } else {
                this.usedNo = usedNo;
            }
        }
    }
    
}
