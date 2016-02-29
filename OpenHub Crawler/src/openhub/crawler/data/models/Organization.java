/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler.data.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import openhub.crawler.OpenHubAPIConnector;
import openhub.crawler.OpenHubHTMLConnector;
import openhub.crawler.UILogger;
import openhub.crawler.XMLPrinter;
import openhub.crawler.data.repositories.OrganizationRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author mateusz
 */
public class Organization implements OpenHubData {

    private long id = 0;
    private String name = null;
    private String urlName = null;
    private String url = null;
    private String description = null;
    private String homepage = null;
    private String type = null;
    private int outsideCommiters;
    private int outsideCommits;
    private int affiliatedCommiters;
    private int affiliatedCommits;
    private int outsideProjects;
    private int affiliatedProjects;

    private OrganizationRepository repository = null;

    private List<Project> projectList = null;
    private List<Commiter> commiterList = null;

    public Organization(String urlName) {
        projectList = new ArrayList<>();
        commiterList = new ArrayList<>();
        repository = new OrganizationRepository();
        this.urlName = urlName;
    }

    public Organization(String urlName, String url) {
        this(urlName);
        this.url = url;
    }

    public Organization(String name, String urlName, String url, String description, String homepage, String type, int outsideCommiters, int outsideCommits, int affiliatedCommiters, int affiliatedCommits, int outsideProjects, int affiliatedProjects) {
        this(urlName);
        this.name = name;
        this.url = url;
        this.description = description;
        this.homepage = homepage;
        this.type = type;
        this.outsideCommiters = outsideCommiters;
        this.outsideCommits = outsideCommits;
        this.affiliatedCommiters = affiliatedCommiters;
        this.affiliatedCommits = affiliatedCommits;
        this.outsideProjects = outsideProjects;
        this.affiliatedProjects = affiliatedProjects;
    }

    public boolean downloadInfo() {
        String query;
        if (url != null) {
            query = url.replace("https://www.openhub.net", "") + "?";
        } else {
            query = "/orgs/" + urlName + ".xml?";
        }
        try {
            Document document = OpenHubAPIConnector.getInstance().getData(query);
            UILogger.getInstance().log(XMLPrinter.documentToString(document));
//            XMLPrinter.printDocument(document, System.out);

            Element root = document.getDocumentElement();
            this.name = root.getElementsByTagName("name").item(0).getTextContent();
            this.description = root.getElementsByTagName("description").item(0).getTextContent();
            this.homepage = root.getElementsByTagName("homepage_url").item(0).getTextContent();
            this.type = root.getElementsByTagName("type").item(0).getTextContent();
            Element infographic = (Element) root.getElementsByTagName("infographic_details").item(0);
            this.outsideCommiters = Integer.parseInt(infographic.getElementsByTagName("outside_committers").item(0).getTextContent());
            this.outsideCommits = Integer.parseInt(infographic.getElementsByTagName("outside_committers_commits").item(0).getTextContent());
            this.affiliatedCommiters = Integer.parseInt(infographic.getElementsByTagName("affiliators_committing_to_portfolio_projects").item(0).getTextContent());
            this.affiliatedCommits = Integer.parseInt(infographic.getElementsByTagName("affiliator_commits_to_portfolio_projects").item(0).getTextContent());
            this.outsideProjects = Integer.parseInt(infographic.getElementsByTagName("outside_projects").item(0).getTextContent());
            this.affiliatedProjects = Integer.parseInt(infographic.getElementsByTagName("portfolio_projects").item(0).getTextContent());

            return true;
        } catch (Exception ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean downloadProjectsInfo() {

        int read = 0;
        int page = 1;

        BufferedReader htmlReader;
        String projectsHMLTQuery;
        while (read < this.affiliatedProjects) {
            try {
                projectsHMLTQuery = "/orgs/" + urlName + "/projects?page=" + page;
                htmlReader = OpenHubHTMLConnector.getInstance().getData(projectsHMLTQuery);

                String inputLine;
                while ((inputLine = htmlReader.readLine()) != null) {
                    Pattern pattern = Pattern.compile("<a title=\".*\" href=\".*\">");
                    Matcher matcher = pattern.matcher(inputLine);
                    if (matcher.find()) {
                        String foundString = matcher.group(0).replace("&#39;", "'");
                        System.out.println(foundString);
                        String titleCut = foundString.split("<a title=\"")[1];
                        String projectName = titleCut.substring(0, titleCut.indexOf('"'));
                        String urlCut = foundString.split("href=\"")[1];
                        String projectUrl = "https://www.openhub.net" + urlCut.substring(0, urlCut.indexOf('"')) + ".xml";
                        System.out.println(projectName);
                        System.out.println(projectUrl);
                        Project temp = new Project(projectName, this.getId(), projectUrl);
                        temp.downloadInfo();
                        temp.save();
                        this.projectList.add(temp);
                    }
                }
                htmlReader.close();
                page++;
            } catch (IOException ex) {
                Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    public boolean downloadCommitersInfo() {
        int all = 1;
        int read = 0;
        int page = 1;
        do {
            String query = "/orgs/" + name + "/affiliated_committers.xml?page=" + page + "&";
            page++;
            try {
                Document document = OpenHubAPIConnector.getInstance().getData(query);
                UILogger.getInstance().log(XMLPrinter.documentToString(document));
                Element root = document.getDocumentElement();
                read = Integer.parseInt(root.getElementsByTagName("items_returned").item(0).getTextContent()) + Integer.parseInt(root.getElementsByTagName("first_item_position").item(0).getTextContent());
                all = Integer.parseInt(root.getElementsByTagName("items_available").item(0).getTextContent());
            } catch (Exception ex) {
                Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } while (read < all);
        return true;
    }

    @Override
    public int download(boolean isRunning) {
        if (isRunning) {
            downloadInfo();
        }
        if (isRunning) {
            downloadProjectsInfo();
        }
        for (int i = 0; i < projectList.size(); i++) {
            projectList.get(i).download(isRunning);
            projectList.get(i).save();
        }

        return 1;
    }

    @Override
    public boolean save() {
        if (this.url == null) {
            this.url = "/orgs/" + this.urlName + ".xml";
        }
        return repository.add(this);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlName() {
        return urlName;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public int getOutsideCommiters() {
        return outsideCommiters;
    }

    public int getOutsideCommits() {
        return outsideCommits;
    }

    public int getAffiliatedCommiters() {
        return affiliatedCommiters;
    }

    public int getAffiliatedCommits() {
        return affiliatedCommits;
    }

    public int getOutsideProjects() {
        return outsideProjects;
    }

    public int getAffiliatedProjects() {
        return affiliatedProjects;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public List<Commiter> getCommiterList() {
        return commiterList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
