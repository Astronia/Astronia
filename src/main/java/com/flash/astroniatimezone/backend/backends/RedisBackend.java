package com.flash.astroniatimezone.backend.backends;

import com.flash.astroniatimezone.backend.AstroniaBackend;
import com.flash.astroniatimezone.backend.BackendType;
import com.flash.astroniatimezone.backend.creds.RedisCredentials;
import com.flash.astroniatimezone.game.player.AstroniaProfile;
import com.flash.astroniatimezone.utils.TaskUtils;
import lombok.Getter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.bson.Document;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RedisBackend extends AstroniaBackend {

    @Getter private JedisPool pool;

    public RedisBackend(RedisCredentials credentials) {
        super(BackendType.REDIS);

        if(!credentials.password()) {
            this.pool = new JedisPool(new GenericObjectPoolConfig(), credentials.getHost(), credentials.getPort(), 3000);
        } else {
            this.pool = new JedisPool(new GenericObjectPoolConfig(), credentials.getHost(), credentials.getPort(), 3000, credentials.getPassword());
        }

        try(Jedis jedis = pool.getResource()) {
            setLoaded(jedis.isConnected());
            if(isLoaded())
                logInfoMessage("Redis Driver successfully loaded.");
            else
                throw new Exception("Unable to establish Jedis connection.");
        } catch(Exception ex) {
            logException("Redis Driver failed to load.", ex);
        }
    }

    @Override
    public void close() {
        if(this.pool != null)
            if(!this.pool.isClosed())
                this.pool.close();
    }

    /*=============================*/
    // Profiles

    @Override
    public void createProfile(AstroniaProfile profile) {
        this.saveProfile(profile);
    }

    @Override
    public void deleteProfile(AstroniaProfile profile) {
        try(Jedis jedis = this.getPool().getResource()) {
            jedis.del(this.getKey(KeyType.PROFILE, profile.getUuid().toString()));
        }
    }

    @Override
    public void deleteProfiles() {
        TaskUtils.runAsync(() -> {
            try(Jedis jedis = this.getPool().getResource()) {
                jedis.del(this.getKey(KeyType.PROFILE) + "*");
            }
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
        try(Jedis jedis = this.getPool().getResource()) {
            jedis.set(this.getKey(KeyType.PROFILE, profile.getUuid().toString()), profile.toDocument().toJson());
        }
    }

    @Override
    public void loadProfile(AstroniaProfile profile) {
        try(Jedis jedis = this.getPool().getResource()) {
            String json = jedis.get(this.getKey(KeyType.PROFILE, profile.getUuid().toString()));

            if(json != null) {
                Document doc = Document.parse(json);
                profile.load(doc);
            } else {
                this.createProfile(profile);
            }
        }
    }

    @Override
    public void loadProfiles() {
        try(Jedis jedis = this.getPool().getResource()) {
            Set<String> profiles = jedis.keys(this.getKey(KeyType.PROFILE) + "*");

            profiles.forEach(profile -> {
                Document doc = Document.parse(jedis.get(profile));
                if(doc == null || !doc.containsKey("uuid"))
                    return;

                UUID uuid = UUID.fromString(doc.getString("uuid"));
                AstroniaProfile.getByUuid(uuid);
            });
        }
    }

    @Override
    public Map<UUID, Integer> getTopTenSugarcane() {
        return null;
    }

    private String getKey(KeyType type) {
        return "astronia:" + type.getPrefix() + ":";
    }

    private String getKey(KeyType type, String identifier) {
        return getKey(type) + identifier;
    }

    /*=============================*/

    private enum KeyType {
        PROFILE("profile");

        @Getter private String prefix;

        KeyType(String prefix) {
            this.prefix = prefix;
        }
    }
}
