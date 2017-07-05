/**
 * 
 */
package core;

import javafx.embed.swing.JFXPanel;

import audio.AudioClip;
import audio.AudioPlayer;
import entity.BarMesh;
import entity.BarMesh2;
import entity.LineMesh;
import input.Key;
import input.Keyboard;
import math.Position;
import math.Vector2;

/**
 * @author Docter60
 *
 */
public class VoxVisioPlanus {

	public static final int POINT_COUNT = 128;
	
	public static final String LAPTOP_SAMPLE = "C:\\Users\\Docter60\\Music\\iTunes\\iTunes Media\\Music\\Laszlo\\Closer EP\\05 Law of the Jungle.m4a";
	public static final String LAPTOP_SAMPLE2 = "C:\\Users\\Docter60\\Music\\Dance Music\\305.mp3";
	public static final String DESKTOP_SAMPLE = "C:\\Users\\Docte\\Music\\iTunes\\iTunes Media\\Music\\Laszlo\\Closer EP\\05 Law of the Jungle.mp3";
	
	public static final JFXPanel fxPanel = new JFXPanel();
	
	private MainWindow mainWindow;
	private Renderer renderer;
	
	private LineMesh lineMesh;
	private BarMesh barMesh;
	private BarMesh2 barMesh2;
	private AudioPlayer player;
	private AudioClip clip;
	
	public VoxVisioPlanus(){
		mainWindow = new MainWindow("Vox Visio Planus");
		mainWindow.addKeyListener(new Keyboard());
		renderer = new Renderer(mainWindow);
		lineMesh = new LineMesh(mainWindow, new Position(0, 0), new Vector2(0, 0), 1, POINT_COUNT);
		barMesh = new BarMesh(mainWindow, new Position(0, 0), new Vector2(0, 0), 1, POINT_COUNT);
		barMesh2 = new BarMesh2(mainWindow, new Position(0, 0), new Vector2(0, 0), 1, POINT_COUNT);

		
		//creating a thread for the media player
		clip = new AudioClip(DESKTOP_SAMPLE);
		player = new AudioPlayer();
		player.attachAudioClip(clip);
		
		while(true){
			renderer.addToRenderingQueue(lineMesh);
			lineMesh.generateLineMesh(mainWindow.getWidth(), mainWindow.getHeight(), player.getSpectrumData());
			
//			renderer.addToRenderingQueue(barMesh);
//			barMesh.generateBarMesh(mainWindow.getWidth(), mainWindow.getHeight(), player.getSpectrumData());
			
			renderer.addToRenderingQueue(barMesh2);
			barMesh2.generateBarMesh(mainWindow.getWidth(), mainWindow.getHeight(), player.getSpectrumData());
			
			renderer.render();
			
			if(Keyboard.keyIsPulsed(Key.K)) player.pause();
			
			if(Keyboard.keyIsPulsed(Key.L)) player.play();
			
			if(Keyboard.keyIsPulsed(Key.ESC)) System.exit(0);
			
			Keyboard.clearkeyPulse();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public AudioPlayer getAudioPlayer(){
		return player;
	}
	
	public static void main(String[] args) {
		VoxVisioPlanus voxVisioPlanus = new VoxVisioPlanus();
	}

}
