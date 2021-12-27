package me.hopedev.AdvancedManager.moderation;

import me.hopedev.AdvancedManager.Main;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.user.UserChangePendingEvent;
import org.javacord.api.listener.user.UserChangePendingListener;

import java.awt.*;

public class MembershipScreening implements UserChangePendingListener {
    @Override
    public void onServerMemberChangePending(UserChangePendingEvent event) {
        User user = event.getUser();
        if (!event.getNewPending()) {
            EmbedBuilder eb = new EmbedBuilder()
                    .setColor(Color.blue)
                    .setTitle("Member accepted Rules")
                    .setDescription("Member passed Membership Screening")
                    .addInlineField("User", user.getName()+" | "+user.getMentionTag())
                    .addInlineField("Discriminator", user.getDiscriminator())
                    .addField("Account created", "<t:"+user.getCreationTimestamp().getEpochSecond()+":R>");



            Main.api.getServerTextChannelById("765098603357143040").get().sendMessage(eb);
        }
    }
}
