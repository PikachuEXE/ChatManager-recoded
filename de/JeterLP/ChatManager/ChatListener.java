package de.JeterLP.ChatManager;

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

    public final static String MESSAGE_FORMAT = "%prefix%player%suffix: %message";
    public final static String GLOBAL_MESSAGE_FORMAT = "&9[%world] %prefix%player%suffix: &e%message";
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
        String message = this.messageFormat;
        boolean localChat = this.rangedMode;
        String chatMessage = event.getMessage();
        if (localChat = true) {
            if (chatMessage.startsWith("!") && player.hasPermission("chatmanager.chat.global")) {
                chatMessage = chatMessage.replaceFirst("!", "");
                message = this.globalMessageFormat;
            }
        }
        message = main.getUtils().translateColorCodes(message);
        chatMessage = main.getUtils().translateColorCodes(chatMessage);
        message = message.replace("%message", "%2$s").replace("%displayname", "%1$s");
        message = main.getUtils().replacePlayerPlaceholders(player, message);
        message = main.getUtils().replaceTime(message);

        event.setFormat(message);
        event.setMessage(chatMessage);
        if (localChat) {
            double range = this.chatRange;
            event.getRecipients().clear();
            event.getRecipients().addAll(main.getUtils().getLocalRecipients(player, message, range));
        }
    }
}
