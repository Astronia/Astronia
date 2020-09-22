package com.flash.astroniatimezone.command;

import com.flash.astroniatimezone.api.chat.C;
import com.flash.astroniatimezone.api.command.Command;
import com.flash.astroniatimezone.api.command.CommandData;
import com.flash.astroniatimezone.game.time.AstroniaTimezone;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeCommand {

    @Command(label = "time", aliases = {"timezone", "tzone"}, permission = "astronia.timezone", playerOnly = true)
    public void onCommand(CommandData data) {
        Player player = (Player) data.getSender();

        if (data.getArgs().length == 0) {
            player.sendMessage(C.color(getTime()));
            return;
        }

        StringBuilder timezoneName = new StringBuilder();
        for (int i = 0; i < data.getArgs().length; i++) {
            if (i == data.getArgs().length - 1) {
                timezoneName.append(data.getArg(i));
            } else {
                timezoneName.append(data.getArg(i)).append(" ");
            }
        }

        String zone = timezoneName.toString();

        if (getTime(zone) == "" || AstroniaTimezone.getTimezoneByName(zone) == null) {
            player.sendMessage(C.color("&cPlease enter a valid Timezone"));
            return;
        }

        player.sendMessage(C.color(getTime(zone)));
    }

    private String getTime() {
        return getTime("UTC");
    }

    private String getTime(String string) {
        Calendar calendar = Calendar.getInstance();
        TimeZone fromTimeZone = calendar.getTimeZone();

        AstroniaTimezone timezone = AstroniaTimezone.getTimezoneByName(string);

        if (timezone == null)
            timezone = AstroniaTimezone.UTC;

        TimeZone toTimeZone = TimeZone.getTimeZone(timezone.getId());

        if (toTimeZone == null) {
            return "";
        }

        calendar.setTimeZone(fromTimeZone);
        calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
        if (fromTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
        }

        calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
        if (toTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
        }



        String greeting = getGreeting(calendar.getTime().getHours());

         return "&d&lHeya! " + greeting + " &d&lThe time in " + toTimeZone.getDisplayName() + " is " + calendar.getTime().getHours() + ":" + (calendar.getTime().getMinutes() < 10 ? "0" + calendar.getTime().getMinutes() : calendar.getTime().getMinutes()) + ":" + (calendar.getTime().getSeconds() < 10 ? "0" + calendar.getTime().getSeconds() : calendar.getTime().getSeconds());
    }

    private String getGreeting(int hours) {
        if (hours > 5 && hours < 11) {
            return "&a&lGood Morning!";
        } else if (hours > 11 && hours < 17) {
            return "&e&lGood Afternoon!";
        } else if (hours > 17 && hours < 20) {
            return "&c&lGood Evening!";
        } else {
            return "&9&lGood Night!";
        }
    }
}
