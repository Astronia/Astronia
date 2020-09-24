package com.flash.astroniatimezone.utils.player;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UUIDPair {

    private UUID key;
    private String value;

    public UUIDPair(UUID key, String value) {
        this.key = key;
        this.value = value;

        PlayerUtils.getPairs().put(value.toLowerCase(), this);
    }
}
