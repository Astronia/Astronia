package com.flash.astroniatimezone.backend;

import lombok.Getter;

public enum BackendType {

    REDIS("Redis"),
    MONGO("Mongo");

    @Getter private static BackendType fallback = BackendType.MONGO;

    @Getter private String verboseName;

    BackendType(String verboseName) {
        this.verboseName = verboseName;
    }

    public static BackendType getOrDefault(String match) {
        if(match == null)
            return getFallback();

        for(BackendType type : values()) {
            if(type.getVerboseName().toLowerCase().equals(match.toLowerCase()))
                return type;
        }

        return getFallback();
    }
}
