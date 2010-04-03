package piotrrr.thesis.misc.fsm.needs;


import piotrrr.thesis.bots.botbase.BotBase;
import piotrrr.thesis.misc.entities.EntityWrapper;
import soc.qase.state.Entity;

public class FightingState implements State {
	
	BotBase bot;
	
	public FightingState(BotBase bot) {
		this.bot = bot;
	}

	@Override
	public EntityWrapper []  getDesiredEntities() {
		EntityWrapper []   ret = {
				new EntityWrapper(Entity.CAT_PLAYERS, 1.0),
				new EntityWrapper(Entity.TYPE_HEALTH, 0.4),
				new EntityWrapper(Entity.TYPE_ARMOR, 0.4),
				new EntityWrapper(Entity.CAT_WEAPONS, 0.4),
				new EntityWrapper(Entity.TYPE_AMMO, 0.4),
		};
		return ret;
	}

	@Override
	public State getNextState() {
		float wellness = NeedsFSM.getBotWellness(bot);
		float firepower = NeedsFSM.getBotFirePower(bot);
		if (wellness <= NeedsFSM.BAD_WELLNESS) return new HealingState(bot);
		if (firepower <= NeedsFSM.BAD_FIRE_POWER) return new ArmingState(bot);
		return this;
	}
	
}