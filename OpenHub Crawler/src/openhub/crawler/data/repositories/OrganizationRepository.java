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
public class OrganizationRepository {

    private DatabaseManager databaseManager = null;

    public OrganizationRepository() {
        databaseManager = DatabaseManager.getInstance();
        databaseManager.connect();
    }

    public Organization get(String name) {
        try {
            String query = "";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);

            //prepare
            ResultSet resultSet = databaseManager.executeQuery(preparedStatement);

            Organization organization = new Organization(name);
            connection.close();
            return organization;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Organization> getAll() {
        try {
            String query = "SELECT * FROM orgs order by id";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);

            //prepare
            ResultSet resultSet = databaseManager.executeQuery(preparedStatement);

            List<Organization> organizationList = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String urlName = resultSet.getString("urlName");
                String url = resultSet.getString("url");
                String description = resultSet.getString("description");
                String homepage = resultSet.getString("homepage");
                String type = resultSet.getString("type");
                int outsideCommiters = resultSet.getInt("outsideCommiters");
                int outsideCommits = resultSet.getInt("outsideCommits");
                int affiliatedCommiters = resultSet.getInt("affiliatedCommiters");
                int affiliatedCommits = resultSet.getInt("affiliatedCommits");
                int outsideProjects = resultSet.getInt("outsideProjects");
                int affiliatedProjects = resultSet.getInt("affiliatedProjects");
                int id = resultSet.getInt("id");

                Organization organization = new Organization(name, urlName, url, description, homepage, type, outsideCommiters, outsideCommits, affiliatedCommiters, affiliatedCommits, outsideProjects, affiliatedProjects);
                organization.setId(id);
                organizationList.add(organization);
            }
            connection.close();
            return organizationList;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean add(Organization organization) {
        try {

            Iterator<Project> projectIterator = organization.getProjectList().iterator();
            while (projectIterator.hasNext()) {
                Project tempProject = projectIterator.next();
                tempProject.save();
            }

            Iterator<Commiter> commiterIterator = organization.getCommiterList().iterator();
            while (commiterIterator.hasNext()) {
                Commiter tempCommiter = commiterIterator.next();
                tempCommiter.save();
            }

            String query = "INSERT INTO orgs (name, urlName, description, homepage, "
                    + "type, outsideCommiters, outsideCommits, affiliatedCommiters,"
                    + "affiliatedCommits, outsideProjects, affiliatedProjects, url) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);
            preparedStatement.setString(1, organization.getName());
            preparedStatement.setString(2, organization.getUrlName());
            preparedStatement.setString(3, organization.getDescription());
            preparedStatement.setString(4, organization.getHomepage());
            preparedStatement.setString(5, organization.getType());
            preparedStatement.setInt(6, organization.getOutsideCommiters());
            preparedStatement.setInt(7, organization.getOutsideCommits());
            preparedStatement.setInt(8, organization.getAffiliatedCommiters());
            preparedStatement.setInt(9, organization.getAffiliatedCommits());
            preparedStatement.setInt(10, organization.getOutsideProjects());
            preparedStatement.setInt(11, organization.getAffiliatedProjects());
            preparedStatement.setString(12, organization.getUrl());
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, "INSERT QUERY for :{0}", organization.getName());
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, preparedStatement.toString());
            databaseManager.executeUpdate(preparedStatement);
            connection.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.WARNING, "SQL INSERT ERROR");
            return update(organization);
        }
    }

    public boolean update(Organization organization) {
        try {
            String query = "UPDATE orgs SET urlName=?, description=?, homepage=?, type=?, "
                    + "outsideCommiters=?, outsideCommits=?, affiliatedCommiters=?, "
                    + "affiliatedCommits=?, outsideProjects=?, affiliatedProjects=?, url=?  WHERE name=?";
            Connection connection = databaseManager.getConnection();
            PreparedStatement preparedStatement = databaseManager.getPreparedStatement(connection, query);
            preparedStatement.setString(1, organization.getUrlName());
            preparedStatement.setString(2, organization.getDescription());
            preparedStatement.setString(3, organization.getHomepage());
            preparedStatement.setString(4, organization.getType());
            preparedStatement.setInt(5, organization.getOutsideCommiters());
            preparedStatement.setInt(6, organization.getOutsideCommits());
            preparedStatement.setInt(7, organization.getAffiliatedCommiters());
            preparedStatement.setInt(8, organization.getAffiliatedCommits());
            preparedStatement.setInt(9, organization.getOutsideProjects());
            preparedStatement.setInt(10, organization.getAffiliatedProjects());
            preparedStatement.setString(11, organization.getUrl());
            preparedStatement.setString(12, organization.getName());
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, "UPDATE QUERY :{0}", organization.getName());
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.INFO, preparedStatement.toString());
            connection.close();
            databaseManager.executeUpdate(preparedStatement);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrganizationRepository.class.getName()).log(Level.SEVERE, "SQL UPDATE ERROR", ex);
            return false;
        }
    }

}
