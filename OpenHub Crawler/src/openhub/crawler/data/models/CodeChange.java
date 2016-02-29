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
public class CodeChange {

    private final String language;
    private final int codeAdded;
    private final int codeRemoved;
    private final int commentsAdded;
    private final int commentsRemoved;
    private final int blanksAdded;
    private final int blanksRemoved;

    public CodeChange(String language, int codeAdded, int codeRemoved, int commentsAdded, int commentsRemoved, int blanksAdded, int blanksRemoved) {
        this.language = language;
        this.codeAdded = codeAdded;
        this.codeRemoved = codeRemoved;
        this.commentsAdded = commentsAdded;
        this.commentsRemoved = commentsRemoved;
        this.blanksAdded = blanksAdded;
        this.blanksRemoved = blanksRemoved;
    }

    public String getLanguage() {
        return language;
    }

    public int getCodeAdded() {
        return codeAdded;
    }

    public int getCodeRemoved() {
        return codeRemoved;
    }

    public int getCommentsAdded() {
        return commentsAdded;
    }

    public int getCommentsRemoved() {
        return commentsRemoved;
    }

    public int getBlanksAdded() {
        return blanksAdded;
    }

    public int getBlanksRemoved() {
        return blanksRemoved;
    }

}
