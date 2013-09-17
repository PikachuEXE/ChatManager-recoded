package de.JeterLP.ChatManager;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author t3hk0d3, TheJeterLP
 */
public class ChatListener implements Listener {

    protected static Pattern chatColorPattern = Pattern.compile("(?i)&([0-9A-F])");
    protected static Pattern chatMagicPattern = Pattern.compile("(?i)&([K])");
    protected static Pattern chatBoldPattern = Pattern.compile("(?i)&([L])");
    protected static Pattern chatStrikethroughPattern = Pattern.compile("(?i)&([M])");
    protected static Pattern chatUnderlinePattern = Pattern.compile("(?i)&([N])");
    protected static Pattern chatItalicPattern = Pattern.compile("(?i)&([O])");
    protected static Pattern chatResetPattern = Pattern.compile("(?i)&([R])");
    public final static String MESSAGE_FORMAT = "%prefix%player%suffix: %message";
    public final static String GLOBAL_MESSAGE_FORMAT = "%prefix%player%suffix: &e%message";
    public final static Boolean RANGED_MODE = false;
    public final static double CHAT_RANGE = 100d;
    protected String messageFormat = MESSAGE_FORMAT;
    protected String globalMessageFormat = GLOBAL_MESSAGE_FORMAT;
    protected boolean rangedMode = RANGED_MODE;
    protected double chatRange = CHAT_RANGE;
    protected String displayNameFormat = "%prefix%player%suffix";
    protected String optionChatRange = "chat-range";
    protected String optionMessageFormat = "message-format";
    protected String optionGlobalMessageFormat = "global-message-format";
    protected String optionRangedMode = "force-ranged-mode";
    protected String optionDisplayname = "display-name-format";
    protected String permissionChatColor = "chatmanager.chat.color";
    protected String permissionChatMagic = "chatmanager.chat.magic";
    protected String permissionChatBold = "chatmanager.chat.bold";
    protected String permissionChatStrikethrough = "chatmanager.chat.strikethrough";
    protected String permissionChatUnderline = "chatmanager.chat.underline";
    protected String permissionChatItalic = "chatmanager.chat.italic";
    private ChatManager main;

    public ChatListener(FileConfiguration config, ChatManager main) {
        this.messageFormat = config.getString("message-format", this.messageFormat);
        this.globalMessageFormat = config.getString("global-message-format", this.globalMessageFormat);
        this.rangedMode = config.getBoolean("ranged-mode", this.rangedMode);
        this.chatRange = config.getDouble("chat-range", this.chatRange);
        this.displayNameFormat = config.getString("display-name-format", this.displayNameFormat);
        this.main = main;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        String worldName = player.getWorld().getName();
        String message = this.messageFormat;
        boolean localChat = this.rangedMode;
        String chatMessage = event.getMessage();
        if (localChat = true) {
            if (chatMessage.startsWith("!") && player.hasPermission("chatmanager.chat.global")) {
                chatMessage = chatMessage.replaceFirst("!", "");
                message = this.globalMessageFormat;
            }
        }
        message = this.translateColorCodes(message);
        chatMessage = this.translateColorCodes(chatMessage);
        message = message.replace("%message", "%2$s").replace("%displayname", "%1$s");
        message = this.replacePlayerPlaceholders(player, message);
        message = this.replaceTime(message);

        event.setFormat(message);
        event.setMessage(chatMessage);

        if (localChat) {
            double range = this.chatRange;
            event.getRecipients().clear();
            event.getRecipients().addAll(this.getLocalRecipients(player, message, range));
        }
    }

    protected void updateDisplayNames() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            updateDisplayName(player);
        }
    }

    protected void updateDisplayName(Player player) {
        player.setDisplayName(this.translateColorCodes(this.replacePlayerPlaceholders(player, this.optionDisplayname)));
    }

    protected String replacePlayerPlaceholders(Player player, String format) {       
        String worldName = player.getWorld().getName();
        return format.replace("%prefix", this.translateColorCodes(main.getPlugin().getPrefix(player, player.getWorld().getName()))).replace("%suffix", this.translateColorCodes(main.getPlugin().getSuffix(player, player.getWorld().getName()))).replace("%world", this.getColoredWorldName(worldName)).replace("%player", player.getDisplayName()).replace("%group", main.getPlugin().getGroups(player, player.getWorld().getName())[0]);
    }

    protected List<Player> getLocalRecipients(Player sender, String message, double range) {
        Location playerLocation = sender.getLocation();
        List<Player> recipients = new LinkedList<Player>();
        double squaredDistance = Math.pow(range, 2);
        for (Player recipient : Bukkit.getServer().getOnlinePlayers()) {
            // Recipient are not from same world
            if (!recipient.getWorld().equals(sender.getWorld())) {
                continue;
            }

            if (playerLocation.distanceSquared(recipient.getLocation()) > squaredDistance && !sender.hasPermission("chatmanager.override.ranged")) {
                continue;
            }

            recipients.add(recipient);
        }
        return recipients;
    }

    protected String replaceTime(String message) {
        Calendar calendar = Calendar.getInstance();

        if (message.contains("%h")) {
            message = message.replace("%h", String.format("%02d", calendar.get(Calendar.HOUR)));
        }

        if (message.contains("%H")) {
            message = message.replace("%H", String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
        }

        if (message.contains("%g")) {
            message = message.replace("%g", Integer.toString(calendar.get(Calendar.HOUR)));
        }

        if (message.contains("%G")) {
            message = message.replace("%G", Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)));
        }

        if (message.contains("%i")) {
            message = message.replace("%i", String.format("%02d", calendar.get(Calendar.MINUTE)));
        }

        if (message.contains("%s")) {
            message = message.replace("%s", String.format("%02d", calendar.get(Calendar.SECOND)));
        }

        if (message.contains("%a")) {
            message = message.replace("%a", (calendar.get(Calendar.AM_PM) == 0) ? "am" : "pm");
        }

        if (message.contains("%A")) {
            message = message.replace("%A", (calendar.get(Calendar.AM_PM) == 0) ? "AM" : "PM");
        }

        return message;
    }

    protected String translateColorCodes(String string) {
        if (string == null) {
            return "";
        }
        String newstring = string;
        newstring = chatColorPattern.matcher(newstring).replaceAll("\u00A7$1");
        newstring = chatMagicPattern.matcher(newstring).replaceAll("\u00A7$1");
        newstring = chatBoldPattern.matcher(newstring).replaceAll("\u00A7$1");
        newstring = chatStrikethroughPattern.matcher(newstring).replaceAll("\u00A7$1");
        newstring = chatUnderlinePattern.matcher(newstring).replaceAll("\u00A7$1");
        newstring = chatItalicPattern.matcher(newstring).replaceAll("\u00A7$1");
        newstring = chatResetPattern.matcher(newstring).replaceAll("\u00A7$1");
        return newstring;
    }

    /**
     * Returns a colored world string 
     *
     * @param world The world to retrieve the string about.
     * @return A colored worldstring
     */
    private String getColoredWorldName(String world) {
        world = world.replaceAll("&((?i)[0-9a-fk-or])", "§$1");
        return world;
    }
}
