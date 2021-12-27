package me.hopedev.AdvancedManager;

import com.vdurmont.emoji.EmojiParser;
import me.hopedev.AdvancedManager.utils.ConditionedUser;
import me.hopedev.AdvancedManager.utils.Tag;
import me.hopedev.AdvancedManager.utils.TagSystem;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class TagListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if (event.getMessageContent().startsWith("-")) {

            // ConditionedUser condUser = new ConditionedUser(event.getMessageAuthor().asUser().get(), event.getServer().get());

            /*
            quote: https://discord.com/channels/339110599084736522/372979487450857472/840681320584708117
            if (!condUser.isSupportUser()) {
                return;
            }

             */

            String requestedTag = event.getMessageContent().replaceFirst("-", "").toLowerCase().split(" ")[0];

            if (TagSystem.isExistingTag(requestedTag)) {
                Tag queriedTag = TagSystem.getTagByName(requestedTag);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.blue).setDescription(queriedTag.tagContent);

                if (queriedTag.imageAttachmentURL != null) {
                    eb.setImage(queriedTag.imageAttachmentURL);
                }

                eb.setFooter("React with "+EmojiParser.parseToUnicode(":wastebasket:")+" or delete your message to delete this.");

                Message message = event.getChannel().sendMessage(eb).join();
                StringBuilder sbMentionedUsers = new StringBuilder();

                if (event.getMessage().getReferencedMessage().isPresent()) {
                    event.getMessage().getReferencedMessage().get().getMentionedUsers().forEach(user -> sbMentionedUsers.append(user.getMentionTag()).append(" "));
                }

                event.getMessage().getMentionedUsers().forEach(user -> sbMentionedUsers.append(user.getMentionTag()).append(" "));


                message.addReaction(EmojiParser.parseToUnicode(":wastebasket:"));
                String authorID = event.getMessageAuthor().getIdAsString();

                // add listeners to call message
                // ReactionAdd
                message.addReactionAddListener(reactionEvent -> {
                   if (!reactionEvent.getUser().get().isYourself()) {
                       reactionEvent.removeReaction();
                       if (reactionEvent.getUser().get().getIdAsString().equals(authorID)) {
                           if (reactionEvent.getReaction().get().getEmoji().equalsEmoji(EmojiParser.parseToUnicode(":wastebasket:"))) {
                               message.delete();
                               // reactionEvent.getReaction().get().remove();
                               // event.getMessage().delete();
                               Emoji emoji = Main.api.getCustomEmojiById("821407763007012875").get();
                               event.getMessage().addReaction(emoji);
                           }
                       }
                   }
                });

                event.getMessage().addMessageDeleteListener(deleteEvent -> message.delete());




                if (event.getMessage().getMentionedUsers().size() != 0) {
                    message.edit(sbMentionedUsers.toString());

                }

            }


        }


    }
}
