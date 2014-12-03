package edu.tamu.team1.project3;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class GameDataValidator {
    GameDataValidator() {

    }

    public static class GameData{
        public String type;
        public String name;
        public String scientific;
        public int    id;
        public String habitat;
        public String physical;
        public String diet;
        public String lifespan;
    }

    ArrayList<GameData> validate(Context context, int xmlresId) {
        try {
//            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            InputStream filestream = context.getResources().openRawResource(R.raw.schema);
            Source schemaSource = new StreamSource(filestream);
//            Schema schema = schemaFactory.newSchema(schemaSource);
//            Validator validator = schema.newValidator();

            // validate the DOM tree
            InputStream xmlfilestream = context.getResources().openRawResource(xmlresId);//xml file

            Document document =
                    DocumentBuilderFactory
                            .newInstance()
                            .newDocumentBuilder()
                            .parse(xmlfilestream);

//            validator.validate(new DOMSource(document));

            ArrayList<GameData> array= new ArrayList<GameData>();
            Element root = document.getDocumentElement();
            NodeList elements = root.getElementsByTagName("item");//get list of items

            for(int i=0;i<elements.getLength();++i){
                Node item = elements.item(i);//get item
                NodeList properties = item.getChildNodes();
                GameData gameData= new GameData();
                for (int j=0;j<properties.getLength();j++) {
                    Node property = properties.item(j);
                    String name = property.getNodeName();

                    if (name.equalsIgnoreCase("type")) {
                        gameData.type = property.getFirstChild().getTextContent();
                    }
                    else if (name.equalsIgnoreCase("name")) {
                        gameData.name = property.getFirstChild().getTextContent();
                    }
                    else if (name.equalsIgnoreCase("scientific")) {
                        gameData.scientific = property.getFirstChild().getTextContent();
                    }
                    else if (name.equalsIgnoreCase("id")) {
                        gameData.id = Integer.parseInt(property.getFirstChild().getTextContent());
                    }
                    else if (name.equalsIgnoreCase("habitat")) {
                        gameData.habitat = property.getFirstChild().getTextContent();
                    }
                    else if (name.equalsIgnoreCase("physical")) {
                        gameData.physical = property.getFirstChild().getTextContent();
                    }
                    else if (name.equalsIgnoreCase("diet")) {
                        gameData.diet = property.getFirstChild().getTextContent();
                    }
                    else if (name.equalsIgnoreCase("lifespan")) {
                        gameData.lifespan = property.getFirstChild().getTextContent();
                    }
                }
                array.add(gameData);
            }

            return array;
        } catch (Exception e) {
            Log.e("GameDataValidator Validate Exception", "" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
