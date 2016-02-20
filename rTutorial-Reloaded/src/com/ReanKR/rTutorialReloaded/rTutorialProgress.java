package com.ReanKR.rTutorialReloaded;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ReanKR.rTutorialReloaded.rTutorialReloaded;
import com.ReanKR.rTutorialReloaded.File.BackupManager;
import com.ReanKR.rTutorialReloaded.File.FileSection;
import com.ReanKR.rTutorialReloaded.Util.SoundCreation;
import com.ReanKR.rTutorialReloaded.Util.SubSection;
import com.connorlinfoot.titleapi.TitleAPI;

import me.confuser.barapi.BarAPI;
import net.milkbowl.vault.economy.Economy;

@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	private rTutorialReloaded plugin;
	private SoundCreation SC = new SoundCreation();
	
	public rTutorialProgress(rTutorialReloaded plugin)
	{
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	public void TutorialCooldown(final Player p)
	{
		final int tid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
		{
			@Override
			public void run()
			{
				if(! plugin.getServer().getOfflinePlayer(p.getName()).isOnline()) // if player leaved server when tutorial progressing
				{
					Bukkit.getConsoleSender().sendMessage("Tutorial can't running : Player Leaved");
					if(rTutorialReloaded.CompatiblePlugins[0]) BarAPI.removeBar(p);
					PlayerGameMode.put(p.getName(), p.getGameMode());
					PlayerSpeed.put(p.getName(), p.getWalkSpeed());
					PlayerFlySpeed.put(p.getName(), p.getFlySpeed());
					BackupManager.SaveUnexpected(p);
					HidePlayer(p, false);
					endTask(p, true);
					return;
				}
				
				if(Progress.get(p.getName()) <= 0)
				{
					PlayerGameMode.put(p.getName(), p.getGameMode());
					PlayerSpeed.put(p.getName(), p.getWalkSpeed());
					PlayerFlySpeed.put(p.getName(), p.getFlySpeed());
					AddOnlinePlayer(p);
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
								Progress.get(p.getName()).intValue()), "rTutorial " + plugin.getDescription().getVersion());
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
		final int tid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
		{
			@Override
			public void run()
			{
				if(! plugin.getServer().getOfflinePlayer(p.getName()).isOnline()) // if player leaved server when tutorial progressing
				{
					Bukkit.getConsoleSender().sendMessage("Tutorial can't running : Player Leaved");
					if(rTutorialReloaded.CompatiblePlugins[0]) BarAPI.removeBar(p);
					BackupManager.SaveUnexpected(p);
					HidePlayer(p, false);
					endTask(p, true);
					return;
				}
				
				if(Progress.get(p.getName()).intValue() >= rTutorialReloaded.MethodAmount)
				{
					BarAPI.removeBar(p);
					BarAPI.setMessage(p, "¡×a" + SubSection.VariableSub(SubSection.SubMsg("BarAPIPercent", p, true, false), 100) , 100);
					endTask(p, true);
					Result(p);
					return;
				}

				String[] Cut = rTutorialReloaded.MessageMethod.get(LocationName[Progress.get(p.getName()).intValue()]).split(",");
				if(! rTutorialReloaded.SoundDisabled) SC.PlayerSound(p, Sound.LEVEL_UP, 10.0F, 1.0F);
				p.teleport(rTutorialReloaded.InfoLocation.get(LocationName[Progress.get(p.getName()).intValue()]));
				if(rTutorialReloaded.CompatiblePlugins[0])
				{
					BarAPI.setMessage(p, SubSection.VariableSub(SubSection.SubMsg("BarAPIPercent", p, true, false), format.format((Progress.get(p.getName()) / rTutorialReloaded.MethodAmount) * 100)) , (Progress.get(p.getName()) / rTutorialReloaded.MethodAmount) * 100);
				}
				if(rTutorialReloaded.CompatiblePlugins[1]) TitleAPI.sendTitle(p, rTutorialReloaded.DefaultDelaySeconds / 6,rTutorialReloaded.DefaultDelaySeconds / 6, rTutorialReloaded.DefaultDelaySeconds*20, SubSection.RepColor(Cut[0]), SubSection.RepColor(Cut[1]));
				else p.sendMessage(SubSection.RepColor(Cut[0]));
				LocationProgress.put(p.getName(), LocationName[Progress.get(p.getName()).intValue()].toString());
				Progress.put(p.getName(), Progress.get(p.getName()) + 1.0F);
			}
		}, 0L, rTutorialReloaded.DefaultDelaySeconds*20L);
		
		HidePlayer(p, true);
		p.setWalkSpeed(0.0F);
		p.setFlySpeed(0.0F);
		taskID.put(p.getName(), tid);
		Progress.put(p.getName(), 0.0F);
		p.setGameMode(GameMode.SPECTATOR);
		rTutorialReloaded.ProgressingTutorial.put(p.getName(), "WORKING");
		BackupManager.SetPlayerProgress(p);
	}
	
	public void Result(final Player p)
	{
		for(String Commands : rTutorialReloaded.ResultCommands)
		{
			String[] Cutter = Commands.split(": ");
			if(Commands.contains("Console"))
			{
				plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), SubSection.Sub(Cutter[1], p));
			}
			
			else if(Commands.contains("Money"))
			{
				Economy Echo = com.ReanKR.rTutorialReloaded.Listeners.EconomyAPI.getEconomy();
				Echo.depositPlayer(p, Double.parseDouble(Cutter[1]));
			}
			else
			{
				plugin.getServer().dispatchCommand(p, SubSection.Sub(Commands, p));
			}
		}
		for(ItemStack Items : rTutorialReloaded.ResultItems)
		{
			p.getInventory().addItem(Items);
		}
		rTutorialReloaded.ProgressingTutorial.put(p.getName(), "COMPLETE");
		BackupManager.SetPlayerProgress(p);
	}

	public void endTask(Player p, boolean CompleteTutorial)
	{
		if(taskID.containsKey(p.getName()))
		{
			int tid = taskID.get(p.getName());
			plugin.getServer().getScheduler().cancelTask(tid);
			taskID.remove(p.getName());
			Progress.remove(p.getName());
		}

		if(CompleteTutorial)
		{
			BarAPI.removeBar(p);
			HidePlayer(p, false);
			p.setGameMode(PlayerGameMode.get(p.getName()));
			p.setWalkSpeed(PlayerSpeed.get(p.getName()));
			p.setFlySpeed(PlayerFlySpeed.get(p.getName()));
		}
	}
	
	public static void AddOnlinePlayer(Player p)
	{
		List<Player> NameOnlinePlayers = new ArrayList();
		for(Player player : Bukkit.getOnlinePlayers())
		{
			NameOnlinePlayers.add(player);
			if(player != null)
			{
				NameOnlinePlayers.add(player);
			}
		}
		ListHidePlayer.put(p.getName(), NameOnlinePlayers);
		return;
	}
	
	public static void HidePlayer(Player p, boolean isHide)
	{
		try
		{
			for(Player player : ListHidePlayer.get(p.getName()))
			{
				if(isHide) p.hidePlayer(player);
				else p.showPlayer(player);
			}
		}
		catch(NullPointerException e)
		{
			
		}
	}
}
