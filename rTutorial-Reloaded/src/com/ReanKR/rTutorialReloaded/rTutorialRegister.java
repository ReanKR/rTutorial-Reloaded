package com.ReanKR.rTutorialReloaded;

import com.ReanKR.rTutorialReloaded.File.FileSection;
import com.ReanKR.rTutorialReloaded.File.LocationLoader;
import java.io.File;
import java.io.IOException;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class rTutorialRegister
{
    public static void LocationRegister(Location Loc, String MainMsg, String SubMsg, String Index)
    {
	    File file = new File("plugins/rTutorialReloaded/Location.yml");
	    YamlConfiguration LocationYaml = YamlConfiguration.loadConfiguration(file);
	    ConfigurationSection CS = LocationYaml.getConfigurationSection("Locations");
	    CS.createSection(Index);
	    World world = Loc.getWorld();
	    double x = Double.valueOf(Loc.getX());
	    double y = Double.valueOf(Loc.getY());
	    double z = Double.valueOf(Loc.getZ());
	    float pitch = Loc.getPitch();
	    float yaw = Loc.getYaw();
	    ConfigurationSection CS2 = CS.getConfigurationSection(Index);
	    CS2.createSection("Location");
	    CS2.createSection("Message");
	    ConfigurationSection CS3 = FileSection.PlusSelect(CS2, "Location");
	    CS3.set("World", world.getName());
	    CS3.set("Coordinate", x + "," + y + "," + z);
	    CS3.set("Angle", String.valueOf(yaw + "," + pitch));
	    ConfigurationSection CS4 = FileSection.PlusSelect(CS2, "Message");
	    CS4.set("Main", MainMsg);
	    if(! (SubMsg.equalsIgnoreCase("¾øÀ½") || SubMsg.equalsIgnoreCase("None")))
	    {
	     CS4.set("Sub", SubMsg);
	    }
	    try
	    {
			LocationYaml.save(file);
		}
	    catch (IOException e)
	    {
			e.printStackTrace();
		}
	    LocationLoader.LocationCfg();
	    return;
    }
}