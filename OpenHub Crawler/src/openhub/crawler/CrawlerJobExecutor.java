/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler;

import java.util.Iterator;
import openhub.crawler.data.models.OpenHubData;

/**
 *
 * @author mateusz
 */
public class CrawlerJobExecutor extends Thread {

    private Iterator<OpenHubData> jobsScheduled;

    public CrawlerJobExecutor(Iterator<OpenHubData> iterator) {
        jobsScheduled = iterator;
    }

    private volatile boolean isRunning = true;

    @Override
    public void run() {
        while (isRunning&&jobsScheduled.hasNext()) {
                OpenHubData data = jobsScheduled.next();
                data.download(isRunning);
                data.save();
        }
    }

    public void kill() {
        isRunning = false;
    }

}
