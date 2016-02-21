package com.ReanKR.rTutorialReloaded.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ReanKR.rTutorialReloaded.rTutorialProgress;
import com.ReanKR.rTutorialReloaded.Util.SubSection;

public class DisableManager
{
	public static void DisablePlugin()
	{
		for(Player player : Bukkit.getServer().getOnlinePlayers())
		{
			if(rTutorialProgress.taskID.containsKey(player))
			{
				rTutorialProgress.endTask(player, true);
				SubSection.Msg(player, "튜토리얼 시스템이 예상치 않게 종료되었습니다.");
				BackupManager.SaveUnexpected(player);
			}
		}
	}
}
