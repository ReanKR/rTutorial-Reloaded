package com.ReanKR.rTutorialReloaded.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.ReanKR.rTutorialReloaded.rTutorialProgress;
import com.ReanKR.rTutorialReloaded.rTutorialRegister;
import com.ReanKR.rTutorialReloaded.rTutorialReloaded;
import com.ReanKR.rTutorialReloaded.File.BackupManager;
import com.ReanKR.rTutorialReloaded.File.ConfigLoader;
import com.ReanKR.rTutorialReloaded.Util.SubSection;

public class rTutorialCommand implements CommandExecutor
{
	private rTutorialProgress TP = new rTutorialProgress();
	private rTutorialReloaded main;

	public rTutorialCommand(rTutorialReloaded Main)
	{
		this.main = Main;
	}

	@Override
	public boolean onCommand(CommandSender Sender, Command command, String label, String[] args)
	{
		if(Sender instanceof Player)
		{
			String cmd = command.getName();
			Player p = (Player)Sender;
			if(cmd.equalsIgnoreCase("rTutorial.main"))
			{
				if(args.length < 1)
				{
					Msg(p, "&a========== &9r&aT&butorial&cR&feloaded &b" + rTutorialReloaded.plugin.getDescription().getVersion() + "&a ==========");
					Msg(p, " ");
					Msg(p, "&6/rt | rtutorial &f: &bShow rTutorialReloaded all commands");
					Msg(p, "&6/rt | rtutorial &aenable &f: &dEnable tutorial system.");
					Msg(p, "&6/rt | rtutorial &astart &f: &dstart tutorial.");
					Msg(p, "&6/rt | rtutorial &ccancel &f: &dcancel progressing tutorial.");
					Msg(p, "&6/rt | rtutorial &econtinue &f: &dcontinue progressing tutorial.");
					Msg(p, " ");
					Msg(p, "&6/rt | rtutorial &fcreate &f: &dCreating new tutorial location.");
					Msg(p, "&6/rt | rtutorial &fcreate &ccancel &f: &dCancel to Create tutorial location.");
					Msg(p, "&6/rt | rtutorial &fcreate &asave &f: &dSave to create tutorial location.");
					Msg(p, " ");
					Msg(p, "&a========== &9r&aT&butorial&cR&feloaded &b" + rTutorialReloaded.plugin.getDescription().getVersion() + "&a ==========");
					return true;
				}
				else
				{
					if(args[0].equalsIgnoreCase("enable"))
					{
						ConfigLoader.TutorialEnable(p);
					}
					else if(args[0].equalsIgnoreCase("start"))
					{
						if(rTutorialReloaded.EditComplete)
						{
							TP.TutorialCooldown(p);
							return true;
						}
						else
						{
							SubSection.SubMsg("CannotStartTutorial", p, false, true);
							SubSection.SubMsg("NotEnabledTutorial", p, false, true);
							return true;
						}
					}
					else if(args[0].equalsIgnoreCase("create"))
					{
						if(args.length < 2)
						{
							rTutorialReloaded.IsCreateNewLocation.put(p, true);
							SubSection.Msg(p, "메인 메세지를 대화 창을 이용해 적어주십시오.");
							return true;
						}
						else
						{
							if(args[1].equalsIgnoreCase("save"))
							{
								if(rTutorialReloaded.SavedNewLocation.containsKey(p))
								{
									if(rTutorialReloaded.SavedNewLocation.get(p).booleanValue())
									{
										rTutorialRegister.LocationRegister(p.getLocation(), rTutorialReloaded.MainMessage.get(p), rTutorialReloaded.SubMessage.get(p), rTutorialReloaded.LocationName.get(p));
										rTutorialReloaded.LocationName.remove(p);
										rTutorialReloaded.MainMessage.remove(p);
										rTutorialReloaded.SavedNewLocation.remove(p);
										rTutorialReloaded.SubMessage.remove(p);
										SubSection.SubMsg("SavedNewLocation", p, false, true);
										return true;
									}
								}
								else
								{
									SubSection.Msg(p, (SubSection.VariableSub(SubSection.SubMsg("NoSavedData", p, true, false), "/rt create")));
									return false;
								}
							}
	
							else if(args[1].equalsIgnoreCase("cancel"))
							{
								if(rTutorialReloaded.SavedNewLocation.containsKey(p))
								{
									if(rTutorialReloaded.SavedNewLocation.get(p).booleanValue())
									{
										rTutorialReloaded.LocationName.remove(p);
										rTutorialReloaded.MainMessage.remove(p);
										rTutorialReloaded.SavedNewLocation.remove(p);
										rTutorialReloaded.SubMessage.remove(p);
										SubSection.SubMsg("CancelCreateLocation", p, false, true);
										return true;
									}
								}
								else if(rTutorialReloaded.IsCreateNewLocation.containsKey(p))
								{
									rTutorialReloaded.IsCreateNewLocation.remove(p);
									rTutorialReloaded.SubMessage.remove(p);
									rTutorialReloaded.MainMessage.remove(p);
									rTutorialReloaded.LocationName.remove(p);
									SubSection.SubMsg("CancelCreateLocation", p, false, true);
									return true;
								}
								else
								{
									SubSection.SubMsg("NoCancel", p, false, true);
									return false;
								}
							}
						}
					}
					else if(args[0].equalsIgnoreCase("continue"))
					{
						if(rTutorialReloaded.isPlayerBackup.containsKey(p))
						{
							if(rTutorialReloaded.isPlayerBackup.get(p).booleanValue())
							{
								TP.TutorialCooldown(p);
								return true;
							}
						}
						else
						{
							SubSection.SubMsg("NoExistContinueData", p, false, true);
							return false;
						}
					}
					else if(args[0].equalsIgnoreCase("Cancel"))
					{
						if(rTutorialReloaded.isPlayerBackup.containsKey(p))
						{
							if(rTutorialReloaded.isPlayerBackup.get(p).booleanValue())
							{
								BackupManager.RestorePlayer(p);
								rTutorialReloaded.isPlayerBackup.remove(p);
								return true;
							}
						}
						else
						{
							SubSection.SubMsg("NoExistContinueData", p, false, true);
							return false;
						}
					}
					else
					{
						SubSection.SubMsg("UnknownCommand", p, false, true);
						return false;
					}
				}
			}
		}
		else
		{
			ConsoleCommandSender Console = Bukkit.getConsoleSender();
			Console.sendMessage(rTutorialReloaded.Prefix + "Version : " + rTutorialReloaded.plugin.getDescription().getVersion());
			Console.sendMessage(rTutorialReloaded.Prefix + "This version not supported console commands.");
			return false;
		}
		return false;
	}
	
	public static void Msg(Player p, String str)
	{
		p.sendMessage(SubSection.RepColor(rTutorialReloaded.Prefix + str));
	}
}
