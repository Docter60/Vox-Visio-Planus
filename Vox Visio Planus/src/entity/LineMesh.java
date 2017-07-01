/**
 * 
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import math.Position;
import math.Vector2;

/**
 * @author Docter60
 *
 */
public class LineMesh extends Element{

	private List<Point> points;

	public LineMesh(Position position, Vector2 rotation, double scale) {
		super(position, rotation, scale);
		points = new ArrayList<Point>();
	}
	
	public void setPoint(int index, Point point){
		points.set(index, point);
	}

	public void addPoint(Point p){
		points.add(p);
	}
	
	public void removeLastPoint(){
		points.remove(points.size() - 1);
	}
	
	public void clearPointList(){
		points.clear();
	}
	
	@Override
	public void draw(Graphics2D g) {
		// TODO doesn't account for transform
		g.setColor(Color.WHITE);
		for(int i = 0; i < points.size(); i++){
			if(i + 1 == points.size()) return;
			double x1 = points.get(i).getX();
			double y1 = points.get(i).getY();
			double x2 = points.get(i + 1).getX();
			double y2 = points.get(i + 1).getY();

			Line2D line = new Line2D.Double(x1, y1, x2, y2);
			g.draw(line);
		}
	}
	
}
