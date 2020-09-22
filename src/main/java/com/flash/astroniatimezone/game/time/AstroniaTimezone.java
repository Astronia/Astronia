package com.flash.astroniatimezone.game.time;

import lombok.Getter;
import org.apache.commons.lang.WordUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum AstroniaTimezone {

    ACT("ACT", "Australian Central Time", "ACT"),
    AET("AET", "Australian Eastern Time", "AET"),
    AGT("AGT", "Argentina Standard Time", "AGT"),
    ART("ART", "Arabic Standard Time", "Egypt Standard Time", "ART"),
    AST("AST", "Alaska Standard Time", "AST"),
    BET("BET", "Brazil Eastern Time", "BET"),
    BST("BST", "Bangladesh Standard Time", "BST"),
    CAT("CAT", "Central African Time", "CAT"),
    CET("CET", "Central European Time", "CET"),
    CNT("CNT", "Canada Newfoundland Time", "CNT"),
    CST("CST", "Central Standard Time", "Central", "CST"),
    CST6CDT("CST6CDT", "CDT", "Central Daylight Time", "Central Daylight", "CDT"),
    CTT("CTT", "China Taiwan Time", "CTT"),
    EAT("EAT", "Eastern African Time", "EAT"),
    ECT("ECT", "European Central Time", "ECT"),
    EET("EET", "Eastern European Time", "EET"),
    EST("EST", "Eastern Standard Time", "Eastern", "EST"),
    EST5EDT("EST5EDT", "EDT", "Eastern Daylight Time", "Eastern Daylight"),
    GMT("GMT", "Greenwich Mean Time", "Greenwich", "GMT"),
    HST("HST", "Hawaii Standard Time", "Hawaii", "HST"),
    IET("IET", "Indiana Eastern Standard Time", "Indiana", "IET"),
    IST("IST", "India Standard Time", "India", "IST"),
    JST("JST", "Japan Standard Time", "Japan", "JST"),
    MET("MET", "Middle East Time", "MET"),
    MIT("MIT","Midway Islands Time", "MIT"),
    MST("MST","Mountain Standard Time", "Mountain", "MST"),
    MST7MDT("MST7MDT", "Mountain Daylight Time", "MDT", "Mountain Daylight"),
    NET("NET", "Near East Time", "NET"),
    NST("NST", "New Zealand Standard Time", "New Zealand", "NST"),
    PLT("PLT", "Pakistan Lahore Time", "PLT"),
    PNT("PNT", "Phoenix Standard Time", "PNT"),
    PRC("PRC", "China Standard Time", "China", "PRC"),
    PRT("PRT", "Puerto Rico and US Virgin Islands Time", "Puerto Rico", "US Virgin Islands", "PRT"),
    PST("PST", "Pacific Standard Time", "Pacific", "PST"),
    PST8PDT("PST8PDT", "PDT", "Pacific Daylight Time"),
    ROK("ROK", "Korean Standard Time", "KST", "Korea", "ROK"),
    SST("SST", "Solomon Standard Time", "SST"),
    UTC("UTC", "Coordinated Universal Time", "UTC", "UCT"),
    VST("VST", "Vietnam Standard Time", "Vietnam", "VST");


    @Getter
    String id;
    @Getter
    List<String> names;
    
    AstroniaTimezone(String ID, String... timezoneNames) {
        this.id = ID;
        this.names = Arrays.asList(timezoneNames);
    }

    public static String getIDFromName(String name) {
        return getTimezoneByName(name).getId();
    }

    public static AstroniaTimezone getTimezoneByName(String name) {
        for (AstroniaTimezone zone : values()) {
            for (String names : zone.getNames()) {
                if (names.equalsIgnoreCase(name)) {
                    return zone;
                }
            }
        }
        return AstroniaTimezone.UTC;
    }
}
