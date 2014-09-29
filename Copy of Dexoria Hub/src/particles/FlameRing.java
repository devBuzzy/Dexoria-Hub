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

public class FlameRing implements Listener{
	
	/*
	 * just a test, will work on user in the real thing.
	 */
	
	int wich = 1;
	boolean changed = false;
	double lastX;
	double lastY;
	double lastZ;
	
	int task;
	
	List<Location> locs = new ArrayList<Location>();
	
	public void start(final Player p){	
		lastX = p.getLocation().getX();
		lastY = p.getLocation().getY();
		lastZ = p.getLocation().getZ();
		
		for (double t = 0; t < 2*Math.PI; t+=Math.toRadians(6)){
			  Location l = p.getLocation().add(Math.cos(t), 0.2, Math.sin(t));
			  locs.add(l);
			}
		
		final int locamount = locs.size();
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Hub.instance, new Runnable(){

			@Override
			public void run() {
				if(isSame(p.getLocation(), new Location(p.getWorld(), lastX, lastY, lastZ)) == false){
					
					changed = true; 
					ParticleEffect.FLAME.display(p.getLocation(), 0.5f, 0.5f, 0.5f, 0f, 2);
					lastX = p.getLocation().getX();
					lastY = p.getLocation().getY();
					lastZ = p.getLocation().getZ();
					locs.clear();
					changed = true;
					return;
					
				}else if(isSame(p.getLocation(), new Location(p.getWorld(), lastX, lastY, lastZ)) == true){
					
					if(changed == true){
						locs.clear();
						for (double t = 0; t < 2*Math.PI; t+=Math.toRadians(6)){
							  Location l = p.getLocation().add(Math.cos(t), 0.2, Math.sin(t));
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
	
	public void stop(){
		Bukkit.getScheduler().cancelTask(task);
	}
}
