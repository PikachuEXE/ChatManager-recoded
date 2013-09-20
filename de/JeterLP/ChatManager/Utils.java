package de.JeterLP.ChatManager;

import de.JeterLP.ChatManager.Plugins.Factions;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author TheJeterLP
 */
public class Utils {

    protected static Pattern chatColorPattern = Pattern.compile("(?i)&([0-9A-F])");
    protected static Pattern chatMagicPattern = Pattern.compile("(?i)&([K])");
    protected static Pattern chatBoldPattern = Pattern.compile("(?i)&([L])");
    protected static Pattern chatStrikethroughPattern = Pattern.compile("(?i)&([M])");
    protected static Pattern chatUnderlinePattern = Pattern.compile("(?i)&([N])");
    protected static Pattern chatItalicPattern = Pattern.compile("(?i)&([O])");
    protected static Pattern chatResetPattern = Pattern.compile("(?i)&([R])");
    protected String permissionChatColor = "chatex.chat.color";
    protected String permissionChatMagic = "chatex.chat.magic";
    protected String permissionChatBold = "chatex.chat.bold";
    protected String permissionChatStrikethrough = "chatex.chat.strikethrough";
    protected String permissionChatUnderline = "chatex.chat.underline";
    protected String permissionChatItalic = "chatex.chat.italic";
    protected String permissionChatReset = "chatex.chat.reset";
    private ChatManager main;

    public Utils(ChatManager main) {
        this.main = main;
    }

    public String getColoredWorldName(String world) {
        world = world.replaceAll("&((?i)[0-9a-fk-or])", "§$1");
        return world;
    }

    public String translateColorCodes(String string, Player p) {
        if (string == null) {
            return "";
        }
        String newstring = string;
        if (p.hasPermission(this.permissionChatColor)) {
            newstring = chatColorPattern.matcher(newstring).replaceAll("\u00A7$1");
        }
        if (p.hasPermission(this.permissionChatMagic)) {
            newstring = chatMagicPattern.matcher(newstring).replaceAll("\u00A7$1");
        }
        if (p.hasPermission(this.permissionChatBold)) {
            newstring = chatBoldPattern.matcher(newstring).replaceAll("\u00A7$1");
        }
        if (p.hasPermission(this.permissionChatStrikethrough)) {
            newstring = chatStrikethroughPattern.matcher(newstring).replaceAll("\u00A7$1");
        }
        if (p.hasPermission(this.permissionChatUnderline)) {
            newstring = chatUnderlinePattern.matcher(newstring).replaceAll("\u00A7$1");
        }
        if (p.hasPermission(this.permissionChatItalic)) {
            newstring = chatItalicPattern.matcher(newstring).replaceAll("\u00A7$1");
        }
        if (p.hasPermission(this.permissionChatReset)) {
            newstring = chatResetPattern.matcher(newstring).replaceAll("\u00A7$1");
        }
        return newstring;
    }

    public String replaceColors(String message) {
        return message.replaceAll("&((?i)[0-9a-fk-or])", "§$1");
    }

    public List<Player> getLocalRecipients(Player sender, String message, double range) {
        Location playerLocation = sender.getLocation();
        List<Player> recipients = new LinkedList<Player>();
        double squaredDistance = Math.pow(range, 2);
        for (Player recipient : Bukkit.getServer().getOnlinePlayers()) {
            // Recipient are not from same world
            if (!recipient.getWorld().equals(sender.getWorld())) {
                continue;
            }

            if (playerLocation.distanceSquared(recipient.getLocation()) > squaredDistance && !sender.hasPermission("chatex.override.ranged")) {
                continue;
            }

            recipients.add(recipient);
        }
        return recipients;
    }

    protected void updateDisplayNames() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            updateDisplayName(player);
        }
    }

    public void updateDisplayName(Player player) {
        player.setDisplayName(this.replaceColors(this.replacePlayerPlaceholders(player, main.getListener().optionDisplayname)));
    }

    protected String replacePlayerPlaceholders(Player player, String format) {
        String worldName = player.getWorld().getName();
        String result = format;
        
        result = replaceMoney(result, player);
        if (main.getHook().checkFactions()) {
        	result = result.replace("%faction", this.replaceColors(Factions.getFactionName(player)));
        } else {
        	result = result.replace("%faction", "");
        }
        
        result = result.replace("%prefix", this.replaceColors(main.getPlugin().getPrefix(player, player.getWorld().getName())));
        result = result.replace("%suffix", this.replaceColors(main.getPlugin().getSuffix(player, player.getWorld().getName())));
        
        result = result.replace("%world", this.getColoredWorldName(worldName)).replace("%player", player.getDisplayName());
        result = result.replace("%group", this.replaceColors(main.getPlugin().getGroups(player, player.getWorld().getName())[0]));
		
        return format;
    }

    protected String replaceTime(String message) {
        Calendar calendar = Calendar.getInstance();

        if (message.contains("%h")) {
            final String hour = String.valueOf(calendar.get(Calendar.HOUR));
            message = message.replace("%h", hour);
        }

        if (message.contains("%H")) {
            final String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
            message = message.replace("%H", hour);
        }

        if (message.contains("%i")) {
            final String minute = String.valueOf(calendar.get(Calendar.MINUTE));
            message = message.replace("%i", minute);
        }

        if (message.contains("%s")) {
            final String second = String.valueOf(calendar.get(Calendar.SECOND));
            message = message.replace("%s", second);
        }

        if (message.contains("%a")) {
            message = message.replace("%a", (calendar.get(Calendar.AM_PM) == 0) ? "am" : "pm");
        }

        if (message.contains("%A")) {
            message = message.replace("%A", (calendar.get(Calendar.AM_PM) == 0) ? "AM" : "PM");
        }

        if (message.contains("%m")) {
            final String month = String.valueOf(calendar.get(Calendar.MONTH));
            message = message.replace("%m", month);
        }

        if (message.contains("%M")) {
            String month = "";
            final int monat = calendar.get(Calendar.MONTH) + 1;
            if (monat == 1) {
                month = "January";
            } else if (monat == 2) {
                month = "February";
            } else if (monat == 3) {
                month = "March";
            } else if (monat == 4) {
                month = "April";
            } else if (monat == 5) {
                month = "May";
            } else if (monat == 6) {
                month = "June";
            } else if (monat == 7) {
                month = "July";
            } else if (monat == 8) {
                month = "August";
            } else if (monat == 9) {
                month = "September";
            } else if (monat == 10) {
                month = "October";
            } else if (monat == 11) {
                month = "November";
            } else if (monat == 12) {
                month = "December";
            }
            message = message.replace("%M", month);
        }

        if (message.contains("%y")) {
            final String year = String.valueOf(calendar.get(Calendar.YEAR));
            message = message.replace("%m", year);
        }

        if (message.contains("%Y")) {
            int year = calendar.get(Calendar.YEAR);
            String year_new = String.valueOf(year);
            year_new = year_new.replace("19", "").replace("20", "");
            message = message.replace("%Y", year_new);
        }

        if (message.contains("%d")) {
            final String day = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
            message = message.replace("%d", day);
        }

        if (message.contains("%D")) {
            final String day = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
            message = message.replace("%D", day);
        }

        return message;
    }

    protected String replaceMoney(String message, Player player) {
        if (!main.getHook().checkVault()) {
            return message.replace("%mon", "");
        }

        final String balance = String.valueOf(main.getEconomy().getBalance(player.getName()));
        message = message.replace("%mon", balance);
        return message;
    }
}
