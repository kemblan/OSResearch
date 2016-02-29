/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler.data.models;

import java.util.List;
import openhub.crawler.data.repositories.CommitRepository;

/**
 *
 * @author mateusz
 */
public class Commit {

    private CommitRepository repository = null;
    private long id;
    private long project;
    private Commiter commiter;
    private String comment;
    private String commitID;
    private int linesAdded;
    private int linesDeleted;
    private int filesModified;

    private String contributorName;
    private String contributorId;
    private List<CodeChange> codeChanges;

    public Commit(int project, String comment, String commitID, int linesAdded, int linesDeleted, int filesModified, String contributorName, List<CodeChange> codeChanges) {
        this.project = project;
        this.comment = comment;
        this.commitID = commitID;
        this.linesAdded = linesAdded;
        this.linesDeleted = linesDeleted;
        this.filesModified = filesModified;
        this.contributorName = contributorName;
        this.codeChanges = codeChanges;
        this.repository = new CommitRepository();
    }

    public Commit(long project, String comment, String commitID, int linesAdded, int linesDeleted, int filesModified, String contributorName, String contributorId, List<CodeChange> codeChanges) {
        this.project = project;
        this.comment = comment;
        this.commitID = commitID;
        this.linesAdded = linesAdded;
        this.linesDeleted = linesDeleted;
        this.filesModified = filesModified;
        this.contributorName = contributorName;
        this.contributorId = contributorId;
        this.codeChanges = codeChanges;
        this.repository = new CommitRepository();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getProject() {
        return project;
    }

    public Commiter getCommiter() {
        return commiter;
    }

    public String getComment() {
        return comment;
    }

    public String getCommitID() {
        return commitID;
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public int getLinesDeleted() {
        return linesDeleted;
    }

    public int getFilesModified() {
        return filesModified;
    }

    public String getContributorName() {
        return contributorName;
    }

    public List<CodeChange> getCodeChanges() {
        return codeChanges;
    }

    public String getContributorId() {
        return contributorId;
    }

    void save() {
        repository.add(this);
    }

}
