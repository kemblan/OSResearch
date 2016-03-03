/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler.data.models;

import java.util.Calendar;

/**
 *
 * @author mateusz
 */
public class Fact {

    private long timeStamp;

    public Fact(long timestamp) {
        this.timeStamp = timestamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        return calendar.get(Calendar.MONTH);
    }

    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        return calendar.get(Calendar.YEAR);
    }
}
