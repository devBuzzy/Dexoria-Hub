package me.lewys.com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.v1_7_R1.BiomeBase;
import net.minecraft.server.v1_7_R1.BiomeMeta;
import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.EntityPig;
import net.minecraft.server.v1_7_R1.EntityTypes;

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

import customEntitys.crazyPig;

import Bar.BarManager;
import EventManager.Join;
import Fireworks.Show;
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
		Bukkit.getPluginManager().registerEvents(new Join(), this);
		Bukkit.getPluginManager().registerEvents(new HidePlayers(), this);
		Bukkit.getPluginManager().registerEvents(new GameToggler(), this);
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
		  
		  registerEntity("Pig", 90, EntityPig.class, crazyPig.class);
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
            	  Score score2 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE + Currency.getGC(e.getPlayer().getUniqueId().toString())));
            	  score2.setScore(15);
            	  
            	  oldPoints.remove(p.getName());
            	  oldPoints.put(e.getPlayer().getUniqueId(), Currency.getGC(p.getUniqueId().toString()));
            	  
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
				
				if(args.length < 0){
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
				
				if(args[0].equalsIgnoreCase("donate")){
					if(args[1].equals(null)){
						return false;
					}else{
						String donor_name = args[1];
						
						Bukkit.broadcastMessage(ChatColor.BLUE + "Donation >" + ChatColor.GRAY +
								" Thanks " + ChatColor.RED + donor_name + ChatColor.GRAY 
								+ " for donating and helping the server!");
						
						Show s = new Show();
						s.startShow();
					}
				}
			}
		}
		
		return false;
	}
	
	public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass) {
        try {
 
            /*
            * First, we make a list of all HashMap's in the EntityTypes class
            * by looping through all fields. I am using reflection here so we
            * have no problems later when minecraft changes the field's name.
            * By creating a list of these maps we can easily modify them later
            * on.
            */
            List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                }
            }
 
            /*
            * since minecraft checks if an id has already been registered, we
            * have to remove the old entity class before we can register our
            * custom one
            *
            * map 0 is the map with names and map 2 is the map with ids
            */
            if (dataMaps.get(2).containsKey(id)) {
                dataMaps.get(0).remove(name);
                dataMaps.get(2).remove(id);
            }
 
            /*
            * now we call the method which adds the entity to the lists in the
            * EntityTypes class, now we are actually 'registering' our entity
            */
            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);
 
            /*
            * after doing the basic registering stuff , we have to register our
            * mob as to be the default for every biome. This can be done by
            * looping through all BiomeBase fields in the BiomeBase class, so
            * we can loop though all available biomes afterwards. Here, again,
            * I am using reflection so we have no problems later when minecraft
            * changes the fields name
            */
            for (Field f : BiomeBase.class.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(BiomeBase.class.getSimpleName())) {
                    if (f.get(null) != null) {
 
                        /*
                        * this peace of code is being called for every biome,
                        * we are going to loop through all fields in the
                        * BiomeBase class so we can detect which of them are
                        * Lists (again, to prevent problems when the field's
                        * name changes), by doing this we can easily get the 4
                        * required lists without using the name (which probably
                        * changes every version)
                        */
                        for (Field list : BiomeBase.class.getDeclaredFields()) {
                            if (list.getType().getSimpleName().equals(List.class.getSimpleName())) {
                                list.setAccessible(true);
                                @SuppressWarnings("unchecked")
                                List<BiomeMeta> metaList = (List<BiomeMeta>) list.get(f.get(null));
 
                                /*
                                * now we are almost done. This peace of code
                                * we're in now is called for every biome. Loop
                                * though the list with BiomeMeta, if the
                                * BiomeMeta's entity is the one you want to
                                * change (for example if EntitySkeleton matches
                                * EntitySkeleton) we will change it to our
                                * custom entity class
                                */
                                for (BiomeMeta meta : metaList) {
                                    Field clazz = BiomeMeta.class.getDeclaredFields()[0];
                                    if (clazz.get(meta).equals(nmsClass)) {
                                        clazz.set(meta, customClass);
                                    }
                                }
                            }
                        }
 
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}
