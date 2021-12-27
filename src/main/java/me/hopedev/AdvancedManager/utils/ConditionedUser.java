package me.hopedev.AdvancedManager.utils;

import me.hopedev.AdvancedManager.Main;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class ConditionedUser {

    private final User user;
    private final Server server;

    public ConditionedUser(User user, Server server) {
        this.user = user;
        this.server = server;
    }

    public final boolean isSupportUser() {
        return user.getRoles(server).contains(Main.supportRole) || user.getRoles(server).contains(Main.devFriendRole);
    }
}
