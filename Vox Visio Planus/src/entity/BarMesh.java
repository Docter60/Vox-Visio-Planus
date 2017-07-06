/**
 * 
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import asset.ColorGradient;
import core.MainWindow;
import math.Mathg;
import math.Position;
import math.Vector2;

/**
 * @author Docter60
 *
 */
public class BarMesh extends Element{
	
	protected Point[] points;
	protected MainWindow window;
	protected ColorGradient cg;
	protected boolean hasBorder;
	
	public BarMesh(MainWindow window, Position position, Vector2 rotation, double scale, int size){
		super(position, rotation, scale);
		points = new Point[size];
		for(int i = 0; i < points.length; i++)
			points[i] = new Point(0, window.getHeight());
		this.window = window;
		this.cg = new ColorGradient();
		this.cg.configureRainbowGradient();
	}
	
	public void setPoint(int index, Point point){
		points[index] = point;
	}
	
	public Point getPoint(int index){
		return points[index];
	}
	
	public void hasBorder(boolean bool){
		this.hasBorder = bool;
	}
	
	public void generateBarMesh(int screenWidth, int screenHeight, float[] data){
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
		g.setColor(Color.WHITE);
		for(int i = 0; i < points.length; i++){
			double x1 = points[i].getX();
			double y1 = points[i].getY();
			
			int x = (int) x1;
			int y = (int) y1;
			int width = (int) (window.getContentPane().getWidth() / points.length);
			int height = (int) (window.getContentPane().getHeight() - y1);
			
			Color c = cg.getColor(i / (float) points.length);
			g.setColor(c);
			g.fillRect(x, y, width, height);
			if(this.hasBorder)
				g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
		}
	}
}
