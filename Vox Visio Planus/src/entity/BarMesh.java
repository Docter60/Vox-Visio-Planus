/**
 * 
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import core.MainWindow;
import core.VoxVisioPlanus;
import math.Position;
import math.Vector2;

/**
 * @author Docter60
 *
 */
public class BarMesh extends Element{
	
	private Point[] points;
	private MainWindow window;
	
	public BarMesh(MainWindow window, Position position, Vector2 rotation, double scale, int size){
		super(position, rotation, scale);
		points = new Point[size];
		for(int i = 0; i < points.length; i++)
			points[i] = new Point(0, window.getHeight());
		this.window = window;
	}
	
	public void setPoint(int index, Point point){
		points[index] = point;
	}
	
	public Point getPoint(int index){
		return points[index];
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		for(int i = 0; i < points.length; i++){
			if(i + 1 == points.length) return;
			double x1 = points[i].getX();
			double y1 = points[i].getY();
			double x2 = points[i + 1].getX();
			double y2 = points[i + 1].getY();
			
			g.setColor(Color.GREEN);
			g.fillRect((int) x1, (int) y1, (int) (x2 - x1), (int) (window.getHeight() - y1));
			g.setColor(Color.BLACK);
			g.drawRect((int) x1, (int) y1, (int) (x2 - x1), (int) (window.getHeight() - y1));
		}
	}
}
