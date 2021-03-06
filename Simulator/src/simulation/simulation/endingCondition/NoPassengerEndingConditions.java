package simulation.simulation.endingCondition;

import model.agent.humanAgent.aatom.Passenger;

/**
 * The no passengers ending conditions for a simulation ensure that a simulation
 * ends after there are no more passengers.
 * 
 * @author S.A.M. Janssen
 */
public class NoPassengerEndingConditions extends EndingConditions {
	@Override
	public Object[] getReturnValues() {
		return null;
	}

	@Override
	public boolean hasEnded(long numberOfSteps) {
		if (simulator.getMap().getTime() < 10)
			return false;
		return simulator.getMap().getMapComponents(Passenger.class).size() == 0 || super.hasEnded(numberOfSteps);
	}
}
