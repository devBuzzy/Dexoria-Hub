package Gadgets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class GadgetManager implements Listener{
	public static List<String> immune = new ArrayList<String>();
	
	public static boolean isImmune(String player_name){
		if(immune.contains(player_name))
			return true;
		return false;
	}
	
	public static void addImmunity(String player_name){
		immune.add(player_name);
	}
	
	public static void removeImmunity(String player_name){
		immune.remove(player_name);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		if(immune.contains(e.getPlayer().getName())){
			immune.remove(e.getPlayer().getName());
		}
	}
}
