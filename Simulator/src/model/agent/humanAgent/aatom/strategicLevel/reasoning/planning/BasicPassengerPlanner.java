package model.agent.humanAgent.aatom.strategicLevel.reasoning.planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.agent.humanAgent.aatom.tacticalLevel.activity.Activity;
import model.agent.humanAgent.aatom.tacticalLevel.activity.passenger.CheckpointActivity;
import model.agent.humanAgent.aatom.tacticalLevel.activity.passenger.FacilityActivity;
import model.agent.humanAgent.aatom.tacticalLevel.activity.passenger.GateActivity;
import model.agent.humanAgent.aatom.tacticalLevel.activity.passenger.PassengerBorderControlActivity;
import model.agent.humanAgent.aatom.tacticalLevel.activity.passenger.PassengerCheckInActivity;
import model.environment.objects.area.BorderControlGateArea;
import model.environment.objects.flight.Flight;

/**
 * A basic passenger activity planner.
 * 
 * @author S.A.M. Janssen
 */
public class BasicPassengerPlanner extends PlanningModule {

	/**
	 * The flight.
	 */
	private Flight flight;
	/**
	 * Checked in or not.
	 */
	private boolean checkedIn;

	/**
	 * Creates a basic passenger planner.
	 * 
	 * @param flight
	 *            The flight.
	 * @param checkedIn
	 *            Checked in or not.
	 */
	public BasicPassengerPlanner(Flight flight, boolean checkedIn) {
		this.flight = flight;
		this.checkedIn = checkedIn;
	}

	/**
	 * @param activityType
	 *            The activity type.
	 * @return The specific activity.
	 */
	private Activity getActivityFromType(Class<? extends Activity> activityType) {
		for (Activity a : goalModule.getGoalActivities()) {
			if (activityType.isInstance(a))
				return a;
		}
		return null;
	}

	@Override
	public Activity getNextActivity() {
		if (planning.isEmpty())
			return null;
		return planning.get(0);
	}

	@Override
	public List<Activity> getPlanning() {
		if (planning != null)
			return planning;

		planning = new ArrayList<>();

		// check-in
		if (!checkedIn)
			planning.add(getActivityFromType(PassengerCheckInActivity.class));

		// checkpoint
		planning.add(getActivityFromType(CheckpointActivity.class));

		if (flight.getGateArea() instanceof BorderControlGateArea)
			planning.add(getActivityFromType(PassengerBorderControlActivity.class));

		// TODO randomize location and check feasibility
		planning.add(getActivityFromType(FacilityActivity.class));

		// gate
		planning.add(getActivityFromType(GateActivity.class));

		// remove all nulls
		planning.removeAll(Collections.singleton(null));

		return planning;
	}

	@Override
	public void update(int timeStep) {
		// remove completed activities.
		while (!planning.isEmpty() && planning.get(0).isFinished()) {
			planning.remove(0);
		}

		// remove a facility activity if only 30 minutes until flight.
		if (!planning.isEmpty()) {
			if (flight.getTimeToFlight() < 1800
					&& planning.get(0).equals(getActivityFromType(FacilityActivity.class))) {
				planning.remove(0);
			}
		}
	}
}
