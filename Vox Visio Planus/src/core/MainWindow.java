/**
 * 
 */
package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

/**
 * @author Docter60
 *
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame{

	private GraphicsDevice gd;
	private DisplayMode dm;
	
	private boolean isFullscreen;
	
	public MainWindow(String title){
		super(title);
		
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		dm = gd.getDisplayMode();
		
		isFullscreen = false;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(dm.getWidth() / 2, dm.getHeight() / 2));
		getContentPane().setBackground(Color.BLACK);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		createBufferStrategy(2);
	}
	
	public void setFullscreen(boolean fullscreen){
		if(isFullscreen != fullscreen){
			isFullscreen = fullscreen;
			
			if(!fullscreen){
				gd.setDisplayMode(dm);
				setVisible(false);
				dispose();
				setUndecorated(false);
				gd.setFullScreenWindow(null);
				setSize(WIDTH, HEIGHT);
				setLocationRelativeTo(null);
				setVisible(true);
			}
			else{
				dm = gd.getDisplayMode();
				setVisible(false);
				dispose();
				setUndecorated(true);
				gd.setFullScreenWindow(this);
				setVisible(true);
			}
		}
	}
	
}
