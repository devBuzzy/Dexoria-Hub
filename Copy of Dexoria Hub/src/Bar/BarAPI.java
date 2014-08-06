package Bar;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import me.lewys.com.Hub;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BarAPI {
    public static class FakeDragon {
        private class LazyPacket { //THANKS CAPTAINBERN :D
            protected Object crafted_packet = null;
 
            /**
             * This is a little class that makes it possible for me
             * to easily craft/send packets. It has been created with the
             * aim to make it as easy as possible.
             */
            public LazyPacket(String name){
                try{
                    crafted_packet = BarAPI.BarReflectionUtils.getNMSClass(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
 
            /**
             * Sets a public field value of a class/packet.
             */
            public void setPublicValue(String field, Object value){
                try{
                    Field f = crafted_packet.getClass().getField(field);
 
                    f.setAccessible(true);
                    f.set(crafted_packet, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
 
            /**
             * Sets a private field value of a class/packet.
             */
            public void setPrivateValue(String field, Object value){
                try{
                    Field f = crafted_packet.getClass().getDeclaredField(field);
 
                    f.setAccessible(true);
                    f.set(crafted_packet, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
 
            /**
             * Returns the packet-object.
             */
            public Object getPacketObject(){
                return this.crafted_packet;
            }
 
            /**
             * Method used to send the packet to specified player.
             */
            public void send(Player player){
                try {
                    Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                    Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
 
                    BarAPI.BarReflectionUtils.getMethod("sendPacket", playerConnection.getClass(), 1).invoke(playerConnection, crafted_packet);
                } catch (Exception e) {
                   Hub.instance.getLogger().warning("Failed to send packet to player: " + player.getName() + "!");
                }
            }
        }
 
        private class LazyDataWatcher { //THANKS CAPTAINBERN :D
            private Object datawatcher;
 
            public LazyDataWatcher(){
                try {
                    datawatcher = BarAPI.BarReflectionUtils.getNMSClass("DataWatcher");
                } catch (Exception e){
 
                }
            }
 
            public void write(int i, Object object){
                try{
                    Method method = datawatcher.getClass().getMethod("a", int.class, Object.class);
 
                    method.invoke(datawatcher, i, object);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
 
            public Object getDataWatcherObject(){
                return datawatcher;
            }
        }
 
        public static final int ENTITY_ID = 696969;
        public static final float MAX_HEALTH = 200.0F;
 
        private Player player;
 
        private String name = "null";
 
        private float health = FakeDragon.MAX_HEALTH;
 
        private boolean invisible = false;
        private boolean exists = false;
 
        private LazyDataWatcher dataWatcher;
 
        public FakeDragon(Player player){
            this.player = player;
        }
 
        public Player getPlayer(){
            return player;
        }
 
        public String getName(){
            return name;
        }
 
        public void setName(String name){
            this.name = name;
 
            spawn();
            update();
        }
 
        public float getHealth(){
            return health;
        }
 
        public void setHealth(float health){
            this.health = health;
 
            spawn();
            update();
        }
 
        public boolean isInvisible(){
            return invisible;
        }
 
        public void setInvisible(boolean invisible){
            this.invisible = invisible;
 
            spawn();
            update();
        }
 
        public boolean doesExist(){
            return exists;
        }
 
        public void spawn(){
            if(!(exists)){
                updateWatcher();
 
                LazyPacket mobSpawnPacket = new LazyPacket("Packet24MobSpawn");
                Location loc = player.getLocation().clone().subtract(0, 200, 0);
                Vector velocity = new Vector(0, 0, 0);
 
                mobSpawnPacket.setPublicValue("a", FakeDragon.ENTITY_ID);
                mobSpawnPacket.setPublicValue("b", (byte) EntityType.ENDER_DRAGON.getTypeId());
                mobSpawnPacket.setPublicValue("c", (int) Math.floor(loc.getBlockX() * 32.0D));
                mobSpawnPacket.setPublicValue("d", (int) Math.floor(loc.getBlockY() * 32.0D));
                mobSpawnPacket.setPublicValue("e", (int) Math.floor(loc.getBlockZ() * 32.0D));
                mobSpawnPacket.setPublicValue("f", (byte) ((int) ((loc.getPitch() * 256.0F) / 360.0F)));
                mobSpawnPacket.setPublicValue("g", (byte) 0);
                mobSpawnPacket.setPublicValue("h", (byte) ((int) ((loc.getYaw() * 256.0F) / 360.0F)));
                mobSpawnPacket.setPublicValue("i", (byte) velocity.getX());
                mobSpawnPacket.setPublicValue("j", (byte) velocity.getY());
                mobSpawnPacket.setPublicValue("k", (byte) velocity.getZ());
                mobSpawnPacket.setPrivateValue("t", dataWatcher.getDataWatcherObject());
                mobSpawnPacket.send(player);
 
                exists = true;
            }
        }
 
        public void destroy(){
            if(exists){
                LazyPacket destroyEntityPacket = new LazyPacket("Packet29DestroyEntity");
 
                destroyEntityPacket.setPublicValue("a", new int[]{ FakeDragon.ENTITY_ID });
                destroyEntityPacket.send(player);
 
                exists = false;
            }
        }
 
        public void update(){
            updateWatcher();
 
            if(exists){
                //Metadata packet
                LazyPacket metadataPacket = new LazyPacket("Packet40EntityMetadata");
 
                metadataPacket.setPublicValue("a", FakeDragon.ENTITY_ID);
 
                Method watcherC = BarAPI.BarReflectionUtils.getMethod("c", dataWatcher.getDataWatcherObject().getClass());
 
                try{
                    metadataPacket.setPrivateValue("b", watcherC.invoke(dataWatcher.getDataWatcherObject()));
                } catch(IllegalAccessException e){
                    e.printStackTrace();
                } catch(InvocationTargetException e){
                    e.printStackTrace();
                }
 
                metadataPacket.send(player);
 
                //Teleport packet
                LazyPacket teleportPacket = new LazyPacket("Packet34EntityTeleport");
                Location loc = player.getLocation().clone().subtract(0, 200, 0);
 
                teleportPacket.setPublicValue("a", FakeDragon.ENTITY_ID);
                teleportPacket.setPublicValue("b", (int) Math.floor(loc.getBlockX() * 32.0D));
                teleportPacket.setPublicValue("c", (int) Math.floor(loc.getBlockY() * 32.0D));
                teleportPacket.setPublicValue("d", (int) Math.floor(loc.getBlockZ() * 32.0D));
                teleportPacket.setPublicValue("e", (byte) ((int) ((loc.getYaw() * 256.0F) / 360.0F)));
                teleportPacket.setPublicValue("f", (byte) ((int) ((loc.getPitch() * 256.0F) / 360.0F)));
                teleportPacket.send(player);
            }
        }
 
        public void updateWatcher(){
            dataWatcher = new LazyDataWatcher();
 
            dataWatcher.write(0, (invisible ? (Byte) (byte) 0x20 : (Byte) (byte) 0));
            dataWatcher.write(6, (Float) (float) health);
            dataWatcher.write(7, (Integer) (int) 0);
            dataWatcher.write(8, (Byte) (byte) 0);
            dataWatcher.write(10, (String) name);
            dataWatcher.write(11, (Byte) (byte) 1);
        }
    }
 
    public static class BarReflectionUtils {
        public static Object getNMSClass(String name, Object... args) throws Exception {
            Class<?> c = Class.forName(BarReflectionUtils.getNMSPackageName() + "." + name);
            int params = 0;
            if (args != null) {
                params = args.length;
            }
            for (Constructor<?> co : c.getConstructors()) {
                if (co.getParameterTypes().length == params) {
                    return co.newInstance(args);
                }
            }
 
            return null;
        }
 
        public static Class<?> getNMSClassExact(String name, Object... args){
            Class<?> c;
 
            try{
                c = Class.forName(BarReflectionUtils.getNMSPackageName() + "." + name);
            }catch(Exception e){
                return null;
            }
 
            return c;
        }
 
        public static Object getOBCClass(String name, Object... args) throws Exception {
            Class<?> c = Class.forName(BarReflectionUtils.getOBCPackageName() + "." + name);
            int params = 0;
            if (args != null) {
                params = args.length;
            }
            for (Constructor<?> co : c.getConstructors()) {
                if (co.getParameterTypes().length == params) {
                    return co.newInstance(args);
                }
            }
            return null;
        }
 
        public static Class<?> getOBCClassExact(String name, Object... args){
            Class<?> c;
            try{
                c= Class.forName(BarReflectionUtils.getOBCPackageName() + "." + name);
            }catch(Exception e){
                return null;
            }
            return c;
        }
 
        public static Method getMethod(String name, Class<?> c, int params){
            for (Method m : c.getMethods()){
                if (m.getName().equals(name) && m.getParameterTypes().length == params){
                    return m;
                }
            }
            return null;
        }
 
        public static Method getMethod(String name, Class<?> c){
            for (Method m : c.getMethods()) {
                if (m.getName().equals(name)) {
                    return m;
                }
            }
            return null;
        }
 
        public static Field getField(String name, Class<?> c){
            for (Field f : c.getFields()) {
                if (f.getName().equals(name)) {
                    return f;
                }
            }
            return null;
        }
 
        public static void setValue(Object instance, String fieldName, Object value) throws Exception{
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        }
 
        public static String getNMSPackageName(){
            return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        }
 
        public static String getOBCPackageName(){
            return "org.bukkit.craftbukkit" + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        }
 
        public static Object bukkitPlayerToEntityPlayer(Player player){
            Object entityPlayer = null;
 
            try{
                entityPlayer = BarReflectionUtils.getMethod("getHandle", player.getClass(), 0).invoke(player);
            } catch(Exception e){
                return null;
            }
 
            return entityPlayer;
        }
    }
 
    private static HashMap<String, FakeDragon> playerDragons = new HashMap<String, FakeDragon>();
 
    public static String getMessage(Player player){
        FakeDragon dragon = getDragon(player);
 
        return dragon.getName();
    }
 
    public static void setMessage(Player player, String msg, boolean override){
        if(hasBar(player)){
            if(!(override)){
                return;
            }
        }
 
        FakeDragon dragon = getDragon(player);
 
        if(msg.length() > 64){
            msg = msg.substring(0, 63);
        }
 
        dragon.setName(msg);
    }
 
    public static float getHealth(Player player){
        FakeDragon dragon = getDragon(player);
 
        return dragon.getHealth();
    }
 
    public static void setHealth(Player player, float health, boolean override){
        if(hasBar(player)){
            if(!(override)){
                return;
            }
        }
 
        FakeDragon dragon = getDragon(player);
 
        dragon.setHealth(health);
    }
 
    public static void setPercent(Player player, float percent, boolean override){
        if(hasBar(player)){
            if(!(override)){
                return;
            }
        }
 
        FakeDragon dragon = getDragon(player);
        float health = (percent / 100.0F) * BarAPI.FakeDragon.MAX_HEALTH;
 
        dragon.setHealth(health);
    }
 
    public static void displayBar(final Player player, String msg, float percent, int seconds, boolean override){
        setMessage(player, msg, override);
        setPercent(player, percent, override);
 
        new BukkitRunnable(){
            @Override
            public void run(){
                removeBar(player);
            }
        }.runTaskLater(Hub.instance, (seconds * 20));
    }
 
    public static void displayLoadingBar(final Player player, String msg, final String endMsg, int seconds, final boolean loadUp, final boolean override){
        setMessage(player, msg, override);
        setPercent(player, (loadUp ? 0.0F : 100.0F), override);
 
        final float changePerSecond = BarAPI.FakeDragon.MAX_HEALTH / seconds;
        final float changePerTick = changePerSecond / 20;
 
        new BukkitRunnable(){
            @Override
            public void run(){
                FakeDragon dragon = getDragon(player);
 
                if((loadUp ? dragon.getHealth() < BarAPI.FakeDragon.MAX_HEALTH : dragon.getHealth() > 0)){
                    if(loadUp){
                        setHealth(player, getHealth(player) + changePerTick, override);
                    } else {
                        setHealth(player, getHealth(player) - changePerTick, override);
                    }
                } else {
                    setMessage(player, endMsg, override);
                    setPercent(player, 100, override);
                    this.cancel();
 
                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            removeBar(player);
                        }
                    }.runTaskLater(Hub.instance, 40L);
                }
            }
        }.runTaskTimer(Hub.instance, 0L, 1L);
    }
 
    public static void removeBar(Player player){
        if(hasBar(player)){
            FakeDragon dragon = getDragon(player);
 
            dragon.destroy();
            playerDragons.remove(player.getName());
        }
    }
 
    public static boolean hasBar(Player player){
        return playerDragons.containsKey(player.getName());
    }
 
    public static FakeDragon getDragon(Player player){
        if(hasBar(player)){
            return playerDragons.get(player.getName());
        } else {
            FakeDragon dragon = new FakeDragon(player);
 
            dragon.setInvisible(true);
            playerDragons.put(player.getName(), dragon);
 
            return dragon;
        }
    }
}
