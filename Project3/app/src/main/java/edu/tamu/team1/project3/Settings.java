package edu.tamu.team1.project3;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Settings {
    public Settings(){}

    //copies raw resource onto the SD card
    public static boolean create(Context context) throws Exception {
        try {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                String path = Environment.getExternalStorageDirectory().getPath() + "/m_cubed";
                File folder = new File(path);

                if(!folder.exists()) {
                    folder.mkdirs();
                }

                File xmlFile = new File(path, "msetting.xml");

                if(!xmlFile.exists()) {
                    xmlFile.mkdirs();
                }

                InputStream input = context.getResources().openRawResource(R.raw.settings);

                Document doc = DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder()
                        .parse(input);

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(xmlFile);
                transformer.transform(source, result);

                return true;

            }
            else {
                Toast.makeText(context, "Unable to open external storage. Check if SD card is installed properly", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        catch(IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
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
    public void setTheme(String theme) throws Exception {
        String path = Environment.getExternalStorageDirectory().toString() + "/m_cubed/msetting.xml";

        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new File(path));

        Node themeNode = doc.getElementsByTagName("theme").item(0);
        themeNode.setTextContent(theme);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);

        //I have no idea why, but it doesn't seem to like writing to the original
        //file created from the raw resource. But redirecting it to another file
        //seems to work just fine, so use this format for all settings
        String path2 = Environment.getExternalStorageDirectory().toString() + "/m_cubed/settings.xml";
        StreamResult result2 = new StreamResult(new File(path2));
        transformer.transform(source, result2);
    }

    public static String getSettingsTheme() throws Exception{
        String path = Environment.getExternalStorageDirectory().toString() + "/m_cubed/settings.xml";

        Document doc = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new File(path));

        return doc.getElementsByTagName("theme").item(0).getTextContent();
    }
    // Functions used for getting and setting the topic of the game
    public void setTopic(String topic){

    }
    public String getTopic(){
        return null;
    }
}
