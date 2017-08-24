package model.agent.humanAgent.tacticalLevel.activity.operator;

import model.agent.humanAgent.Passenger;
import model.agent.humanAgent.operationalLevel.action.communication.CommunicationType;
import model.agent.humanAgent.tacticalLevel.activity.Activity;
import model.environment.objects.physicalObject.sensor.Observation;
import model.environment.objects.physicalObject.sensor.WalkThroughMetalDetector;

/**
 * The ETD check activity.
 * 
 * @author S.A.M. Janssen
 */
public class ETDCheckActivity extends Activity {

	/**
	 * The WTMD.
	 */
	private WalkThroughMetalDetector wtmd;

	/**
	 * The passenger to check.
	 */
	private Passenger passengerToCheck;
	/**
	 * The passenger has waited.
	 */
	private boolean waited;
	/**
	 * The goto order has been given.
	 */
	private boolean goTo;
	/**
	 * The next passenger in line.
	 */
	private Passenger nextPassenger;
	/**
	 * The passenger waiting time.
	 */
	private double passengerWaitingTime;

	/**
	 * Creates an ETD check activity.
	 * 
	 * @param wtmd
	 *            The WTMD.
	 */
	public ETDCheckActivity(WalkThroughMetalDetector wtmd) {
		this(wtmd, 10);
	}

	/**
	 * Creates an ETD check activity.
	 * 
	 * @param wtmd
	 *            The WTMD.
	 * @param passengerWaitingTime
	 *            The passenger waiting time.
	 */
	public ETDCheckActivity(WalkThroughMetalDetector wtmd, double passengerWaitingTime) {
		this.wtmd = wtmd;
		this.passengerWaitingTime = passengerWaitingTime;
	}

	@Override
	public boolean canStart(int timeStep) {
		if (isInProgress())
			return false;
		passengerToCheck = checkObservation();
		return passengerToCheck != null;

	}

	/**
	 * Checks the observation.
	 * 
	 * @return The passenger to check.
	 */
	public Passenger checkObservation() {
		Observation<?> observation = wtmd.getObservation();
		if (!observation.equals(Observation.NO_OBSERVATION)) {
			if ((int) observation.getObservation() == 2) {
				return wtmd.getLastObservedPassenger();
			}
		}
		return null;

	}

	@Override
	public void startActivity() {
		if (wtmd.getPassengerToCheck() != null)
			passengerToCheck.communicate(CommunicationType.WAIT, -1);
		super.startActivity();
	}

	@Override
	public void update(int timeStep) {
		if (nextPassenger == null) {
			nextPassenger = checkObservation();
			if (nextPassenger != null)
				nextPassenger.communicate(CommunicationType.WAIT, -1);
		}

		// passenger is at checking position
		if (passengerToCheck.getPosition().distanceTo(wtmd.getCheckPosition()) < 0.5) {
			// set waiting order
			if (!waited) {
				passengerToCheck.communicate(CommunicationType.WAIT, passengerWaitingTime);
				waited = true;
			}
			// check when done
			else if (!passengerToCheck.getStopOrder()) {
				wtmd.setPassengerToCheck(null);
				passengerToCheck = nextPassenger;
				nextPassenger = null;
				waited = false;
				goTo = false;
				if (passengerToCheck == null) {
					endActivity();
				}
				return;
			}
		}
		// passenger is not at checking position
		else if (!goTo) {
			passengerToCheck.communicate(CommunicationType.WAIT, 0);
			passengerToCheck.communicate(CommunicationType.GOTO, wtmd.getCheckPosition());
			goTo = true;
		}
	}

}
