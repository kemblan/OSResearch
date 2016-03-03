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
public class CommitersFact extends Fact {

    private final long commiters;

    public CommitersFact(long timeStamp, long commiters) {
        super(timeStamp);
        this.commiters = commiters;
    }

    public long getCommiters() {
        return commiters;
    }

}
