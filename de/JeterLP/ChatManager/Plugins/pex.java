package de.JeterLP.ChatManager.Plugins;

import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * @author TheJeterLP
 */
public class pex implements PermissionsPlugin {

    @Override
    public String getPrefix(Player p, String world, boolean multiPrefixes, boolean PrependPlayerPrefix) {
        final PermissionUser user = PermissionsEx.getPermissionManager().getUser(p.getName());
        if (user == null) {
        	return "";
        }
        
        if (!multiPrefixes) {
        	return user.getPrefix(world);
        }
        
        String personalPrefix = user.getOwnPrefix();

        // If player has own prefix, and don't want to prepend
        if (!PrependPlayerPrefix && (personalPrefix != null && !personalPrefix.isEmpty())) {
            return personalPrefix;
        }

    	// PrependPlayerPrefix is true at this point
        String finalPrefix = "";
        if (personalPrefix != null && !personalPrefix.isEmpty()) {
        	finalPrefix = personalPrefix;
        }
        
        //adds in the user's groups first in order they are in
        PermissionGroup[] userGroups = user.getGroups();
    	for (PermissionGroup group : userGroups) {
    		String groupPrefix = group.getPrefix();
    			
    		if (groupPrefix == null || groupPrefix.isEmpty()) {
				continue;
    		}
    		
    		finalPrefix += groupPrefix;
		}

        //if there is no prefix so far, just sets it to "" to remove the %prefix and not add anything
        if (finalPrefix == null || finalPrefix.isEmpty()) {
            finalPrefix = "";
        }
        
        return finalPrefix;
    }

    @Override
    public String getSuffix(Player p, String world, boolean multiSuffixes, boolean PrependPlayerSuffix) {
    	final PermissionUser user = PermissionsEx.getPermissionManager().getUser(p.getName());
        if (user == null) {
        	return "";
        }
        
        if (!multiSuffixes) {
        	return user.getSuffix(world);
        }
        
        String personalSuffix = user.getOwnSuffix();

        // If player has own suffix, and don't want to prepend
        if (!PrependPlayerSuffix && (personalSuffix != null && !personalSuffix.isEmpty())) {
            return personalSuffix;
        }
        
        // PrependPlayerSuffix is true at this point
        String finalSuffix = "";
        if (personalSuffix != null && !personalSuffix.isEmpty()) {
        	finalSuffix = personalSuffix;
        }
        
        //adds in the user's groups first in order they are in
        PermissionGroup[] userGroups = user.getGroups();
        for (PermissionGroup group : userGroups) {
    		String groupSuffix = group.getSuffix();
    			
    		if (groupSuffix == null || groupSuffix.isEmpty()) {
				continue;
    		}
    		
    		finalSuffix += groupSuffix;
		}
        
        //if there is no prefix so far, just sets it to "" to remove the %prefix and not add anything
        if (finalSuffix == null || finalSuffix.isEmpty()) {
        	finalSuffix = "";
        }
        
        return finalSuffix;
    }

    @Override
    public String[] getGroupNames(Player p, String world) {
        final PermissionUser user = PermissionsEx.getPermissionManager().getUser(p.getName());
        if (user != null) {
            return user.getGroupsNames();
        } else {
            String[] data = {""};
            return data;
        }
    }
}
