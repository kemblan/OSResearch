package openhub.crawler;

import java.net.URL;
import java.math.*;
import java.net.URLConnection;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.Normalizer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Crawler {

    static Logger log = Logger.getLogger(Crawler.class.getName());
    public String[] apiKeyArray = new String[2];
    int currentKey = 0;
    String currentApiKey;

    public Crawler() {
        loadApiKeys();
        currentApiKey = apiKeyArray[1];
        listProjects(1);

    }

    private void loadApiKeys() {

        apiKeyArray[1] = "a7aea453246b64fd2e353b5387c57cc24a2287157415ac2b93756c4148735fda";
//        apiKeyArray[2] = "f5fKtK4ztMQMQp0BdBQJlg";
//        apiKeyArray[3] = "xTVvlk1zMOH0Nq6hgydA";
//        apiKeyArray[4] = "is685M3Z6L7x7fR5tnVw";
//        apiKeyArray[5] = "yx5d1ZZk3nnRrde0SkcuA";
//        apiKeyArray[6] = "S3dI0dqBxzipjDOpQnP1A";
//        apiKeyArray[7] = "AUTQCvVTNOTyMSpzd7dGQ";
//        apiKeyArray[8] = "EpkIvj8dnyQcPZD8N4YuiA";
//        apiKeyArray[9] = "X3WcRPCwFIs6VawtrE8JgA";
//        apiKeyArray[10] = "STczxiBtRTn7b4yRibOVQ";
    }

    private void listProjects(int page) {

        System.out.println("CRAWLER IS ONLINE!");

        // Iterating on projects
        try {
            // Request XML file.

            URL url = new URL(
                    "https://www.openhub.net/orgs/kde.xml?api_key="+currentApiKey);
//                    "http://www.ohloh.net/projects.xml?query=Cassandra&sort=relevance&page="
//                    + page + "&api_key=" + currentApiKey + "&v=1");
            System.out.println("GLOBAL URL: " + url);
            URLConnection con = url.openConnection();
           System.out.println(con.getHeaderFields().entrySet().iterator().next().getValue());
            // Check for status OK.
//            if (con.getHeaderField("Status").startsWith("200")) {
            if (con.getHeaderField("status") != null) {
                System.out.println("PROJECTS REQUEST succeeded");
                System.out.println(con.getHeaderField("Status"));
            } else {
                System.out
                        .println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.println("Request failed. API NEW API KEY NEEDED!!!");
                System.out.println("CURRENT key:" + currentApiKey);
                System.out.println("trying with different API Key");
                if (currentKey < apiKeyArray.length - 1) {
                    currentKey++;
                } else {
                    return;
                }
                currentApiKey = apiKeyArray[currentKey];
                System.out.println("CURRENT key:" + currentKey);
                System.out.println("CURRENT key:" + currentApiKey);
                System.out
                        .println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                listProjects(page);

                return;
            }
            System.out.println("*************************************");
            System.out.println("STARTING: Looking up projects...");

            // Create a document from the URL's input stream, and parse.
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = builder.parse(con.getInputStream());

            printDocument(doc, System.out);
//            NodeList responseNodes = doc.getElementsByTagName("response");
//
//            for (int i = 0; i < responseNodes.getLength(); i++) {
//                Element element = (Element) responseNodes.item(i);
//
//                // First check for the status code inside the XML file. It
//                // is
//                // most likely, though, that if the request would have
//                // failed,
//                // it is already returned earlier.
//                NodeList statusList = element.getElementsByTagName("status");
//                if (statusList.getLength() == 1) {
//                    Node statusNode = statusList.item(0);
//
//                    // Check if the text inidicates that the request was
//                    // successful.
//                    if (!statusNode.getTextContent().equals("success")) {
//                        System.out.println("Failed. "
//                                + statusNode.getTextContent());
//                        return;
//                    }
//                }
//                Element itemsAvaiable = (Element) element.getElementsByTagName(
//                        "items_available").item(0);
//                System.out.println("Total items:"
//                        + itemsAvaiable.getTextContent());
//
//                int totalPages = Integer.parseInt(itemsAvaiable
//                        .getTextContent()) / 10 + 1;
//
//                System.out.println("Number of pages: " + totalPages);
//                System.out.println("Current PROJECTS page:" + page
//                        + " from Total Pages:" + totalPages);
//                // getting the objects from the result
//                Element resultElement = (Element) element.getElementsByTagName(
//                        "result").item(0);
//
//                NodeList projectList = element.getElementsByTagName("project");
//
//                for (int z = 0; z < projectList.getLength(); z++) {
//                    Element projectElement = (Element) resultElement
//                            .getElementsByTagName("project").item(z);
//                    Element idElement = (Element) projectElement
//                            .getElementsByTagName("id").item(0);
//
//                    // Making sure that the project Element is not empty
//                    if (projectElement != null) {
//
//                        System.out.println("CURRRENT PROJECT ID : "
//                                + idElement.getTextContent());
//                        System.out
//                                .println("*************************************");
//                        listProject("" + idElement.getTextContent());
//                    }
//                }
//                if (page < totalPages) {
//
//                    page++;
//
//                    System.out.println("PROJECTS PAGE CHANGE to ->" + page
//                            + " from Total Pages:" + totalPages);
//
//                    System.out
//                            .println("***************************************************");
//                    listProjects(page);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listProject(String projectId) {

        System.out.println("##############################");
        System.out.println("Initialising project request.");

        // Iterating on projects
        try {
            // Request XML file.

            URL url = new URL("http://www.ohloh.net/projects/" + projectId
                    + ".xml?query=Apache&sort=name&api_key=" + currentApiKey
                    + "&v=1");
            System.out.println("" + url);
            URLConnection con = url.openConnection();

            // Check for status OK.
            if (con.getHeaderField("Status").startsWith("200")) {
                System.out.println("PROJECT Request succeeded.");
            } else {
                System.out
                        .println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out
                        .println("IM in project query - Request failed. API NEW API KEY NEEDED!!!");
                System.out.println("CURRENT key:" + currentApiKey);
                System.out.println("trying with different API Key");
                currentKey++;
                currentApiKey = apiKeyArray[currentKey];
                System.out.println("CURRENT key:" + currentApiKey);
                System.out
                        .println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                listProject(projectId);
            }

            // Create a document from the URL's input stream, and parse.
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = builder.parse(con.getInputStream());

            NodeList responseNodes = doc.getElementsByTagName("response");
            for (int i = 0; i < responseNodes.getLength(); i++) {
                Element element = (Element) responseNodes.item(i);

                NodeList statusList = element.getElementsByTagName("status");
                if (statusList.getLength() == 1) {
                    Node statusNode = statusList.item(0);

                    // Check if the text inidicates that the request was
                    // successful.
                    if (!statusNode.getTextContent().equals("success")) {
                        System.out.println("Failed. "
                                + statusNode.getTextContent());
                        return;
                    }
                }

                // getting the objects from the result
                Element resultElement = (Element) element.getElementsByTagName(
                        "result").item(0);

                Element idElement = (Element) resultElement
                        .getElementsByTagName("id").item(0);
                Element nameElement = (Element) resultElement
                        .getElementsByTagName("name").item(0);
                Element updatedAtElement = (Element) resultElement
                        .getElementsByTagName("updated_at").item(0);
                Element createdAtElement = (Element) resultElement
                        .getElementsByTagName("created_at").item(0);
                Element descriptionElement = (Element) resultElement
                        .getElementsByTagName("description").item(0);
                Element homepageElement = (Element) resultElement
                        .getElementsByTagName("homepage_url").item(0);
                Element analysisIdElement = (Element) resultElement
                        .getElementsByTagName("analysis_id").item(0);
                Element totalContributorCountElement = (Element) resultElement
                        .getElementsByTagName("total_contributor_count")
                        .item(0);
                Element totalCommitCountElement = (Element) resultElement
                        .getElementsByTagName("total_commit_count").item(0);
                Element totalCodeLinesElement = (Element) resultElement
                        .getElementsByTagName("total_code_lines").item(0);
                Element mainLanguageElement = (Element) resultElement
                        .getElementsByTagName("main_language_name").item(0);

                System.out.println("projectID: " + convertToInt(idElement));

                System.out.println("Name Before conversion: " + convertToString(nameElement));
                System.out.println("Name After conversion: " + StringEscapeUtils.escapeJson(convertToString(nameElement)));
                System.out.println("Updated at: "
                        + convertToString(updatedAtElement));
                System.out.println("Created at: "
                        + convertToString(createdAtElement));
                System.out.println("Homepage URL: "
                        + convertToString(homepageElement));
                System.out.println("Created at: "
                        + convertToString(analysisIdElement));

//				try {
//					
//					databaseBuilder.projectTableUpdate(
//							convertToInt(idElement),
//							StringEscapeUtils.escapeJson(convertToString(nameElement)),
//							convertToString(createdAtElement),
//							convertToString(updatedAtElement),
//							"",
//							convertToString(homepageElement),
//							convertToInt(analysisIdElement));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
                // analysis
                System.out.println("Main Language: "
                        + convertToString(mainLanguageElement));
                System.out.println("Analysis id: "
                        + convertToInt(analysisIdElement));
                System.out.println("Total contributor Count: "
                        + convertToInt(totalContributorCountElement));
                System.out.println("Total Commit count: "
                        + convertToInt(totalCodeLinesElement));
                System.out.println("Total lines count: "
                        + convertToInt(totalCommitCountElement));

//				try {
//					databaseBuilder.analysisTableUpdate(convertToInt(analysisIdElement),
//							convertToInt(totalContributorCountElement),
//							convertToInt(totalCommitCountElement), 
//							convertToInt(totalCodeLinesElement), 
//							convertToString(mainLanguageElement),
//							convertToInt(idElement));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
                listContributors(projectId, 1);
                System.out.println("Writing output");
                // createOutuptXML(outputDocument);
                System.out.println("##############################");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String convertToString(Element el) {
        String str;
        try {
            if (el.getTextContent() != null) {
                str = el.getTextContent();
            } else {
                str = "EMPTY";
                System.out.println("EMPTY ELEMENT FIX");
            }
        } catch (Exception e) {
            str = "EMPTY";
            System.out.println("EMPTY ELEMENT FIX - CATCHED");
        }
        return str;
    }

    public int convertToInt(Element el) {
        int num;
        try {
            if (el.getTextContent() != null) {
                num = Integer.parseInt(el.getTextContent());
            } else {
                num = 0;
            }
        } catch (Exception e) {
            System.out.println("COnvert to int failed - backup");
            num = 0;
        }
        return num;
    }

    public void listContributors(String projectId, int page) {

        // 
        // Iterating on projects
        try {
            // Request XML file.

            URL url = new URL("http://www.ohloh.net/projects/" + projectId
                    + "/contributors.xml?&sort=name&page=" + page + "&api_key="
                    + currentApiKey);
            System.out.println("CONTRBUTORS URL: " + url);
            URLConnection con = url.openConnection();

            // Check for status OK.
            if (con.getHeaderField("Status").startsWith("200")) {
                System.out.println("CONTRIBUTORS REQUEST succeeded.");
            } else {
                System.out
                        .println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out
                        .println("I'm in contributors query - Request failed. API NEW API KEY NEEDED!!!");
                System.out.println("CURRENT key:" + currentApiKey);
                System.out.println("trying with different API Key");
                currentKey++;
                currentApiKey = apiKeyArray[currentKey];
                System.out.println("CURRENT key:" + currentApiKey);
                System.out
                        .println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                listContributors(projectId, page);
                return;
            }

            // Create a document from the URL's input stream, and parse.
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = builder.parse(con.getInputStream());

            NodeList responseNodes = doc.getElementsByTagName("response");

            for (int i = 0; i < responseNodes.getLength(); i++) {

                Element element = (Element) responseNodes.item(i);

                Element itemsAvaiable = (Element) element.getElementsByTagName(
                        "items_available").item(0);
                int totalPages = Integer.parseInt(itemsAvaiable
                        .getTextContent()) / 20 + 1;
                System.out.println("Total Pages Contributors:" + totalPages);
                System.out.println("Current pages Contributors:" + page);

                NodeList statusList = element.getElementsByTagName("status");
                if (statusList.getLength() == 1) {
                    Node statusNode = statusList.item(0);

                    // Check if the text inidicates that the request was
                    // successful.
                    if (!statusNode.getTextContent().equals("success")) {
                        System.out.println("Failed. "
                                + statusNode.getTextContent());
                        return;
                    }
                }

                Element resultElement = (Element) element.getElementsByTagName(
                        "result").item(0);

                NodeList contributorFactNodes = doc
                        .getElementsByTagName("contributor_fact");

                for (int j = 0; j < contributorFactNodes.getLength(); j++) {
                    Element contributorFactElement = (Element) resultElement
                            .getElementsByTagName("contributor_fact").item(j);

                    long contributorID = 0;
                    String contributorName = new String("EMPTY");
                    int contributorCommits = 0;
                    int contributorManMonths = 0;
                    String contributorFirstCommitTime = new String("EMPTY");
                    String contributorLastCommitTime = new String("EMPTY");
                    int analysisID = 0;

                    if (contributorFactElement
                            .getElementsByTagName("analysis_id").item(0)
                            != null) {

                        analysisID = Integer.parseInt(contributorFactElement
                                .getElementsByTagName("analysis_id").item(0)
                                .getTextContent());
                        System.out.println("AnalysisID" + analysisID);
                    } else {
                        analysisID = 0;
                        System.out.println("AnalysisID EMPTY");
                    }

                    // DatabaseUpdate
                    // contributor_id
                    if (contributorFactElement.getElementsByTagName(
                            "contributor_id").item(0) != null) {
                        contributorID = Long.parseLong(contributorFactElement
                                .getElementsByTagName("contributor_id").item(0)
                                .getTextContent());
                        System.out.println("Contributor ID: " + contributorID);
                    } else {
                        contributorID = 0;
                        System.out.println("Contributor Id EMPTY");
                    }

                    // contributor_name
                    if (contributorFactElement.getElementsByTagName(
                            "contributor_name").item(0) != null) {
                        contributorName = contributorFactElement
                                .getElementsByTagName("contributor_name")
                                .item(0).getTextContent();

                        System.out
                                .println("Contributor Name Before conversion" + contributorName);
                        contributorName = Normalizer.normalize(contributorName, Normalizer.Form.NFD);
                        contributorName = contributorName.replaceAll("\\p{M}", "");
                        contributorName = contributorName.replaceAll("'", "*");
                        contributorName = contributorName.replaceAll("\\?", "ZNAKZAPYTANIA");
                        contributorName = StringEscapeUtils.escapeJson(contributorName);
                        System.out
                                .println("Contributor Name AFter conversion" + contributorName);
                    } else {
                        contributorName = "EMPTY";
                        System.out.println("Contributor Name EMPTY");
                    }

                    // Man_months
                    if (contributorFactElement.getElementsByTagName(
                            "man_months").item(0) != null) {
                        contributorManMonths = Integer
                                .parseInt(contributorFactElement
                                        .getElementsByTagName("man_months")
                                        .item(0).getTextContent());
                        System.out.println("Contributor Man Months"
                                + contributorManMonths);
                    } else {
                        contributorManMonths = 0;
                        System.out.println("Contributor Man_months EMPTY");
                    }

                    // commits
                    if (contributorFactElement.getElementsByTagName("commits")
                            .item(0) != null) {
                        contributorCommits = Integer
                                .parseInt(contributorFactElement
                                        .getElementsByTagName("commits")
                                        .item(0).getTextContent());
                        System.out.println("Commits Contributor:"
                                + contributorCommits);
                    } else {
                        contributorCommits = 0;
                        System.out.println("Contributor Commits EMPTY");
                    }

                    // first_commit_time
                    if (contributorFactElement.getElementsByTagName(
                            "first_commit_time").item(0) != null) {
                        contributorFirstCommitTime = contributorFactElement
                                .getElementsByTagName("first_commit_time")
                                .item(0).getTextContent();
                        System.out.println("First_commit_time:"
                                + contributorFirstCommitTime);
                    } else {
                        contributorCommits = 0;
                        System.out
                                .println("Contributor first_commit_time EMPTY");
                    }

                    // last_commit_time
                    if (contributorFactElement.getElementsByTagName(
                            "last_commit_time").item(0) != null) {
                        contributorLastCommitTime = contributorFactElement
                                .getElementsByTagName("last_commit_time")
                                .item(0).getTextContent();
                        System.out.println("Last_commit_time:"
                                + contributorLastCommitTime);
                    } else {
                        contributorCommits = 0;
                        System.out
                                .println("Contributor last_commit_time EMPTY");
                    }

//					try {
//						databaseBuilder.contributor_factTableUpdate(contributorID,
//								StringEscapeUtils.escapeHtml4(contributorName), contributorCommits,
//								contributorFirstCommitTime,
//								contributorLastCommitTime, contributorManMonths,
//								analysisID, Integer.parseInt(projectId));
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					
//					}
                }

                if (page < totalPages) {

                    System.out.println("Current page:" + page
                            + " from Total Pages:" + totalPages);
                    page++;

                    listContributors(projectId, page);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

    transformer.transform(new DOMSource(doc), 
         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
}

}
