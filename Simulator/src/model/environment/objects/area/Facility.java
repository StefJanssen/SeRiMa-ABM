package model.environment.objects.area;

import java.util.List;

import model.agent.humanAgent.HumanAgent;
import model.environment.position.Position;

/**
 * A restaurant is a {@link Area} where {@link HumanAgent}s can eat something.
 * 
 * @author S.A.M. Janssen
 */
public abstract class Facility extends Area {

	/**
	 * Creates a rectangular facility from a specified (x,y) coordinate, width and
	 * height.
	 * 
	 * @param topX
	 *            The x coordinate.
	 * @param topY
	 *            The y coordinate.
	 * @param width
	 *            The width.
	 * @param height
	 *            The height.
	 */
	public Facility(double topX, double topY, double width, double height) {
		super(topX, topY, width, height);
	}
	
	/**
	 * Creates a restaurant with a set of specified corner points.
	 * 
	 * @param corners
	 *            The corner points.
	 */
	public Facility(List<Position> corners) {
		super(corners);
	}
}