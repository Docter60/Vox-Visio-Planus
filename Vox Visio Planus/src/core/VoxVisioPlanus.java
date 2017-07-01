/**
 * 
 */
package core;

import java.awt.Point;

import entity.LineMesh;
import math.Position;
import math.Vector2;

/**
 * @author Docter60
 *
 */
public class VoxVisioPlanus {

	public static final int POINT_COUNT = 100;
	
	private MainWindow mainWindow;
	private Renderer renderer;
	
	private LineMesh mesh;
	
	public VoxVisioPlanus(){
		mainWindow = new MainWindow("Vox Visio Planus");
		renderer = new Renderer(mainWindow);
		mesh = new LineMesh(new Position(0, 0), new Vector2(0, 0), 1);
		generateLineMesh();
		renderer.addToRenderingQueue(mesh);
		while(true){
			renderer.render();
		}
		//renderer.render();
	}
	
	public void generateLineMesh(){
		for(int i = 0; i < POINT_COUNT; i++){
			int x = i * (mainWindow.getContentPane().getWidth() / POINT_COUNT);
			int y = (int) (Math.random() * mainWindow.getHeight());
			mesh.addPoint(new Point(x, y));
		}
	}
	
	public static void main(String[] args) {
		VoxVisioPlanus voxVisioPlanus = new VoxVisioPlanus();
	}

}
