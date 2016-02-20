package com.ReanKR.rTutorialReloaded;

import com.ReanKR.rTutorialReloaded.File.FileSection;
import com.ReanKR.rTutorialReloaded.File.LocationLoader;
import com.ReanKR.rTutorialReloaded.rTutorialReloaded;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class rTutorialRegister
{
    public void LocationRegister(Location Loc, String LocName, List<String> Msg, int index)
    {
	    File file = new File("plugins/rTutorialReloaded/Location.yml");
	    YamlConfiguration LocationYaml = YamlConfiguration.loadConfiguration(file);
	    ConfigurationSection CS = LocationYaml.getConfigurationSection("Locations");
	    CS.createSection(LocName);
	    World world = Loc.getWorld();
	    double x = Double.valueOf(Loc.getX());
	    double y = Double.valueOf(Loc.getY());
	    double z = Double.valueOf(Loc.getZ());
	    float pitch = Loc.getPitch();
	    float yaw = Loc.getYaw();
	    ConfigurationSection CS2 = CS.getConfigurationSection(LocName);
	    CS2.createSection("Location");
	    CS2.createSection("Message");
	    ConfigurationSection CS3 = FileSection.PlusSelect(CS2, "Location");
	    CS3.set("World", world.getName());
	    CS3.set("Coordinate", x + "," + y + "," + z);
	    CS3.set("Angle", String.valueOf(yaw + "," + pitch));
	    ConfigurationSection CS4 = FileSection.PlusSelect(CS2, "Message");
	    CS4.set("Main", Msg.get(0));
	    if(! (Msg.get(1).equalsIgnoreCase("¾øÀ½") || Msg.get(1).equalsIgnoreCase("None")))
	    {
	     CS4.set("Sub", Msg.get(1));
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