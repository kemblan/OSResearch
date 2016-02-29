/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler.data.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import openhub.crawler.data.DatabaseManager;
import openhub.crawler.data.models.CodeChange;
import openhub.crawler.data.models.Commit;
import openhub.crawler.data.models.Project;

/**
 *
 * @author mateusz
 */
public class CommitRepository {

    private DatabaseManager databaseManager = null;

    public CommitRepository() {
        databaseManager = DatabaseManager.getInstance();
        databaseManager.connect();
    }

    public boolean add(Commit commit) {
        try {
            int contributorId = 0;
            String getContribQuery = "SELECT id FROM contributors_projects WHERE name =? AND project=? AND contributor_id=?";
            PreparedStatement contribSelect = databaseManager.getPreparedStatement(getContribQuery);
            contribSelect.setString(1, commit.getContributorName());
            contribSelect.setLong(2, commit.getProject());
            contribSelect.setString(3, commit.getContributorId());
            ResultSet contributorKey = databaseManager.executeQuery(contribSelect);
            if (contributorKey.next()) {
                contributorId = contributorKey.getInt(1);
            } else {
                String contributorQuery = "INSERT INTO contributors_projects (name, project, contributor_id)"
                        + "VALUES (?,?,?)";
                PreparedStatement contributorPreparedStatement = databaseManager.getPreparedStatement(contributorQuery);
                contributorPreparedStatement.setString(1, commit.getContributorName());
                contributorPreparedStatement.setLong(2, commit.getProject());
                contributorPreparedStatement.setString(3, commit.getContributorId());
                databaseManager.executeUpdate(contributorPreparedStatement);
                contributorKey = contributorPreparedStatement.getGeneratedKeys();
                if (contributorKey.next()) {
                    contributorId = contributorKey.getInt(1);
                }
            }

            String query = "INSERT INTO commits (contributor_id, project_id, comment, commit_uuid, "
                    + "files_modified, lines_added, lines_removed) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(query);
            preparedStatement.setLong(1, contributorId);
            preparedStatement.setLong(2, commit.getProject());
            preparedStatement.setString(3, commit.getComment());
            preparedStatement.setString(4, commit.getCommitID());
            preparedStatement.setInt(5, commit.getFilesModified());
            preparedStatement.setInt(6, commit.getLinesAdded());
            preparedStatement.setInt(7, commit.getLinesDeleted());

            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, "INSERT QUERY for commit");
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, preparedStatement.toString());
            databaseManager.executeUpdate(preparedStatement);

            ResultSet insertKeys = preparedStatement.getGeneratedKeys();
            if (insertKeys.next()) {
                commit.setId(insertKeys.getInt(1));
                Iterator<CodeChange> iterator = commit.getCodeChanges().iterator();
                while (iterator.hasNext()) {
                    CodeChange codeChange = iterator.next();

                }
            }

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.WARNING, "SQL INSERT ERROR", ex);
            return update(commit);
        }
    }

    public boolean update(Commit commit) {
//        try {
//            String query = "UPDATE orgs SET urlName=?, description=?, homepage=?, type=?, "
//                    + "outsideCommiters=?, outsideCommits=?, affiliatedCommiters=?, "
//                    + "affiliatedCommits=?, outsideProjects=?, affiliatedProjects=?, url=?  WHERE name=?";
//            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(query);
//            preparedStatement.setString(1, organization.getUrlName());
//            preparedStatement.setString(2, organization.getDescription());
//            preparedStatement.setString(3, organization.getHomepage());
//            preparedStatement.setString(4, organization.getType());
//            preparedStatement.setInt(5, organization.getOutsideCommiters());
//            preparedStatement.setInt(6, organization.getOutsideCommits());
//            preparedStatement.setInt(7, organization.getAffiliatedCommiters());
//            preparedStatement.setInt(8, organization.getAffiliatedCommits());
//            preparedStatement.setInt(9, organization.getOutsideProjects());
//            preparedStatement.setInt(10, organization.getAffiliatedProjects());
//            preparedStatement.setString(11, organization.getUrl());
//            preparedStatement.setString(12, organization.getName());
//            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, "UPDATE QUERY :{0}", organization.getName());
//            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, preparedStatement.toString());
//            databaseManager.executeUpdate(preparedStatement);
//            return true;
//        } catch (SQLException ex) {
//            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.SEVERE, "SQL UPDATE ERROR", ex);
//            return false;
//        }
        return false;
    }

}
