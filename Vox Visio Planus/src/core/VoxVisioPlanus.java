/**
 * 
 */
package core;

import java.awt.Point;

import javafx.embed.swing.JFXPanel;

import audio.AudioClip;
import audio.AudioPlayer;
import entity.BarMesh;
import entity.LineMesh;
import input.Key;
import input.Keyboard;
import math.Mathg;
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
	
	public static final JFXPanel fxPanel = new JFXPanel();
	
	private MainWindow mainWindow;
	private Renderer renderer;
	
	private LineMesh lineMesh;
	private BarMesh barMesh;
	private AudioPlayer player;
	private AudioClip clip;
	
	public VoxVisioPlanus(){
		mainWindow = new MainWindow("Vox Visio Planus");
		mainWindow.addKeyListener(new Keyboard());
		renderer = new Renderer(mainWindow);
		lineMesh = new LineMesh(mainWindow, new Position(0, 0), new Vector2(0, 0), 1, POINT_COUNT);
		barMesh = new BarMesh(mainWindow, new Position(0, 0), new Vector2(0, 0), 1, POINT_COUNT);

		
		//creating a thread for the media player
		clip = new AudioClip(DESKTOP_SAMPLE);
		player = new AudioPlayer();
		player.attachAudioClip(clip);
		
		
		while(true){
			//renderer.addToRenderingQueue(lineMesh);
			renderer.addToRenderingQueue(barMesh);
			//generateLineMesh();
			generateBarMesh();
			renderer.render();
			
			if(Keyboard.keyIsPulsed(Key.K)) player.pause();
			
			if(Keyboard.keyIsPulsed(Key.L)) player.play();
			
			if(Keyboard.keyIsPulsed(Key.ESC)) System.exit(0);
			
			Keyboard.clearkeyPulse();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void generateLineMesh(){
		float[] spectrumData = player.getAudioSpectrum().getSpectrumData();
		for(int i = 0; i < POINT_COUNT; i++){
			int x = (int) (i * ((float) mainWindow.getContentPane().getWidth() / (float) POINT_COUNT));
			double y = (mainWindow.getHeight()) - ((spectrumData[i] + 70f) * 20f );
			
			Point oldPoint = lineMesh.getPoint(i);
			//System.out.println(oldPoint);
			
			int lerpY = (int) Mathg.lerp(oldPoint.getY(), y, 0.075);
			
			lineMesh.setPoint(i, new Point(x, lerpY));
		}
	}
	
	public void generateBarMesh(){
		float[] spectrumData = player.getAudioSpectrum().getSpectrumData();
		for(int i = 0; i < POINT_COUNT; i++){
			int x = (int) (i * ((float) mainWindow.getContentPane().getWidth() / (float) POINT_COUNT));
			double y = (mainWindow.getHeight()) - ((spectrumData[i] + 70f) * 20f );
			
			Point oldPoint = barMesh.getPoint(i);
			//System.out.println(oldPoint);
			
			int lerpY = (int) Mathg.lerp(oldPoint.getY(), y, 0.075);
			
			barMesh.setPoint(i, new Point(x, lerpY));
		}
	}
	
	public AudioPlayer getAudioPlayer(){
		return player;
	}
	
	public static void main(String[] args) {
		VoxVisioPlanus voxVisioPlanus = new VoxVisioPlanus();
	}

}
