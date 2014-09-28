package particles;

import java.util.HashMap;

public class ParticleManager {
	HashMap<String, Effects> activePlayers = new HashMap<>();
	HashMap<String, Class> activeParticle = new HashMap<>();
	
	public void startEffect(String player, Effects effect){
		switch(effect){
		case ENDERSPIRAL: 
			
		case LOVE: 
			
		case THUNDERCLOUD: 
			
		case MAGICSNAKE: 
			
		case FLAMERING: 
		}
	}
	
	public boolean hasParticleActive(String playername){
		return activePlayers.containsKey(playername);
	}
	
	public Class getActiveEffect(String playername){
		return activeParticle.get(playername);
	}
}
