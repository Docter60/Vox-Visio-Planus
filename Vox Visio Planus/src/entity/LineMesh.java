/**
 * 
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;

import core.MainWindow;
import math.Position;
import math.Vector2;

/**
 * @author Docter60
 *
 */
public class LineMesh extends Element{

	private MainWindow window;
	private Point[] points;

	public LineMesh(MainWindow window, Position position, Vector2 rotation, double scale, int size) {
		super(position, rotation, scale);
		points = new Point[size];
		for(int i = 0; i < points.length; i++)
			points[i] = new Point(0, 0);
	}
	
	public void setPoint(int index, Point point){
		points[index] = point;
	}
	
	public Point getPoint(int index){
		return points[index];
	}
	
	@Override
	public void draw(Graphics2D g) {
		// TODO doesn't account for transform
		g.setColor(Color.WHITE);
		for(int i = 0; i < points.length; i++){
			if(i + 1 == points.length) return;
			double x1 = points[i].getX();
			double y1 = points[i].getY();
			double x2 = points[i + 1].getX();
			double y2 = points[i + 1].getY();

			Line2D line = new Line2D.Double(x1, y1, x2, y2);
			g.draw(line);
			
//			QuadCurve2D q = new QuadCurve2D.Double();
//			q.setCurve(x1, y1, 0.5 * (x1 + x2), 0.5 * (y1 + y2) - 10, x2, y2);
//			g.draw(q);
			
//			Ellipse2D e = new Ellipse2D.Double(360, 270, 270 - y1, 270 - y1);
//			g.draw(e);
		}
	}
	
}
