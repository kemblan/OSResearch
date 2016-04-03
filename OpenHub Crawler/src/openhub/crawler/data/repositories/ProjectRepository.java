/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler.data.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import openhub.crawler.data.DatabaseManager;
import openhub.crawler.data.models.CodeFact;
import openhub.crawler.data.models.CommitersFact;
import openhub.crawler.data.models.CommitsFact;
import openhub.crawler.data.models.Project;

/**
 *
 * @author mateusz
 */
public class ProjectRepository {

    private DatabaseManager databaseManager = null;

    public ProjectRepository() {
        databaseManager = DatabaseManager.getInstance();
        databaseManager.connect();
    }

    public Project get(String vanityName) {
        try {
            String query = "SELECT * FROM projects WHERE vanity_name=?";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);
            preparedStatement.setString(1, vanityName);
            ResultSet resultSet = databaseManager.executeQuery(preparedStatement);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int averageRating = resultSet.getInt("average_rating");
                int ratingCount = resultSet.getInt("rating_count");
                int reviewCount = resultSet.getInt("review_count");
                long org = resultSet.getLong("org_id");
                String url = resultSet.getString("url");
                Project project = new Project(name, org, url, reviewCount, ratingCount, averageRating, description, vanityName);
                long id = resultSet.getLong("id");
                project.setId(id);
                connection.close();
                return project;
            }
            connection.close();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Project> getAll() {
        try {
            String query = "SELECT * FROM projects ORDER BY id";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);
            ResultSet resultSet = databaseManager.executeQuery(preparedStatement);

            List<Project> projectList = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String vanityName = resultSet.getString("vanity_name");
                String description = resultSet.getString("description");
                int averageRating = resultSet.getInt("average_rating");
                int ratingCount = resultSet.getInt("rating_count");
                int reviewCount = resultSet.getInt("review_count");
                long org = resultSet.getLong("org_id");
                String url = resultSet.getString("url");
                Project project = new Project(name, org, url, reviewCount, ratingCount, averageRating, description, vanityName);
                long id = resultSet.getLong("id");
                project.setId(id);
                projectList.add(project);
            }
            connection.close();
            return projectList;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean add(Project project) {
        try {
            String query = "INSERT INTO projects (name, vanity_name, description, average_rating, "
                    + "rating_count, review_count, org_id, url) VALUES (?,?,?,?,?,?,?,?)";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getVanityName());
            preparedStatement.setString(3, project.getDescription());
            preparedStatement.setDouble(4, project.getAverageRating());
            preparedStatement.setInt(5, project.getRatingCount());
            preparedStatement.setInt(6, project.getReviewCount());
            preparedStatement.setLong(7, project.getOrgId());
            preparedStatement.setString(8, project.getUrl());

            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, "INSERT QUERY for :{0}", project.getName());
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, preparedStatement.toString());
            databaseManager.executeUpdate(preparedStatement);
            connection.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.WARNING, "SQL INSERT ERROR", ex);
            return update(project);
        }
    }

    public boolean update(Project project) {
        try {
            String query = "UPDATE projects SET name=?, vanity_name=?, description=?, average_rating=?, "
                    + "rating_count=?, review_count=?, org_id=? WHERE url=?";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getVanityName());
            preparedStatement.setString(3, project.getDescription());
            preparedStatement.setDouble(4, project.getAverageRating());
            preparedStatement.setInt(5, project.getRatingCount());
            preparedStatement.setInt(6, project.getReviewCount());
            preparedStatement.setLong(7, project.getOrgId());
            preparedStatement.setString(8, project.getUrl());

            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, "UPDATE QUERY for :{0}", project.getName());
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, preparedStatement.toString());
            databaseManager.executeUpdate(preparedStatement);
            connection.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.WARNING, "SQL UPDATE ERROR", ex);
            return update(project);
        }
    }

    public void saveCodeHistory(Project aThis, Map<Long, CodeFact> codeMap) {
        try {
            Iterator it = codeMap.entrySet().iterator();
            String query = "INSERT INTO project_code (project, year, month, lines_code,"
                    + "lines_comment, lines_blank) VALUES (?,?,?,?,?,?)";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);
            while (it.hasNext()) {
                CodeFact fact = (CodeFact) ((Map.Entry) it.next()).getValue();

                preparedStatement.setLong(1, aThis.getId());
                preparedStatement.setInt(2, fact.getYear());
                preparedStatement.setInt(3, fact.getMonth()+1);
                preparedStatement.setLong(4, fact.getCodes());
                preparedStatement.setLong(5, fact.getComments());
                preparedStatement.setLong(6, fact.getBlanks());
                preparedStatement.addBatch();
            }
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, "INSERT CODE QUERY for :{0}", aThis.getName());
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, preparedStatement.toString());
            databaseManager.executeBatch(preparedStatement);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveCommitersHistory(Project aThis, Map<Long, CommitersFact> commitersMap) {
        try {
            Iterator it = commitersMap.entrySet().iterator();
            String query = "INSERT INTO project_commiters (project, year, month, commiters) VALUES (?,?,?,?)";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);
            while (it.hasNext()) {
                CommitersFact fact = (CommitersFact) ((Map.Entry) it.next()).getValue();

                preparedStatement.setLong(1, aThis.getId());
                preparedStatement.setInt(2, fact.getYear());
                preparedStatement.setInt(3, fact.getMonth()+1);
                preparedStatement.setLong(4, fact.getCommiters());
                preparedStatement.addBatch();
            }
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, "INSERT CODE QUERY for :{0}", aThis.getName());
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, preparedStatement.toString());
            databaseManager.executeBatch(preparedStatement);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveCommitsHistory(Project aThis, Map<Long, CommitsFact> commitsMap) {
        try {
            Iterator it = commitsMap.entrySet().iterator();
            String query = "INSERT INTO project_commits (project, year, month, commits) VALUES (?,?,?,?)";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);
            while (it.hasNext()) {
                CommitsFact fact = (CommitsFact) ((Map.Entry) it.next()).getValue();

                preparedStatement.setLong(1, aThis.getId());
                preparedStatement.setInt(2, fact.getYear());
                preparedStatement.setInt(3, fact.getMonth()+1);
                preparedStatement.setLong(4, fact.getCommits());
                preparedStatement.addBatch();
            }
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, "INSERT CODE QUERY for :{0}", aThis.getName());
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, preparedStatement.toString());
            databaseManager.executeBatch(preparedStatement);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Project get(long t) {
        try {
            String query = "SELECT * FROM projects WHERE id=?";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);
            preparedStatement.setLong(1, t);
            ResultSet resultSet = databaseManager.executeQuery(preparedStatement);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int averageRating = resultSet.getInt("average_rating");
                int ratingCount = resultSet.getInt("rating_count");
                int reviewCount = resultSet.getInt("review_count");
                long org = resultSet.getLong("org_id");
                String url = resultSet.getString("url");
                String vanityName = resultSet.getString("vanity_name");
                Project project = new Project(name, org, url, reviewCount, ratingCount, averageRating, description, vanityName);

                project.setId(t);
                connection.close();
                return project;
            }
            connection.close();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
