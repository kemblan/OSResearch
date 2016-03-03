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
public class CodeFact extends Fact{

    private long codes;
    private long comments;
    private long blanks;

    public CodeFact(long timeStamp) {
        super(timeStamp);        
    }

    public void setCodes(long codes) {
        this.codes = codes;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public void setBlanks(long blanks) {
        this.blanks = blanks;
    }


    public long getCodes() {
        return codes;
    }

    public long getComments() {
        return comments;
    }

    public long getBlanks() {
        return blanks;
    }

}
