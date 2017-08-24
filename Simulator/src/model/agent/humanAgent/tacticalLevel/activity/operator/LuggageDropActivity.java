package model.agent.humanAgent.tacticalLevel.activity.operator;

import java.util.ArrayList;
import java.util.List;

import model.agent.humanAgent.Passenger;
import model.agent.humanAgent.operationalLevel.action.communication.CommunicationType;
import model.agent.humanAgent.tacticalLevel.activity.Activity;
import model.environment.objects.physicalObject.sensor.XRaySystem;
import simulation.simulation.util.Utilities;

/**
 * The luggage drop activity.
 * 
 * @author S.A.M. Janssen
 */
public class LuggageDropActivity extends Activity {

	/**
	 * The time mean service rate.
	 */
	private double meanServiceRate;
	/**
	 * The system.
	 */
	private XRaySystem system;
	/**
	 * Passengers that are already instructed.
	 */
	private List<Passenger> alreadyInstructed;

	/**
	 * Creates a luggage drop activity.
	 * 
	 * @param system
	 *            The system.
	 */
	public LuggageDropActivity(XRaySystem system) {
		this(system, 60);
	}

	/**
	 * Creates a luggage drop activity.
	 * 
	 * @param system
	 *            The system.
	 * @param meanServiceRate
	 *            The mean service rate.
	 */
	public LuggageDropActivity(XRaySystem system, double meanServiceRate) {
		this.system = system;
		this.meanServiceRate = meanServiceRate;
		alreadyInstructed = new ArrayList<>();
	}

	@Override
	public boolean canStart(int timeStep) {
		if (system.getDropOffPassenger() != null) {
			if (alreadyInstructed.contains(system.getDropOffPassenger()))
				return false;

			if (system.getDropOffPosition().distanceTo(system.getDropOffPassenger().getPosition()) < 0.5)
				return true;
		}
		return false;
	}

	@Override
	public void startActivity() {
		super.startActivity();
		alreadyInstructed.add(system.getDropOffPassenger());
		system.getDropOffPassenger().communicate(CommunicationType.WAIT,
				Utilities.RANDOM_GENERATOR.nextNormal(meanServiceRate, meanServiceRate / 10));
	}

	@Override
	public void update(int timeStep) {
		endActivity();
	}

}