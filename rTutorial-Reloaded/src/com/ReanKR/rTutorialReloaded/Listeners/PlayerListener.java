package com.ReanKR.rTutorialReloaded.Listeners;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ReanKR.rTutorialReloaded.rTutorialProgress;
import com.ReanKR.rTutorialReloaded.rTutorialReloaded;
import com.ReanKR.rTutorialReloaded.File.FileSection;
import com.ReanKR.rTutorialReloaded.Util.SubSection;

public class PlayerListener implements Listener
{
	@EventHandler
	public void PlayerCommand(PlayerCommandPreprocessEvent e)
	{
		
	}
	
	@EventHandler
	public void PlayerChat(AsyncPlayerChatEvent e)
	{
		if(rTutorialProgress.LocationProgress.containsKey(e.getPlayer().getName()))
		{
			SubSection.SubMsg("BlockChatWhenTutorial", e.getPlayer(), false, true);
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		ConfigurationSection BackupSection = FileSection.LoadFile("Backup");
		if(BackupSection.contains(p.getName()))
		{
			SubSection.SubMsg("ContinueTutorial", p, false, true);
			SubSection.VariableSub(SubSection.SubMsg("ContinueCommand", p, true, true), "/rt continue");
			SubSection.VariableSub(SubSection.SubMsg("CancelCommand", p, true, true), "/rt cancel");
			rTutorialReloaded.isPlayerBackup.put(p, true);
		}
	}
}
