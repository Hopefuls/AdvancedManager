package me.hopedev.AdvancedManager.utils.storage;


import me.hopedev.AdvancedManager.utils.LatencyUtil;
import me.hopedev.AdvancedManager.utils.Tag;
import me.hopedev.AdvancedManager.utils.TagSystem;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CachingManager {

    // static cache
    private static HashMap<String, Tag> cache = new HashMap<>();


    public static HashMap<String, Tag> getCache() {
        return cache;
    }


    public static void setCache() {
        System.out.println("[CACHING] starting caching..");
        LatencyUtil latencyUtil = new LatencyUtil();
        try {
            latencyUtil.start();
            int cachedCount = 0;
            PreparedStatement statement = StorageManager.prepareStatement("SELECT * FROM tagData");
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                Tag tag = new Tag(set.getString("tagName"));

                tag.tagContent = set.getString("tagContent");
                tag.imageAttachmentURL = set.getString("imageAttachmentURL");


                TagSystem.tagStorage.put(set.getString("tagName"), tag);
                cachedCount++;
                System.out.println("[TAGS] Loading Tag "+set.getString("tagName"));
            }
            latencyUtil.end();
            System.out.println("[CACHING] caching finished! Took " + latencyUtil.toString());

        } catch (SQLException ecx) {
            ecx.printStackTrace();
            System.exit(3);
        }

    }

    public static void updateCache(String tagName, UpdateType type, Tag tag) {
        switch (type) {
            case ADD:
            case MODIFY:
                TagSystem.tagStorage.put(tagName, tag);
                break;
            case REMOVE:
                TagSystem.tagStorage.remove(tagName);
                break;
        }
    }

    public static void updateCache(String tagName, UpdateType type) {
        updateCache(tagName, type, null);
    }

    private static void updateDatabaseEntry(String tagName, UpdateType type, Tag tag) {


    }
    private static void updateDatabaseEntry(String tagName, UpdateType type) {


    }

}
