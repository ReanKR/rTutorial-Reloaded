package com.ReanKR.rTutorialReloaded;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ReanKR.rTutorialReloaded.rTutorialReloaded;
import com.ReanKR.rTutorialReloaded.API.TitleAPI;
import com.ReanKR.rTutorialReloaded.File.BackupManager;
import com.ReanKR.rTutorialReloaded.File.FileSection;
import com.ReanKR.rTutorialReloaded.Util.SoundCreation;
import com.ReanKR.rTutorialReloaded.Util.SubSection;

import me.confuser.barapi.BarAPI;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class rTutorialProgress
{
	DecimalFormat format = new DecimalFormat("0.#");
	public static Map<String, Integer> taskID;
	public static Map<String, Float> Progress;
	public static Map<String, String> LocationProgress;
	public static Map<String, GameMode> PlayerGameMode;
	public static Map<String, Float> PlayerSpeed;
	public static Map<String, Float> PlayerFlySpeed;
	public static Map<String, List<Player>> ListHidePlayer;
	public static Map<String, Location> LastLocation;
	private SoundCreation SC = new SoundCreation();
	
	@SuppressWarnings("deprecation")
	public void TutorialCooldown(final Player p)
	{
		final int tid = rTutorialReloaded.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(rTutorialReloaded.plugin, new Runnable()
		{
			@Override
			public void run()
			{
				if(! rTutorialReloaded.plugin.getServer().getOfflinePlayer(p.getName()).isOnline()) // if player leaved server when tutorial progressing
				{
					if(rTutorialReloaded.CompatiblePlugins[0]) BarAPI.removeBar(p);
					HidePlayer(p, false); 
					endTask(p, false);
					return;
				}
				
				if(Progress.get(p.getName()) <= 0)
				{
					if(rTutorialReloaded.isPlayerBackup.containsKey(p.getName()))
					{
						if(rTutorialReloaded.isPlayerBackup.get(p.getName()).booleanValue())
						{
							float WalkSpeed = Float.parseFloat(FileSection.PlusSelect(FileSection.LoadFile("Backup"), p.getName()).get("WalkSpeed").toString());
							float FlySpeed = Float.parseFloat(FileSection.PlusSelect(FileSection.LoadFile("Backup"), p.getName()).get("FlySpeed").toString());
							GameMode GM = GameMode.valueOf(FileSection.PlusSelect(FileSection.LoadFile("Backup"), p.getName()).get("Gamemode").toString());
							PlayerGameMode.put(p.getName(), GM);
							PlayerSpeed.put(p.getName(), WalkSpeed);
							PlayerFlySpeed.put(p.getName(), FlySpeed);
						}
					}
					else
					{
						PlayerGameMode.put(p.getName(), p.getGameMode());
						PlayerSpeed.put(p.getName(), p.getWalkSpeed());
						PlayerFlySpeed.put(p.getName(), p.getFlySpeed());
					}
					LastLocation.put(p.getName(), p.getLocation());
					HidePlayer(p, true);
					endTask(p, false);
					ProgressingTutorial(p);
					return;
				}

				else
				{
					if(! rTutorialReloaded.SoundDisabled) SC.PlayerSound(p, Sound.NOTE_PLING, 10.0F, 1.0F);
					
					if(rTutorialReloaded.CompatiblePlugins[0])
					{
						BarAPI.setMessage(p, "rTutorialReloaded v1.0.0" , (Progress.get(p.getName()) / rTutorialReloaded.DefaultCooldownSeconds) * 100);
					}
					
					if(rTutorialReloaded.CompatiblePlugins[1])
					{
						if(Progress.get(p.getName()) >= 2) TitleAPI.sendTitle(p, 0, 0, 30, SubSection.VariableSub(SubSection.SubMsg("LeftSeconds", p, true, false),
								Progress.get(p.getName()).intValue()), "rTutorial " + rTutorialReloaded.plugin.getDescription().getVersion());
						else TitleAPI.sendTitle(p, 0, 0, 30, SubSection.VariableSub(SubSection.SubMsg("LeftSeconds", p, true, false),
								Progress.get(p.getName()).intValue()), "Let's go tutorial");
					}
					else
					{
						p.sendMessage(rTutorialReloaded.Prefix + SubSection.VariableSub(SubSection.SubMsg("LeftSeconds", p, true, false), Progress.get(p.getName()).intValue()));
					}
					Progress.put(p.getName(), Progress.get(p.getName()) - 1);
				}
			}

		},0L, 20L);
		taskID.put(p.getName(), tid);
		Progress.put(p.getName(), (float)rTutorialReloaded.DefaultCooldownSeconds);
		rTutorialReloaded.ProgressingTutorial.put(p.getName(), "COOLDOWN");
		BackupManager.SetPlayerProgress(p);
	}
	
	@SuppressWarnings("deprecation")
	public void ProgressingTutorial(final Player p)
	{
		ConfigurationSection Section = FileSection.LoadFile("Location").getConfigurationSection("Locations");
		Object[] LocationName = Section.getKeys(false).toArray();
		final int tid = rTutorialReloaded.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(rTutorialReloaded.plugin, new Runnable()
		{
			@Override
			public void run()
			{
				p.setWalkSpeed(0.0F);
				p.setFlySpeed(0.0F);
				p.setGameMode(GameMode.SPECTATOR);
				if(! rTutorialReloaded.plugin.getServer().getOfflinePlayer(p.getName()).isOnline()) // if player leaved server when tutorial progressing
				{
					if(rTutorialReloaded.CompatiblePlugins[0]) BarAPI.removeBar(p);
					
					BackupManager.SaveUnexpected(p);
					HidePlayer(p, false);
					endTask(p, false);
					return;
				}
				
				if(Progress.get(p.getName()).intValue() >= rTutorialReloaded.MethodAmount)
				{
					if(rTutorialReloaded.CompatiblePlugins[0]) BarAPI.removeBar(p);
					endTask(p, true);
					Result(p);
					return;
				}
				String[] Cut = rTutorialReloaded.MessageMethod.get(LocationName[Progress.get(p.getName()).intValue()]).split(",");
				if(! rTutorialReloaded.SoundDisabled) SC.PlayerSound(p, Sound.LEVEL_UP, 10.0F, 1.0F);
				p.teleport(rTutorialReloaded.InfoLocation.get(LocationName[Progress.get(p.getName()).intValue()]));
				if(rTutorialReloaded.CompatiblePlugins[0])
				{
					BarAPI.setMessage(p, SubSection.VariableSub(SubSection.SubMsg("BarAPIPercent", p, true, false), format.format((((Progress.get(p.getName()) + 1.0F) / rTutorialReloaded.MethodAmount)) * 100)) , (Progress.get(p.getName()) / rTutorialReloaded.MethodAmount) * 100);
				}
				if(rTutorialReloaded.CompatiblePlugins[1]) TitleAPI.sendTitle(p, rTutorialReloaded.DefaultDelaySeconds / 6,rTutorialReloaded.DefaultDelaySeconds / 6, rTutorialReloaded.DefaultDelaySeconds*20, SubSection.RepColor(Cut[0]), SubSection.RepColor(Cut[1]));
				else p.sendMessage(SubSection.RepColor(Cut[0]));
				LocationProgress.put(p.getName(), LocationName[Progress.get(p.getName()).intValue()].toString());
				Progress.put(p.getName(), Progress.get(p.getName()) + 1.0F);
			}
		}, 0L, rTutorialReloaded.DefaultDelaySeconds*20L);
		if(rTutorialReloaded.isPlayerBackup.containsKey(p.getName()))
		{
			if(rTutorialReloaded.isPlayerBackup.get(p.getName()).booleanValue())
			{
				rTutorialReloaded.isPlayerBackup.remove(p.getName());
				RestoreLocation(p);
			}
		}
		else
		{
			Progress.put(p.getName(), 0.0F);
		}
		HidePlayer(p, true);
		taskID.put(p.getName(), tid);
		rTutorialReloaded.ProgressingTutorial.put(p.getName(), "WORKING");
		BackupManager.SetPlayerProgress(p);
	}
	
	public void Result(final Player p)
	{
		SubSection.SubMsg("CompleteTutorial", p, false, true);
		if(!(FileSection.PlayerSection(p).getBoolean("Finished")))
		{
			if(rTutorialReloaded.BroadcastCompleteTutorial)
			{
				Bukkit.broadcastMessage(rTutorialReloaded.Prefix + SubSection.Sub(SubSection.MessageString("BroadcastTutorialComplete"), p));
			}
			if(rTutorialReloaded.RunCommands)
			{
				for(String Commands : rTutorialReloaded.ResultCommands)
				{
					String[] Cutter = Commands.split(": ");
					if(Commands.contains("Console"))
					{
						rTutorialReloaded.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), SubSection.Sub(Cutter[1], p));
					}
					
					else if(Commands.contains("Money") && rTutorialReloaded.CompatiblePlugins[3])
					{
						Economy Echo = com.ReanKR.rTutorialReloaded.Listeners.EconomyAPI.getEconomy();
						Echo.depositPlayer(p, Double.parseDouble(Cutter[1]));
						SubSection.Msg(p, "돈을 받았습니다. " + Cutter[1] + " " + Echo.currencyNamePlural());
					}
					else
					{
						rTutorialReloaded.plugin.getServer().dispatchCommand(p, SubSection.Sub(Commands, p));
					}
				}
			}
			if(rTutorialReloaded.RewardItems)
			{
				SubSection.SubMsg("FirstCompleteTutorial", p, false, true);
				for(ItemStack Items : rTutorialReloaded.ResultItems)
				{
					String ItemName = null;
					p.getInventory().addItem(Items);
					ItemName = Items.getItemMeta().getDisplayName();
					if(! Items.getItemMeta().hasDisplayName())
					{
						ItemName = Items.getType().name();
					}
					SubSection.Msg(p, "아이템 : " + ItemName + ChatColor.WHITE + "x" + Items.getAmount());
				}
			}
		}
		rTutorialReloaded.ProgressingTutorial.put(p.getName(), "COMPLETE");
		BackupManager.SetPlayerProgress(p);
	}
	
	public boolean RestoreLocation(Player p)
	{
		ConfigurationSection BackupSection = FileSection.LoadFile("Backup").getConfigurationSection(p.getName());
		ConfigurationSection Section = FileSection.LoadFile("Location").getConfigurationSection("Locations");
		Object[] LocationName = Section.getKeys(false).toArray();
		int count = 0;
		try
		{
			while(LocationName[count] != null)
			{
				if(LocationName[count].toString().equalsIgnoreCase(BackupSection.get("Progressing").toString()))
				{
					rTutorialProgress.Progress.put(p.getName(), Float.parseFloat(String.valueOf(count)));
					return true;
				}
				else
				{
					count++;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}

		return false;
	}

	public static void endTask(Player p, boolean CompleteTutorial)
	{
		if(taskID.containsKey(p.getName()))
		{
			int tid = taskID.get(p.getName() );
			rTutorialReloaded.plugin.getServer().getScheduler().cancelTask(tid);
			taskID.remove(p.getName());
			Progress.remove(p.getName());
		}

		if(CompleteTutorial)
		{
			rTutorialProgress.LocationProgress.remove(p.getName());
			rTutorialProgress.ListHidePlayer.remove(p.getName());
			rTutorialProgress.Progress.remove(p.getName());
			if(rTutorialReloaded.CompatiblePlugins[0]) BarAPI.removeBar(p);
			HidePlayer(p, false);
			p.setGameMode(PlayerGameMode.get(p.getName()));
			p.setWalkSpeed(PlayerSpeed.get(p.getName()));
			p.setFlySpeed(PlayerFlySpeed.get(p.getName()));
			BackupManager.RemoveBackup(p);
		}
	}
	
	public static void HidePlayer(Player p, boolean isHide)
	{
		try
		{
			for(Player player : Bukkit.getOnlinePlayers())
			{
				if(player != null)
				{
					if(isHide)
					{
						p.hidePlayer(player);
					}
					else
					{
						p.showPlayer(player);
					}
				}
			}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	}
}
