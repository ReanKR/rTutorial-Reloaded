package com.ReanKR.rTutorialReloaded.Listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

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
	/*
	@EventHandler(priority = EventPriority.HIGHEST)
	public void WorldChange(PlayerTeleportEvent e)
	{
		Location GetTo = e.getTo();
		Location GetFrom = e.getFrom();
		if(! GetFrom.getWorld().getName().equalsIgnoreCase(GetTo.getWorld().getName()))
		{
			if(FileSection.PlayerSection(e.getPlayer()).getString("Progress").equalsIgnoreCase("WORKING"))
			{
			e.getPlayer().setWalkSpeed(0.0F);
			e.getPlayer().setFlySpeed(0.0F);
			e.getPlayer().setGameMode(GameMode.SPECTATOR);
			}
		}
	}*/

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		FirstJoinPlayer(p);
		if(rTutorialReloaded.isPlayerBackup.containsKey(p.getName()))
		{
			if(rTutorialReloaded.isPlayerBackup.get(p.getName()).booleanValue())
			{
				SubSection.Msg(p, SubSection.MessageString("ContinueTutorial"));
				SubSection.Msg(p, SubSection.VariableSub(SubSection.SubMsg("ContinueCommand", p, true, false), "/rt continue"));
				SubSection.Msg(p, SubSection.VariableSub(SubSection.SubMsg("CancelCommand", p, true, false), "/rt cancel"));
			}
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
