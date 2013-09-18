package de.JeterLP.ChatManager.Plugins;

import de.JeterLP.ChatManager.ChatManager;
import org.bukkit.entity.Player;

/**
 * @author TheJeterLP
 */
public class Vault implements PermissionsPlugin {

    @Override
    public String getPrefix(Player p, String world) {
        return ChatManager.getInstance().getChat().getPlayerPrefix(world, p.getName());
    }

    @Override
    public String getSuffix(Player p, String world) {
        return ChatManager.getInstance().getChat().getPlayerSuffix(world, p.getName());
    }

    @Override
    public String[] getGroups(Player p, String world) {
        return ChatManager.getInstance().getChat().getPlayerGroups(world, p.getName());
    }
}