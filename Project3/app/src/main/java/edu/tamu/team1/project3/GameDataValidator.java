package edu.tamu.team1.project3;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

/**
 * Created by Alan on 12/2/2014.
 */
public class GameDataValidator {
    private Validator validator;
    private Schema schema;
    private SchemaFactory schemaFactory;
    private DocumentBuilder parser;
    private Document document;
    Context context;

    GameDataValidator(Context context) {
        try {
            schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);//instantiates factory for schema, specifies language
            InputStream filestream = context.getResources().openRawResource(R.raw.schema);
            Source schemaSource = new StreamSource(filestream);
            schema = schemaFactory.newSchema(schemaSource);
            validator = schema.newValidator();
            this.context = context;
        } catch (Exception e) {
            Log.e("GameDataValidator Constructor Exception", e.getMessage());
        }

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

    ArrayList<GameData> validate(int xmlresId) {
        try {
            // validate the DOM tree
            parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStream xmlfilestream = context.getResources().openRawResource(xmlresId);//xml file
            document = parser.parse(xmlfilestream);
            validator.validate(new DOMSource(document));
            ArrayList<GameData> array= new ArrayList<GameData>();
            NodeList elements = document.getElementsByTagName("item");//get list of items
            for(int i=0;i<elements.getLength();++i){
                Node item = elements.item(i);//get item
                NamedNodeMap attributes= item.getAttributes();//get list of attributes
                GameData gameData= new GameData();
                gameData.type=attributes.getNamedItem("type").getTextContent();
                gameData.name=attributes.getNamedItem("name").getTextContent();
                gameData.scientific=attributes.getNamedItem("scientific").getTextContent();
                gameData.id=Integer.parseInt(attributes.getNamedItem("id").getTextContent());
                gameData.habitat=attributes.getNamedItem("habitat").getTextContent();
                gameData.physical=attributes.getNamedItem("physical").getTextContent();
                gameData.diet=attributes.getNamedItem("diet").getTextContent();
                gameData.lifespan=attributes.getNamedItem("lifespan").getTextContent();
                array.add(gameData);
            }
            return array;
        } catch (Exception e) {
            Log.e("GameDataValidator Validate Exception", e.getMessage());
            return null;
        }
    }
}
