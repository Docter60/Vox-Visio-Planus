/**
 * 
 */
package entity;

import java.awt.Graphics2D;

import math.Position;
import math.Vector2;

/**
 * @author Docter60
 *
 */
public abstract class Element implements Drawable{
	
	protected Position position;
	protected Vector2 rotation;
	protected double scale;
	
	public Element(Position position, Vector2 rotation, double scale){
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public abstract void draw(Graphics2D g);

}
