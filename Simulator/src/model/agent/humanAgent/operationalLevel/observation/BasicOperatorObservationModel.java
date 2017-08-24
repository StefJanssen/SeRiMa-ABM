package model.agent.humanAgent.operationalLevel.observation;

import java.util.Collection;

import model.agent.humanAgent.HumanAgent;
import model.agent.humanAgent.Passenger;
import model.environment.map.Map;
import model.environment.objects.area.Area;
import model.environment.objects.physicalObject.PhysicalObject;
import simulation.simulation.util.Utilities;

/**
 * A basic observation model for an operator movementModel.
 * 
 * @author S.A.M. Janssen
 */
public class BasicOperatorObservationModel extends ObservationModule {

	/**
	 * Creates an observation model.
	 * 
	 * @param map
	 *            The map.
	 */
	public BasicOperatorObservationModel(Map map) {
		super(map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> getObservation(Class<T> type) {
		if (type.equals(HumanAgent.class)) {
			// TODO PARAMETERS
			Collection<HumanAgent> movementModelsInNeighborhood = Utilities.getMapComponentsInNeighborhood(
					movementModel.getPosition(), 1, map.getMapComponents(HumanAgent.class));
			return (Collection<T>) movementModelsInNeighborhood;
		} else if (type.equals(PhysicalObject.class)) {
			Collection<PhysicalObject> physicalObjects = Utilities.getMapComponentsInNeighborhood(
					movementModel.getPosition(), 1, map.getMapComponents(PhysicalObject.class));
			return (Collection<T>) physicalObjects;
		} else if (type.equals(Passenger.class)) {
			Collection<Passenger> passengers = Utilities.getMapComponentsInNeighborhood(movementModel.getPosition(), 10,
					map.getMapComponents(Passenger.class));
			return (Collection<T>) passengers;
		} else if (type.equals(Area.class)) {
			Collection<Area> area = Utilities.getMapComponentsInNeighborhood(movementModel.getPosition(), 0.1,
					map.getMapComponents(Area.class));
			return (Collection<T>) area;
		}
		return null;
	}
}