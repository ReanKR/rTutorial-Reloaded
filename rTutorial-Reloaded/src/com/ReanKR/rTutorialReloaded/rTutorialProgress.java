package com.ReanKR.rTutorialReloaded;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.ReanKR.rTutorialReloaded.rTutorialReloaded;
import com.ReanKR.rTutorialReloaded.File.BackupManager;
import com.ReanKR.rTutorialReloaded.Util.SoundCreation;
import com.ReanKR.rTutorialReloaded.Util.SubSection;
import com.connorlinfoot.titleapi.TitleAPI;

import me.confuser.barapi.BarAPI;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class rTutorialProgress
{
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
					BackupManager.SaveUnexpected(p);
					HidePlayer(p, false);
					endTask(p, false);
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
		rTutorialReloaded.ProgressingTutorial.put(p, "COOLDOWN");
		BackupManager.SetPlayerProgress(p);
	}
	
	@SuppressWarnings("deprecation")
	public void ProgressingTutorial(final Player p)
	{
		String[] LocationName = (String[]) rTutorialReloaded.LocationMethod.toArray();
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
					endTask(p, false);
					return;
				}
				
				if(! rTutorialReloaded.SoundDisabled) SC.PlayerSound(p, Sound.LEVEL_UP, 10.0F, 1.0F);
				p.teleport(rTutorialReloaded.InfoLocation.get(LocationName[Progress.get(p.getName()).intValue()]));
				if(rTutorialReloaded.CompatiblePlugins[0])
				{
					BarAPI.setMessage(p, SubSection.VariableSub(SubSection.SubMsg("BarAPIPercent", p, true, false), (Progress.get(p.getName()) / rTutorialReloaded.MethodAmount) * 100) , (Progress.get(p.getName()) / rTutorialReloaded.MethodAmount) * 100);
				}
				if(rTutorialReloaded.CompatiblePlugins[1]) TitleAPI.sendTitle(p, rTutorialReloaded.DefaultDelaySeconds / 6,rTutorialReloaded.DefaultDelaySeconds / 6, rTutorialReloaded.DefaultDelaySeconds*20, rTutorialReloaded.MainMessage.get(LocationName[Progress.get(p.getName()).intValue()]), rTutorialReloaded.SubMessage.get(LocationName[Progress.get(p.getName()).intValue()]));
				else p.sendMessage(SubSection.RepColor(rTutorialReloaded.MainMessage.get(LocationName[Progress.get(p.getName()).intValue()])));
				Progress.put(p.getName(), Progress.get(p.getName()) + 1.0F);
				LocationProgress.put(p.getName(), LocationName[Progress.get(p.getName()).intValue()]);
			}
		}, 0L, rTutorialReloaded.DefaultDelaySeconds*20L);
		
		HidePlayer(p, true);
		p.setWalkSpeed(0.0F);
		p.setFlySpeed(0.0F);
		taskID.put(p.getName(), tid);
		Progress.put(p.getName(), 0F);
		p.setGameMode(GameMode.SPECTATOR);
		rTutorialReloaded.ProgressingTutorial.put(p, "WORKING");
		BackupManager.SetPlayerProgress(p);
	}
	
	public void Result(final Player p)
	{
		rTutorialReloaded.ProgressingTutorial.put(p, "COMPLETE");
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
		for(Player player : ListHidePlayer.get(p.getName()))
		{
			if(isHide) p.hidePlayer(player);
			else p.showPlayer(player);
		}
	}
}
