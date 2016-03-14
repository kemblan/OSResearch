/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.xml.parsers.ParserConfigurationException;
import openhub.crawler.data.DatabaseManager;
import openhub.crawler.data.models.OpenHubData;
import openhub.crawler.data.models.Organization;
import openhub.crawler.data.models.Project;
import openhub.crawler.data.repositories.OrganizationRepository;
import openhub.crawler.data.repositories.ProjectRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author mateusz
 */
public class OpenHubCrawler {

    private DefaultListModel scheduledJobsView = null;

    private List<OpenHubData> scheduledJobs = null;
    private CrawlerJobExecutor jobExecutionThead = null;

    public void initialize(JList scheduledJobsListView) {
        this.scheduledJobsView = new DefaultListModel();
        scheduledJobsListView.setModel(scheduledJobsView);
        this.scheduledJobs = new ArrayList<>();
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        try {
            databaseManager.loadConfiguration();
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(OpenHubCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OpenHubCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

        UILogger.getInstance().log("Initialization done.");
    }

    public void scheduleJob(String name, String type) {
        if (type.equals("Organization")) {
            OpenHubData itemScheduled = new Organization(name);
            this.scheduledJobs.add(itemScheduled);
            this.scheduledJobsView.addElement("[Organization] " + name);
        } else if (type.equals("Project")) {
////            OpenHubData itemScheduled = new Project(name);
//            this.scheduledJobs.add(itemScheduled);
//            this.scheduledJobsView.addElement("[Project] " + name);
        }

    }

    public void clearJobs() {
        this.scheduledJobs.clear();
        this.scheduledJobsView.clear();
    }

    public void removeJob(int index) {
        String name = this.scheduledJobsView.get(index).toString();
        this.scheduledJobsView.remove(index);
        Iterator<OpenHubData> jobsScheduled = this.scheduledJobs.iterator();
        while (jobsScheduled.hasNext()) {
            OpenHubData data = jobsScheduled.next();
            if (data.getName().equals(name)) {
                this.scheduledJobs.remove(data);
                return;
            }
        }
    }

    public void start() {
        if (scheduledJobs.size() > 0) {
            if (jobExecutionThead == null) {
                jobExecutionThead = new CrawlerJobExecutor(this.scheduledJobs.iterator());
                jobExecutionThead.start();
            } else if (!jobExecutionThead.isAlive()) {
                stop();
                jobExecutionThead = new CrawlerJobExecutor(this.scheduledJobs.iterator());
                jobExecutionThead.start();
            }
        }
    }

    public void stop() {
        if (jobExecutionThead != null) {
            jobExecutionThead.kill();
            jobExecutionThead = null;
        }
    }

    public void downloadOrganizations() {

        Thread workerThread = new Thread() {
            @Override
            public void run() {
                try {
                    int page = 1;
                    String query;
                    NodeList nodeList;
                    do {
                        query = "/orgs.xml?page=" + page + "&";
                        Document document = OpenHubAPIConnector.getInstance().getData(query);
                        XMLPrinter.printDocument(document, System.out);
                        Element root = document.getDocumentElement();
                        nodeList = root.getElementsByTagName("org");
                        for (int i = 0; i < nodeList.getLength(); i++) {
                            Element orgElement = (Element) nodeList.item(i);
                            String urlName = orgElement.getElementsByTagName("url_name").item(0).getTextContent();
                            String url = orgElement.getElementsByTagName("url").item(0).getTextContent();
                            Organization temp = new Organization(urlName, url);
                            temp.downloadInfo();
                            temp.save();
                        }
                        page++;
                    } while (nodeList.getLength() > 0);
                    UILogger.getInstance().log("Organization list downloaded");
                } catch (Exception ex) {
                    Logger.getLogger(OpenHubCrawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        workerThread.start();

    }

    public void downloadProjectsForAllOrganizations() {
        Thread workerThread = new Thread() {
            @Override
            public void run() {

                OrganizationRepository organizationRepository = new OrganizationRepository();
                List<Organization> organizationList = organizationRepository.getAll();

                Iterator<Organization> iterator = organizationList.iterator();
                int count = 0;
                while (iterator.hasNext() && count < 6) {
                    Organization temp = iterator.next();
                    if (count != 0) {
                        if (temp.downloadProjectsInfo()) {
                            temp.save();
                        }
                    }
                    count++;
                }
            }
        };
        workerThread.start();
    }

    public void downloadProjectForOrganization(String urlName) {
        Thread workerThread = new Thread() {
            @Override
            public void run() {

            }
        };
        workerThread.start();
    }

    void downloadCommits() {
        Thread workerThread = new Thread() {
            volatile boolean isRunning;

            {
                this.isRunning = true;
            }

            @Override
            public void run() {
                ProjectRepository projectRepository = new ProjectRepository();
//                Project project = projectRepository.get("VisualEditor");
//                              project.download(isRunning);

                List<Project> projectsList = projectRepository.getAll();
                Iterator<Project> projectsIterator = projectsList.iterator();

                while (projectsIterator.hasNext()) {

                    Project temp = projectsIterator.next();
                    temp.download(isRunning);
                }

            }
        };
        workerThread.start();
    }
     void downloadCommits(int min, int max) {
        Thread workerThread = new Thread() {
            volatile boolean isRunning;

            {
                this.isRunning = true;
            }

            @Override
            public void run() {
                ProjectRepository projectRepository = new ProjectRepository();
//                Project project = projectRepository.get("VisualEditor");
//                              project.download(isRunning);

                List<Project> projectsList = projectRepository.getAll();
                Iterator<Project> projectsIterator = projectsList.iterator();

                while (projectsIterator.hasNext()) {

                    Project temp = projectsIterator.next();
                    if(temp.getOrgId()>=min&&temp.getOrgId()<=max)
                    temp.download(isRunning);
                }

            }
        };
        workerThread.start();
    }

    void downloadProjectsForOrganizations(int min, int max) {
        Thread workerThread = new Thread() {
            @Override
            public void run() {

                OrganizationRepository organizationRepository = new OrganizationRepository();
                List<Organization> organizationList = organizationRepository.getAll();

                Iterator<Organization> iterator = organizationList.iterator();
                int count = 1;
                while (iterator.hasNext() && count <= max) {
                    Organization temp = iterator.next();
                    if (count >= min) {
                        if (temp.downloadProjectsInfo()) {
                            temp.save();
                        }
                    }
                    count++;
                }
            }
        };
        workerThread.start();
    }

}
