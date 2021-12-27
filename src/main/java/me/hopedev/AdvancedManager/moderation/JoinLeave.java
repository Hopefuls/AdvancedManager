package me.hopedev.AdvancedManager.moderation;

import me.hopedev.AdvancedManager.Main;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.event.server.member.ServerMemberLeaveEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;

import java.awt.*;

public class JoinLeave implements ServerMemberJoinListener, ServerMemberLeaveListener {
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent event) {
        User user = event.getUser();
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.green)
                .setTitle("Member joined")
                .addInlineField("User", user.getName()+" | "+user.getMentionTag())
                .addInlineField("Discriminator", user.getDiscriminator())
                .addField("Account created", "<t:"+user.getCreationTimestamp().getEpochSecond()+":R>");

        Main.api.getServerTextChannelById("765098603357143040").get().sendMessage(eb);
    }

    @Override
    public void onServerMemberLeave(ServerMemberLeaveEvent event) {
        User user = event.getUser();
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.red)
                .setTitle("Member left")
                .addInlineField("User", user.getName()+" | "+user.getMentionTag())
                .addInlineField("Discriminator", user.getDiscriminator())
                .addField("Account created", "<t:"+user.getCreationTimestamp().getEpochSecond()+":R>");

        Main.api.getServerTextChannelById("765098603357143040").get().sendMessage(eb);
    }
}
