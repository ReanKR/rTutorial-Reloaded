package com.ReanKR.rTutorialReloaded.Listeners;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ReanKR.rTutorialReloaded.Util.SoundCreation;
import com.ReanKR.rTutorialReloaded.Util.SubSection;
import com.ReanKR.rTutorialReloaded.rTutorialReloaded;

public class CreateNewLocation implements Listener
{
	private SoundCreation SC = new SoundCreation();
	
	@EventHandler
	public void CreateNewMethod(AsyncPlayerChatEvent e)
	{
			if(! rTutorialReloaded.LocationName.containsKey(e.getPlayer()) && 
					(rTutorialReloaded.MainMessage.containsKey(e.getPlayer()) && rTutorialReloaded.SubMessage.containsKey(e.getPlayer())))
			{
				rTutorialReloaded.LocationName.put(e.getPlayer(), e.getMessage());
				rTutorialReloaded.IsCreateNewLocation.remove(e.getPlayer());
				rTutorialReloaded.SavedNewLocation.put(e.getPlayer(), true);
				SubSection.SubMsg("CompleteCreatingMethod", e.getPlayer(), false, true);
				SubSection.Msg(e.getPlayer(), (SubSection.VariableSub(SubSection.SubMsg("ContinueCommand", e.getPlayer(), true, false), "/rt create save")));
				SubSection.Msg(e.getPlayer(), (SubSection.VariableSub(SubSection.SubMsg("CancelCommand", e.getPlayer(), true, false), "/rt create cancel")));
				e.setCancelled(true);
			}
			if(rTutorialReloaded.IsCreateNewLocation.containsKey(e.getPlayer()))
			{
				if(rTutorialReloaded.IsCreateNewLocation.get(e.getPlayer()).booleanValue())
				{
					if(! rTutorialReloaded.MainMessage.containsKey(e.getPlayer()))
					{
						rTutorialReloaded.MainMessage.put(e.getPlayer(), e.getMessage());
						SubSection.Msg(e.getPlayer(), "보조 메세지를 입력하여 주십시오. 없으면 없음 또는 None이라고 입력해주십시오.");
						SubSection.Msg(e.getPlayer(), "(TitleAPI를 사용할 시 보조 메세지가 사용됩니다.)");
						e.setCancelled(true);
					}
					else
					{
						rTutorialReloaded.SubMessage.put(e.getPlayer(), e.getMessage());
						SubSection.Msg(e.getPlayer(), "현재 설정한 위치 이름을 정해주세요. (인덱스 이름)");
						e.setCancelled(true);
					}
				}
			}
	}
	
	@EventHandler
	public void BlockingWhenCreating(PlayerCommandPreprocessEvent e)
	{
		if(rTutorialReloaded.IsCreateNewLocation.containsKey(e.getPlayer()))
		{
			if(rTutorialReloaded.IsCreateNewLocation.get(e.getPlayer()).booleanValue())
			{
				if(!(e.getMessage().equalsIgnoreCase("/rt create cancel") || e.getMessage().equalsIgnoreCase("/rtutorial create cancel")))
				{
					SC.PlayerSound(e.getPlayer(), Sound.ANVIL_LAND, 1.2F, 1.7F);
					SubSection.SubMsg("BlockCommandWhenCreate", e.getPlayer(), false, true);
					SubSection.Msg(e.getPlayer(), (SubSection.VariableSub(SubSection.SubMsg("CancelCommand", e.getPlayer(), true, false), "/rt create cancel")));
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void PlayerQuit(PlayerQuitEvent e)
	{
		if(rTutorialReloaded.IsCreateNewLocation.containsKey(e.getPlayer()) || rTutorialReloaded.SavedNewLocation.containsKey(e.getPlayer()))
		{
			if(rTutorialReloaded.IsCreateNewLocation.get(e.getPlayer()) || rTutorialReloaded.SavedNewLocation.get(e.getPlayer()))
			{
				rTutorialReloaded.MainMessage.remove(e.getPlayer());
				rTutorialReloaded.SubMessage.remove(e.getPlayer());
				rTutorialReloaded.IsCreateNewLocation.remove(e.getPlayer());
				rTutorialReloaded.SavedNewLocation.remove(e.getPlayer());
			}
		}
		return;
	}
}
