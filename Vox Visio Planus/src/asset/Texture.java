/**
 * 
 */
package asset;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Docter60
 *
 */
public class Texture {

	private BufferedImage image;
	private Color tint;
	private int width;
	private int height;

	public Texture(String file){
		try {
			this.image = ImageIO.read(new File(file));
			this.width = image.getWidth();
			this.height = image.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load file from: " + file);
		}
		this.tint = Color.WHITE;
	}

	public Color getTint() {
		return tint;
	}

	public void setTint(Color tint) {
		this.tint = tint;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
