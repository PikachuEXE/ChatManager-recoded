package de.JeterLP.ChatManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author t3hk0d3, TheJeterLP
 */
public class ChatListener implements Listener {
    
    public final static String OPTION_MESSAGE_FORMAT = "message-format";
    public final static String OPTION_GLOBAL_MESSAGE_FORMAT = "global-message-format";
    public final static String OPTION_RANGED_MODE = "ranged-mode";
    public final static String OPTION_CHAT_RANGE = "chat-range";
    public final static String OPTION_MULTI_PREFIXES = "multi-prefixes";
    public final static String OPTION_MULTI_SUFFIXES = "multi-suffixes";
    public final static String OPTION_PREPEND_PLAYER_PREFIX = "prepend-player-prefix";
    public final static String OPTION_PREPEND_PLAYER_SUFFIX = "prepend-player-suffix";

    public final static String MESSAGE_FORMAT = "%prefix%player%suffix: %message";
    public final static String GLOBAL_MESSAGE_FORMAT = "&9[%world] %prefix%player%suffix: &e%message";
    public final static Boolean RANGED_MODE = false;
    public final static double CHAT_RANGE = 100d;
    public final static Boolean MULTI_PREFIXES = false;
    public final static Boolean MULTI_SUFFIXES = false;
    public final static Boolean PREPEND_PLAYER_PREFIX = false;
    public final static Boolean PREPEND_PLAYER_SUFFIX = false;
    
    protected String messageFormat = MESSAGE_FORMAT;
    protected String globalMessageFormat = GLOBAL_MESSAGE_FORMAT;
    protected boolean rangedMode = RANGED_MODE;
    protected double chatRange = CHAT_RANGE;
    protected boolean multiPrefixes = MULTI_PREFIXES;
    protected boolean multiSuffixes = MULTI_SUFFIXES;
    protected boolean prependPlayerPrefix = PREPEND_PLAYER_PREFIX;
    protected boolean prependPlayerSuffix = PREPEND_PLAYER_SUFFIX;
    
    private ChatManager main;
    
    private String chatMessage;

    public ChatListener(FileConfiguration config, ChatManager main) {
        this.messageFormat = config.getString(OPTION_MESSAGE_FORMAT, MESSAGE_FORMAT);
        this.globalMessageFormat = config.getString(OPTION_GLOBAL_MESSAGE_FORMAT, GLOBAL_MESSAGE_FORMAT);
        
        this.rangedMode = config.getBoolean(OPTION_RANGED_MODE, RANGED_MODE);
        this.chatRange = config.getDouble(OPTION_CHAT_RANGE, CHAT_RANGE);
        
        this.multiPrefixes = config.getBoolean(OPTION_MULTI_PREFIXES, MULTI_PREFIXES);
        this.multiSuffixes = config.getBoolean(OPTION_MULTI_SUFFIXES, MULTI_SUFFIXES);
        this.prependPlayerPrefix = config.getBoolean(OPTION_PREPEND_PLAYER_PREFIX, PREPEND_PLAYER_PREFIX);
        this.prependPlayerSuffix = config.getBoolean(OPTION_PREPEND_PLAYER_SUFFIX, PREPEND_PLAYER_SUFFIX);
        
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        boolean localChat = this.rangedMode;
        Player player = event.getPlayer();
        String messageFormat = this.messageFormat;
        chatMessage = event.getMessage();
        if (localChat == true) {
            if (chatMessage.startsWith("!") && player.hasPermission("chatex.chat.global")) {
                chatMessage = chatMessage.replaceFirst("!", "");
                messageFormat = globalMessageFormat;
            }
        }
        messageFormat = main.getUtils().replaceColors(messageFormat);
        messageFormat = messageFormat.replace("%message", "%2$s").replace("%displayname", "%1$s");
        messageFormat = main.getUtils().replacePlayerPlaceholders(player, messageFormat);
        messageFormat = main.getUtils().replaceTime(messageFormat);

        event.setFormat(messageFormat);

        if (localChat) {
            double range = this.chatRange;
            event.getRecipients().clear();
            event.getRecipients().addAll(main.getUtils().getLocalRecipients(player, messageFormat, range));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChatMessage(final AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();     
        chatMessage = main.getUtils().translateColorCodes(chatMessage, player);
        event.setMessage(chatMessage);
    }
    
    
    public String getMessageFormat() {
    	return this.messageFormat;
    }
    
    public boolean getMultiPrefixes() {
    	return this.multiPrefixes;
    }
    
    public boolean getMultiSuffixes() {
    	return this.multiSuffixes;
    }
    
    public boolean getPrependPlayerPrefix() {
    	return this.prependPlayerPrefix;
    }
    
    public boolean getPrependPlayerSuffix() {
    	return this.prependPlayerSuffix;
    }
}
