package dataHelpers;

import model.Email;
import org.testng.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser extends DataManager {
    private static final String URI_FOR_CREDENTIALS = "src/main/resources/Credentials.xml";
    private static final String URI_FOR_EMAILS = "src/main/resources/Emails.xml";

    private Document initParser(String uri) {
        Document document = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document =  dBuilder.parse(uri);
            Reporter.log("Test data has been read from XML file " + uri);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
            Reporter.log("Error occurred while trying to read test data from XML file.");
        }
        return document;
    }

    public String getPassword(String login) {
        Document document = initParser(URI_FOR_CREDENTIALS);
        NodeList nodeList = document.getElementsByTagName("User");
        String password = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if(getTagValue("login", element).equals(login)) {
                password = getTagValue("password", element);
            }
        }
        return password;
    }

    public List<Email> getEmails() {
        Document document = initParser(URI_FOR_EMAILS);
        return parseEmails(document);
    }

    private List<Email> parseEmails(Document document) {
        NodeList nodeList = document.getElementsByTagName("Email");
        List<Email> emails = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            emails.add(getEmail(nodeList.item(i)));
        }
        return emails;
    }

    private static Email getEmail(Node node) {
        Element element = (Element) node;
        return Email.newEntity()
                .withId(getAttribute("id", element))
                .withFrom(getTagValue("from", element))
                .withTo(getTagValue("to", element))
                .withCc(getTagValue("cc", element))
                .withBcc(getTagValue("bcc", element))
                .withSubject(getTagValue("subject", element))
                .withText(getTagValue("text", element))
                .build();
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    private static String getAttribute(String attributeName, Element element) {
        return element.getAttribute(attributeName);
    }
}
