package model.agent.humanAgent.operationalLevel.action.movement;

import model.agent.humanAgent.HumanAgent;
import model.environment.objects.physicalObject.Chair;
import model.environment.position.Position;
import model.environment.position.Vector;
import simulation.simulation.util.Updatable;
import simulation.simulation.util.Utilities;

/**
 * A movement model is responsible for the movement of a passenger.
 * 
 * @author S.A.M. Janssen
 */
public abstract class MovementModel implements Updatable {

	/**
	 * The agent.
	 */
	protected HumanAgent agent;
	/**
	 * The current velocity.
	 */
	protected Vector currentVelocity;
	/**
	 * The desired speed.
	 */
	protected double desiredSpeed;
	/**
	 * Flag to indicate sitting.
	 */
	private boolean isSitting;
	/**
	 * The time that the agent still needs to stop moving. If this is -1, the
	 * time is infinite.
	 */
	private double stopMovingTime;

	/**
	 * Creates a movement model.
	 * 
	 * @param desiredSpeed
	 *            The desired speed.
	 */
	public MovementModel(double desiredSpeed) {
		this.desiredSpeed = desiredSpeed;
	}

	/**
	 * Bounds the speed of a movement to a maximum.
	 * 
	 * @param vector
	 *            The movement.
	 * @return The movement bounded with a maximum speed.
	 */
	protected Vector boundSpeed(Vector vector) {
		if (vector.length() > 1.5 * desiredSpeed)
			return vector.normalize().scalarMultiply(1.5 * desiredSpeed);
		return vector;
	}

	/**
	 * Gets the current speed.
	 * 
	 * @return The current speed.
	 */
	public Vector getCurrentSpeed() {
		if (getStopOrder())
			return new Vector(0, 0);
		return currentVelocity;
	}

	/**
	 * Gets the next move.
	 * 
	 * @param timeStep
	 *            The time step (in milliseconds).
	 * @return The next move.
	 */
	public abstract Vector getMove(int timeStep);

	/**
	 * Gets the position of the agent.
	 * 
	 * @return The position.
	 */
	public Position getPosition() {
		return agent.getPosition();
	}

	/**
	 * Indicates if the agent has a stop order. It will also make sure that the
	 * agent stops if someone in its very close neighborhood has a stop order.
	 * 
	 * @return True if he is ordered to stop, false otherwise.
	 */
	public boolean getStopOrder() {
		return stopMovingTime != 0;
	}

	/**
	 * Sets the agent.
	 * 
	 * @param agent
	 *            The agent.
	 */
	public void init(HumanAgent agent) {
		this.agent = agent;
		currentVelocity = new Vector(0, 0);
	}

	/**
	 * Determines if the agent is sitting.
	 * 
	 * @return True if the agent is sitting, false otherwise.
	 */
	public boolean isSitting() {
		return isSitting;
	}

	/**
	 * Asks the agent to sit down on a specific {@link Chair}. Returns the
	 * {@link Position} of the chair if he will sit down, returns
	 * {@link Position#NO_POSITION} if he will not.
	 * 
	 * @param chair
	 *            The chair.
	 * @return The position of the chair if he will sit down, and no position if
	 *         he will not.
	 */
	public Position setSitDown(Chair chair) {
		if (chair.isOccupied())
			return Position.NO_POSITION;

		if (Utilities.getDistance(agent, chair) < 1) {
			isSitting = true;
			return new Position(chair.getPosition().x + 0.5 * chair.getWidth(),
					chair.getPosition().y + 0.5 * chair.getWidth());
		}
		return Position.NO_POSITION;
	}

	/**
	 * Sets the time that indicates if the time to stop.
	 * 
	 * @param stopOrder
	 *            The stopping time.
	 */
	public void setStopOrder(double stopOrder) {
		this.stopMovingTime = stopOrder;
	}

	@Override
	public void update(int timeStep) {
		if (stopMovingTime > 0)
			stopMovingTime -= timeStep / 1000.0;
		else if (stopMovingTime != -1)
			stopMovingTime = 0;
	}

	/**
	 * Updates the move vector to a 0 vector if the move results in an out of
	 * bound position. TODO this method needs the map, but doesn't get it.
	 * 
	 * @param vector
	 *            The move.
	 * @return The updated vector.
	 */
	protected Vector updateOutOfBounds(Vector vector) {
		// Position newPosition = new Position(agent.getPosition().x + vector.x,
		// agent.getPosition().y + vector.y);
		// if (agent.getMap().isOutOfBounds(newPosition))
		// return new Vector(0, 0);
		return vector;
	}

}
