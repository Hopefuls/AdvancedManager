package me.hopedev.AdvancedManager.utils;

public class Tag {

    public final String tagName;
    public String imageAttachmentURL;
    public String tagContent;

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public final boolean delete() {
        return TagSystem.deleteTag(this);
    }

    public final void save() {
        TagSystem.updateOrCreateTag(this);
    }



}
