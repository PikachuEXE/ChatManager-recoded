package de.JeterLP.ChatManager.Plugins;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import org.bukkit.entity.Player;

/**
 * @author TheJeterLP
 */
public class Factions {

    public static String getFactionName(Player player) {
        final UPlayer uplayer = UPlayer.get(player);
        final Faction faction = uplayer.getFaction();
        return faction.getName();

    }
}
