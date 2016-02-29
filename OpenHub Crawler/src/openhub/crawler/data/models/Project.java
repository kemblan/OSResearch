/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhub.crawler.data.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import openhub.crawler.OpenHubAPIConnector;
import openhub.crawler.OpenHubHTMLConnector;
import openhub.crawler.XMLPrinter;
import openhub.crawler.data.repositories.ProjectRepository;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author mateusz
 */
public class Project implements OpenHubData {

    private ProjectRepository repository = null;
    private long id = 0;
    private String name = null;
    private String url = null;
    private long organization = 0;
    private int reviewCount = 0;
    private int ratingCount = 0;
    private double averageRating = 0;
    private String description = null;
    private String vanityName = null;

    public Project(String projectName, long organizationName, String url) {
        this.name = projectName;
        this.organization = organizationName;
        this.url = url;
        this.repository = new ProjectRepository();
    }

//    public Project(String projectName, int organizationName) {
//        this.name = projectName;
//        this.organization = organizationName;
//    }
//
//    public Project(String projectName) {
//        this.name = projectName;
//    }
    public Project(String name, long organization, String url, int reviewCount, int ratingCount, double averageRating, String description, String vanityName) {
        this.name = name;
        this.url = url;
        this.organization = organization;
        this.reviewCount = reviewCount;
        this.ratingCount = ratingCount;
        this.averageRating = averageRating;
        this.description = description;
        this.vanityName = vanityName;
    }

    public boolean downloadInfo() {
        String query;
        if (url != null) {
            query = url.replace("https://www.openhub.net", "") + "?";
        } else {
            query = "/projects.xml?query=" + name + "&";
        }
        try {
            Document document = OpenHubAPIConnector.getInstance().getData(query);
            XMLPrinter.printDocument(document, System.out);

            Element root = document.getDocumentElement();
            if (root.getElementsByTagName("description").getLength() > 0) {
                this.description = root.getElementsByTagName("description").item(0).getTextContent();
            } else {
                this.description = "";
            }
            this.vanityName = root.getElementsByTagName("vanity_url").item(0).getTextContent();
            this.ratingCount = Integer.parseInt(root.getElementsByTagName("rating_count").item(0).getTextContent());
            this.averageRating = Double.parseDouble(root.getElementsByTagName("average_rating").item(0).getTextContent());
            if (root.getElementsByTagName("review_count").getLength() > 0) {
                this.reviewCount = Integer.parseInt(root.getElementsByTagName("review_count").item(0).getTextContent());
            } else {
                this.reviewCount = 0;
            }
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Organization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean downloadCommitsInfo() {
        int page = 1;
        int allPages = 0;
        try {
            String query = "/p/" + vanityName + "/commits?page=" + page;
            org.jsoup.nodes.Document document = OpenHubHTMLConnector.getInstance().getData2(query);
            Elements commitPages = document.select(".pagination").select("a").select(":not(.next)").select(":not(.prev)");
            System.out.println(commitPages.toString());
            for (org.jsoup.nodes.Element commitPage : commitPages) {
                if (!commitPage.hasAttr("rel")) {
                    int tempPage = Integer.parseInt(commitPage.text());
                    if (tempPage > allPages) {
                        allPages = tempPage;
                    }
                }

            }

            System.out.println("wyszstkich stron jest : " + allPages);
            do {
                Elements commitLines = document.select("tbody").select("tr");
                for (org.jsoup.nodes.Element commitLine : commitLines) {
                    System.out.println("commitLine: " + commitLine.toString());
                    org.jsoup.nodes.Element commitLink = commitLine.select(".commit-details").first();
                    System.out.println("commitLink: " + commitLink.tagName());
                    String commitId = commitLink.attr("commit_id");
                    String query2 = "/p/" + vanityName + "/commits/" + commitId;
//                    String query2 = "/p/firefox/commits/395418251";
                    org.jsoup.nodes.Document commitDocument = OpenHubHTMLConnector.getInstance().getData2(query2);
                    org.jsoup.nodes.Element commitInfo = commitDocument.select(".commit_info").first();

                    Elements commitInfos = commitInfo.select("tr");

                    String commitUUID = commitDocument.select(".commit_id").first().text().replace("Commit ID","").replace(" ", "");
                    String contributor = commitInfos.get(0).select("a").get(1).text();
                    String tempId = commitInfos.get(0).select("a").get(1).attr("href");
                    System.out.println(tempId);
                    tempId = tempId.substring(tempId.lastIndexOf("/") + 1);
                    String contributorId = tempId;
                    String date = commitInfos.get(0).select(".info").get(0).text();
                    String comment = commitInfos.get(3).select(".info").get(0).text();
                    int files = Integer.parseInt(commitInfos.get(0).select(".info").get(1).text());
                    int added = Integer.parseInt(commitInfos.get(1).select(".info").get(1).text());
                    int removed = Integer.parseInt(commitInfos.get(2).select(".info").get(1).text());

                    Elements languagesInfo = commitDocument.select(".language_total.center").select("tbody").select("tr");//.select(":not(.nohover)");
                    List<CodeChange> codeChanges = new ArrayList<>();
                    for (org.jsoup.nodes.Element languageInfo : languagesInfo) {
                        String language = languageInfo.select("a").first().text();
                        int codeAdded = Integer.parseInt(languageInfo.select(".center").get(0).text());
                        int codeRemoved = Integer.parseInt(languageInfo.select(".center").get(1).text());
                        int commentsAdded = Integer.parseInt(languageInfo.select(".center").get(2).text());
                        int commentsRemoved = Integer.parseInt(languageInfo.select(".center").get(3).text());
                        int blanksAdded = Integer.parseInt(languageInfo.select(".center").get(4).text());
                        int blanksRemoved = Integer.parseInt(languageInfo.select(".center").get(5).text());
                        CodeChange codeChange = new CodeChange(language, codeAdded, codeRemoved, commentsAdded, commentsRemoved, blanksAdded, blanksRemoved);
                        codeChanges.add(codeChange);
                        Commit commit = new Commit(this.id, comment, commitId, added, removed, files, contributor,contributorId, codeChanges);
                        commit.save();
                    }
                }
                page++;
                query = "/p/" + vanityName + "/commits?page=" + page;
                document = OpenHubHTMLConnector.getInstance().getData2(query);
            } while (page <= allPages);

        } catch (IOException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    public boolean downloadCommitersInfo() {
        int all = 1;
        int read = 0;
        int page = 1;
        do {
            String query = "/p/" + name + "/contributors.xml?page=" + page + "&";
            page++;
            try {
                Document document = OpenHubAPIConnector.getInstance().getData(query);
                XMLPrinter.printDocument(document, System.out);
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
        return 0;
    }

    @Override
    public boolean save() {
        return repository.add(this);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public long getOrgId() {
        return organization;
    }

    public String getVanityName() {
        return vanityName;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
