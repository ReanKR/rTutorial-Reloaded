package com.ReanKR.rTutorialReloaded.Listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
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
		if(rTutorialProgress.LocationProgress.containsKey(e.getPlayer().getName()))
		{
			SubSection.SubMsg("BlockCommandWhenTutorial", e.getPlayer(), false, true);
			e.setCancelled(true);
		}
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
	/*
	@EventHandler
	public void BlockingDura(PlayerItemDamageEvent e)
	{
		e.getItem().setDurability((short) (e.getItem().getDurability() - 4));
	}*/
	
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		FirstJoinPlayer(p);
		ConfigurationSection BackupSection = FileSection.LoadFile("Backup");
		if(BackupSection.contains(p.getName()))
		{
			SubSection.SubMsg("ContinueTutorial", p, false, true);
			SubSection.VariableSub(SubSection.SubMsg("ContinueCommand", p, true, true), "/rt continue");
			SubSection.VariableSub(SubSection.SubMsg("CancelCommand", p, true, true), "/rt cancel");
			rTutorialReloaded.isPlayerBackup.put(p, true);
		}
	}
	
	public void FirstJoinPlayer(Player p)
	{
		File file = new File("plugins/rTutorialReloaded/Player.yml");
		FileConfiguration PlayerFile = FileSection.LoadFile("Player");
		if(!PlayerFile.contains(p.getName()))
		{
			if(rTutorialReloaded.EditComplete && rTutorialReloaded.RunFirstJoinPlayer)
			{
				SubSection.Msg(p, SubSection.Sub((SubSection.SubMsg("FirstJoin", p, true, false)), p));
			}
			try
			{
				ConfigurationSection Section = PlayerFile.getConfigurationSection(p.getName());
				Section.set("UUID", p.getUniqueId().toString());
				Section.set("Finished", false);
				Section.set("Progress", "NONE");
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
				PlayerFile.createSection(p.getName());
				ConfigurationSection Section = PlayerFile.getConfigurationSection(p.getName());
				Section.set("UUID", p.getUniqueId().toString());
				Section.set("Finished", false);
				Section.set("Progress", "NONE");
			}
			try
			{
				PlayerFile.save(file);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
