package particles;

import java.util.ArrayList;
import java.util.List;

import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class test implements Listener{
	
	/*
	 * just a test, will work on user in the real thing.
	 */
	
	int wich = 1;
	boolean changed = false;
	double lastX;
	double lastY;
	double lastZ;

	List<Location> locs = new ArrayList<Location>();
	
	@EventHandler
	public void onMove(final PlayerInteractEvent e){
		
		lastX = e.getPlayer().getLocation().getX();
		lastY = e.getPlayer().getLocation().getY();
		lastZ = e.getPlayer().getLocation().getZ();
		
		final Player player = e.getPlayer();
		
		for (double t = 0; t < 2*Math.PI; t+=Math.toRadians(6)){
			  Location l = player.getLocation().add(Math.cos(t), 0.2, Math.sin(t));
			  locs.add(l);
			}
		
		final int locamount = locs.size();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Hub.instance, new Runnable(){

			@Override
			public void run() {
				if(isSame(e.getPlayer().getLocation(), new Location(e.getPlayer().getWorld(), lastX, lastY, lastZ)) == false){
					
					changed = true; 
					ParticleEffect.FLAME.display(e.getPlayer().getLocation(), 0.5f, 0.5f, 0.5f, 0f, 2);
					lastX = e.getPlayer().getLocation().getX();
					lastY = e.getPlayer().getLocation().getY();
					lastZ = e.getPlayer().getLocation().getZ();
					locs.clear();
					changed = true;
					return;
					
				}else if(isSame(e.getPlayer().getLocation(), new Location(e.getPlayer().getWorld(), lastX, lastY, lastZ)) == true){
					
					if(changed == true){
						locs.clear();
						for (double t = 0; t < 2*Math.PI; t+=Math.toRadians(6)){
							  Location l = player.getLocation().add(Math.cos(t), 0.5, Math.sin(t));
							  locs.add(l);
							}
					}
					
					if(locs.isEmpty())
						return;
					
					if(wich < locamount){
						Location finalloc = locs.get(wich);
						ParticleEffect.FLAME.display(finalloc, 0f, 0f, 0f, 0f, 1);
						wich++;
					}else{
						wich=1;	
				}
			}
		}
	}, 1, 1);
}
	
	public boolean isSame(Location one, Location two){
		if((one.getX() == two.getX()) && (one.getY() == two.getY() && (one.getZ() == two.getZ())))
				return true;
		else return false;
	}
}
