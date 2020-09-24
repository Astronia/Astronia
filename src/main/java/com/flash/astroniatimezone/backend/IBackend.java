package com.flash.astroniatimezone.backend;

import com.flash.astroniatimezone.game.player.AstroniaProfile;

import java.util.Map;
import java.util.UUID;

public interface IBackend {

    /**
     * Called when the plugin is being disabled.
     */
    void close();

    // Profile //
    void createProfile(AstroniaProfile profile);

    void deleteProfile(AstroniaProfile profile);

    void deleteProfiles();

    void saveProfile(AstroniaProfile profile);

    void saveProfileSync(AstroniaProfile profile);

    void loadProfile(AstroniaProfile profile);

    void loadProfiles();
    // End Profile //

    public Map<UUID, Integer> getTopTenSugarcane();
}
