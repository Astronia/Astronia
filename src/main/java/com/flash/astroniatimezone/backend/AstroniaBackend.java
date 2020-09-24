package com.flash.astroniatimezone.backend;

import com.flash.astroniatimezone.Main;
import lombok.Getter;
import lombok.Setter;

public abstract class AstroniaBackend implements IBackend {

    @Getter private final BackendType type;

    @Getter @Setter private boolean loaded;

    public AstroniaBackend(BackendType type) {
        this.type = type;
    }

    protected void logInfoMessage(String message) {
        Main.getInstance().getLogger().info("(Backend) {" + this.getType().getVerboseName() + "} - " + message);
    }

    protected void logException(String message, Exception e) {
        Main.getInstance().getLogger().severe("(Backend) {" + this.getType().getVerboseName() + "} - " + message);
        Main.getInstance().getLogger().severe("-------------------------------------------");
        e.printStackTrace();
        Main.getInstance().getLogger().severe("-------------------------------------------");
    }
}
