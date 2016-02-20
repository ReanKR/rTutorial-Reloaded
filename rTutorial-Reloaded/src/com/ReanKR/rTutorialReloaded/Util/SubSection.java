package com.ReanKR.rTutorialReloaded.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ReanKR.rTutorialReloaded.rTutorialReloaded;

public class SubSection
{
	/* 
	 * RepColor : ���� ��ȣ ġȯ
	 * VariableSub : ���� �� %var%�� object�� ġȯ��
	 * Sub : ���� �� %player%��  �÷��̾� �̸����� ġȯ��
	 * VariableColorSub : ���� �� %var%�� object�� ġȯ�ϰ� ���� ��ȣ ġȯ��
	 * SubMsg : message.yml �� �޼��� �żҵ� ���� ���, %player%�� �÷��̾� �̸����� ġȯ, ���� �� �޼����� ��� ����, ���λ� ���� �Ǵ� ����
	 * Msg : ���� ������ �Ϲ������� �޼����� ����� �� ��� (���� ��ȣ ġȯ, ���λ� ����)
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
	
	
	/* Return�� true�� �ϸ� ���ڿ��� ��ȯ�ϰ�,false��� �÷��̾�� �޼����� ����ϵ��� �Ѵ�. */ 
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
