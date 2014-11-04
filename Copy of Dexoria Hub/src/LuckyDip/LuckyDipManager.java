package LuckyDip;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class LuckyDipManager implements Listener{
	
	HashMap<String, luckydip> active = new HashMap<>();
	
	public void doLuckDip(Player p, Location ploc){
		luckydip ld = new luckydip(ploc,p);
		
		ld.doLuckyDip();
	}
	
	public void addActivePlayer(String pname, luckydip ld){
		active.put(pname, ld);
	}
	
	public void stopLuckyDip(String playername){
		active.get(playername).stopLava();
		active.get(playername).end();
	}
	
	public void endLuckyDip(String playername){
		active.remove(playername);
	}
	
	public boolean hasLuckyDipRunning(String Playername){
		return active.containsKey(Playername);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if(active.containsKey(e.getPlayer().getName())){
			if(active.get(e.getPlayer().getName()).canmove == false){
		Location from = e.getFrom();
		Location to = e.getTo();
		
		if(from.getX() != to.getX() || from.getZ() != to.getZ())
			e.getPlayer().teleport(from);
			}
		}
	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e){
		if(e.getPlayer().getItemInHand().getType() == Material.CAULDRON_ITEM){
			doLuckDip(e.getPlayer(), e.getPlayer().getLocation());
		}
	}
}
