package com.ReanKR.rTutorialReloaded.File;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.ReanKR.rTutorialReloaded.rTutorialProgress;
import com.ReanKR.rTutorialReloaded.rTutorialReloaded;

public class BackupManager
{
	public static void SaveUnexpected(Player player)
	{
	    File file = new File("plugins/rTutorialReloaded/Backup.yml");
		FileConfiguration ConfigFile = FileSection.LoadFile("Backup");
		if(! ConfigFile.contains(player.getName())) ConfigFile.createSection(player.getName());
		ConfigurationSection PlayerSection = FileSection.PlusSelect(ConfigFile, player.getName());
		PlayerSection.set("UUID", player.getUniqueId().toString());
		PlayerSection.set("WalkSpeed", rTutorialProgress.PlayerSpeed.get(player.getName()));
		PlayerSection.set("FlySpeed", rTutorialProgress.PlayerFlySpeed.get(player.getName()));
		PlayerSection.set("Gamemode", rTutorialProgress.PlayerGameMode.get(player.getName()));
		if(rTutorialReloaded.ProgressingTutorial.get(player.getName()).equalsIgnoreCase("WORKING"))
		{
			PlayerSection.set("Progressing", rTutorialProgress.LocationProgress.get(player.getName()));
		}
		try
		{
			ConfigFile.save(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void SetPlayerProgress(Player p)
	{
		File file = new File("plugins/rTutorialReloaded/Player.yml");
		FileConfiguration PlayerFile = FileSection.LoadFile("Player");
		/* To do-generate section */
	}
}
