package LuckyDip;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LuckyDipManager {
	
	HashMap<String, luckydip> active = new HashMap<>();
	
	public void doLuckDip(Player p, Location ploc){
		luckydip ld = new luckydip(ploc,p);
		
		ld.doLuckyDip();
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
}
