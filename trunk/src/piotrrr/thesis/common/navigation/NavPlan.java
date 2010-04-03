package piotrrr.thesis.common.navigation;

import soc.qase.ai.waypoint.Waypoint;

/**
 * This class encapsulates the path that the bot has planned to move along.
 * @author Piotr Gwizda�a
 */
public class NavPlan {

	/**
	 * The destination waypoint
	 */
	public Waypoint dest;
	
	/**
	 * Tells whether the plan is done.
	 */
	public boolean done = false;
	
	public long deadline = 0;
	
	public NavPlan parentPlan = null;
	
	/**
	 * The array that represents the path chosen. The first element is the source
	 * the last element is the destination.
	 */
	public Waypoint [] path;
	
	public NavPlan(Waypoint dest, long deadline) {
		this.dest = dest;
		this.deadline = deadline;
	}
	
}
