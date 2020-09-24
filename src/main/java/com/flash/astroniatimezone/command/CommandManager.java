package com.flash.astroniatimezone.command;

import com.flash.astroniatimezone.Main;
import com.flash.astroniatimezone.api.command.CommandAPI;
import com.flash.astroniatimezone.utils.Locale;
import lombok.Getter;

public class CommandManager {

    @Getter private CommandAPI commandApi;

    public CommandManager() {
        this.commandApi = new CommandAPI(Main.getInstance(), "Astronia", Locale.NO_PERMISSION.toString(), Locale.PLAYER_ONLY.toString(), Locale.COMMAND_NOT_FOUND.toString());
    }

    public void registerCommands() {
        registerCommands(new CaneTopCommand());
        registerCommands(new TimeCommand());
    }

    public void registerCommands(Object object) {
        commandApi.registerCommands(object);
    }

    public void unregisterCommands(Object object) {
        commandApi.unregisterCommands(object);
    }
}
