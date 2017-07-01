/**
 * 
 */
package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import entity.Drawable;

/**
 * @author Docter60
 *
 */
public class Renderer {

	private MainWindow mainWindow;
	private BufferStrategy bs;
	private List<Drawable> drawables;
	
	public Renderer(MainWindow mainWindow){
		this.mainWindow = mainWindow;
		this.bs = mainWindow.getBufferStrategy();
		this.drawables = new ArrayList<Drawable>();
	}
	
	public void addToRenderingQueue(Drawable drawable){
		drawables.add(drawable);
	}
	
	public void render(){
		do{
			Graphics2D g = (Graphics2D) bs.getDrawGraphics();
			g.translate(mainWindow.getInsets().left, mainWindow.getInsets().top);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, mainWindow.getContentPane().getWidth(), mainWindow.getContentPane().getHeight());
			try{
				for(Drawable drawable : drawables)
					drawable.draw(g);
			}finally{
				g.dispose();
			}
			bs.show();
			clearRenderingQueue();
		}while(bs.contentsLost());
	}
	
	public void clearRenderingQueue(){
		drawables.clear();
	}
	
}
