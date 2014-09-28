package particles;

import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Love implements Listener{
	
	/*
	 * just a test, will work on user in the real thing.
	 */
	
	
	@EventHandler
	public void onMove(final PlayerInteractEvent e){
		
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Hub.instance, new Runnable(){

			@Override
			public void run() {
				ParticleEffect.HEART.display(e.getPlayer().getLocation().add(0,0.5,0), 0.3f, 0.5f, 0.3f, 0f, 1);
		}
	}, 0, 10);
}
	
}
