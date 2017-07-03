/**
 * 
 */
package core;

import java.awt.Point;

import audio.AudioClip;
import audio.AudioPlayer;
import entity.LineMesh;
import math.Position;
import math.Vector2;

/**
 * @author Docter60
 *
 */
public class VoxVisioPlanus {

	public static final int POINT_COUNT = 100;
	
	public static final String LAPTOP_SAMPLE = "C:\\Users\\Docter60\\Music\\iTunes\\iTunes Media\\Music\\Laszlo\\Closer EP\\05 Law of the Jungle.mp3";
	public static final String DESKTOP_SAMPLE = "C:\\Users\\Docte\\Music\\iTunes\\iTunes Media\\Music\\Laszlo\\Closer EP\\05 Law of the Jungle.mp3";
	
	private MainWindow mainWindow;
	private Renderer renderer;
	
	private Thread audioPlayer;
	private LineMesh mesh;
	private AudioPlayer player;
	private AudioClip clip;
	
	public VoxVisioPlanus(){
		mainWindow = new MainWindow("Vox Visio Planus");
		renderer = new Renderer(mainWindow);
		mesh = new LineMesh(new Position(0, 0), new Vector2(0, 0), 1);
		generateLineMesh();

		
		//creating a thread for the media player
		clip = new AudioClip(DESKTOP_SAMPLE);
		player = new AudioPlayer(clip);
		audioPlayer = new Thread(player);
		audioPlayer.start();
		player.setVolume(0.5f);
		
		
		
		while(true){
			renderer.addToRenderingQueue(mesh);
			generateLineMesh();
			renderer.render();
			mesh.clearPointList();
		}
	}
	
	public void generateLineMesh(){
		for(int i = 0; i < POINT_COUNT; i++){
			int x = (int) (i * ((float) mainWindow.getContentPane().getWidth() / (float) POINT_COUNT));
			int y = (int) (Math.random() * mainWindow.getContentPane().getHeight());
			mesh.addPoint(new Point(x, y));
		}
	}
	
	public static void main(String[] args) {
		VoxVisioPlanus voxVisioPlanus = new VoxVisioPlanus();
	}

}
