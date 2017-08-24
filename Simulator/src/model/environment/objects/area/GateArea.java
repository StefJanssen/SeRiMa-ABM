package model.environment.objects.area;

import java.util.List;

import model.environment.position.Position;

/**
 * 
 * @author S.A.M. Janssen
 */
public class GateArea extends Area {

	/**
	 * Creates a rectangular gate area from a specified (x,y) coordinate, width
	 * and height.
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
	public GateArea(double topX, double topY, double width, double height) {
		super(topX, topY, width, height);
	}

	/**
	 * Creates the area with a set of specified corner points.
	 * 
	 * @param corners
	 *            The corner points.
	 */
	public GateArea(List<Position> corners) {
		super(corners);
	}
}