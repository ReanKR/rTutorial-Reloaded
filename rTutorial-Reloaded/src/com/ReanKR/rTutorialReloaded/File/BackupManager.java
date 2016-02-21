package com.ReanKR.rTutorialReloaded.File;

import java.io.File;
import java.io.IOException;

import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.ReanKR.rTutorialReloaded.rTutorialProgress;
import com.ReanKR.rTutorialReloaded.rTutorialReloaded;
import com.ReanKR.rTutorialReloaded.Util.SubSection;

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
		PlayerSection.set("Gamemode", rTutorialProgress.PlayerGameMode.get(player.getName()).name());
		if(rTutorialReloaded.ProgressingTutorial.get(player.getName()).equalsIgnoreCase("WORKING"))
		{
			PlayerSection.set("Progressing", rTutorialProgress.LocationProgress.get(player.getName()));
		}
		else
		{
			
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
		try
		{
			ConfigurationSection Section = PlayerFile.getConfigurationSection(p.getName());
			Section.set("Progress", rTutorialReloaded.ProgressingTutorial.get(p.getName()));
		}
		catch(NullPointerException e)
		{
			PlayerFile.createSection(p.getName());
			ConfigurationSection Section = PlayerFile.getConfigurationSection(p.getName());
			Section.set("Progress", rTutorialReloaded.ProgressingTutorial.get(p.getName()));
		}
		if(rTutorialReloaded.ProgressingTutorial.get(p.getName()).equalsIgnoreCase("COMPLETE"))
		{
			ConfigurationSection Section = PlayerFile.getConfigurationSection(p.getName());
			Section.set("Finished", true);
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
	
	public static void RestorePlayer(Player p)
	{
		File file = new File("plugins/rTutorialReloaded/Backup.yml");
		File Pfile = new File("plugins/rTutorialReloaded/Player.yml");
		YamlConfiguration BackupFile = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection PlayerFile = BackupFile.getConfigurationSection(p.getName());
		float WalkSpeed = Float.parseFloat(PlayerFile.get("WalkSpeed").toString());
		float FlySpeed = Float.parseFloat(PlayerFile.get("FlySpeed").toString());
		String GM = PlayerFile.get("Gamemode").toString();
		if(GM.equalsIgnoreCase("SURVIVAL")) p.setGameMode(GameMode.SURVIVAL);
		else if(GM.equalsIgnoreCase("CREATIVE")) p.setGameMode(GameMode.CREATIVE);
		else if(GM.equalsIgnoreCase("SPECTATOR")) p.setGameMode(GameMode.SPECTATOR);
		else p.setGameMode(GameMode.SURVIVAL);
		p.setWalkSpeed(WalkSpeed);
		p.setFlySpeed(FlySpeed);
		BackupFile.set(p.getName(), null);
		try
		{
			BackupFile.save(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		YamlConfiguration PlayerYaml = YamlConfiguration.loadConfiguration(Pfile);
		SubSection.Msg(p, "진행하던 튜토리얼을 취소합니다. 다시 하고 싶다면 /rt start를 입력하시길 바랍니다.");
		ConfigurationSection PlayerSection = PlayerYaml.getConfigurationSection(p.getName());
		PlayerSection.set("Progress", "Complete");
		try
		{
			PlayerYaml.save(Pfile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
