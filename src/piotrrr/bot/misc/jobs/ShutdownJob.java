package piotrrr.bot.misc.jobs;

import piotrrr.bot.base.BotBase;

/**
 * Used to call some methods, when the application is interrupted by 
 * for instance CTR+C
 * @author Piotr Gwizda�a
 */
public class ShutdownJob extends Job {

	public ShutdownJob(BotBase bot) {
		super(bot);
	}
	
	@Override
	public void run() {
		bot.disconnect();
	}

}
