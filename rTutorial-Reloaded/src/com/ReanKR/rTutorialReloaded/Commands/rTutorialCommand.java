package com.ReanKR.rTutorialReloaded.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.ReanKR.rTutorialReloaded.rTutorialReloaded;
import com.ReanKR.rTutorialReloaded.Util.SubSection;

public class rTutorialCommand implements CommandExecutor
{
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
					Msg(p, "&a========== &9r&aT&butorial&cR&feloaded &a" + rTutorialReloaded.plugin.getDescription().getVersion() + "==========");
					Msg(p, " ");
					Msg(p, "&6/rt | rtutorial : Show rTutorialrReloaded all commands");
					Msg(p, "&6/rt | rtutorial &aenable &f: &dEnable tutorial system.");
					Msg(p, "&6/rt | rtutorial &astart &f: &dstart tutorial.");
					Msg(p, "");
					Msg(p, "&6/rt | rtutorial &fcreate &f: &dCreating new tutorial location.");
					Msg(p, "&6/rt | rtutorial &fcreate &ccancel &f: &dCancel to Create tutorial location.");
					Msg(p, "&6/rt | rtutorial &fcreate &asave &f: &dSave to create tutorial location.");
					Msg(p, "");
					Msg(p, "&6/rt | rtutorial &3list &f: &dshow location list.");
					Msg(p, "&6/rt | rtutorial &3remove &9[Name] &f: &ddelete index name saved location list.");
					Msg(p, " ");
					Msg(p, "&a========== &9r&aT&butorial&cR&feloaded &a" + rTutorialReloaded.plugin.getDescription().getVersion() + "==========");
					return true;
				}
				else
				{
					if(args[0].equalsIgnoreCase("create"))
					{
						
					}
				}
			}
			else
			{
				SubSection.SubMsg("UnknownCommand", p, true, true);
				return false;
			}
		}
		else
		{
			ConsoleCommandSender Console = Bukkit.getConsoleSender();
		}
		return false;
	}
	
	public static void Msg(Player p, String str)
	{
		p.sendMessage(SubSection.RepColor(rTutorialReloaded.Prefix + str));
	}
}
