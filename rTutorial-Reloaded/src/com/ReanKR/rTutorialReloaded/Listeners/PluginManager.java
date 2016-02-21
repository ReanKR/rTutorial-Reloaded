package com.ReanKR.rTutorialReloaded.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.ReanKR.rTutorialReloaded.rTutorialReloaded;

import net.milkbowl.vault.economy.Economy;

public class PluginManager extends JavaPlugin
{
	private static Server server = rTutorialReloaded.plugin.getServer();

	public static void PluginChecking(ConfigurationSection Node)
	{
		if(! server.getPluginManager().isPluginEnabled("Vault"))
		{
			if(Node.getBoolean("Vault"))
			{
				rTutorialReloaded.ErrorReporting.add("config.yml, Compatibles, rTutorialReloaded required Vault.");
				SwitchCompatiblePlugin("Vault");
				rTutorialReloaded.plugin.getPluginLoader().disablePlugin(rTutorialReloaded.RTutorialReloaded);
			}

		    if(Node.getBoolean("Economy"))
		    {
		    	rTutorialReloaded.ErrorReporting.add("config.yml, Compatibles, Cannot hooked Economy.");
		    	rTutorialReloaded.ErrorReporting.add("Is your server enabled formal Vault or with respect to API?");
		    	SwitchCompatiblePlugin("Economy");
		    }
		}
		else
		{
			if(! Node.getBoolean("Vault"))
			{
				SwitchCompatiblePlugin("Vault");
				Bukkit.getConsoleSender().sendMessage(rTutorialReloaded.Prefix + "」dVault Hooked");
			}

			else
			{
				Bukkit.getConsoleSender().sendMessage(rTutorialReloaded.Prefix + "」dVault Hooked");
			}
			
			Economy echo = EconomyAPI.getEconomy();
			if(Node.getBoolean("Economy"))
			{
				try
				{
					if(! echo.isEnabled())
					{
						rTutorialReloaded.ErrorReporting.add("config.yml, Compatibles, Economy API not hooked");
						SwitchCompatiblePlugin("Economy");
					}
					else
					{
						if(Node.getBoolean("Economy"))
						{
							server.getConsoleSender().sendMessage(rTutorialReloaded.Prefix + ChatColor.GREEN + echo.getName() + " Hooked");
						}
					}
				}
				catch(NullPointerException e)
				{
					rTutorialReloaded.ErrorReporting.add("config.yml, Compatibles, Economy API not hooked");
					SwitchCompatiblePlugin("Economy");
				}
			}
		}
		
		if(! server.getPluginManager().isPluginEnabled("BarAPI"))
		{
			if(Node.getBoolean("BarAPI"))
			{
				rTutorialReloaded.ErrorReporting.add("config.yml, Compatibles, BarAPI not found.");
				SwitchCompatiblePlugin("BarAPI");
			}
		}

		else
		{
			if(Node.getBoolean("BarAPI"))
			{
				server.getConsoleSender().sendMessage(rTutorialReloaded.Prefix + "」eBarAPI Hooked");
			}
		}

		if(! server.getPluginManager().isPluginEnabled("TitleAPI"))
		{
			if(Node.getBoolean("TitleAPI"))
			{
				rTutorialReloaded.ErrorReporting.add("config.yml, Compatibles, TitleAPI not found.");
				SwitchCompatiblePlugin("TitleAPI");
			}
		}

	    else
	    {
			if(Node.getBoolean("TitleAPI"))
			{
				server.getConsoleSender().sendMessage(rTutorialReloaded.Prefix + "」bTitleAPI Hooked");
			}
	    }
		
		LoadPluginStatus(Node);
		return;
	}
	public static void SwitchCompatiblePlugin(String PluginName)
	{
		FileConfiguration Config = rTutorialReloaded.plugin.getConfig();
		ConfigurationSection CS = Config.getConfigurationSection("Compatibles");
		CS.set(PluginName, Boolean.valueOf(!CS.getBoolean(PluginName)));
		rTutorialReloaded.plugin.saveConfig();
	}
	
	public static void LoadPluginStatus(ConfigurationSection Node)
	{
		rTutorialReloaded.CompatiblePlugins[0] = Node.getBoolean("BarAPI");
		rTutorialReloaded.CompatiblePlugins[1] = Node.getBoolean("TitleAPI");
		rTutorialReloaded.CompatiblePlugins[2] = Node.getBoolean("Vault");
		rTutorialReloaded.CompatiblePlugins[3] = Node.getBoolean("Economy");
		return;
	}
}
