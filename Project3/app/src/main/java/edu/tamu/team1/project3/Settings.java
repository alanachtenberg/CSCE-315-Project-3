package edu.tamu.team1.project3;

import android.os.Environment;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

@Root
public class Settings {

    @Element private String theme;
    @Element private String topic;
    @Element private boolean fling;
    @Element private boolean swipe;

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
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
        serialize();
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
        serialize();
    }

    private void serialize() {
        try {
            String path = Environment.getExternalStorageDirectory().getPath() + "/m_cubed";
            File folder = new File(path);
            if(!folder.exists()) folder.mkdirs();

            File xmlFile = new File(path, "settings.xml");
            if(!xmlFile.exists()) xmlFile.createNewFile();

            Serializer serializer = new Persister();

            serializer.write(this, xmlFile);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Settings deserialize() {
        try {
            String path = Environment.getExternalStorageDirectory().getPath() + "/m_cubed";
            File folder = new File(path);
            if(!folder.exists()) folder.mkdirs();

            File xmlFile = new File(path, "settings.xml");
            if(!xmlFile.exists()) xmlFile.createNewFile();

            Serializer serializer = new Persister();

            return serializer.read(Settings.class, xmlFile);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
