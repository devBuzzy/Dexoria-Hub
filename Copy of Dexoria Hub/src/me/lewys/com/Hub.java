package me.lewys.com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.v1_7_R3.EntityVillager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import utils.HubEntitys;
import utils.NMSUtils;
import Bar.BarManager;
import EventManager.Interact;
import EventManager.Join;
import Fireworks.Show;
import Gadgets.Firework;
import Gadgets.GadgetManager;
import Gadgets.GadgetMenu;
import Gadgets.PaintGrenade;
import Gadgets.SnowGun;
import Gadgets.TnTBow;
import LuckyDip.LuckyDipManager;
import customEntitys.HubVillager;
import dexoria.core.DexCore;

public class Hub extends JavaPlugin implements Listener{
	
	
	File pet_dogF;	
	static FileConfiguration dogFC;
	
	File pet_catF;	
	static FileConfiguration catFC;
	
	File pet_slimeF;	
	static FileConfiguration slimeFC;
	
	public static Hub instance;
	
    private HashMap<String, BukkitRunnable> scoreboardTask = new HashMap<String, BukkitRunnable>();
    Map<UUID, Integer> oldGC = new HashMap<UUID, Integer>();
    Map<UUID, Integer> oldCC = new HashMap<UUID, Integer>();
    Map<UUID, String> oldStaff = new HashMap<UUID, String>();

