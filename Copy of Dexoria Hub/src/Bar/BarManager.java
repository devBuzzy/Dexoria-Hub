package Bar;

import java.util.HashMap;
import java.util.Random;

import me.lewys.com.Hub;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BarManager implements Listener{

	HashMap<Player, BukkitRunnable> bartask = new HashMap<Player, BukkitRunnable>();
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent e){
		doBar(e.getPlayer());
	}
	
	public void doBar(final Player p){
		bartask.put(p, new BukkitRunnable(){
			int msg = -1;
			@Override
			public void run() {
				
				msg ++;
				
				Random r = new Random();
				ChatColor c = ChatColor.values()[r.nextInt(ChatColor.values().length)];
				while(c == ChatColor.STRIKETHROUGH || c == ChatColor.ITALIC
					|| 	c == ChatColor.BOLD || c == ChatColor.MAGIC 
					|| c == ChatColor.UNDERLINE || c == ChatColor.BLACK 
					|| c == ChatColor.GRAY 	|| c == ChatColor.WHITE 
					|| c == ChatColor.DARK_GREEN) {
				c = ChatColor.values()[r.nextInt(ChatColor.values().length)];
				}
				
				if(msg >= 25){
				msg = -1;
				return;
				}
				if(msg >= 20){
				BarAPI.setMessage(p, c + "" + ChatColor.BOLD + "Dexoria: " + ChatColor.WHITE + "New Rank:" + ChatColor.GREEN + " Founder", true);
				return;
				}
				if(msg >= 15){
				BarAPI.setMessage(p, c + "" + ChatColor.BOLD + "Dexoria: " + ChatColor.AQUA + "" + ChatColor.BOLD + "Custom MiniGames", true);
				return;
				}
				if(msg >= 10){
				BarAPI.setMessage(p, c + "" + ChatColor.BOLD + "Dexoria: " + ChatColor.GOLD + "www.dexoria.com", true);
				return;
				}
				else{
				BarAPI.setMessage(p, c + "" + ChatColor.BOLD + "Dexoria: " + ChatColor.RED + "Enjoy your stay!", true);
				return;	
				}
			}
			
		});
		bartask.get(p).runTaskTimer(Hub.getPluginInstance(), 10, 10);
	}
	
	@EventHandler
	public void onPlayerExit(PlayerQuitEvent e){
		bartask.remove(e.getPlayer());
	}
}
