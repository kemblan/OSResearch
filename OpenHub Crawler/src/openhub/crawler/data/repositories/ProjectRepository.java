/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler.data.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import openhub.crawler.data.DatabaseManager;
import openhub.crawler.data.models.Commiter;
import openhub.crawler.data.models.Organization;
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
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(query);
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
                return project;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Project> getAll() {
//        try {
//            String query = "SELECT * FROM orgs";
//            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(query);
//
//            //prepare
//            ResultSet resultSet = databaseManager.executeQuery(preparedStatement);
//
//            List<Organization> organizationList = new ArrayList<>();
//            while (resultSet.next()) {
//                String name = resultSet.getString("name");
//                String urlName = resultSet.getString("urlName");
//                String url = resultSet.getString("url");
//                String description = resultSet.getString("description");
//                String homepage = resultSet.getString("homepage");
//                String type = resultSet.getString("type");
//                int outsideCommiters = resultSet.getInt("outsideCommiters");
//                int outsideCommits = resultSet.getInt("outsideCommits");
//                int affiliatedCommiters = resultSet.getInt("affiliatedCommiters");
//                int affiliatedCommits = resultSet.getInt("affiliatedCommits");
//                int outsideProjects = resultSet.getInt("outsideProjects");
//                int affiliatedProjects = resultSet.getInt("affiliatedProjects");
//
//                Organization organization = new Organization(name, urlName, url, description, homepage, type, outsideCommiters, outsideCommits, affiliatedCommiters, affiliatedCommits, outsideProjects, affiliatedProjects);
//                organizationList.add(organization);
//            }
//            return organizationList;
//        } catch (SQLException ex) {
//            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
        return null;
    }

    public boolean add(Project project) {
        try {
            String query = "INSERT INTO projects (name, vanity_name, description, average_rating, "
                    + "rating_count, review_count, org_id, url) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(query);
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
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.WARNING, "SQL INSERT ERROR");
            return update(project);
        }
    }

    public boolean update(Project project) {
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
