package me.hopedev.AdvancedManager.moderation;

import me.hopedev.AdvancedManager.Main;
import me.hopedev.AdvancedManager.utils.Variables;
import org.javacord.api.entity.auditlog.AuditLog;
import org.javacord.api.entity.auditlog.AuditLogActionType;
import org.javacord.api.entity.auditlog.AuditLogEntry;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageDeleteEvent;
import org.javacord.api.listener.message.MessageDeleteListener;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.prefs.Preferences;

public class MessageDelete implements MessageDeleteListener {
    public static ArrayList<String> auditLogStorage = new ArrayList<>();

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        String[] blacklistedChannels = new String[]{
                "821080437413249114",
                "765098603357143040",
                "838171643465039914" };

        if (event.getChannel().asServerTextChannel().get().getName().startsWith("ticket-"))
            return;

        if (Arrays.stream(blacklistedChannels).anyMatch(s -> event.getChannel().getIdAsString().equals(s))) {
            return;
        }

        MessageAuthor author = event.getMessageAuthor().get();

        AuditLog auditLog = event.getServer().get().getAuditLog(1).join();

        User moderator = null;

        if (auditLog.getInvolvedUsers().contains(author.asUser().get())) {
            AuditLogEntry entry = auditLog.getEntries().get(0);

            if (entry.getType().equals(AuditLogActionType.MESSAGE_DELETE)) {
                auditLogStorage.add(entry.getIdAsString());
                moderator = entry.getUser().join();
            }


        }


        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.orange)
                .setTitle("Message deleted")
                .addInlineField("User", author.asUser().get().getMentionTag())
                .addInlineField("Channel", event.getChannel().asServerTextChannel().get().getMentionTag());

        if (moderator != null) {
            eb.addInlineField("Deleted by", moderator.getMentionTag());
        }
        eb.addField("Message", event.getMessageContent().get());




        Main.api.getServerTextChannelById("765098603357143040").get().sendMessage(eb);

    }
}
