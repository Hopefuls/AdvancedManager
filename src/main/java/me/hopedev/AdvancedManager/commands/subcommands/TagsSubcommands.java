package me.hopedev.AdvancedManager.commands.subcommands;

import me.hopedev.AdvancedManager.utils.Regex;
import me.hopedev.AdvancedManager.utils.Tag;
import me.hopedev.AdvancedManager.utils.TagSystem;
import me.hopedev.AdvancedManager.utils.Variables;
import me.hopedev.commandhandler.CommandData;
import me.hopedev.commandhandler.CommandMessage;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class TagsSubcommands {

    private final CommandData commandData;


    public TagsSubcommands(CommandData commandData) {
        this.commandData = commandData;
    }


    public final void executeTagCreationCommand() {
        CommandMessage commandMessage = commandData.getCommandMessage();

        if (commandMessage.getArgs().size() < 2) {
            commandData.respond("Usage: -tags create <name> <content>");
            return;
        }
        ArrayList<String> filteredArgs = commandMessage.getArgs();
        filteredArgs.remove(0);


        String tagName = filteredArgs.get(0).toLowerCase(); // get first index of commandMessage | -tags create <>

        if (TagSystem.isExistingTag(tagName)) {
            commandData.respond("Tag with the name `"+tagName+"` already exists.");
            return;
        }

        ArrayList<String> forbiddenTagNames = new ArrayList<>();
        forbiddenTagNames.add("tag");
        forbiddenTagNames.add("tags");
        forbiddenTagNames.add("t");
        forbiddenTagNames.add("setlog");

        if (forbiddenTagNames.contains(tagName)) {
            commandData.respond("This tag name cannot be used!");
            return;
        }


        filteredArgs.remove(0);

        System.out.println(filteredArgs.size());

        if (filteredArgs.size() == 0) {
            commandData.respond("The tag cannot be empty.");
            return;
        }


        if (tagName.contains("`") || tagName.contains("*")) {
            commandData.respond("Tag name invalid");
            return;
        }



        String tagContent = StringUtils.join(filteredArgs, " ");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.green);
        eb.setDescription(Variables.TICK_YES.var+" Tag `"+tagName+"` created successfully!");

        Tag newTag = new Tag(tagName);

        newTag.tagContent = tagContent;
        newTag.imageAttachmentURL = commandData.getMessage().getAttachments().size() != 0
                ? commandData.getMessage().getAttachments().get(0).getUrl().toString()
                : null;

        newTag.save();

        commandData.respond(eb);


    }


    public final void executeTagDeletionCommand() {
        CommandMessage commandMessage = commandData.getCommandMessage();

        if (commandMessage.getArgs().size() == 1) {
            commandData.respond("Usage: -tags delete <name>");
            return;
        }
        String tagName = commandMessage.getArgs().get(1); // get first index of commandMessage | -tags delete <>

        if (!TagSystem.isExistingTag(tagName)) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.setDescription(Variables.TICK_NO.var + " A tag with the name `" + tagName + "` could not be found.");

            commandData.respond(eb);
            return;
        }

        Tag queriedTag = TagSystem.getTagByName(tagName);

        queriedTag.delete();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.green);
        eb.setDescription(Variables.TICK_YES.var+" Tag `"+tagName+"` was deleted!");

        commandData.respond(eb);


    }

    public final void executeTagEditingCommand() {
        CommandMessage commandMessage = commandData.getCommandMessage();

        if (commandMessage.getArgs().size() < 2) {
            commandData.respond("Usage: -tags edit <name> <new content>");
            return;
        }
        ArrayList<String> filteredArgs = commandMessage.getArgs();
        filteredArgs.remove(0);


        String tagName = filteredArgs.get(0).toLowerCase(); // get first index of commandMessage | -tags create <>

        if (!TagSystem.isExistingTag(tagName)) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.setDescription(Variables.TICK_NO.var+" A tag with the name `"+tagName+"` could not be found.");

            commandData.respond(eb);
            return;
        }

        filteredArgs.remove(0);

        System.out.println(filteredArgs.size());

        if (filteredArgs.size() == 0) {
            commandData.respond("The tag cannot be empty.");
            return;
        }

        String tagContent = StringUtils.join(filteredArgs, " ");

        Tag queriedTag = TagSystem.getTagByName(tagName);

        queriedTag.imageAttachmentURL = commandData.getMessage().getAttachments().size() != 0
                ? commandData.getMessage().getAttachments().get(0).getUrl().toString()
                : queriedTag.imageAttachmentURL;

        queriedTag.tagContent = tagContent;

        // save tag
        queriedTag.save();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.green);
        eb.setDescription(Variables.TICK_YES.var+" Tag `"+tagName+"` updated successfully!");

        commandData.respond(eb);


    }
}
