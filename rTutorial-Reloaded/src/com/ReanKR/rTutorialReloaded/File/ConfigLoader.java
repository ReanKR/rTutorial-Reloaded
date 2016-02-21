package com.ReanKR.rTutorialReloaded.File;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import com.ReanKR.rTutorialReloaded.rTutorialReloaded;
import com.ReanKR.rTutorialReloaded.File.FileSection;
import com.ReanKR.rTutorialReloaded.Listeners.PluginManager;
import com.ReanKR.rTutorialReloaded.Util.SubSection;
import com.ReanKR.rTutorialReloaded.Util.VariableManager;

public class ConfigLoader
{
	public static rTutorialReloaded plugin = rTutorialReloaded.RTutorialReloaded;
	
	public static void TutorialEnable(Player p)
	{
		if(LocationLoader.LocationCfg())
		{
			File file = new File("plugins/rTutorialReloaded/config.yml");
			YamlConfiguration ConfigFile = YamlConfiguration.loadConfiguration(file);
			ConfigurationSection Section = ConfigFile.getConfigurationSection("Main");
			Section.set("Edit-Complete", !(rTutorialReloaded.EditComplete));
			try {
				ConfigFile.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			SubSection.Msg(p,SubSection.VariableSub(SubSection.SubMsg("NotExistLocation", p, true, false), "/rt create"));
			return;
		}
		LoadCfg(false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public static void LoadCfg(boolean isPluginLoad)
	{
		FileSection.LoadFile("Enchantments");
		FileConfiguration ConfigFile = FileSection.LoadFile("config");
		Set<String> Keyword = ConfigFile.getKeys(false);
		for(String Str : Keyword) // Main, Compatibles, Exception-Commands, Result
		{
			ConfigurationSection MainNode = FileSection.PlusSelect(ConfigFile, Str);
			try
			{
				if(Str.equalsIgnoreCase("Main"))
				{
					Set<String> MainKeyword = MainNode.getKeys(false);
					for(String MainStr : MainKeyword)
					{
						if(MainStr.equalsIgnoreCase("Config-Version") && ! MainNode.isSet("Config-Version"))
						{
							rTutorialReloaded.ErrorReporting.add("config.yml - Missing Config Version.");
							plugin.getPluginLoader().disablePlugin(rTutorialReloaded.RTutorialReloaded);
							return;
						}
						else if(MainStr.equalsIgnoreCase("Run-First-Join-Player") && MainNode.isSet("Run-First-Join-Player"))
						{
							rTutorialReloaded.RunFirstJoinPlayer = MainNode.getBoolean("Run-First-Join-Player");
						}
						else if(MainStr.equalsIgnoreCase("Block-Movement") && MainNode.isSet("Block-Movement"))
						{
							rTutorialReloaded.BlockMovement = MainNode.getBoolean("Block-Movement");
						}
						else if(MainStr.equalsIgnoreCase("Block-All-Commands") && MainNode.isSet("Block-All-Commands"))
						{
							rTutorialReloaded.BlockAllCommands = MainNode.getBoolean("Block-All-Commands");
						}
						else if(MainStr.equalsIgnoreCase("Broadcast-Complete-Tutorial") && MainNode.isSet("Broadcast-Complete-Tutorial"))
						{
							rTutorialReloaded.BroadcastCompleteTutorial = MainNode.getBoolean("Broadcast-Complete-Tutorial");
						}
						else if(MainStr.equalsIgnoreCase("Edit-Complete") && MainNode.isSet("Edit-Complete"))
						{
							rTutorialReloaded.EditComplete = MainNode.getBoolean("Edit-Complete");
						}
						else if(MainStr.equalsIgnoreCase("Default-Delay-Seconds") && MainNode.isSet("Default-Delay-Seconds"))
						{
							rTutorialReloaded.DefaultDelaySeconds = MainNode.getInt("Default-Delay-Seconds");
						}
						else if(MainStr.equalsIgnoreCase("Default-Cooldown-Seconds") && MainNode.isSet("Default-Cooldown-Seconds"))
						{
							rTutorialReloaded.DefaultCooldownSeconds = MainNode.getInt("Default-Cooldown-Seconds");
						}
						else if(MainStr.equalsIgnoreCase("Sound-Disabled") && MainNode.isSet("Sound-Disabled"))
						{
							rTutorialReloaded.SoundDisabled = MainNode.getBoolean("Sound-Disabled");
						}
					}
				}
				else if(Str.equalsIgnoreCase("Compatibles"))
				{
					if(isPluginLoad)
					{
						PluginManager.PluginChecking(MainNode);
					}
				}
				
				else if(Str.equalsIgnoreCase("Exception-Commands"))
				{
					rTutorialReloaded.ExceptionCommands = ConfigFile.getStringList("Exception-Commands");
				}
				
				else if(Str.equalsIgnoreCase("Result"))
				{
					Set<String> ResultKeyword = MainNode.getKeys(false);
					for(String ResultStr : ResultKeyword)
					{
						if(ResultStr.equalsIgnoreCase("Run-Commands") && MainNode.isSet("Run-Commands"))
						{
							rTutorialReloaded.RunCommands = MainNode.getBoolean("Run-Commands");
						}
						else if(ResultStr.equalsIgnoreCase("Reward-Items") && MainNode.isSet("Reward-Items"))
						{
							rTutorialReloaded.RewardItems = MainNode.getBoolean("Reward-Items");
						}
						else if(ResultStr.equalsIgnoreCase("Commands") && MainNode.isSet("Commands"))
						{
							rTutorialReloaded.ResultCommands = MainNode.getStringList("Commands");
						}
						else if(ResultStr.equalsIgnoreCase("Items") && MainNode.isSet("Items"))
						{
							Set<String> ItemKeyword = FileSection.PlusSelect(MainNode, "Items").getKeys(false);
							for(String ItemStr : ItemKeyword)
							{
								ConfigurationSection ItemNode = FileSection.PlusSelect(FileSection.PlusSelect(MainNode, "Items"), ItemStr);
				                int ID = 0;
				                int Amounts = 0;
				                byte Data = 0;
								List<String> Lores = new ArrayList();
								List<String> EnchantList = new ArrayList();
				                String DisplayName = null;
				                short Durability = 0;
				                try
				                {
				                	if(VariableManager.IgnException(ItemNode, "ID")) ID = Integer.valueOf(ItemNode.getInt("ID"));
				                	if(VariableManager.IgnException(ItemNode, "DATA-VALUE")) Data = Byte.parseByte(ItemNode.getString("DATA-VALUE"));
				                	if(VariableManager.IgnException(ItemNode, "Amounts")) Amounts = Integer.valueOf(ItemNode.getInt("Amounts"));
				                	if(VariableManager.IgnException(ItemNode, "DESCRIPTION")) Lores = SubSection.RepColorList(ItemNode.getStringList("DESCRIPTION"));
				                	if(VariableManager.IgnException(ItemNode, "ENCHANTMENT")) EnchantList = ItemNode.getStringList("ENCHANTMENT");
				                	if(VariableManager.IgnException(ItemNode, "NAME")) DisplayName = SubSection.RepColor(ItemNode.getString("NAME"));
				                	if(VariableManager.IgnException(ItemNode, "DURABILITY")) Durability = Short.valueOf(ItemNode.getString("DURABILITY"));
				                }
				                catch (NullPointerException e)
				                {
				                  rTutorialReloaded.ErrorReporting.add("config.yml - Results - Items - " + ItemStr + " - ItemMeta Incorrect Values.");
				                  continue;
				                }
				                
								ItemStack item = new MaterialData(ID, Data).toItemStack(Amounts);
								ItemMeta IM = item.getItemMeta();
								IM.setDisplayName(DisplayName);
								IM.setLore(Lores);
				                if(! EnchantList.isEmpty())
				                {
					                for(String Enchant : EnchantList)
					                {
					                	String[] Filter = Enchant.split(", ");
					                	IM.addEnchant(Enchantment.getByName(Filter[0]), Integer.valueOf(Filter[1]), true);
					                }
				                }
								item.setDurability(Durability);
								item.setItemMeta(IM);
								rTutorialReloaded.ResultItems.add(item);
							}
						}
					}
					
				}
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
				rTutorialReloaded.ErrorReporting.add("config.yml - " + Str + " - Tried to return null value.");
			}
		}
	}

	public void LoadMessage()
	{
		FileConfiguration LangFile = FileSection.LoadFile("message.yml");
	    ConfigurationSection LangNode = LangFile.getConfigurationSection("Messages");
	    Set<String> MessageMethod = LangNode.getKeys(true);
	    for(String MethodName: MessageMethod)
	    {
	    	rTutorialReloaded.SystemMessage.put(MethodName, SubSection.RepColor(LangNode.getString(MethodName)));
	    }
	    return;
	}
}
