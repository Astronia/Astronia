package com.flash.astroniatimezone.command;

import com.flash.astroniatimezone.Main;
import com.flash.astroniatimezone.api.command.CommandAPI;
import lombok.Getter;

public class CommandManager {

    @Getter private CommandAPI commandApi;

    public CommandManager() {
        this.commandApi = new CommandAPI(Main.getInstance(), "Astronia", "You do not have permission to use that command", "That command is for players only", "That command could not be found");
    }

    public void registerCommands() {
        registerCommands(new TimeCommand());
    }

    public void registerCommands(Object object) {
        commandApi.registerCommands(object);
    }

    public void unregisterCommands(Object object) {
        commandApi.unregisterCommands(object);
    }
}
