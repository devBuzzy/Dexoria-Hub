package me.lewys.com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import Bar.BarManager;
import Gadgets.Firework;
import Gadgets.GadgetManager;
import Gadgets.GadgetMenu;
import Gadgets.PaintGrenade;
import Gadgets.SnowGun;
import Gadgets.TnTBow;

public class Hub extends JavaPlugin implements Listener{
	
	
	File pet_dogF;	
	static FileConfiguration dogFC;
	
	File pet_catF;	
	static FileConfiguration catFC;
	
	File pet_slimeF;	
	static FileConfiguration slimeFC;
	
	public static Hub instance;
	
    private HashMap<String, BukkitRunnable> scoreboardTask = new HashMap<String, BukkitRunnable>();
    Map<UUID, Integer> oldPoints = new HashMap<UUID, Integer>();
    Map<UUID, String> oldStaff = new HashMap<UUID, String>();
	
	public void onEnable(){
		
		RandomFirework.getManager().addColors();
		RandomFirework.getManager().addTypes();
		
		instance = this;
		
		dogFC = new YamlConfiguration();		
		pet_dogF = new File(getDataFolder(), "Pet_Dog.yml");
		
		catFC = new YamlConfiguration();	
		pet_catF = new File(getDataFolder(), "Pet_Cat.yml");
		
		slimeFC = new YamlConfiguration();	
		pet_slimeF = new File(getDataFolder(), "Pet_Slime.yml");
		
		 try{
			 firstRun();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 loadYamls();
		 
		Bukkit.getPluginManager().registerEvents(new Protection(), this);
		Bukkit.getPluginManager().registerEvents(new Firework(), this);
		Bukkit.getPluginManager().registerEvents(new TnTBow(), this);
		Bukkit.getPluginManager().registerEvents(new GadgetMenu(), this);
		Bukkit.getPluginManager().registerEvents(new SnowGun(), this);
		Bukkit.getPluginManager().registerEvents(new BarManager(), this);
		Bukkit.getPluginManager().registerEvents(new PaintGrenade(), this);
		Bukkit.getPluginManager().registerEvents(new GadgetManager(), this);
		Bukkit.getPluginManager().registerEvents(this, this);
		
		  if(!pet_dogF.exists()){
		        pet_dogF.getParentFile().mkdirs();
		    }
		  
		  if(!pet_catF.exists()){
			  pet_catF.getParentFile().mkdirs();
		  }
		  
		  if(!pet_slimeF.exists()){
			  pet_slimeF.getParentFile().mkdirs();
		  }
	}
	
	public void onDisable(){
		saveYamls();
	}
	
	private void firstRun() throws Exception {
	    if(!pet_dogF.exists()){
	    	pet_dogF.getParentFile().mkdirs();
	        copy(getResource("config.yml"), pet_dogF);
	    }
	    
	    if(!pet_catF.exists()){
	    	pet_catF.getParentFile().mkdirs();
	        copy(getResource("config.yml"), pet_catF);
	    }
	    
	    if(!pet_slimeF.exists()){
	    	pet_slimeF.getParentFile().mkdirs();
	        copy(getResource("config.yml"), pet_slimeF);
	    }
	}
	
	private void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void saveYamls() {
	    try {
	    	dogFC.save(pet_dogF);
	    	catFC.save(pet_catF);
	    	slimeFC.save(pet_slimeF);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void loadYamls() {
	    try {
	    	dogFC.load(pet_dogF);
	    	catFC.load(pet_catF);
	    	slimeFC.load(pet_slimeF);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static FileConfiguration getpet_dogFile(){
		return dogFC;
	}
	
	public static FileConfiguration getpet_catFile(){
		return catFC;
	}
	
	public static FileConfiguration getpet_slimeFile(){
		return slimeFC;
	}
	
	public static Hub getPluginInstance(){
		return instance;
	}
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		
		final Scoreboard board = manager.getNewScoreboard();
		
		final Objective objective = board.registerNewObjective("test", "dummy");
		
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		objective.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Dexoria");
		
		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "" + ChatColor.BOLD + "Points")); 
		score.setScore(16);
		
  	  
   Score score1 = objective.getScore(Bukkit.getOfflinePlayer("---------"));
		score1.setScore(14);
		
		Score score6 = objective.getScore(Bukkit.getOfflinePlayer("----------")); 
		score6.setScore(11);
		
		final Player p = e.getPlayer();
		  scoreboardTask.put(p.getName(), new BukkitRunnable() {
              public void run() {
        		
            	  board.resetScores(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE + oldPoints.get(e.getPlayer().getUniqueId())));
            	  Score score2 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE + Points.getPoints(e.getPlayer().getName())));
            	  score2.setScore(15);
            	  
            	  oldPoints.remove(p.getName());
            	  oldPoints.put(e.getPlayer().getUniqueId(), Points.getPoints(p.getName()));
            	  
                      if(!(e.getPlayer().isOnline())){
                    	  
                              scoreboardTask.remove(p.getName());
                              cancel();
                      }
              }
      });
     
