package com.flash.astroniatimezone.game.player.data;

import lombok.Getter;
import lombok.Setter;

public class AstroniaStatistic {

    @Getter private final StatisticType type;
    @Getter @Setter private int value;

    public AstroniaStatistic(StatisticType type, int value) {
        this.type = type;
        this.value = value;
    }

    public AstroniaStatistic Increment() {
        this.value++;
        return this;
    }

    public AstroniaStatistic reduce() {
        this.value--;
        return this;
    }
}
