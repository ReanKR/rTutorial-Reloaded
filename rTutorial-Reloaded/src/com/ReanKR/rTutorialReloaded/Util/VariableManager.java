package com.ReanKR.rTutorialReloaded.Util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;

import com.ReanKR.rTutorialReloaded.rTutorialProgress;
import com.ReanKR.rTutorialReloaded.rTutorialReloaded;

public class VariableManager
{
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static void InitAllVariable()
	{
		rTutorialReloaded.CompatiblePlugins = new boolean[5];
		rTutorialReloaded.ExceptionCommands = new ArrayList();
		rTutorialReloaded.ResultCommands = new ArrayList();
		rTutorialReloaded.ResultItems = new ArrayList();
		rTutorialReloaded.LocationMethod = new ArrayList();
		rTutorialReloaded.InfoLocation = new HashMap();
		rTutorialReloaded.MessageMethod = new HashMap();
		rTutorialReloaded.SystemMessage = new HashMap();
		rTutorialReloaded.TutorialStatus = new HashMap();
		rTutorialReloaded.TutorialComplete = new HashMap();
		rTutorialReloaded.ProgressingTutorial = new HashMap();
		rTutorialReloaded.MainMessage = new HashMap();
		rTutorialReloaded.SubMessage = new HashMap();
		rTutorialReloaded.LocationName = new HashMap();
		rTutorialReloaded.IsCreateNewLocation = new HashMap();
		rTutorialReloaded.SavedNewLocation = new HashMap();
		rTutorialReloaded.isPlayerBackup = new HashMap();
		rTutorialReloaded.CreatingCount = new HashMap();
		rTutorialReloaded.ErrorReporting = new ArrayList();
		rTutorialProgress.taskID = new HashMap();
		rTutorialProgress.Progress = new HashMap();
		rTutorialProgress.LocationProgress = new HashMap();
		rTutorialProgress.PlayerGameMode = new HashMap();
		rTutorialProgress.PlayerSpeed = new HashMap();
		rTutorialProgress.PlayerFlySpeed = new HashMap();
		rTutorialProgress.ListHidePlayer = new HashMap();
	}
	
	public static boolean IgnException(ConfigurationSection Section, String Str)
	{
		return Section.contains(Str) ? true : false;
	}
	
}
