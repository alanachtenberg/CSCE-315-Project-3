package edu.tamu.team1.project3;

import android.os.Environment;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Root
public class Settings {

    @Element(required=false)
    private String theme;

    @Element(required=false)
    private String topic;

    @Element
    private boolean fling;

    @Element
    private boolean swipe;

    public Settings() {
    }

    public boolean isSwipe() {
        return swipe;
    }

    public void setSwipe(boolean swipe) {
        this.swipe = swipe;
        serialize();
    }

    public boolean isFling() {
        return fling;
    }

    public void setFling(boolean fling) {
        this.fling = fling;
        serialize();
    }

    public String getTopic() {
        if(topic == null) return "fish";
        else return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic.toLowerCase();
        serialize();
    }

    public String getTheme() {
        if (theme == null) return "red";
        else return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme.toLowerCase();
        serialize();
    }

    private void serialize() {
        try {
            boolean canWrite;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                canWrite = true;
            } else {
                canWrite = false;
            }

            if (canWrite) {

                String path = Environment.getExternalStorageDirectory().getPath() + "/m_cubed";
                File folder = new File(path);

                if (!folder.exists()) {
                    folder.mkdirs();
                }
                if (folder.exists()) {
                    File xmlFile = new File(path, "settings.xml");
                    final OutputStream out = new BufferedOutputStream(new FileOutputStream(xmlFile, false));

                    Serializer serializer = new Persister();
                    serializer.write(this, out);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Settings deserialize() {
        try {
            boolean canWrite;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                canWrite = true;
            } else {
                canWrite = false;
            }

            if (canWrite) {

                String path = Environment.getExternalStorageDirectory().getPath() + "/m_cubed";
                File folder = new File(path);

                if (!folder.exists()) {
                    folder.mkdirs();
                }
                if (folder.exists()) {
                    File xmlFile = new File(path, "settings.xml");

                    Serializer serializer = new Persister();
                    return serializer.read(Settings.class, xmlFile);
                }
            } else {
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new Settings();
    }



//    //copies raw resource onto the SD card
//    public static boolean create(Context context) throws Exception {
//        try {
//            String state = Environment.getExternalStorageState();
//            if (Environment.MEDIA_MOUNTED.equals(state)) {
//                String path = Environment.getExternalStorageDirectory().getPath() + "/m_cubed";
//                File folder = new File(path);
//
//                if(!folder.exists()) {
//                    folder.mkdirs();
//                }
//
//                File xmlFile = new File(path, "msettings.xml");
//                xmlFile.createNewFile();
//
////                File xmlFile2 = new File(path, "settings.xml");
////                xmlFile2.createNewFile();
//
//                InputStream input = context.getResources().openRawResource(R.raw.settings);
//
//                Document doc = DocumentBuilderFactory
//                        .newInstance()
//                        .newDocumentBuilder()
//                        .parse(input);
//
//                Transformer transformer = TransformerFactory.newInstance().newTransformer();
//                DOMSource source = new DOMSource(doc);
//                StreamResult result = new StreamResult(xmlFile);
//                transformer.transform(source, result);
//
////                StreamResult result2 = new StreamResult(xmlFile2);
////                transformer.transform(source, result2);
//
//                return true;
//
//            }
//            else {
//                Toast.makeText(context, "Unable to open external storage. Check if SD card is installed properly", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        }
//        catch(IOException e) {
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
//            return false;
//        }
//    }
//
////======================================================================
//// Functions used for getting and setting the Fling setting for the game
////======================================================================
//
//    public static void setFling(boolean x) throws Exception{
//        String check;
//        String path = Environment.getExternalStorageDirectory().toString() + "/m_cubed/msettings.xml";
//        File xmlFile = new File(path);
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(xmlFile);
//
//        if(x)
//            check = "true";
//        else
//            check = "false";
//
//        String swipe;
//        if(Settings.getSwipe())
//            swipe = "true";
//        else
//            swipe = "false";
//
//        Node flingNode = doc.getElementsByTagName("fling").item(0);     // get items by tag names
//        Node swipeNode = doc.getElementsByTagName("swipe").item(0);
//        Node themeNode = doc.getElementsByTagName("theme").item(0);
//        Node topicNode = doc.getElementsByTagName("topic").item(0);
//
//        flingNode.setTextContent(check);                                // set the correct value
//        swipeNode.setTextContent(swipe);
//        themeNode.setTextContent(Settings.getSettingsTheme());
//        topicNode.setTextContent(Settings.getSettingsTopic());
//
//
//        Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        DOMSource source = new DOMSource(doc);
//        StreamResult result = new StreamResult(new File(path));
//        transformer.transform(source, result);
//
//        // redirect to another file in order to make it work
//        String path2 = Environment.getExternalStorageDirectory().toString() + "/m_cubed/settings.xml";
//        StreamResult result2 = new StreamResult(new File(path2));
//        transformer.transform(source, result2);
//    }
//
//    public static boolean getFling() throws Exception {
//        String check;
//        String path = Environment.getExternalStorageDirectory().toString() + "/m_cubed/settings.xml";
//
//        Document doc = DocumentBuilderFactory
//                .newInstance()
//                .newDocumentBuilder()
//                .parse(new File(path));
//
//        check =  doc.getElementsByTagName("fling").item(0).getTextContent();
//
//        if(check.equals("true"))
//            return true;
//        else
//            return false;
//    }
//
////======================================================================
//// Functions used for getting and setting the Swipe setting for the game
////======================================================================
//
//    public static void setSwipe(boolean x) throws Exception {
//        String check;
//        String path = Environment.getExternalStorageDirectory().toString() + "/m_cubed/msettings.xml";
//        File xmlFile = new File(path);
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(xmlFile);
//
//        String fling;
//        if(Settings.getFling())
//            fling = "true";
//        else
//            fling = "false";
//
//        if(x)
//            check = "true";
//        else
//            check = "false";
//
//        Node flingNode = doc.getElementsByTagName("fling").item(0);     // get items by tag names
//        Node swipeNode = doc.getElementsByTagName("swipe").item(0);
//        Node themeNode = doc.getElementsByTagName("theme").item(0);
//        Node topicNode = doc.getElementsByTagName("topic").item(0);
//
//        flingNode.setTextContent(fling);                                // set the correct values
//        swipeNode.setTextContent(check);
//        themeNode.setTextContent(Settings.getSettingsTheme());
//        topicNode.setTextContent(Settings.getSettingsTopic());
//
//        Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        DOMSource source = new DOMSource(doc);
//        StreamResult result = new StreamResult(new File(path));
//        transformer.transform(source, result);
//
//        // redirect to another file in order to make it work
//        String path2 = Environment.getExternalStorageDirectory().toString() + "/m_cubed/settings.xml";
//        StreamResult result2 = new StreamResult(new File(path2));
//        transformer.transform(source, result2);
//    }
//
//    public static boolean getSwipe() throws Exception {
//        String check;
//        String path = Environment.getExternalStorageDirectory().toString() + "/m_cubed/settings.xml";
//
//        Document doc = DocumentBuilderFactory
//                .newInstance()
//                .newDocumentBuilder()
//                .parse(new File(path));
//
//        // Return the text associated with <swipe>TEXT</swipe>
//        check =  doc.getElementsByTagName("swipe").item(0).getTextContent();
//
//        if(check.equals("true"))
//            return true;
//        else
//            return false;
//    }
//
////==============================================================
//// Functions used for getting and setting the theme of the game
////==============================================================
//
//    public static void setTheme(String theme) throws Exception {
//        String path = Environment.getExternalStorageDirectory().toString() + "/m_cubed/msettings.xml";
//        File xmlFile = new File(path);
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(xmlFile);
//
//        String fling;
//        if(Settings.getFling())
//            fling = "true";
//        else
//            fling = "false";
//        String swipe;
//        if(Settings.getSwipe())
//            swipe = "true";
//        else
//            swipe = "false";
//
//        Node flingNode = doc.getElementsByTagName("fling").item(0);     // get items by tag names
//        Node swipeNode = doc.getElementsByTagName("swipe").item(0);
//        Node themeNode = doc.getElementsByTagName("theme").item(0);
//        Node topicNode = doc.getElementsByTagName("topic").item(0);
//
//        flingNode.setTextContent(fling);                                // set the correct values
//        swipeNode.setTextContent(swipe);
//        themeNode.setTextContent(theme);
//        topicNode.setTextContent(Settings.getSettingsTopic());
//
//        //I have no idea why, but it doesn't seem to like writing to the original
//        //file created from the raw resource. But redirecting it to another file
//        //seems to work just fine, so use this format for all settings
//        Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        DOMSource source = new DOMSource(doc);
//        StreamResult result = new StreamResult(new File(path));
//        transformer.transform(source, result);
//
//        // redirect to another file in order to make it work
//        String path2 = Environment.getExternalStorageDirectory().toString() + "/m_cubed/settings.xml";
//        File settingsFile = new File(path2);
//        if(!settingsFile.exists()) settingsFile.createNewFile();
//        StreamResult result2 = new StreamResult(settingsFile);
//        transformer.transform(source, result2);
//    }
//
//    public static String getSettingsTheme() throws Exception{
//        String path = Environment.getExternalStorageDirectory().toString() + "/m_cubed/settings.xml";
//
//        File settingsFile = new File(path);
//        if(!settingsFile.exists()) settingsFile.createNewFile();
//
//        Document doc = DocumentBuilderFactory
//                .newInstance()
//                .newDocumentBuilder()
//                .parse(settingsFile);
//
//        // Return the text associated with <theme>TEXT</theme>
//        return doc.getElementsByTagName("theme").item(0).getTextContent();
//    }
//
////==============================================================
//// Functions used for getting and setting the topic of the game
////==============================================================
//
//    public static void setTopic(String topic) throws Exception {
//        String path = Environment.getExternalStorageDirectory().toString() + "/m_cubed/msettings.xml";
//        File xmlFile = new File(path);
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(xmlFile);
//
//        String fling;
//        if(Settings.getFling())
//            fling = "true";
//        else
//            fling = "false";
//        String swipe;
//        if(Settings.getSwipe())
//            swipe = "true";
//        else
//            swipe = "false";
//
//        Node flingNode = doc.getElementsByTagName("fling").item(0);     // get items by tag names
//        Node swipeNode = doc.getElementsByTagName("swipe").item(0);
//        Node themeNode = doc.getElementsByTagName("theme").item(0);
//        Node topicNode = doc.getElementsByTagName("topic").item(0);
//
//        flingNode.setTextContent(fling);                                // set the correct values
//        swipeNode.setTextContent(swipe);
//        themeNode.setTextContent(Settings.getSettingsTheme());
//        topicNode.setTextContent(topic);
//
//        //redirecting it to another file to make work
//        Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        DOMSource source = new DOMSource(doc);
//        StreamResult result = new StreamResult(new File(path));
//        transformer.transform(source, result);
//
//        // redirect to another file in order to make it work
//        String path2 = Environment.getExternalStorageDirectory().toString() + "/m_cubed/settings.xml";
//        File settingsFile = new File(path2);
//        if(!settingsFile.exists()) settingsFile.createNewFile();
//        StreamResult result2 = new StreamResult(settingsFile);
//        transformer.transform(source, result2);
//    }
//
//    public static String getSettingsTopic() throws Exception{
//        // get the path to our settings.xml file
//        String path = Environment.getExternalStorageDirectory().toString() + "/m_cubed/settings.xml";
//        // Define factory API with the settings.xml file
//        Document doc = DocumentBuilderFactory
//                .newInstance()
//                .newDocumentBuilder()
//                .parse(new File(path));
//        // Return the text associated with <topic>TEXT</topic>
//        return doc.getElementsByTagName("topic").item(0).getTextContent();
//    }
}
