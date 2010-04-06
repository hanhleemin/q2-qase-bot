package piotrrr.thesis.bots.simplebot;

import piotrrr.thesis.bots.botbase.BotBase;
import piotrrr.thesis.common.jobs.BasicCommands;
import piotrrr.thesis.common.jobs.DebugTalk;
import piotrrr.thesis.common.jobs.StateReporter;
import piotrrr.thesis.common.navigation.NavPlan;
import soc.qase.ai.waypoint.WaypointMap;

/**
 * This is a simple bot that will be used as a base of other bots as well as 
 * reference in comparative study.
 * @author Piotr Gwizda�a
 */
public class SimpleBot extends BotBase {
	
	/**
	 * The directory where bot's maps are stored. Relative to main directory.
	 */
	static final String MAPS_DIR = ".\\botmaps\\from-demo\\";

	/**
	 * Finite state machine - used to determine bot's needs.
	 */
	NeedsFSM fsm;
	
	/**
	 * The map that bot uses to navigate.
	 */
	WaypointMap map = null;
	
	/**
	 * Bot's Knowledge Base
	 */
	SimpleKB kb = null;
	
	/**
	 * Bot's current navigation plan
	 */
	NavPlan plan = null;

	/**
	 * Is being set to true when the state have been changed by StateReporter.
	 * @see StateReporter
	 */
	boolean stateChanged;

	/**
	 * Basic constructor.
	 * @param botName the name of the bot to be created
	 * @param skinName the name of the skin to be used
	 */
	public SimpleBot(String botName, String skinName) {
		super(botName, skinName);
		fsm = new NeedsFSM(this);
		//TODO:
		addBotJob(new DebugTalk(this, 50));
		addBotJob(new StateReporter(this));
		addBotJob(new BasicCommands(this, "Player"));
	}

	@Override
	protected void botLogic() {
		super.botLogic();
		
		if (map == null) { 
			map = WaypointMap.loadMap(MAPS_DIR+getMapName());
			assert map != null;
			kb = SimpleKB.createKB(map);
			assert kb != null;
			say("KB built. Categorized items: "+kb.getKBSize()+" out of "+map.getItemNodes().size()+".");
		}
		
		kb.updateKB(world.getEntities(false), getFrameNumber());
		
		//Dbg.prn("Item nodes: "+map.getItemNodeWaypoints().length);
		
		/**
		 * This is how we do:
		 * 1. Find where to go - get plan
		 * 2. Get move instructions
		 * 3. Execute movement
		 * 4. Get firing instructions
		 * 5. Execute firing
		 */
		
		fsm.getDesiredEntities();
		
	}
	
	/**
	 * Returns the current name of the state of bot's finite state machine.
	 * @return state name
	 */
	public String getCurrentStateName() {
		String stateName =  fsm.getCurrentStateName();
		return stateName.substring(stateName.lastIndexOf(".")+1);
	}
	
	@Override
	protected void respawn() {
		super.respawn();
		plan = null;
	}

	public boolean isStateChanged() {
		return stateChanged;
	}

	public void setStateChanged(boolean stateChanged) {
		this.stateChanged = stateChanged;
	}

}