    @SuppressWarnings("deprecation")
	public void onEnable()
    {
    	
     	for(Entity e : Bukkit.getWorld("Hub").getEntities()){
    		if(e instanceof Player)
    			continue; 
    		e.remove();
    	}
    	
     	
     	Bukkit.getScheduler().scheduleAsyncDelayedTask(this, new Runnable(){

			@Override
			public void run() {
				NMSUtils nms = new NMSUtils();
				nms.registerEntity("Villager", 120, EntityVillager.class, HubVillager.class);
		    	HubEntitys.spawn();
			}
     		
     	}, 10);
    	
    	
      instance = this;
		
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
		Bukkit.getPluginManager().registerEvents(new Interact(), this);
		Bukkit.getPluginManager().registerEvents(new TnTBow(), this);
		Bukkit.getPluginManager().registerEvents(new GadgetMenu(), this);
		Bukkit.getPluginManager().registerEvents(new SnowGun(), this);
		Bukkit.getPluginManager().registerEvents(new BarManager(), this);
		Bukkit.getPluginManager().registerEvents(new PaintGrenade(), this);
		Bukkit.getPluginManager().registerEvents(new GadgetManager(), this);
		Bukkit.getPluginManager().registerEvents(new Join(), this);
		Bukkit.getPluginManager().registerEvents(new LuckyDipManager(), this);
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
		
		Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "" + ChatColor.BOLD + "Game $")); 
		score.setScore(18);
		
  	  
		final Player p = e.getPlayer();
		  scoreboardTask.put(p.getName(), new BukkitRunnable() {
              public void run() {
        		
            	  board.resetScores(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE + oldGC.get(e.getPlayer().getUniqueId())));
            	  Score score2 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE + DexCore.getCurrencySystem().getGC(e.getPlayer().getUniqueId().toString())));
            	  score2.setScore(17);
            	  
            	  board.resetScores(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE + oldCC.get(e.getPlayer().getUniqueId())));
            	  Score score3 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE + DexCore.getCurrencySystem().getCC(e.getPlayer().getUniqueId().toString())));
            	  score3.setScore(14);
            	  
            	  oldGC.remove(e.getPlayer().getUniqueId());
            	  oldGC.put(e.getPlayer().getUniqueId(), DexCore.getCurrencySystem().getGC(p.getUniqueId().toString()));
            	  
            	  oldCC.remove(e.getPlayer().getUniqueId());
            	  oldCC.put(e.getPlayer().getUniqueId(), DexCore.getCurrencySystem().getCC(p.getUniqueId().toString()));
            	  
                      if(!(e.getPlayer().isOnline())){
                    	  
                              scoreboardTask.remove(p.getName());
                              cancel();
                      }
              }
      });
     
        scoreboardTask.get(p.getName()).runTaskTimerAsynchronously(Hub.getPluginInstance(), 0, 20);
        
        
    	Score score100 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "")); 
		score100.setScore(16);
		
	  	Score score1000 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "" + ChatColor.BOLD + "Cosmetic $")); 
			score1000.setScore(15);
		
			Score score500 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BLACK + "")); 
			score500.setScore(13);
			
		Score score4 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "" + ChatColor.BOLD + "Rank")); 
		score4.setScore(12);
        
		
		if(p.hasPermission("hub.owner")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.RED + "OWNER")); 
			score5.setScore(11);
		}else if(p.hasPermission("hub.admin")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.RED + "ADMIN")); 
			score5.setScore(11);
		}else if(p.hasPermission("hub.mod")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.GOLD + "MOD")); 
			score5.setScore(11);
		}else if(p.hasPermission("hub.helper")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.GREEN + "HELPER")); 
			score5.setScore(11);
		}else if(p.hasPermission("hub.ultimate")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.AQUA + "ULTIMATE")); 
			score5.setScore(11);
		}else if(p.hasPermission("hub.platinum")){
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "PLATINUM")); 
			score5.setScore(11);
		}else{
			Score score5 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.GRAY + "Default")); 
			score5.setScore(11);
		}
		
		Score score6 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "")); 
		score6.setScore(10);
		
		Score score7 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + "" + ChatColor.BOLD + "Online Staff")); 
		score7.setScore(9);
		
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
				score8.setScore(8);
				oldStaff.remove(p.getUniqueId());
				oldStaff.put(p.getUniqueId(), "YES");
				}else{
				Score score8 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE +"NO")); 
				score8.setScore(8);
				oldStaff.remove(p.getUniqueId());
				oldStaff.put(p.getUniqueId(), "NO");
				}
			}	
		}, 0, 20);
		
		Score score9 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BOLD + "" + ChatColor.WHITE +"")); 
		score9.setScore(7);
		
		
		Score score10 = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Website")); 
		score10.setScore(6);
		
		Score score11 = objective.getScore(Bukkit.getOfflinePlayer("www.dexoria.com")); 
		score11.setScore(5);
		
		e.getPlayer().setScoreboard(board);	
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		if(scoreboardTask.containsKey(e.getPlayer()))
			scoreboardTask.remove(e.getPlayer());
		if(oldGC.containsKey(e.getPlayer().getUniqueId()))
			oldGC.remove(e.getPlayer().getUniqueId());
		if(oldCC.containsKey(e.getPlayer().getUniqueId()))
			oldCC.remove(e.getPlayer().getUniqueId());
		if(oldStaff.containsKey(e.getPlayer().getUniqueId()))
			oldStaff.remove(e.getPlayer().getUniqueId());
	}
	
	
	/*
	 * Hub Items
	 */
	
	public ItemStack gadgetmenu(){
		ItemStack is = new ItemStack(Material.CHEST);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Gadgets");
		
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack compass(){
		ItemStack is = new ItemStack(Material.COMPASS);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Game Selector");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Chose a game to play!");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack games_enabled(){
		ItemStack is = new ItemStack(Material.SLIME_BALL);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Hub Games Enabled");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Toggle being effected by gadgets");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack games_disabled(){
		ItemStack is = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Hub Games Disabled");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Toggle being effected by gadgets");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack player_enabled(){
		ItemStack is = new ItemStack(Material.TORCH);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Players Enabled");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Toggle player visibility");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	public ItemStack player_disabled(){
		ItemStack is = new ItemStack(Material.REDSTONE_TORCH_OFF);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Players Disabled");
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.WHITE + "Toggle player visibility");
		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if((sender.isOp()) || (sender.hasPermission("hub.owner")) || (!(sender instanceof Player) || (args.length <= 0))){
		if(label.equalsIgnoreCase("hub")){
				
				if(args.length <= 0){
					if(sender instanceof Player){
					final Location hubloc = new Location(Bukkit.getWorld("hub"), -843.5, 17, 22);
					Player p = (Player) sender;
					((Player) sender).sendMessage(ChatColor.BLUE + "Hub > " + ChatColor.GRAY + "You were sent to the hub!");
					
					p.teleport(hubloc);
					
					p.getInventory().clear();
					
					p.getInventory().setHelmet(new ItemStack(Material.AIR));
					p.getInventory().setChestplate(new ItemStack(Material.AIR));
					p.getInventory().setLeggings(new ItemStack(Material.AIR));
					p.getInventory().setBoots(new ItemStack(Material.AIR));
					
					p.getInventory().setItem(0, compass());
					p.getInventory().setItem(4, gadgetmenu());
					p.getInventory().setItem(7, games_enabled());
					p.getInventory().setItem(8, player_enabled());
					
					p.updateInventory();
					}
					
					else{
						Bukkit.getLogger().info("Usage: /hub <reload | stop>");
					}
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
}
