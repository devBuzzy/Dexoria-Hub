package Fireworks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class FireworkShow {
	
	public static List<String> wait_list = new ArrayList<>();
	public static List<Show> runningshows = new ArrayList<>();
	
	public static void doShow(int AmountOfFireworks, Location loc1, Location loc2, String donor){
		
	}
	
	private static boolean areShowsWaiting() {
		if(wait_list.size() <= 0) 
			return false;	
		
		return true;
	}
	
	private static String getNextShow(){
		return wait_list.get(1);
	}
	
	private static void addWaitingShow(String string){
		wait_list.add(string);
	}
}