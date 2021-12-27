package me.hopedev.AdvancedManager;

import me.hopedev.AdvancedManager.commands.TagsCommand;
import me.hopedev.AdvancedManager.moderation.JoinLeave;
import me.hopedev.AdvancedManager.moderation.MembershipScreening;
import me.hopedev.AdvancedManager.moderation.MessageDelete;
import me.hopedev.AdvancedManager.utils.Variables;
import me.hopedev.AdvancedManager.utils.storage.CachingManager;
import me.hopedev.AdvancedManager.utils.storage.StorageManager;
import me.hopedev.commandhandler.CommandBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.permission.Role;

import java.util.prefs.Preferences;

public class Main {


    // API
    public static DiscordApi api;


    // Static stuff
    public static Role supportRole;
    public static Role devFriendRole;

    public static void main(String[] args) throws ClassNotFoundException {

        System.out.println("Starting..");
        if (args.length == 0) {
            System.err.println("No token given in gradle args, exiting..");
            System.exit(1);
        }


        System.out.println("Token present! Logging into discord..");
        api = new DiscordApiBuilder().setAllIntents().setToken(args[0]).login().join();
        System.out.println("Successfully logged in!");
        System.out.println("Adding commands..");

        CommandBuilder builder = new CommandBuilder("-", api)
                .addCommand("tags", new String[]{"t", "tag"}, new TagsCommand(), "1", "1")
                .addCommand("setlog", null, (data, commands) -> {

                    if (data.getEvent().getMessageAuthor().isServerAdmin()) {

                        if (data.getMessage().getMentionedChannels().get(0) == null) {
                            data.respond("Please mention a channel");
                            return;
                        }

                        ServerTextChannel channel = data.getMessage().getMentionedChannels().get(0);

                        Preferences preferences = Preferences.userNodeForPackage(Main.class).node("logchannel");

                        preferences.put("id", channel.getIdAsString());

                        data.respond("Log-Channel updated to "+channel.getMentionTag());
                    }

                }, "1", "1");

        builder.build();

        System.out.println("Done!");

        defineStatics();

        StorageManager.connect();
        CachingManager.setCache();

        api.addMessageDeleteListener(new MessageDelete());
        api.addListener(new JoinLeave());
        api.addListener(new MembershipScreening());
    }


    public static void defineStatics() {
        supportRole = api.getRoleById(Variables.SUPPORT_ROLE_ID.var).get();
        devFriendRole = api.getRoleById(Variables.DEV_FRIEND_ROLE_ID.var).get();

        // other
        api.addMessageCreateListener(new TagListener());

    }
}
