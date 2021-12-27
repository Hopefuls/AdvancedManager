package me.hopedev.AdvancedManager.commands;

import me.hopedev.AdvancedManager.commands.subcommands.TagsSubcommands;
import me.hopedev.AdvancedManager.utils.ConditionedUser;
import me.hopedev.AdvancedManager.utils.TagSystem;
import me.hopedev.commandhandler.Command;
import me.hopedev.commandhandler.CommandData;
import me.hopedev.commandhandler.CommandExecutor;
import me.hopedev.commandhandler.CommandMessage;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class TagsCommand implements CommandExecutor {
    @Override
    public void execute(CommandData data, ArrayList<Command> commands) {

        ConditionedUser condUser = new ConditionedUser(data.getUser(), data.getEvent().getServer().get());

        CommandMessage commandMessage = data.getCommandMessage();

        if (commandMessage.getArgs().size() == 0) {
            return;
        }

        TagsSubcommands subcommands = new TagsSubcommands(data);
        switch (commandMessage.getArg(0).toLowerCase()) {


            case "save":
            case "create":
                if (!condUser.isSupportUser()) {
                    data.respond("You are not authorized to use this command!");
                    break;
                }
                subcommands.executeTagCreationCommand();
                break;

            case "remove":
            case "delete":
                if (!condUser.isSupportUser()) {
                    data.respond("You are not authorized to use this command!");
                    break;
                }
                subcommands.executeTagDeletionCommand();
                break;

            case "edit":
                if (!condUser.isSupportUser()) {
                    data.respond("You are not authorized to use this command!");
                    break;
                }
                subcommands.executeTagEditingCommand();
                break;

            case "list":


                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.blue);

                if (TagSystem.tagStorage.keySet().size() == 0) {
                    eb.setDescription("No tags found");
                    data.respond(eb);
                    return;
                }

                StringBuilder sb = new StringBuilder();
                ArrayList<String> tags = new ArrayList<>(TagSystem.tagStorage.keySet());

                for (int i = 0; i < tags.size()-1; i++) {
                    sb.append(" `"+tags.get(i)+"` /");
                }

                sb.append(" `"+tags.get(tags.size()-1)+"`");
                eb.setDescription("Available Tags: \n\n"+sb.toString());
                data.respond(eb);
                break;
        }
    }
}
