package com.flash.astroniatimezone.game.player;

import com.flash.astroniatimezone.Main;
import com.flash.astroniatimezone.game.player.data.AstroniaStatistic;
import com.flash.astroniatimezone.game.player.data.StatisticType;
import com.mongodb.BasicDBObject;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class AstroniaProfile {

    @Getter private static final Map<String, AstroniaProfile> profiles = new HashMap<>();

    @Getter private final UUID uuid;

    @Getter @Setter private String name;

    @Getter private List<AstroniaStatistic> statistics;

    public AstroniaProfile(UUID uuid, boolean cache) {
        this.uuid = uuid;

        this.statistics = new ArrayList<>();

        Main.getInstance().getBackend().loadProfile(this);

        if (cache)
            profiles.put(uuid.toString(), this);
    }

    public AstroniaProfile(UUID uuid) {
        this(uuid, true);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public AstroniaStatistic getStatistic(StatisticType type) {
        return statistics.stream()
                .filter(statistic -> statistic.getType() == type)
                .findFirst()
                .orElse(null);
    }

    public void updateStatistic(AstroniaStatistic statistic) {
        AstroniaStatistic found = getStatistic(statistic.getType());
        if(found == null)
            return;

        found.setValue(statistic.getValue());
    }

    public void reset() {
        this.statistics = new ArrayList<>();

        for(StatisticType type : StatisticType.values()) {
            if(this.getStatistic(type) != null)
                continue;

            AstroniaStatistic statistic = new AstroniaStatistic(type, 0);
            this.getStatistics().add(statistic);
        }
    }

    public void save() {
        Main.getInstance().getBackend().saveProfile(this);
    }

    public void load(Document doc) {
        if(doc.get("name") != null)
            this.setName(doc.getString("name"));

        if(doc.get("statistics") != null) {
            Document statistics = (Document) doc.get("statistics");

            for(String statistic : statistics.keySet()) {
                Integer value = (Integer) statistics.get(statistic);
                AstroniaStatistic statisticObj = new AstroniaStatistic(StatisticType.getByKey(statistic), value);
                this.getStatistics().add(statisticObj);
            }
        }

        for(StatisticType type : StatisticType.values()) {
            if(this.getStatistic(type) != null)
                continue;

            AstroniaStatistic statistic = new AstroniaStatistic(type, 0);
            this.getStatistics().add(statistic);
        }
    }

    public Document toDocument() {
        Document doc = new Document();
        BasicDBObject statistics = new BasicDBObject();

        for(AstroniaStatistic statistic : this.getStatistics())
            statistics.append(statistic.getType().getKey(), statistic.getValue());

        doc.append("uuid", this.getUuid().toString());
        doc.append("name", this.getName());
        doc.append("statistics", statistics);

        return doc;
    }

    public static AstroniaProfile getByUuid(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return new AstroniaProfile(uuid, true);
        }

        return getByPlayer(player);
    }

    public static AstroniaProfile getByUuid(UUID uuid, boolean cache) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return new AstroniaProfile(uuid, cache);
        }

        return getByPlayer(player);
    }

    public static AstroniaProfile getByPlayer(Player player) {
        if(profiles.containsKey(player.getUniqueId().toString()))
            return profiles.get(player.getUniqueId().toString());

        return new AstroniaProfile(player.getUniqueId(), true);
    }

    public static void instate() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            Bukkit.getLogger().info("Attempting to save all online profiles...");
            long start = System.currentTimeMillis();
            AstroniaProfile.getProfiles().values().forEach(Main.getInstance().getBackend()::saveProfile);

            long end = System.currentTimeMillis();
            Bukkit.getLogger().info("Saved " + AstroniaProfile.getProfiles().size() + " profile(s). Took " + (end - start) + "ms.");
        }, 200L, (20L * 60L) * 10L);
    }
}
