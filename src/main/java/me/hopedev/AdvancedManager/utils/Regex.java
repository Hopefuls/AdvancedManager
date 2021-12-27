package me.hopedev.AdvancedManager.utils;

public enum Regex {
    SPECIAL_CHARS_REGEX("/[^a-zA-Z0-9_]");

    public final String regex;

    Regex(String regex) {
        this.regex = regex;
    }
}
