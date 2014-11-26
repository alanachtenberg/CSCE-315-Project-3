package edu.tamu.team1.project3;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Settings {
    public Settings(){}
    public String getSettingsXML(String file) throws Exception{
        String xml = null;

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        InputSource is = new InputSource(file);
        Document document = docBuilderFactory.newDocumentBuilder().parse(is);

        StringWriter sw = new StringWriter();
        Transformer serializer = TransformerFactory.newInstance().newTransformer();
        serializer.transform(new DOMSource(document), new StreamResult(sw));

        xml = sw.toString();
        return xml;
    }
    // Functions used for getting and setting the Fling setting for the game
    public void setFling(boolean x) throws Exception{

    }
    public boolean getFling(){
        return false;
    }
    // Functions used for getting and setting the Swipe setting for the game
    public void setSwipe(boolean x){

    }
    public boolean getSwipe(){
        return false;
    }
    // Functions used for getting and setting the theme of the game
    public void setTheme(String theme){

    }

    public String getSettingsTheme() throws Exception{

        String xml = getSettingsXML("settings.xml");
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(xml));

        Document doc = builder.parse(src);
        String theme = doc.getElementsByTagName("theme").item(0).getTextContent();
        return theme;
    }
    // Functions used for getting and setting the topic of the game
    public void setTopic(String topic){

    }
    public String getTopic(){
        return null;
    }
}
