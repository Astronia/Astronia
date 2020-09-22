package com.flash.astroniatimezone.game.time;

import lombok.Getter;
import org.apache.commons.lang.WordUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum AstroniaTimezone {

    ACT("ACT", "australian central time", "act"),
    AET("AET", "australian eastern time", "aet"),
    AGT("AGT", "argentina standard aime", "agt"),
    ART("ART", "arabic standard time", "egypt standard time", "art"),
    AST("AST", "alaska standard time", "ast"),
    BET("BET", "brazil eastern time", "bet"),
    BST("BST", "bangladesh standard time", "bst"),
    CAT("CAT", "central african time", "cat"),
    CET("CET", "central european time", "cet"),
    CNT("CNT", "canada newfoundland time", "cnt"),
    CST("CST", "central standard time", "central", "cst"),
    CST6CDT("CST6CDT", "cdt", "central daylight time", "central daylight", "cdt"),
    CTT("CTT", "china taiwan time", "ctt"),
    EAT("EAT", "eastern african time", "eat"),
    ECT("ECT", "european central time", "ect"),
    EET("EET", "eastern european time", "eet"),
    EST("EST", "eastern standard time", "eastern", "est"),
    EST5EDT("EST5EDT", "edt", "eastern daylight time", "eastern daylight"),
    GMT("GMT", "greenwich mean time", "greenwich", "gmt"),
    HST("HST", "hawaii standard time", "hawaii", "hst"),
    IET("IET", "indiana eastern standard time", "indiana", "iet"),
    IST("IST", "india standard time", "india", "ist"),
    JST("JST", "japan standard time", "japan", "jst"),
    MET("MET", "middle east time", "met"),
    MIT("MIT","midway islands time", "mit"),
    MST("MST","mountain standard time", "mountain", "mst"),
    MST7MDT("MST7MDT", "mountain daylight time", "mdt", "mountain daylight"),
    NET("NET", "near east time", "net"),
    NST("NST", "new zealand standard time", "new zealand", "nst"),
    PLT("PLT", "pakistan lahore time", "plt"),
    PNT("PNT", "phoenix standard time", "pnt"),
    PRC("PRC", "china standard time", "china", "prc"),
    PRT("PRT", "puerto rico and us virgin islands time", "puerto rico", "us virgin islands", "prt"),
    PST("PST", "pacific standard time", "pacific", "pst"),
    PST8PDT("PST8PDT", "pdt", "pacific daylight time"),
    ROK("ROK", "korean standard time", "kst", "korea", "rok"),
    SST("SST", "solomon standard time", "sst"),
    UTC("UTC", "coordinated universal time", "utc", "uct"),
    VST("VST", "vietnam standard time", "vietnam", "vst");


    @Getter
    String id;
    @Getter
    List<String> names;
    
    AstroniaTimezone(String ID, String... timezoneNames) {
        this.id = ID;
        this.names = Arrays.asList(timezoneNames);
    }

    public static String getIDFromName(String name) {
        return gettimezoneByName(name).getId();
    }

    public static AstroniaTimezone gettimezoneByName(String name) {
       return Arrays.stream(values())
                .filter((zone -> zone.getNames().contains(name.toLowerCase())))
                .findFirst()
                .orElse(AstroniaTimezone.UTC);
    }

    /*
        for (Astroniatimezone zone : values()) {
            for (String names : zone.getNames()) {
                if (names.equalsIgnoreCase(name)) {
                    return zone;
                }
            }
        }
        return Astroniatimezone.UTC;*/
}
