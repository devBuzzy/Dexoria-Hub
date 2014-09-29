package particles;

import me.lewys.com.Hub;
import me.lewys.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Love implements Listener{
	
	int task;
	
	@EventHandler
	public void start(final Player p){
		
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Hub.instance, new Runnable(){

			@Override
			public void run() {
				ParticleEffect.HEART.display(p.getLocation().add(0,0.5,0), 0.3f, 0.5f, 0.3f, 0f, 1);
		}
	}, 0, 10);
}
	
	public void stop(){
		Bukkit.getScheduler().cancelTask(task);
	}
	
}
