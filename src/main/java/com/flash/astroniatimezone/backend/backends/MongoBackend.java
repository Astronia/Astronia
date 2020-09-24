package com.flash.astroniatimezone.backend.backends;

import com.flash.astroniatimezone.Main;
import com.flash.astroniatimezone.backend.AstroniaBackend;
import com.flash.astroniatimezone.backend.BackendType;
import com.flash.astroniatimezone.backend.IBackend;
import com.flash.astroniatimezone.backend.creds.MongoCredentials;
import com.flash.astroniatimezone.game.player.AstroniaProfile;
import com.flash.astroniatimezone.utils.TaskUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class MongoBackend extends AstroniaBackend implements IBackend {

    private MongoClient mongo;
    private MongoDatabase db;
    private MongoCollection<Document> profiles;

    public MongoBackend(MongoCredentials credentials) {
        super(BackendType.MONGO);

        try {
            ServerAddress address = new ServerAddress(credentials.getHostname(), credentials.getPort());

            if(Main.getInstance().getConfig().getBoolean("backend.mongo.auth.enable")) {
                MongoCredential credential = MongoCredential.createCredential(credentials.getUsername(), credentials.getAuthDb(), credentials.getPassword().toCharArray());
                this.mongo = new MongoClient(address, Collections.singletonList(credential));
            } else {
                this.mongo = new MongoClient(address);
            }

            this.db = this.mongo.getDatabase(credentials.getDatabase());
            this.profiles = this.db.getCollection("profiles");

            this.logInfoMessage("Mongo Driver successfully loaded.");
            setLoaded(true);
        } catch(Exception e) {
            this.logException("Mongo Driver failed to load.", e);
        }
    }

    @Override
    public void close() {
        if (this.mongo != null)
            this.mongo.close();
    }

    /*=============================*/
    // Profiles

    @Override
    public void createProfile(AstroniaProfile profile) {
        TaskUtils.runAsync(() -> {
            this.profiles.insertOne(profile.toDocument());
        });
    }

    @Override
    public void deleteProfile(AstroniaProfile profile) {
        TaskUtils.runAsync(() -> {
            this.profiles.deleteOne(eq("uuid", profile.getUuid().toString()));
        });
    }

    @Override
    public void deleteProfiles() {
        TaskUtils.runAsync(() -> {
            this.profiles.drop();
            this.profiles = this.db.getCollection("profiles");
        });
    }

    @Override
    public void saveProfile(AstroniaProfile profile) {
        TaskUtils.runAsync(() -> {
            this.saveProfileSync(profile);
        });
    }

    @Override
    public void saveProfileSync(AstroniaProfile profile) {
        Document doc = profile.toDocument();
        this.profiles.findOneAndReplace(eq("uuid", profile.getUuid().toString()), doc);
    }

    @Override
    public void loadProfile(AstroniaProfile profile) {
        Document doc = this.profiles.find(eq("uuid", profile.getUuid().toString())).first();

        if(doc != null) {
            profile.load(doc);
        } else {
            this.createProfile(profile);
        }
    }

    @Override
    public void loadProfiles() {
        for(Document doc : this.profiles.find()) {
            if(!doc.containsKey("uuid"))
                continue;

            UUID uuid = UUID.fromString(doc.getString("uuid"));
            AstroniaProfile.getByUuid(uuid);
        }
    }

    /*=============================*/

    public Map<UUID, Integer> getTopTenSugarcane() {
        Map<UUID, Integer> sugarcaneValues = new HashMap<>();

        for (Document doc : this.profiles.find()) {
            if(!doc.containsKey("uuid"))
                continue;

            if(doc.get("statistics") != null) {
                Document statistics = (Document) doc.get("statistics");

                for(String statistic : statistics.keySet()) {
                    Integer value = (Integer) statistics.get(statistic);
                    sugarcaneValues.put(UUID.fromString(doc.getString("uuid")), value);
                }
            }

            if (sugarcaneValues.size() > 10)
                return sugarcaneValues;
        }


        return sugarcaneValues;
    }
}
