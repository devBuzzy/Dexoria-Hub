package Fireworks;

import me.lewys.com.Hub;
import me.lewys.com.RandomFirework;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Show {
	
	public Show(String donor) {
	}
	
	boolean hasFinished = false;
	
	public int task;
	public int fireworks = 0;
	public int cooldown = 60;
	
	public String donor;
	
	public void startShow() {
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Hub.instance, new Runnable() {
			
			@Override
			public void run() {
			fireworks++;
			
			if(fireworks <= 50) {
				int x = getRandomNumber(-866, -816);
				int y = getRandomNumber(13, 17);
				int z = getRandomNumber(-3, 48);
				
				Location loc = new Location(Bukkit.getWorld("hub"), x,y,z);
				RandomFirework.getManager().launchRandomFirework(loc);
				
				}else{
					cooldown--;
					
					if(cooldown < 0) {
						hasFinished = true;
						Bukkit.getScheduler().cancelTask(task);
					}
				}
			}
		}, 5, 5);
	}
	
	private int getRandomNumber(int Min, int Max){
		return Min + (int)(Math.random() * ((Max - Min) + 1));
	}
	
	public boolean isFinished() {
		return hasFinished;	
	}
}
