package com.ReanKR.rTutorialReloaded.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ReanKR.rTutorialReloaded.rTutorialReloaded;

public class SubSection
{
	/* 
	 * RepColor : 색깔 기호 치환
	 * VariableSub : 문장 중 %var%를 object로 치환함
	 * Sub : 문장 중 %player%를  플레이어 이름으로 치환함
	 * VariableColorSub : 문장 중 %var%를 object로 치환하고 색깔 기호 치환함
	 * SubMsg : message.yml 중 메세지 매소드 내용 출력, %player%를 플레이어 이름으로 치환, 게임 내 메세지로 출력 가능, 접두사 유무 판단 가능
	 * Msg : 게임 내에서 일반적으로 메세지를 출력할 때 사용 (색깔 기호 치환, 접두사 붙임)
	 * */

	public static String Sub(String str, Player p)
	{
		String replace = str.replaceAll("%player%", p.getName());
		return replace;
	}
	
	public static String RepColor(String Str)
	{
		String replace = ChatColor.translateAlternateColorCodes('&', Str);
		return replace;
	}
	
	public static String VariableSub(String str, Object object)
	{
		String replace = str.replaceAll("%var%", String.valueOf(object));
		return replace;
	}
	
	public static String VariableColorSub(String Str, Object object)
	{
		String replace = RepColor(Str);
		replace = VariableSub(replace, String.valueOf(object));
		return replace;
	}
	
	
	/* Return을 true로 하면 문자열을 반환하고,false라면 플레이어에게 메세지를 출력하도록 한다. */ 
	public static String SubMsg(String MessageMethod, Player player, Boolean Return, Boolean AddPrefix)
	{
	    String Message = (String)rTutorialReloaded.SystemMessage.get(MessageMethod);
	    String Replacement = Message.replaceAll("%player%", player.getName());
	    if(Return)
	    {
	    	if(AddPrefix) return RepColor(rTutorialReloaded.Prefix + Replacement);
	    	else return RepColor(Replacement);
	    }
	    else
	    {
	    	if(AddPrefix) player.sendMessage(RepColor(rTutorialReloaded.Prefix + Replacement));
	    	else player.sendMessage(RepColor(Replacement));
	    }
	    return null;
	}
	
	public static void Msg(Player p, String str)
	{
		p.sendMessage(SubSection.RepColor(rTutorialReloaded.Prefix + str));
	}
	
	@Deprecated
	public static void GameMsg(Player p, String Str)
	{
		p.sendMessage(rTutorialReloaded.Prefix + Str);
	}
}
