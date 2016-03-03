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
public class CommitsFact extends Fact{
    
    private final long commits;

    public CommitsFact(long timeStamp, long commits) {
        super(timeStamp);
        this.commits = commits;
    }

    public long getCommits() {
        return commits;
    }
    
}
