/**
 * 
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import core.MainWindow;
import math.Mathg;
import math.Position;
import math.Vector2;

/**
 * @author Docter60
 *
 */
public class LineMesh extends Element{

	private Point[] points;

	public LineMesh(MainWindow window, Position position, Vector2 rotation, double scale, int size) {
		super(position, rotation, scale);
		points = new Point[size];
		for(int i = 0; i < points.length; i++)
			points[i] = new Point(0, window.getContentPane().getHeight());
	}
	
	public void setPoint(int index, Point point){
		points[index] = point;
	}
	
	public Point getPoint(int index){
		return points[index];
	}
	
	public void generateLineMesh(int screenWidth, int screenHeight, float[] data){
		for(int i = 0; i < data.length; i++){
			int x = (int) (i * ((float) screenWidth / (float) data.length));
			double y = screenHeight - data[i] * 40;
			Point oldPoint = getPoint(i);
			int lerpY = (int) Mathg.lerp(oldPoint.getY(), y, 0.08);
			setPoint(i, new Point(x, lerpY));
		}
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

			Color c = new Color(255 - i, 128 + i, 128 - i);
			g.setColor(c);
			Line2D line = new Line2D.Double(x1, y1, x2, y2);
			g.draw(line);
		}
	}
	
}
