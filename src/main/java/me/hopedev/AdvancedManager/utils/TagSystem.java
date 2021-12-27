package me.hopedev.AdvancedManager.utils;

import me.hopedev.AdvancedManager.utils.storage.StorageManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class TagSystem {

    public static HashMap<String /*tagName*/, Tag /*tagContent*/> tagStorage = new HashMap<>();


    public static Tag getTagByName(String tagName) {
        return tagStorage.getOrDefault(tagName.toLowerCase(), null);
    }

    public static boolean isExistingTag(String tagName) {
        return tagStorage.getOrDefault(tagName.toLowerCase(), null) != null;
    }

    public static void updateOrCreateTag(Tag tag) {

        // sqlite Stuff

        try {
            PreparedStatement statement;
            if (!tagStorage.containsKey(tag.tagName)) {

                statement = StorageManager.prepareStatement(StorageManager.ADD_ENTRY.sql);
                statement.setString(1, tag.tagName);
                statement.setString(2, tag.tagContent);
                statement.setString(3, tag.imageAttachmentURL);

            } else {
                statement = StorageManager.prepareStatement(StorageManager.UPDATE_ENTRY.sql);
                statement.setString(1, tag.tagContent);
                statement.setString(2, tag.imageAttachmentURL);
                statement.setString(3, tag.tagName);
            }
            StorageManager.threadUpdate(statement);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }


        tagStorage.put(tag.tagName.toLowerCase(), tag);
    }

    public static boolean deleteTag(Tag tag) {
        if (tagStorage.containsKey(tag.tagName.toLowerCase())) {

            try {
                PreparedStatement statement = StorageManager.prepareStatement(StorageManager.REMOVE_ENTRY.sql);
                statement.setString(1, tag.tagName);
                statement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
                return false;
            }
            tagStorage.remove(tag.tagName.toLowerCase());

            return true;
        } else {
            return false;
        }
    }

}
