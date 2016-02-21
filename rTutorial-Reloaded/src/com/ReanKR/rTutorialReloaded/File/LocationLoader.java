package com.ReanKR.rTutorialReloaded.File;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.ReanKR.rTutorialReloaded.rTutorialReloaded;

public class LocationLoader
{
	public static boolean LocationCfg()
	{
		FileConfiguration LocationFile = FileSection.LoadFile("Location");
		File file = new File("plugins/rTutorialReloaded/Location.yml");
		ConfigurationSection CS = null;
		Set<String> Information;
		if(LocationFile.contains("Locations") == false)
		{
			LocationFile.createSection("Locations");
		}
		try
		{
			LocationFile.save(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			CS = LocationFile.getConfigurationSection("Locations");
			Information = CS.getKeys(false);
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
	    	Bukkit.getConsoleSender().sendMessage(rTutorialReloaded.Prefix + "Location.yml is empty. Please set the location at first before tutorial enabling.");
	    	rTutorialReloaded.ErrorReporting.add("Location.yml - File is empty");
	    	return false;
		}
	    int MethodAmount = 0;
	    try
	    {
	    	for (String Loc : Information)
		    {
	    		String WorldName = null; String Coordinate = null; String Angle = null;
	    		String Msg = null; String SubMsg = "NONE";
	    		boolean LocationFound = false; boolean MessageFound = false;
	    		ConfigurationSection LocAmounts = FileSection.PlusSelect(CS, Loc);
	    		Set<String> LocMethod = LocAmounts.getKeys(false);
	            for(String Method : LocMethod)
	            {
	  	          	if(Method.equalsIgnoreCase("Location"))
	  	          	{
	  	          		ConfigurationSection LocInformation = FileSection.PlusSelect(LocAmounts, Method);
	  	          		Set<String> LocDetailed = LocInformation.getKeys(false);
	  	          		boolean CoordinateFound = false, AngleFound = false;
	  		            for(String Detailed : LocDetailed)
	  		            {
	  		            	try
	  		            	{
	  		            		if (Detailed.equalsIgnoreCase("World"))
	  			                {
	  		            			WorldName = LocInformation.getString("World");
	  			                }
	  		            		else if (Detailed.equalsIgnoreCase("Coordinate"))
	  			                {
	  			                	Coordinate = LocInformation.getString("Coordinate");
	  			                	CoordinateFound = true;
	  			                }
	  		            		else if(Detailed.equalsIgnoreCase("Angle"))
	  			                {
	  			                	Angle = LocInformation.getString("Angle");
	  			                	AngleFound = true;
	  			                }
	  		            	}
	  		            	catch(NullPointerException e)
	  		            	{
	  		            		rTutorialReloaded.ErrorReporting.add("Location.yml - " + Loc + " - Misssing required value.");
	  		            		continue;
	  		            	}
	  		            	if(CoordinateFound && AngleFound) LocationFound = true;
	  		            }
	  	          	}
	  	          	else if(Method.equalsIgnoreCase("Message"))
	  	          	{
	  	          		ConfigurationSection LocInformation = FileSection.PlusSelect(LocAmounts, Method);
	  		         	Set<String> MsgDetailed = LocInformation.getKeys(false);
			         	for (String Detailed : MsgDetailed)
			         	{
				            if(Detailed.equalsIgnoreCase("Main"))
				            {
				            	Msg = LocInformation.getString(Detailed);
				            	if (Msg == null) rTutorialReloaded.ErrorReporting.add("Location.yml - " + Loc + " - " + Detailed + " - Not exist value.");
				            	else MessageFound = true;
				            }
				            
				            else if(Detailed.equalsIgnoreCase("Sub"))
				            {
				            	if(rTutorialReloaded.CompatiblePlugins[1]) SubMsg = LocInformation.getString(Detailed);
				            	else rTutorialReloaded.ErrorReporting.add("Location.yml - " + Loc + " - " + Detailed + " - Cannot compatibled TitleAPI. Please check your TitleAPI or config.yml");
				            }
			         	}
	  	          	}
	            }
	            if(LocationFound && MessageFound)
	            {
	                rTutorialReloaded.MessageMethod.put(Loc, Msg + "," + SubMsg);
	            }
	            String[] CoordMethod = Coordinate.split(",");
	            String[] AngleMethod = Angle.split(",");
	            Location Locate = new Location(rTutorialReloaded.plugin.getServer().getWorld(WorldName)
	            		, Double.parseDouble(CoordMethod[0]), Double.parseDouble(CoordMethod[1]),
	            		Double.parseDouble(CoordMethod[2]), Float.parseFloat(AngleMethod[0]), Float.parseFloat(AngleMethod[1]));
	            rTutorialReloaded.InfoLocation.put(Loc, Locate);
	            MethodAmount++;
		    }
	    }
	    catch(NullPointerException e)
	    {
	    	Bukkit.getConsoleSender().sendMessage(rTutorialReloaded.Prefix + "Location.yml is empty or include wrong method. Please set the location at first before tutorial enabling.");
	    	rTutorialReloaded.ErrorReporting.add("Location.yml - File is empty or include wrong method");
	    	return false;
	    }
	    rTutorialReloaded.MethodAmount = MethodAmount;
	    return true;
	}
}