        scoreboardTask.get(p.getName()).runTaskTimer(Hub.getPluginInstance(), 0, 20);
        
		
		Score score4 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "" + ChatColor.BOLD + "Rank")); 
		score4.setScore(13);
        
		
		if(p.hasPermission("hub.owner")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.RED + "OWNER")); 
			score5.setScore(12);
		}else if(p.hasPermission("hub.admin")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.RED + "ADMIN")); 
			score5.setScore(12);
		}else if(p.hasPermission("hub.mod")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.GOLD + "MOD")); 
			score5.setScore(12);
		}else if(p.hasPermission("hub.helper")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.GREEN + "HELPER")); 
			score5.setScore(12);
		}else if(p.hasPermission("hub.ultimate")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.AQUA + "ULTIMATE")); 
			score5.setScore(12);
		}else if(p.hasPermission("hub.platinum")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "PLATINUM")); 
			score5.setScore(12);
		}else{
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.GRAY + "Default")); 
			score5.setScore(12);
		}
		
		Score score7 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.AQUA + "Online Staff")); 
		score7.setScore(10);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			boolean staff = false;
			
			@Override
			public void run() {
				
				board.resetScores(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE + oldStaff.get(p.getUniqueId())));
				
				for(Player p : Bukkit.getOnlinePlayers()){
				if(p.hasPermission("hub.staff")){	
						staff = true;
					}
				}
				if(staff == true){
				Score score8 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE +"YES")); 
				score8.setScore(9);
				oldStaff.remove(p.getUniqueId());
				oldStaff.put(p.getUniqueId(), "YES");
				}else{
				Score score8 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE +"NO")); 
				score8.setScore(9);
				oldStaff.remove(p.getUniqueId());
				oldStaff.put(p.getUniqueId(), "NO");
				}
			}	
		}, 0, 20);
		
		Score score9 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE +"----------")); 
		score9.setScore(8);
		
		
		Score score10 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.DARK_RED +"Website:")); 
		score10.setScore(7);
		
		Score score11 = objective.getScore(Bukkit.getOfflinePlayer("www.dexoria.com")); 
		score11.setScore(6);
		
		e.getPlayer().setScoreboard(board);	
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		if(scoreboardTask.containsKey(e.getPlayer()))
			scoreboardTask.remove(e.getPlayer());
		if(oldPoints.containsKey(e.getPlayer().getUniqueId()))
			oldPoints.remove(e.getPlayer().getUniqueId());
		if(oldStaff.containsKey(e.getPlayer().getUniqueId()))
			oldStaff.remove(e.getPlayer().getUniqueId());
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("hub")){
			if((sender.isOp()) || (!(sender instanceof Player))){
				
				if((args.length < 0) || args.length > 1){
					sender.sendMessage("ERROR: Not enough argumenets!");
					sender.sendMessage("Usage: /hub <reload | stop>");
					return false;
				}
				
				if(args[0].equalsIgnoreCase("reload")){
					for(Player p : Bukkit.getOnlinePlayers())					
						p.kickPlayer(ChatColor.BLUE + "Hub >" 
					+ ChatColor.WHITE + "The hub is reloading, see you soon!");
					
					Bukkit.dispatchCommand(sender, "reload");
					
					return true;
				}
				
				if(args[0].equalsIgnoreCase("stop")){
					for(Player p : Bukkit.getOnlinePlayers())					
						p.kickPlayer(ChatColor.BLUE + "Hub >" 
					+ ChatColor.WHITE + "The server is shuting down, see you soon!");
					
					Bukkit.dispatchCommand(sender, "stop");
					
					return true;
				}
			}
		}
		
		return false;
	}
	
}
