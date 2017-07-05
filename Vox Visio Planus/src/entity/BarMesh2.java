/**
 * 
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import core.MainWindow;
import math.Mathg;
import math.Position;
import math.Vector2;

/**
 * @author Docter60
 *
 */
public class BarMesh2 extends BarMesh{

	/**
	 * @param window
	 * @param position
	 * @param rotation
	 * @param scale
	 * @param size
	 */
	public BarMesh2(MainWindow window, Position position, Vector2 rotation, double scale, int size) {
		super(window, position, rotation, scale, size);
		for(int i = 0; i < points.length; i++)
			points[i] = new Point(0, window.getHeight() / 2);
	}
	
	@Override
	public void generateBarMesh(int screenWidth, int screenHeight, float[] data){
		for(int i = 0; i < data.length; i++){
			int x = (int) (i * ((float) screenWidth / (float) data.length));
			double y = (screenHeight - data[i] * 30.0) / 2.0;
			Point oldPoint = getPoint(i);
			int lerpY = (int) Mathg.lerp(oldPoint.getY(), y, 0.08);
			setPoint(i, new Point(x, lerpY));
		}
	}

	@Override
	public void draw(Graphics2D g){
		g.setColor(Color.WHITE);
		for(int i = 0; i < points.length; i++){
			double x1 = points[i].getX();
			double y1 = points[i].getY();
			
			int x = (int) x1;
			int y = (int) y1;
			int width = (int) (window.getContentPane().getWidth() / points.length);
			int height = 2 * (int) (window.getContentPane().getHeight() / 2.0 - y1);
			
			Color c = new Color(255 - i, 128 + i, 128 - i);
			g.setColor(c);
			g.fillRect(x, y, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
		}
	}
	
}
