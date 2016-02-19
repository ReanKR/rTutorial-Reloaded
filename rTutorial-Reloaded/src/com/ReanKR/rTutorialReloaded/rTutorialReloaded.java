package com.ReanKR.rTutorialReloaded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.ReanKR.rTutorialReloaded.File.ConfigLoader;
import com.ReanKR.rTutorialReloaded.File.LocationLoader;
import com.ReanKR.rTutorialReloaded.Listeners.EconomyAPI;
import com.ReanKR.rTutorialReloaded.Util.ErrorReporter;
import com.ReanKR.rTutorialReloaded.Util.VariableManager;

import net.milkbowl.vault.economy.Economy;

public class rTutorialReloaded extends JavaPlugin implements Listener
{
	// Saved variable loaded by Config.yml
	public static int ConfigVersion;
	public static boolean RunFirstJoinPlayer = false;
	public static boolean BlockMovement = true;
	public static boolean BlockAllCommands = true;
	public static boolean BroadcastCompleteTutorial = false;
	public static boolean EditComplete = false;
	public static int DefaultDelaySeconds = 6;
	public static int DefaultCooldownSeconds = 5;
	public static boolean SoundDisabled = true;
	public static boolean[] CompatiblePlugins;
	public static List<String> ExceptionCommands;
	public static boolean RunCommands = true;
	public static boolean RewardItems = true;
	public static List<String> ResultCommands;
	public static List<ItemStack> ResultItems;

	// Saved variable loaded by Location.yml
	public static List<String> LocationMethod; // World,X,Y,Z,Pitch,Yaw
	public static HashMap<String, String> MessageMethod; // Location Name, Main message, Sub message
	public static int MethodAmount = 0; // Location method amount
	public static HashMap<String, Location> InfoLocation; // Location name, Coordinate, Angle
	
	// Saved variable loaded by message.yml 
	public static Map<String, String> SystemMessage; // MSG_TYPE, Message
	
	// Saved variable about tutorial system
	public static HashMap<Player, String> TutorialStatus; // Loaded by Player.yml Status
	public static HashMap<String, Boolean> TutorialComplete; // Is Player completed tutorial
	public static HashMap<String, String> ProgressingTutorial; // Showing the player that tutorial progressing method name
	public static HashMap<Player, String> MainMessage; // Temp message
	public static HashMap<Player, String> SubMessage; // Temp message
	
	// Saved variable about create to tutorial progressing
	public static HashMap<Player, Boolean> IsCreateNewLocation;  // Enabled Blocking AsyncChatEvent when creating new data
	public static HashMap<Player, Boolean> SavedNewLocation; // Finished creating all progress
	public static HashMap<Player, Integer> CreatingCount; // New method amount

	// rTutorial Reloaded main variable
	public static List<String> ErrorReporting; // Save error collection
	public static rTutorialReloaded RTutorialReloaded;
	public static Plugin plugin;
	public static String Prefix = "」e[」9r」aT」butorial」e]」f ";
	public static Economy Eco;
	
	// Substituted for sentance contraction
	private ConsoleCommandSender Console = Bukkit.getConsoleSender();
	private ConfigLoader CL = new ConfigLoader();
	private LocationLoader LL = new LocationLoader();
	private rTutorialProgress TP = new rTutorialProgress(this);
	
	@Override
	public void onEnable()
	{
		RTutorialReloaded = this;
		plugin = this;
		VariableManager.InitAllVariable();
		CL.LoadCfg();
		CL.LoadMessage();
		LL.LocationCfg();
		Eco = EconomyAPI.getEconomy();
		ErrorReporter.ResultErrorReport();
		Console.sendMessage(Prefix + "」bM」fade 」bb」fy Rean KR,」9 whitehack97@gmail.com");
		Console.sendMessage(Prefix + "」bD」fevoloper 」bW」febsite 」e: 」fhttp://cafe.naver.com/suserver24");
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("rtutorial.main"))
		{
			Player player = (Player)sender;
			TP.TutorialCooldown(player);
			return true;
		}
		return false;
	}
}
