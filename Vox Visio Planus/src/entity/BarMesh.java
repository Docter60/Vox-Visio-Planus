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
			
			int x = (int) x1;
			int y = (int) y1 - 10;
			int width = (int) (x2 - x1);
			int height = (int) (window.getContentPane().getHeight() - y1);
			
			Color c = new Color(255 - i, 128 + i, 128 - i);
			g.setColor(c);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
		}
	}
}
