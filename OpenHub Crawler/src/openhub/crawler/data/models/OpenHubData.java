/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler.data.models;

/**
 *
 * @author mateusz
 */
public interface OpenHubData {
    
    public int download(boolean isRunning);
    public boolean save();

    public String getName();
    
}
