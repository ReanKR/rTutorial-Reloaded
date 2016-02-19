package com.ReanKR.rTutorialReloaded.File;

import com.ReanKR.rTutorialReloaded.rTutorialReloaded;
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileSection
{
	public static YamlConfiguration LoadFile(String Path)
	{
		if (!Path.endsWith(".yml"))
	    {
			Path = Path + ".yml";
	    }
	    File file = new File("plugins/rTutorialReloaded/" + Path);
	    if(!file.exists())
	    {
		      try
		      {
		          rTutorialReloaded.plugin.saveResource(Path, true);
		          Bukkit.getConsoleSender().sendMessage(rTutorialReloaded.Prefix + "Create New File " + file.getAbsolutePath());
		      }
		      catch (NullPointerException e)
		      {
		    	  try
		    	  {
					file.createNewFile();
		    	  }
		    	  catch (IOException e1)
		    	  {
					e1.printStackTrace();
		    	  }
		    	  
		    	  Bukkit.getConsoleSender().sendMessage(rTutorialReloaded.Prefix + "Create New File " + file.getAbsolutePath());
		      }
	    }
	    YamlConfiguration Config = YamlConfiguration.loadConfiguration(file);
	    return Config;
	}

	public static ConfigurationSection PlusSelect(ConfigurationSection CS, String Name)
	{
		return CS.getConfigurationSection(Name);
	}
}
