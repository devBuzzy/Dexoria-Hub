package utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import customEntitys.HubVillager;

public class HubEntitys {
	
	
	public static void spawn(){
		for(Location loc : mineageddon()){
			final Villager v = HubVillager.spawn(loc);
			v.setAdult();
			v.setCustomName(ChatColor.RED + "" + ChatColor.BOLD + "Mineageddon");
			v.setCustomNameVisible(true);
			v.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 10000000,100,false));
		}
	}
	
	public static ArrayList<Location> mineageddon(){
		ArrayList<Location> loc = new ArrayList<>();;
		
		loc.add(new Location(Bukkit.getWorld("Hub"), -840.5, 1, -45.5));
		loc.add(new Location(Bukkit.getWorld("Hub"), -845.5, 1, -45.5));
		
		return loc;
	}
}
